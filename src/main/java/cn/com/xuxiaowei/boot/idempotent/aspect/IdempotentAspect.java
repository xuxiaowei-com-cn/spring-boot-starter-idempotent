package cn.com.xuxiaowei.boot.idempotent.aspect;

import cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent;
import cn.com.xuxiaowei.boot.idempotent.context.IdempotentContext;
import cn.com.xuxiaowei.boot.idempotent.context.IdempotentContextHolder;
import cn.com.xuxiaowei.boot.idempotent.context.StatusEnum;
import cn.com.xuxiaowei.boot.idempotent.exception.NotExistentTokenException;
import cn.com.xuxiaowei.boot.idempotent.exception.NotExistentTokenNameException;
import cn.com.xuxiaowei.boot.idempotent.exception.ServletRequestException;
import cn.com.xuxiaowei.boot.idempotent.properties.IdempotentProperties;
import cn.com.xuxiaowei.boot.idempotent.util.Constants;
import cn.com.xuxiaowei.boot.idempotent.util.DateUtils;
import cn.com.xuxiaowei.boot.idempotent.util.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * 幂等切面
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Aspect
@Component
public class IdempotentAspect {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    /**
     * 用于序列化和反序列化数据
     */
    private ObjectMapper objectMapper;

    /**
     * 用于存取Redis数据
     */
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 幂等配置
     */
    private IdempotentProperties idempotentProperties;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Autowired
    public void setIdempotentProperties(IdempotentProperties idempotentProperties) {
        this.idempotentProperties = idempotentProperties;
    }

    /**
     * 切点
     */
    @Pointcut("@annotation(cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent)")
    public void pointcut() {

    }

