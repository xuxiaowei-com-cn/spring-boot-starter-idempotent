package cn.com.xuxiaowei.boot.idempotent.service.impl;

import cn.com.xuxiaowei.boot.idempotent.service.IdempotentRedisService;
import org.springframework.stereotype.Service;

/**
 * 幂等 Redis 服务 实现类
 *
 * @param <T> Redis数据类型
 * @author xuxiaowei
 * @since 0.0.1
 */
@Service
public class RedisServiceImpl<T> implements IdempotentRedisService<T> {

    /**
     * 根据 Redis Key 获取 Redis数据
     *
     * @param key Redis Key
     * @return Redis数据
     */
    @Override
    public T get(String key) {
        return null;
    }

    /**
     * 将数据放入Redis中
     *
     * @param data Redis数据
     * @return 返回保存结果
     */
    @Override
    public boolean save(T data) {
        return false;
    }

}
