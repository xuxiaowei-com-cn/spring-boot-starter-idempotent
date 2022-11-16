package cn.com.xuxiaowei.boot.next.idempotent.context;

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

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 幂等调用记录
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Accessors(chain = true)
public class IdempotentContext implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 幂等 Token 放入响应 Header 中的 Name
	 */
	public static final String TOKEN = "token";

	/**
	 * 幂等状态 放入响应 Header 中的 Name
	 */
	public static final String STATUS = "status";

	/**
	 * 请求时间（首次） 放入响应 Header 中的 Name
	 */
	public static final String REQUEST_DATE = "requestDate";

	/**
	 * 执行结果时间（首次） 放入响应 Header 中的 Name
	 */
	public static final String RESULT_DATE = "resultDate";

	/**
	 * 过期时间 放入响应 Header 中的 Name
	 */
	public static final String EXPIRE_DATE = "expireDate";

	/**
	 * 调用次数 放入响应 Header 中的 Name
	 */
	public static final String NUMBER = "number";

	/**
	 * 幂等 Token
	 */
	private String token;

	/**
	 * 幂等状态
	 */
	private StatusEnum status;

	/**
	 * 请求时间（首次）
	 */
	private LocalDateTime requestDate;

	/**
	 * 执行结果时间（首次）
	 */
	private LocalDateTime resultDate;

	/**
	 * 过期时间
	 */
	private LocalDateTime expireDate;

	/**
	 * 调用次数
	 * <p>
	 * 0：正在执行 1：调用一次 2：调用两次 3：调用三次
	 */
	private Integer number = 0;

}
