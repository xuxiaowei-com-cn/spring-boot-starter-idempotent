package cn.com.xuxiaowei.boot.idempotent.context;

import cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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
     */
    @SneakyThrows
    public static IdempotentContext repeat(StringRedisTemplate stringRedisTemplate, Idempotent idempotent,
                                           ObjectMapper objectMapper, String redisRecordKey) {

        // 获取 Redis 中 幂等调用记录
        String redisRecordValue = stringRedisTemplate.opsForValue().get(redisRecordKey);

        // 将幂等调用记录转为对象
        IdempotentContext idempotentContext = objectMapper.readValue(redisRecordValue, IdempotentContext.class);

        if (idempotentContext == null) {
            // Redis 中不存在 幂等调用记录

            int expireTime = idempotent.expireTime();
            TimeUnit expireUnit = idempotent.expireUnit();

            LocalDateTime requestDate = LocalDateTime.now();
            long seconds = TimeoutUtils.toSeconds(expireTime, expireUnit);
            LocalDateTime expireDate = requestDate.plusSeconds(seconds);

            // 重新创建一个幂等调用记录
            idempotentContext = new IdempotentContext()
                    .setResultDate(requestDate)
                    .setNumber(1)
                    .setExpireDate(expireDate);
        } else {
            StatusEnum status = idempotentContext.getStatus();
            // 只有上次正常调用，本次才将状态改为重复调用，否则维持上次状态
            status = status == StatusEnum.NORMAL ? StatusEnum.REPEAT : status;
            idempotentContext.setStatus(status).setNumber(idempotentContext.getNumber() + 1);
        }

        // 幂等调用记录放入Redis
        setRedis(stringRedisTemplate, idempotentContext, objectMapper, redisRecordKey);

        return idempotentContext;
    }

}
