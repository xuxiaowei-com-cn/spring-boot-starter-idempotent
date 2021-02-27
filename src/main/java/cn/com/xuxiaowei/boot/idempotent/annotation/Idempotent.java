package cn.com.xuxiaowei.boot.idempotent.annotation;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 幂等注解
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 是否启用严格模式
     * <p>
     * 默认：不启动
     * <p>
     * 严格模式：未匹配到 Token 直接报错，请开发者使用 {@link ControllerAdvice 拦截异常}
     * <p>
     * 非严格模式：未匹配到 Token 跳过幂等
     *
     * @return 是否启用严格模式
     */
    boolean strict() default false;

    /**
     * 幂等 Token 在 header 中的名字
     * <p>
     * 第一优先级
     *
     * @return 返回幂等Token在header中的名字
     */
    String header() default "";

    /**
     * 幂等 Token 在 参数 中的名字
     * <p>
     * 第二优先级（当{@link #header}为空时）
     *
     * @return 返回幂等Token在参数中的名字
     */
    String param() default "";

    /**
     * 幂等放入Redis中的键值
     *
     * @return 幂等放入Redis中的键值
     */
    String key();

    /**
     * 有效期时间
     * <p>
     * 默认：10
     * <p>
     * 单位：{@link #timeUnit()}
     *
     * @return 有效期时间
     */
    int expireTime() default 10;

    /**
     * 有效期时间单位
     * <p>
     * 默认：s
     *
     * @return {@link TimeUnit}
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
