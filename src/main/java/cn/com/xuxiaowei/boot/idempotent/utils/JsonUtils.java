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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

/**
 * JSON 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class JsonUtils {

	/**
	 * 根据 节点 获取 JSON 中的数据
	 * @param nodeName 节点名称，使用 . 分隔
	 * @param json JSON 数据
	 * @return 返回 JSON 中的数据
	 * @throws JsonProcessingException 读取 JSON 异常
	 */
	public static String getNode(String nodeName, String json) throws JsonProcessingException {
		if (StringUtils.hasText(nodeName) && StringUtils.hasText(json)) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(json);
			String[] tokenNameSplit = nodeName.split("\\.");
			while (tokenNameSplit.length > 1) {
				String fieldName = tokenNameSplit[0];
				jsonNode = jsonNode.get(fieldName);
				nodeName = nodeName.substring(fieldName.length() + 1);
				tokenNameSplit = nodeName.split("\\.");
			}
			JsonNode jsonNodeValue = jsonNode.get(nodeName);
			return jsonNodeValue == null ? null : jsonNodeValue.toString();
		}
		else {
			return null;
		}
	}

}
