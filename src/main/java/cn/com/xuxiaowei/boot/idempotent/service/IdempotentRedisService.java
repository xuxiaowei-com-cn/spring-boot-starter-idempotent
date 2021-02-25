package cn.com.xuxiaowei.boot.idempotent.service;

/**
 * 幂等 Redis 服务
 *
 * @param <T> Redis数据类型
 * @author xuxiaowei
 * @since 0.0.1
 */
public interface IdempotentRedisService<T> {

    /**
     * 根据 Redis Key 获取 Redis数据
     *
     * @param key Redis Key
     * @return Redis数据
     */
    T get(String key);

    /**
     * 将数据放入Redis中
     *
     * @param data Redis数据
     * @return 返回保存结果
     */
    boolean save(T data);

}
