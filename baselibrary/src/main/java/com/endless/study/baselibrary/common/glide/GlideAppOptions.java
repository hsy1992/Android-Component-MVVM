package com.endless.study.baselibrary.common.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 自定义配置时 需要实现 {@link BaseImageLoaderStrategy} 与本类
 * @author haosiyuan
 * @date 2019/4/1 2:39 PM
 */
public interface GlideAppOptions {

    /**
     * 配置{@link com.bumptech.glide.Glide} 初始化时被调用
     * @param context
     * @param builder
     */
    void applyGlideOptions(@NonNull Context context, @Nullable GlideBuilder builder);
}
