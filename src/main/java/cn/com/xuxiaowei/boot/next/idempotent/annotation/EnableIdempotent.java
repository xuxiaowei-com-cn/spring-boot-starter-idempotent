package cn.com.xuxiaowei.boot.next.idempotent.annotation;

/*-
 * #%L
 * spring-boot-starter-idempotent
 * %%
 * Copyright (C) 2021 - 2022 徐晓伟工作室
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import cn.com.xuxiaowei.boot.next.idempotent.aspect.IdempotentAspect;
import cn.com.xuxiaowei.boot.next.idempotent.configuration.FilterConfiguration;
import cn.com.xuxiaowei.boot.next.idempotent.configuration.SerializerDeserializerConfiguration;
import cn.com.xuxiaowei.boot.next.idempotent.controller.TestIdempotentRestController;
import cn.com.xuxiaowei.boot.next.idempotent.properties.IdempotentProperties;
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
