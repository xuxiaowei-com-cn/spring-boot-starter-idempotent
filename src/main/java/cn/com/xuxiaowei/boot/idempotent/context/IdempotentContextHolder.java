package cn.com.xuxiaowei.boot.idempotent.context;

import cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent;
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
     *
     * @param stringRedisTemplate Redis 服务
     * @param idempotentContext   幂等调用记录
     * @param objectMapper        用于序列化和反序列化数据
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
     *
     * @param stringRedisTemplate Redis 服务
     * @param idempotent          幂等注解
     * @param objectMapper        用于序列化和反序列化数据
     * @param redisRecordKey      幂等调用记录 Redis Key
     * @param redisRecordValue    Redis 中 幂等调用记录
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
