package com.hsy.study.baselibrary.base.delegate;

import com.hsy.study.baselibrary.cache.Cache;
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
    Cache<String, Object> provideCache();

    /**
     * 提供AppComponent给实现类使用
     * @param appComponent
     */
    void setUpAppComponent(@NonNull AppComponent appComponent);

}
