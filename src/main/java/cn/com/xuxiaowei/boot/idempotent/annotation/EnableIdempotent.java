package cn.com.xuxiaowei.boot.idempotent.annotation;

import cn.com.xuxiaowei.boot.idempotent.aspect.IdempotentAspect;
import cn.com.xuxiaowei.boot.idempotent.configuration.FilterConfiguration;
import cn.com.xuxiaowei.boot.idempotent.configuration.SerializerDeserializerConfiguration;
import cn.com.xuxiaowei.boot.idempotent.controller.TestIdempotentRestController;
import cn.com.xuxiaowei.boot.idempotent.properties.IdempotentProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启幂等
 *
 * @author xuxiaowei
 * @see IdempotentAspect 幂等切面
 * @see FilterConfiguration 过滤器配置
 * @see SerializerDeserializerConfiguration 序列化与反序列化 配置
 * @see TestIdempotentRestController 测试幂等
 * @see IdempotentProperties 幂等 配置
 * @since 0.0.1
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ IdempotentAspect.class, FilterConfiguration.class, SerializerDeserializerConfiguration.class,
		TestIdempotentRestController.class, IdempotentProperties.class })
public @interface EnableIdempotent {

}
