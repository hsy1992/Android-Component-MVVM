package com.hsy.study.baselibrary.dagger.component;

import android.app.Application;

import com.google.gson.Gson;
import com.hsy.study.baselibrary.cache.Cache;
import com.hsy.study.baselibrary.dagger.module.AppModule;
import com.hsy.study.baselibrary.dagger.module.ClientModule;
import com.hsy.study.baselibrary.dagger.module.GlobalConfigModule;
import com.hsy.study.baselibrary.lifecycle.GlobalLifecycleObserver;

import java.io.File;
import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author haosiyuan
 * @date 2018/12/31 下午3:49
 */
@Singleton
@Component(modules = {ClientModule.class, GlobalConfigModule.class, AppModule.class})
public interface AppComponent {

    Application application();

    /**
     * Json 序列化库
     *
     * @return {@link Gson}
     */
    Gson gson();
    /**
     * 返回一个全局公用的线程池
     * @return {@link ExecutorService}
     */
    ExecutorService executorService();

    /**
     * 生命周期感知
     * @return
     */
    GlobalLifecycleObserver observer();

    /**
     * 用来存取一些整个 App 公用的数据, 切勿大量存放大容量数据, 这里的存放的数据和 {@link Application} 的生命周期一致
     * @return {@link Cache}
     */
    Cache<String, Object> extras();

    /**
     * 用于创建框架所需要缓存的对象工厂
     * @return
     */
    Cache.Factory cacheFactory();

    /**
     * 注入
     * @param application
     */
    void inject(Application application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder globalConfigModule(GlobalConfigModule globalConfigModule);

        AppComponent build();
    }

}
