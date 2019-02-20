package com.hsy.study.baselibrary.cache;

import android.app.ActivityManager;
import android.content.Context;

/**
 * 默认缓存策略
 * @author haosiyuan
 * @date 2019/2/11 4:22 PM
 */
public class DefaultCacheType implements ICacheType {

    private static final int MAX_SIZE = 100;
    private static final float MAX_SIZE_MULTIPLIER = 0.002f;

    /**
     * 缓存类型 Id
     * @return
     */
    @Override
    public int getCacheTypeId() {
        return 0;
    }

    /**
     * 计算缓存大小
     * @param context
     * @return
     */
    @Override
    public int calculateCacheSize(Context context) {
        ActivityManager activityManger = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryCacheSize = (int) (activityManger.getMemoryClass() * MAX_SIZE_MULTIPLIER * 1024);
        if (memoryCacheSize > MAX_SIZE){
            return MAX_SIZE;
        }
        return memoryCacheSize;
    }
}
