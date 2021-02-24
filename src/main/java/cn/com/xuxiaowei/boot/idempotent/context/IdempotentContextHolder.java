package cn.com.xuxiaowei.boot.idempotent.context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 幂等内容持有者
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class IdempotentContextHolder {

    private static final ThreadLocal<IdempotentContext> HOLDER = ThreadLocal.withInitial(IdempotentContext::new);

    /**
     * 幂等调用记录放入Redis
     *
     * @param stringRedisTemplate Redis 服务
     * @param key                 用于组成 Redis Key
     * @param idempotentContext   幂等内容
     */
    public static void setRedis(StringRedisTemplate stringRedisTemplate, String key, IdempotentContext idempotentContext) {
        String redisValue = JSON.toJSONString(idempotentContext, SerializerFeature.WriteMapNullValue);
        stringRedisTemplate.opsForValue().set("context:" + key, redisValue, 1, TimeUnit.DAYS);
    }

    /**
     * 幂等调用记录重复次数
     *
     * @param stringRedisTemplate Redis 服务
     * @param key                 用于组成 Redis Key
     */
    public static void repeat(StringRedisTemplate stringRedisTemplate, String key) {

        String redisKey = "context:" + key;

        // 获取 Redis 中 幂等调用记录
        String redisTokenValue = stringRedisTemplate.opsForValue().get(redisKey);

        // 将幂等调用记录转为对象
        IdempotentContext idempotentContext = JSONObject.parseObject(redisTokenValue, IdempotentContext.class);

        if (idempotentContext == null) {
            // Redis 中不存在 幂等调用记录

            // 重新创建一个幂等调用记录
            idempotentContext = IdempotentContextHolder.getCurrentContext()
                    .setResultDate(LocalDateTime.now())
                    .setNumber(1);
        } else {
            idempotentContext.setStatus(StatusEnum.REPEAT).setNumber(idempotentContext.getNumber() + 1);
        }

        // 幂等调用记录放入Redis
        setRedis(stringRedisTemplate, key, idempotentContext);
    }

    public static IdempotentContext getCurrentContext() {
        return HOLDER.get();
    }

    public static void setCurrentContext(IdempotentContext currentContext) {
        HOLDER.set(currentContext);
    }

    public static void clearContext() {
        HOLDER.remove();
    }

}
