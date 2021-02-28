package cn.com.xuxiaowei.boot.idempotent.util;

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
     *
     * @param nodeName 节点名称，使用 . 分隔
     * @param json     JSON 数据
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
            return jsonNode.get(nodeName).toString();
        } else {
            return null;
        }
    }

}
