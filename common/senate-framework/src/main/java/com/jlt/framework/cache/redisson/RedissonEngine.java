package com.jlt.framework.cache.redisson;

import com.jlt.framework.cache.CacheEngine;
import com.jlt.framework.cache.IdGenerator;
import com.jlt.framework.cache.LiveObject;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Martin on 2017/1/23.
 */
public class RedissonEngine implements CacheEngine, IdGenerator {
    @Autowired
    private RedissonClient client;
    @Value("${cache.redissonKey}")
    private String envionment;

    @Override
    public <T extends LiveObject> T getCache(Class<T> clazz) {
        RLiveObjectService liveObjectService = client.getLiveObjectService();
        return liveObjectService.get(clazz, envionment);
    }

    @Override
    public long acquire(String topic) {
        return acquire(topic, 1);
    }

    @Override
    public long acquire(String topic, int count) {
        RAtomicLong atomicLong = client.getAtomicLong(topic);
        return atomicLong.addAndGet(count);
    }
}
