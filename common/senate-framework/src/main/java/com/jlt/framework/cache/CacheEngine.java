package com.jlt.framework.cache;

/**
 * Created by Martin on 2017/1/22.
 */
public interface CacheEngine {

    /**
     * 获取缓存对象
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends LiveObject> T getCache(Class<T> clazz);
}
