package com.hsy.study.baselibrary.dagger.interfaces;

import android.content.Context;

import androidx.annotation.NonNull;
import io.rx_cache2.internal.RxCache;

/**RxCache配置
 * @author haosiyuan
 * @date 2019/1/27 9:17 AM
 */
public interface IRxCacheConfiguration {

    /**
     * 自定义配置RxCache
     * @param context
     * @param builder
     * @return
     */
    RxCache configRxCache(@NonNull Context context, @NonNull RxCache.Builder builder);
}
