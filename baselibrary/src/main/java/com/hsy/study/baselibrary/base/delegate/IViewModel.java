package com.hsy.study.baselibrary.base.delegate;

import com.hsy.study.baselibrary.cache.ICache;
import com.hsy.study.baselibrary.dagger.component.AppComponent;

import androidx.annotation.NonNull;


/**
 * viewModel接口每个 viewModel都实现此类
 * @author haosiyuan
 * @date 2019/1/28 5:10 PM
 */
public interface IViewModel {

    /**
     * 提供给viewModel 缓存容器
     * @return
     */
    ICache<String, Object> provideCache();


    /**
     * 绑定生命周期，防止rxJava内存泄漏
     */
    void bindLifecycle();
}
