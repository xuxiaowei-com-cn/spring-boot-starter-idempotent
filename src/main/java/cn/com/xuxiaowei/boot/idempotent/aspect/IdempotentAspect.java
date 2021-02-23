package cn.com.xuxiaowei.boot.idempotent.aspect;

import cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

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

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
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

        String key = idempotent.key() + ":" + tokenValue;

        // 获取 Redis 中缓存的值
        String redisValue = stringRedisTemplate.opsForValue().get(key);
        if (redisValue == null) {
            // Redis 中无值

            // 执行方法
            Object proceed = joinPoint.proceed();
            // 将结果放入 Redis 中，添加过期时间
            stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(proceed), idempotent.expireTime(), idempotent.timeUnit());
            // 返回执行结果
            return proceed;
        } else {
            // 返回 Redis 中的结果
            return JSON.parseObject(redisValue);
        }
    }

}
