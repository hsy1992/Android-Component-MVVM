package com.endless.study.baselibrary.common.glide;

import android.content.Context;

import androidx.annotation.Nullable;

/**
 * 图片加载策略
 * @author haosiyuan
 * @date 2019/4/1 2:18 PM
 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {

    /**
     * 加载图片
     * @param context
     * @param config 图片配置
     */
    void loadImage(@Nullable Context context, @Nullable T config);

    /**
     * 停止加载图片
     * @param context
     * @param config 图片配置
     */
    void clear(@Nullable Context context, @Nullable T config);
}
