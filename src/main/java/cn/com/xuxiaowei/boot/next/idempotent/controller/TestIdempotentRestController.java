package cn.com.xuxiaowei.boot.next.idempotent.controller;

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

import cn.com.xuxiaowei.boot.next.idempotent.annotation.Idempotent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 测试幂等
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@RestController
@ConditionalOnExpression("${xxw.idempotent.test:false}")
@RequestMapping("${xxw.idempotent.test-prefix:/test}/idempotent")
public class TestIdempotentRestController {

	/**
	 * Map
	 * @param request 请求
	 * @param response 响应
	 * @param millis 延时，毫秒
	 * @return 返回 Map
	 */
	@SneakyThrows
	@Idempotent(key = "key1", expireTime = 60 * 10, expireUnit = TimeUnit.SECONDS, header = "h1")
	@RequestMapping("/map")
	public Map<String, Object> map(HttpServletRequest request, HttpServletResponse response, Long millis) {

		if (millis != null) {
			// 延时
			Thread.sleep(millis);
		}

		Map<String, Object> map = new HashMap<>(8);
		map.put("uuid", UUID.randomUUID().toString());
		log.info(String.valueOf(map));
		return map;
	}

	/**
	 * Map 严格模式
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 Map
	 */
	@Idempotent(strict = true, key = "key1", expireTime = 10, expireUnit = TimeUnit.SECONDS)
	@RequestMapping("/map-strict")
	public Map<String, Object> mapStrict(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>(8);
		map.put("uuid", UUID.randomUUID().toString());
		log.info(String.valueOf(map));
		return map;
	}

	/**
	 * List
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 List
	 */
	@Idempotent(key = "key2", expireTime = 10, expireUnit = TimeUnit.SECONDS, header = "h2")
	@RequestMapping("/list")
	public List<Map<String, Object>> list(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(8);
		list.add(map);
		map.put("uuid", UUID.randomUUID().toString());
		log.info(String.valueOf(list));
		return list;
	}

	/**
	 * Integer
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 Integer
	 */
	@Idempotent(key = "key3", expireTime = 10, expireUnit = TimeUnit.SECONDS, param = "h3")
	@RequestMapping("/integer")
	public Integer integer(HttpServletRequest request, HttpServletResponse response) {
		Random random = new Random();
		int i = random.nextInt();
		log.info(String.valueOf(i));
		return i;
	}

	/**
	 * int
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 int
	 */
	@Idempotent(key = "key3", expireTime = 10, expireUnit = TimeUnit.SECONDS, stream = "h4")
	@RequestMapping("/int")
	public int i(HttpServletRequest request, HttpServletResponse response) {
		Random random = new Random();
		int i = random.nextInt();
		log.info(String.valueOf(i));
		return i;
	}

}
