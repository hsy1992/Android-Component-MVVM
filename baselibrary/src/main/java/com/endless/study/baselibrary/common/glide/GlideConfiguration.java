package com.endless.study.baselibrary.common.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.endless.study.baselibrary.dagger.component.AppComponent;
import com.endless.study.baselibrary.utils.UtilCommon;
import com.endless.study.baselibrary.utils.UtilFile;

import java.io.File;
import java.io.InputStream;

import androidx.annotation.NonNull;

/**
 * {@link com.bumptech.glide.Glide} 配置
 * @author haosiyuan
 * @date 2019/4/1 2:38 PM
 */
@GlideModule(glideName = "GlideEndless")
public class GlideConfiguration extends AppGlideModule {

    /**
     * 图片缓存文件最大值为100Mb
     */
    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;


    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        AppComponent appComponent = UtilCommon.getAppComponent(context);
        //设置磁盘缓存 100M
        builder.setDiskCache(() ->
                DiskLruCacheWrapper
                        .create(UtilFile.makeDirs(new File(appComponent.getCacheFile(), "glide")), IMAGE_DISK_CACHE_MAX_SIZE));

        //内存计算
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        //默认内存缓存
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        //bitmap缓冲池
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        //替换缓存算法
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        //将配置 Glide 的机会转交给 GlideImageLoaderStrategy,如你觉得框架提供的 GlideImageLoaderStrategy
        //并不能满足自己的需求,想自定义 BaseImageLoaderStrategy,那请你最好实现 GlideAppliesOptions
        //因为只有成为 GlideAppliesOptions 的实现类,这里才能调用 applyGlideOptions(),让你具有配置 Glide 的权利
        BaseImageLoaderStrategy loadImgStrategy = appComponent.imageLoader().getLoadImgStrategy();
        if (loadImgStrategy != null && loadImgStrategy instanceof GlideAppOptions) {
            ((GlideAppOptions) loadImgStrategy).applyGlideOptions(context, builder);
        }
    }

    /**
     * 注册组件
     * @param context
     * @param glide
     * @param registry
     */
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //Glide 切换为OkHttp请求
        AppComponent appComponent = UtilCommon.getAppComponent(context);

        registry.replace(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(appComponent.okHttpClient()));
    }

    /**
     * AndroidManifest.xml 是否配置
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
