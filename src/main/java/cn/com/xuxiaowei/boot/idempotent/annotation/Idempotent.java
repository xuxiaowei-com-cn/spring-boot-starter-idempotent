package cn.com.xuxiaowei.boot.idempotent.annotation;

import cn.com.xuxiaowei.boot.idempotent.properties.IdempotentProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
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
@Documented
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
	 * @return 是否启用严格模式
	 */
	boolean strict() default false;

	/**
	 * 幂等 Token 在 header 中的名字
	 * <p>
	 * 第一优先级
	 * @return 返回幂等Token在header中的名字
	 */
	String header() default "";

	/**
	 * 幂等 Token 在 参数 中的名字
	 * <p>
	 * 第二优先级（当 {@link #header} 为空时）
	 * @return 返回幂等Token在参数中的名字
	 */
	String param() default "";

	/**
	 * 幂等 Token 在 请求流 中的名字
	 * <p>
	 * 第三优先级（当 {@link #header}、{@link #param} 都为空时）
	 * <p>
	 * 使用流时，需要启用 {@link IdempotentProperties#setInputStreamFilter(Boolean)}，否则流只能读取一次（在
	 * AOP 中读取之后， {@link Controller} 中无法获取流）
	 * <p>
	 * 请求流需要是JSON类型（Text类型也可以，但是需要能使用 {@link ObjectMapper#readValue(String, Class)} 转换为
	 * JSON）的数据
	 * <p>
	 * 请求流中的 Token Name 使用 . 分割，如： Token Name 为：tokenName，数据结构： <code>
	 * {"tokenName":"a1"}
	 * </code> Token Name 为：data.tokenName，数据结构： <code>
	 * {"data":{"tokenName":"a1"}}
	 * </code>
	 * @return 返回幂等Token在请求流中的名字
	 */
	String stream() default "";

	/**
	 * 幂等放入Redis中的键值
	 * <p>
	 * 每个接口必须不同
	 * @return 幂等放入Redis中的键值
	 */
	String key();

	/**
	 * 有效期时间
	 * <p>
	 * 默认：10
	 * <p>
	 * 单位：{@link #expireUnit()}
	 * @return 有效期时间
	 */
	int expireTime() default 10;

	/**
	 * 有效期时间单位
	 * <p>
	 * 默认：s
	 * @return {@link TimeUnit}
	 */
	TimeUnit expireUnit() default TimeUnit.SECONDS;

	/**
	 * {@link Controller} 执行超时时间
	 * <p>
	 * 默认：2，小于等于 0 代表不启用
	 * <p>
	 * 单位：s
	 * <p>
	 * 超过此时间必有响应
	 * @return 返回 执行超时时间
	 */
	long timeout() default 2;

}
