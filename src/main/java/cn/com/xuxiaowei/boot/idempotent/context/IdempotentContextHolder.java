package cn.com.xuxiaowei.boot.idempotent.context;

import cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent;
import cn.com.xuxiaowei.boot.idempotent.properties.IdempotentProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;

import java.time.Duration;
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
     * @param stringRedisTemplate  Redis 服务
     * @param idempotentProperties 幂等 配置
     * @param tokenValue           用于组成 Redis Key Token Value
     * @param idempotentContext    幂等内容
     * @param idempotent           幂等注解
     * @param objectMapper         用于序列化和反序列化数据
     */
    @SneakyThrows
    public static void setRedis(StringRedisTemplate stringRedisTemplate, IdempotentProperties idempotentProperties,
                                String tokenValue, IdempotentContext idempotentContext, Idempotent idempotent,
                                ObjectMapper objectMapper) {
        String prefix = idempotentProperties.getPrefix();
        String record = idempotentProperties.getRecord();
        String redisKey = prefix + ":" + record + ":" + idempotent.key() + ":" + tokenValue;
        String redisValue = objectMapper.writeValueAsString(idempotentContext);
        LocalDateTime requestDate = idempotentContext.getRequestDate();
        LocalDateTime expireDate = idempotentContext.getExpireDate();
        Duration timeout = Duration.between(requestDate, expireDate);
        stringRedisTemplate.opsForValue().set(redisKey, redisValue, timeout);
    }

    /**
     * 幂等调用记录重复次数
     *
     * @param stringRedisTemplate  Redis 服务
     * @param idempotentProperties 幂等 配置
     * @param tokenValue           用于组成 Redis Key Token Value
     * @param idempotent           幂等注解
     * @param objectMapper         用于序列化和反序列化数据
     */
    @SneakyThrows
    public static void repeat(StringRedisTemplate stringRedisTemplate, IdempotentProperties idempotentProperties,
                              String tokenValue, Idempotent idempotent, ObjectMapper objectMapper) {
        String prefix = idempotentProperties.getPrefix();
        String record = idempotentProperties.getRecord();
        String redisRecordKey = prefix + ":" + record + ":" + idempotent.key() + ":" + tokenValue;

        // 获取 Redis 中 幂等调用记录
        String redisTokenValue = stringRedisTemplate.opsForValue().get(redisRecordKey);

        // 将幂等调用记录转为对象
        IdempotentContext idempotentContext = objectMapper.readValue(redisTokenValue, IdempotentContext.class);

        if (idempotentContext == null) {
            // Redis 中不存在 幂等调用记录

            int expireTime = idempotent.expireTime();
            TimeUnit timeUnit = idempotent.timeUnit();

            LocalDateTime requestDate = LocalDateTime.now();
            long seconds = TimeoutUtils.toSeconds(expireTime, timeUnit);
            LocalDateTime expireDate = requestDate.plusSeconds(seconds);

            // 重新创建一个幂等调用记录
            idempotentContext = IdempotentContextHolder.getCurrentContext()
                    .setResultDate(requestDate)
                    .setNumber(1)
                    .setExpireDate(expireDate);
        } else {
            idempotentContext.setStatus(StatusEnum.REPEAT).setNumber(idempotentContext.getNumber() + 1);
        }

        // 幂等调用记录放入Redis
        setRedis(stringRedisTemplate, idempotentProperties, tokenValue, idempotentContext, idempotent, objectMapper);
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
