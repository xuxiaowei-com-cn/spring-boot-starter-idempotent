package cn.com.xuxiaowei.boot.idempotent.aspect;

import cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;

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
     * 切点
     */
    @Pointcut("@annotation(cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent)")
    public void pointcut() {

    }

    /**
     * 前置通知
     *
     * @param joinPoint 切面方法信息
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;

        if (servletRequestAttributes == null) {
            log.error("幂等切面前置通知未获取到 ServletRequestAttributes，幂等切面失效");
            return joinPoint.proceed();
        } else {
            HttpServletRequest request = servletRequestAttributes.getRequest();

            // 获取幂等注解
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Idempotent idempotent = method.getAnnotation(Idempotent.class);

            String header = idempotent.header();
            String param = idempotent.param();

            if (StringUtils.hasText(header) || StringUtils.hasText(param)) {
                String headerValue = request.getHeader(header);
                String paramValue = request.getParameter(param);
                if (StringUtils.hasText(headerValue)) {

                    log.info(headerValue);

                    return new HashMap<>();
                } else if (StringUtils.hasText(paramValue)) {

                    log.info(paramValue);

                    return new HashMap<>();
                } else {
                    log.error("幂等从请求中未获取到Token，幂等失效");
                    return joinPoint.proceed();
                }
            } else {
                log.error("幂等注解 header、param 均为空，幂等失效");
                return joinPoint.proceed();
            }

        }

    }

}
