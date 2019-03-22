package com.hsy.study.myproject.dagger.component;

import android.app.Application;

import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.baselibrary.dagger.scope.AppScope;
import com.hsy.study.myproject.dagger.ViewModelModule;

import dagger.Component;

/**
 * 全局公共
 * @author haosiyuan
 * @date 2019/3/7 3:50 PM
 */
@AppScope
@Component(modules = {ViewModelModule.class}, dependencies = AppComponent.class)
public interface GlobalComponent {
    void inject(Application mainActivity);

    @Component.Builder
    interface Builder {
        GlobalComponent.Builder appComponent(AppComponent appComponent);
        GlobalComponent build();
    }
}