    /**
     * 环绕通知
     *
     * @param joinPoint 切面方法信息
     * @return 执行结果
     * @throws Throwable 执行异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 获取请求
        // 在 Spring Cloud 中，需要配置 hystrix.command.default.execution.isolation.strategy=SEMAPHORE
        // 否则 RequestContextHolder.getRequestAttributes() 为空
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;

        if (servletRequestAttributes == null) {
            // 未获取到请求
            throw new ServletRequestException("幂等切面前置通知未获取到 ServletRequestAttributes，幂等切面失效");
        } else {

            // 获取 Http 请求
            HttpServletRequest request = servletRequestAttributes.getRequest();
            // 获取 Http 响应
            HttpServletResponse response = servletRequestAttributes.getResponse();

            // 获取幂等注解
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Idempotent idempotent = method.getAnnotation(Idempotent.class);
            boolean strict = idempotent.strict();

            // 获取幂等在请求头中的TokenName
            String header = idempotent.header();
            // 获取幂等在请求参数中的TokenName
            String param = idempotent.param();
            // 获取幂等在请求流中的TokenName
            String stream = idempotent.stream();

            if (StringUtils.hasText(header) || StringUtils.hasText(param) || StringUtils.hasText(stream)) {
                // 存在请求头或参数中的TokenName

                // 获取请求头中的TokenValue
                String headerValue = request.getHeader(header);
                // 获取请求参数中的TokenValue
                String paramValue = request.getParameter(param);

                if (StringUtils.hasText(headerValue)) {
                    // 根据请求头中的TokenValue获取Redis中缓存的结果
                    return getProceed(response, idempotent, joinPoint, headerValue);
                } else if (StringUtils.hasText(paramValue)) {
                    // 根据参数中的TokenValue获取Redis中缓存的结果
                    return getProceed(response, idempotent, joinPoint, paramValue);
                } else {
                    String streamValue = RequestUtils.getInputStreamNode(request, stream);
                    if (StringUtils.hasText(streamValue)) {
                        // 根据请求流中的TokenValue获取Redis中缓存的结果
                        return getProceed(response, idempotent, joinPoint, streamValue);
                    }

                    // 不存在 Token

                    if (strict) {
                        throw new NotExistentTokenException(String.format("严格模式下，从 %s 中未获取到 Token", method));
                    } else {
                        log.error("非严格模式下，幂等从请求中未获取到Token，幂等失效");
                        // 执行方法并返回结果
                        return joinPoint.proceed();
                    }
                }
            } else {
                // 不存在 Token Name

                log.error("幂等注解 header、param、stream 均为空，幂等失效");

                if (strict) {
                    throw new NotExistentTokenNameException(String.format("严格模式下，从 %s 中未获取到 Token Name", method));
                } else {
                    // 执行方法并返回结果
                    return joinPoint.proceed();
                }
            }

        }

    }

    /**
     * 获取 Redis 中的缓存结果
     *
     * @param response   Http 响应
     * @param idempotent 幂等
     * @param joinPoint  切面方法信息
     * @param tokenValue 用于组成 Redis Key Token Value
     * @return 返回 Redis 中的缓存结果
     * @throws Throwable 执行异常
     */
    private Object getProceed(HttpServletResponse response, Idempotent idempotent, ProceedingJoinPoint joinPoint,
                              String tokenValue) throws Throwable {
        String prefix = idempotentProperties.getPrefix();
        String result = idempotentProperties.getResult();
        String record = idempotentProperties.getRecord();
        String key = idempotent.key();
        int expireTime = idempotent.expireTime();
        TimeUnit expireUnit = idempotent.expireUnit();
        String redisRecordKey = prefix + ":" + record + ":" + key + ":" + tokenValue;
        String redisResultKey = prefix + ":" + result + ":" + key + ":" + tokenValue;

        // 获取 Redis 中 幂等调用记录
        String redisRecordValue = stringRedisTemplate.opsForValue().get(redisRecordKey);

        if (redisRecordValue == null) {
            // Redis 中 无 幂等调用结果

            LocalDateTime requestDate = LocalDateTime.now();
            long seconds = TimeoutUtils.toSeconds(expireTime, expireUnit);
            LocalDateTime expireDate = requestDate.plusSeconds(seconds);

            // 幂等调用记录
            IdempotentContext idempotentContext = new IdempotentContext()
                    // 设置Token
                    .setToken(tokenValue)
                    // 设置请求时间
                    .setRequestDate(requestDate)
                    // 过期时间
                    .setExpireDate(expireDate);

            // 返回执行结果
            return ruture(joinPoint, redisResultKey, idempotent,
                    idempotentContext, redisRecordKey, response);
        } else {

            // 将请求放入Redis中
            IdempotentContext idempotentContext = IdempotentContextHolder.repeat(stringRedisTemplate, idempotent,
                    objectMapper, redisRecordKey);

            // 设置幂等响应 Header
            setHeader(response, idempotentContext);

            // 获取 Redis 中 幂等调用结果
            String redisResultValue = stringRedisTemplate.opsForValue().get(redisResultKey);

            // 返回 Redis 中的结果
            // 异常时空值处理
            return redisResultValue == null ? null : objectMapper.readValue(redisResultValue, Object.class);
        }
    }

