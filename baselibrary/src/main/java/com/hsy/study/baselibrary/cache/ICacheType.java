package com.hsy.study.baselibrary.cache;

import android.content.Context;

/**
 * 缓存策略类型
 * @author haosiyuan
 * @date 2019/2/11 4:19 PM
 */
public interface ICacheType {

    /**
     * 返回框架内需要缓存的模块对应的Id
     *
     * @return
     */
    int getCacheTypeId();

    /**
     * 计算对应模块需要的缓存大小
     *
     * @return 返回缓存最大值
     */
    int calculateCacheSize(Context context);
}
