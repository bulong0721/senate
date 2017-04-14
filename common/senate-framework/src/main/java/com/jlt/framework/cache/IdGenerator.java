package com.jlt.framework.cache;

/**
 * Created by Martin on 2017/1/23.
 */
public interface IdGenerator {

    /**
     * @param topic
     * @return
     */
    long acquire(String topic);

    /**
     * 批量获取id
     * @param topic
     * @param count
     * @return 返回值为增量叠加后的值
     */
    long acquire(String topic, int count);
}
