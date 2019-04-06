package com.endless.study.myproject.dagger.global;

import android.app.Application;

import com.endless.study.baselibrary.dagger.component.AppComponent;
import com.endless.study.baselibrary.dagger.scope.AppScope;

import dagger.Component;

/**
 * 全局公共
 * @author haosiyuan
 * @date 2019/3/7 3:50 PM
 */
@AppScope
@Component(modules = {ViewModelModule.class}, dependencies = AppComponent.class)
public interface GlobalComponent {
    void inject(Application application);

    @Component.Builder
    interface Builder {
        GlobalComponent.Builder appComponent(AppComponent appComponent);
        GlobalComponent build();
    }
}
