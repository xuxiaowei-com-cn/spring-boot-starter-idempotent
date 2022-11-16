package cn.com.xuxiaowei.boot.next.idempotent.service;

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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Controller;

/**
 * 幂等超时接口
 *
 * @author xuxiaowei
 * @see IdempotentAspect#setTimeoutExceptionService(TimeoutExceptionService)
 * @since 0.0.1
 */
public interface TimeoutExceptionService {

	/**
	 * 自定义幂等调用超时返回的数据
	 * <p>
	 * 1、如果 {@link Controller} 返回数据类型与本方法返回数据类型相同，可直接使用 `return` 返回结果
	 * <p>
	 * 2、如果 {@link Controller} 返回数据类型与本方法返回数据类型不同，可直接使用 `return` 返回 `null`， 使用
	 * `jakarta.servlet.http.HttpServletResponse` 返回结果
	 * @param request 请求
	 * @param response 响应
	 * @param joinPoint 切点
	 * @return 返回 超时调用结果
	 */
	Object timeout(HttpServletRequest request, HttpServletResponse response, ProceedingJoinPoint joinPoint);

}
