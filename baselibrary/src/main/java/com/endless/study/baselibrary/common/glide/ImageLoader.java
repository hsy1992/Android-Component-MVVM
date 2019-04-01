package com.endless.study.baselibrary.common.glide;

import android.content.Context;

import com.endless.study.baselibrary.utils.UtilPreconditions;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 图片加载
 * @author haosiyuan
 * @date 2019/4/1 2:03 PM
 */
@Singleton
public class ImageLoader {

    @Inject
    @Nullable
    BaseImageLoaderStrategy mStrategy;

    @Inject
    public ImageLoader() {}

    /**
     * 加载图片
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageConfig> void loadImage(Context context, T config) {
        UtilPreconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        this.mStrategy.loadImage(context, config);
    }

    /**
     * 停止加载或清理缓存
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageConfig> void clear(Context context, T config) {
        UtilPreconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        this.mStrategy.clear(context, config);
    }

    /**
     * 可在运行时随意切换 {@link BaseImageLoaderStrategy}
     *
     * @param strategy
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        UtilPreconditions.checkNotNull(strategy, "strategy == null");
        this.mStrategy = strategy;
    }

    @Nullable
    public BaseImageLoaderStrategy getLoadImgStrategy() {
        return mStrategy;
    }
}
