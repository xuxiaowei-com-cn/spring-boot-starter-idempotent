package cn.com.xuxiaowei.boot.next.idempotent.configuration;

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

import cn.com.xuxiaowei.boot.next.idempotent.filter.HttpServletRequestInputStreamFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class FilterConfiguration {

	/**
	 * 请求流转换为多次读取的请求流 过滤器
	 * <p>
	 * 使用配置文件进行条件注解为 {@link Bean}
	 * @return 返回 请求流转换为多次读取的请求流 过滤器
	 * @see ConditionalOnExpression Spring EL 表达式成立时条件才成立
	 */
	@Bean
	@ConditionalOnExpression("${xxw.idempotent.input-stream-filter:false}")
	public HttpServletRequestInputStreamFilter httpServletRequestInputStreamFilter() {
		return new HttpServletRequestInputStreamFilter();
	}

}
