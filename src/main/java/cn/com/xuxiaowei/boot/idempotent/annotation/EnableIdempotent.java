package cn.com.xuxiaowei.boot.idempotent.annotation;

import cn.com.xuxiaowei.boot.idempotent.aspect.IdempotentAspect;
import cn.com.xuxiaowei.boot.idempotent.configuration.FilterConfiguration;
import cn.com.xuxiaowei.boot.idempotent.configuration.SerializerDeserializerConfiguration;
import cn.com.xuxiaowei.boot.idempotent.controller.TestIdempotentRestController;
import cn.com.xuxiaowei.boot.idempotent.properties.IdempotentProperties;
import org.springframework.context.annotation.Import;

/**
 * 开启幂等
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Import({IdempotentAspect.class, FilterConfiguration.class, SerializerDeserializerConfiguration.class,
        TestIdempotentRestController.class, IdempotentProperties.class})
public @interface EnableIdempotent {

}
