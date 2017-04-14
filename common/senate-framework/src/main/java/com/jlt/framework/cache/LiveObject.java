package com.jlt.framework.cache;

import java.io.Serializable;

/**
 * Created by Martin on 2017/1/22.
 */
public interface LiveObject extends Serializable {

    /**
     * 获得该cache所属应用环境
     * @return
     */
    String getEnv();
}
