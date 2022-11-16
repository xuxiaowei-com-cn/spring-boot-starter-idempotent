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

import cn.com.xuxiaowei.boot.next.idempotent.annotation.Idempotent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 幂等调用记录持有者
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class IdempotentContextHolder {

	/**
	 * 幂等调用记录放入Redis
	 * @param stringRedisTemplate Redis 服务
	 * @param idempotentContext 幂等调用记录
	 * @param objectMapper 用于序列化和反序列化数据
	 */
	@SneakyThrows
	public static void setRedis(StringRedisTemplate stringRedisTemplate, IdempotentContext idempotentContext,
			ObjectMapper objectMapper, String redisRecordKey) {
		String redisRecordValue = objectMapper.writeValueAsString(idempotentContext);
		LocalDateTime requestDate = idempotentContext.getRequestDate();
		LocalDateTime expireDate = idempotentContext.getExpireDate();
		Duration timeout = Duration.between(requestDate, expireDate);
		stringRedisTemplate.opsForValue().set(redisRecordKey, redisRecordValue, timeout);
	}

	/**
	 * 幂等调用记录重复次数
	 * @param stringRedisTemplate Redis 服务
	 * @param idempotent 幂等注解
	 * @param objectMapper 用于序列化和反序列化数据
	 * @param redisRecordKey 幂等调用记录 Redis Key
	 * @param redisRecordValue Redis 中 幂等调用记录
	 */
	@SneakyThrows
	public static IdempotentContext repeat(StringRedisTemplate stringRedisTemplate, Idempotent idempotent,
			ObjectMapper objectMapper, String redisRecordKey, String redisRecordValue) {

		// 将幂等调用记录转为对象
		IdempotentContext idempotentContext = objectMapper.readValue(redisRecordValue, IdempotentContext.class);

		// 调用次数自增
		idempotentContext.setNumber(idempotentContext.getNumber() + 1);

		// 幂等调用记录放入Redis
		setRedis(stringRedisTemplate, idempotentContext, objectMapper, redisRecordKey);

		return idempotentContext;
	}

}
