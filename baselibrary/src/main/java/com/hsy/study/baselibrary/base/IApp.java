package com.hsy.study.baselibrary.base;

import com.hsy.study.baselibrary.dagger.component.AppComponent;

import androidx.annotation.NonNull;

/**
 * {@link android.app.Application} 需要实现
 * @author haosiyuan
 * @date 2019/2/16 8:34 PM
 */
public interface IApp {
    @NonNull
    AppComponent getAppComponent();
}
