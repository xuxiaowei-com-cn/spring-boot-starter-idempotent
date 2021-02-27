package cn.com.xuxiaowei.boot.idempotent.aspect;

import cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent;
import cn.com.xuxiaowei.boot.idempotent.context.IdempotentContext;
import cn.com.xuxiaowei.boot.idempotent.context.IdempotentContextHolder;
import cn.com.xuxiaowei.boot.idempotent.context.StatusEnum;
import cn.com.xuxiaowei.boot.idempotent.properties.IdempotentProperties;
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
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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

    /**
     * 用于序列化和反序列化数据
     */
    private ObjectMapper objectMapper;

    private StringRedisTemplate stringRedisTemplate;

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
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;

        if (servletRequestAttributes == null) {
            // 未获取到请求

            log.error("幂等切面前置通知未获取到 ServletRequestAttributes，幂等切面失效");

            // 执行方法并返回结果
            return joinPoint.proceed();
        } else {

            // 获取 Http 请求
            HttpServletRequest request = servletRequestAttributes.getRequest();

            // 获取幂等注解
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Idempotent idempotent = method.getAnnotation(Idempotent.class);

            // 获取幂等在请求头中的TokenName
            String header = idempotent.header();
            // 获取幂等在请求参数中的TokenName
            String param = idempotent.param();

            if (StringUtils.hasText(header) || StringUtils.hasText(param)) {
                // 存在请求头或参数中的TokenName

                // 获取请求头中的TokenValue
                String headerValue = request.getHeader(header);
                // 获取请求参数中的TokenValue
                String paramValue = request.getParameter(param);

                if (StringUtils.hasText(headerValue)) {
                    // 存在请求头中的TokenValue
                    // 根据请求头中的TokenValue获取Redis中缓存的结果
                    return getProceed(idempotent, joinPoint, headerValue);
                } else if (StringUtils.hasText(paramValue)) {
                    // 根据参数中的TokenValue获取Redis中缓存的结果
                    return getProceed(idempotent, joinPoint, paramValue);
                } else {
                    log.error("幂等从请求中未获取到Token，幂等失效");

                    // 执行方法并返回结果
                    return joinPoint.proceed();
                }
            } else {
                // 不存在请求头或参数中的TokenName

                log.error("幂等注解 header、param 均为空，幂等失效");

                // 执行方法并返回结果
                return joinPoint.proceed();
            }

        }

    }

    /**
     * 获取 Redis 中的缓存结果
     *
     * @param idempotent 幂等
     * @param joinPoint  切面方法信息
     * @param tokenValue TokenValue
     * @return 返回 Redis 中的缓存结果
     * @throws Throwable 执行异常
     */
    private Object getProceed(Idempotent idempotent, ProceedingJoinPoint joinPoint, String tokenValue) throws Throwable {
        String prefix = idempotentProperties.getPrefix();
        String key = idempotent.key();
        int expireTime = idempotent.expireTime();
        TimeUnit timeUnit = idempotent.timeUnit();
        String redisKey = prefix + ":" + key + ":" + tokenValue;

        // 是否存在
        Boolean hasKey = stringRedisTemplate.hasKey(redisKey);

        // 获取 key 的过期时间
        Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.SECONDS);

        // 获取 Redis 中缓存的值
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        if (redisValue == null) {
            // Redis 中无值

            LocalDateTime requestDate = LocalDateTime.now();
            long seconds = TimeoutUtils.toSeconds(expireTime, timeUnit);
            LocalDateTime expireDate = requestDate.plusSeconds(seconds);

            // 幂等调用记录
            IdempotentContext idempotentContext = new IdempotentContext()
                    // 设置Token
                    .setToken(tokenValue)
                    // 设置调用状态
                    .setStatus(StatusEnum.NORMAL)
                    // 设置请求时间
                    .setRequestDate(requestDate)
                    // 过期时间
                    .setExpireDate(expireDate);

            // 执行方法
            Object proceed = joinPoint.proceed();

            // 调用结果转 String
            String value = objectMapper.writeValueAsString(proceed);

            // 将结果放入 Redis 中，添加过期时间
            stringRedisTemplate.opsForValue().set(redisKey, value, idempotent.expireTime(), idempotent.timeUnit());

            // 设置响应时间
            idempotentContext.setResultDate(LocalDateTime.now());
            // 设置调用次数
            idempotentContext.setNumber(1);

            // 幂等调用记录放入Redis
            IdempotentContextHolder.setRedis(stringRedisTemplate, idempotentProperties, tokenValue, idempotentContext,
                    idempotent, objectMapper);
            // 清空幂等调用记录线程
            IdempotentContextHolder.clearContext();

            // 返回执行结果
            return proceed;
        } else {

            // 将请求放入Redis中
            IdempotentContextHolder.repeat(stringRedisTemplate, idempotentProperties, tokenValue, idempotent, objectMapper);
            // 清空幂等调用记录线程
            IdempotentContextHolder.clearContext();

            // 返回 Redis 中的结果
            return objectMapper.readValue(redisValue, Object.class);
        }
    }

}
