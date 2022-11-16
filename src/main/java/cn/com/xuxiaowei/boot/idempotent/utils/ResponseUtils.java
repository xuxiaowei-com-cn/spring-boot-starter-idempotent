package cn.com.xuxiaowei.boot.idempotent.utils;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class ResponseUtils {

	/**
	 * 响应
	 * @param response 响应
	 * @param object 数据
	 * @throws IOException IO异常
	 */
	public static void response(HttpServletResponse response, Object object) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String json = objectMapper.writeValueAsString(object);
		response.getWriter().println(json);
		response.setStatus(200);
		response.flushBuffer();
	}

}
