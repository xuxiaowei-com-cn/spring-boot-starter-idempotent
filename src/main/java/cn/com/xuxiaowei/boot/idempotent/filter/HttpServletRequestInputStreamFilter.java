package cn.com.xuxiaowei.boot.idempotent.filter;

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

import cn.com.xuxiaowei.boot.idempotent.configuration.FilterConfiguration;
import cn.com.xuxiaowei.boot.idempotent.http.InputStreamHttpServletRequestWrapper;
import org.springframework.context.annotation.Bean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求流转换为多次读取的请求流 过滤器
 * <p>
 * 使用配置文件进行条件注解为 {@link Bean}
 *
 * @author xuxiaowei
 * @see FilterConfiguration#httpServletRequestInputStreamFilter()
 * @since 0.0.1
 */
public class HttpServletRequestInputStreamFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		// 将请求流转换为可多次读取的请求流
		ServletRequest inputStreamHttpServletRequestWrapper = new InputStreamHttpServletRequestWrapper(
				httpServletRequest);
		chain.doFilter(inputStreamHttpServletRequestWrapper, response);
	}

}