    /**
     * {@link Future} 线程 控制 {@link Controller} 在指定时间内一定给响应
     * <p>
     * 终止超时线程：<code>future.cancel(true);</code>
     *
     * @return 返回 执行结果
     */
    private Object ruture(ProceedingJoinPoint joinPoint, String redisResultKey, Idempotent idempotent,
                          IdempotentContext idempotentContext, String redisRecordKey, HttpServletResponse response) {

        // 设置调用次数
        idempotentContext.setNumber(idempotentContext.getNumber() + 1);

        Future<Object> future = EXECUTOR_SERVICE.submit(() -> {

            Object proceed;

            try {
                // 执行方法
                proceed = joinPoint.proceed();

                // 获取 Redis 中 幂等调用结果
                String redisRecordValue = stringRedisTemplate.opsForValue().get(redisRecordKey);

                if (redisRecordValue != null) {
                    // 超时（超时时会放入一个调用超时记录在 Redis 中）

                    // 将幂等调用记录转为对象
                    IdempotentContext idempotentContextRedis = objectMapper.readValue(redisRecordValue, IdempotentContext.class);

                    // 设置调用次数（在这里不需要 +1）
                    idempotentContext.setNumber(idempotentContextRedis.getNumber());
                }

                // 设置调用状态
                idempotentContext.setStatus(StatusEnum.NORMAL);

                // 调用结果转 String
                String value = objectMapper.writeValueAsString(proceed);

                // 将结果放入 Redis 中，添加过期时间
                stringRedisTemplate.opsForValue().set(redisResultKey, value, idempotent.expireTime(), idempotent.expireUnit());

                // 设置响应时间
                idempotentContext.setResultDate(LocalDateTime.now());

                // 幂等调用记录放入Redis
                IdempotentContextHolder.setRedis(stringRedisTemplate, idempotentContext, objectMapper, redisRecordKey);

                // 设置幂等响应 Header
                setHeader(response, idempotentContext);

            } catch (Throwable e) {
                throw new Exception(e);
            }
            return proceed;
        });

        Object proceed;

        try {
            proceed = future.get(idempotent.timeout(), idempotent.timeoutUnit());
        } catch (InterruptedException e) {
            log.error("任务中断", e);

            // 设置调用状态
            idempotentContext.setStatus(StatusEnum.INTERRUPTED);

            proceed = null;
        } catch (ExecutionException e) {
            log.error("执行异常", e);

            // 设置调用状态
            idempotentContext.setStatus(StatusEnum.EXCEPTION);

            proceed = null;
        } catch (TimeoutException e) {
            log.error("超时异常", e);

            // 设置调用状态
            idempotentContext.setStatus(StatusEnum.EXECUTE);

            proceed = null;
        }

        // 幂等调用记录放入Redis
        IdempotentContextHolder.setRedis(stringRedisTemplate, idempotentContext, objectMapper, redisRecordKey);

        // 设置幂等响应 Header
        setHeader(response, idempotentContext);

        return proceed;
    }

    /**
     * 设置幂等响应 Header
     *
     * @param response          Http 响应
     * @param idempotentContext 幂等调用记录
     */
    private void setHeader(HttpServletResponse response, IdempotentContext idempotentContext) {

        String token = idempotentContext.getToken();
        StatusEnum status = idempotentContext.getStatus();
        LocalDateTime tokenDate = idempotentContext.getTokenDate();
        LocalDateTime requestDate = idempotentContext.getRequestDate();
        LocalDateTime resultDate = idempotentContext.getResultDate();
        LocalDateTime expireDate = idempotentContext.getExpireDate();
        Integer number = idempotentContext.getNumber();

        response.setHeader(IdempotentContext.TOKEN, token);
        response.setHeader(IdempotentContext.STATUS, status.toString());

        response.setHeader(IdempotentContext.TOKEN_DATE, tokenDate == null ? "" :
                DateUtils.localDateTimeFormat(tokenDate, Constants.DEFAULT_DATE_TIME_FORMAT));
        response.setHeader(IdempotentContext.REQUEST_DATE, requestDate == null ? "" :
                DateUtils.localDateTimeFormat(requestDate, Constants.DEFAULT_DATE_TIME_FORMAT));
        response.setHeader(IdempotentContext.RESULT_DATE, resultDate == null ? "" :
                DateUtils.localDateTimeFormat(resultDate, Constants.DEFAULT_DATE_TIME_FORMAT));
        response.setHeader(IdempotentContext.EXPIRE_DATE, expireDate == null ? "" :
                DateUtils.localDateTimeFormat(expireDate, Constants.DEFAULT_DATE_TIME_FORMAT));

        response.setHeader(IdempotentContext.NUMBER, number + "");

    }

}
