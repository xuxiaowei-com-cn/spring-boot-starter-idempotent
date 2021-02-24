package cn.com.xuxiaowei.boot.idempotent.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 幂等内容
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class IdempotentContext {

    private final Map<String, String> MAP = new HashMap<>();

    public String put(String key, String value) {
        return MAP.put(key, value);
    }

    public String remove(String key) {
        return MAP.remove(key);
    }

    public String get(String key) {
        return MAP.get(key);
    }

}
