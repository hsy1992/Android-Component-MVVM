package com.endless.study.baselibrary.dagger.component;

import android.app.Application;

import com.google.gson.Gson;
import com.endless.study.baselibrary.base.delegate.AppDelegate;
import com.endless.study.baselibrary.cache.local.ICache;
import com.endless.study.baselibrary.common.executor.AppExecutors;
import com.endless.study.baselibrary.common.rxjava.errorhandler.RxErrorHandler;
import com.endless.study.baselibrary.common.toast.IToastConfiguration;
import com.endless.study.baselibrary.dagger.module.AppModule;
import com.endless.study.baselibrary.dagger.module.ClientModule;
import com.endless.study.baselibrary.dagger.module.GlobalConfigModule;
import com.endless.study.baselibrary.database.AppDatabase;
import com.endless.study.baselibrary.lifecycle.GlobalLifecycleObserver;
import com.endless.study.baselibrary.repository.IRepositoryManager;

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

    /**
     * {@link Application}
     * @return
     */
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
     * @return {@link ICache}
     */
    ICache<String, Object> extras();

    /**
     * 用于创建框架所需要缓存的对象工厂
     * @return
     */
    ICache.Factory cacheFactory();

    /**
     * 提示
     * @return
     */
    IToastConfiguration toast();

    /**
     * 用于管理网络请求层, 以及数据缓存层
     *
     * @return {@link IRepositoryManager}
     */
    IRepositoryManager repositoryManager();

    /**
     * 获取 数据库{@link AppDatabase}
     * @return
     */
    AppDatabase getAppDatabase();

    /**
     * 获取 {@link AppExecutors}
     * @return
     */
    AppExecutors getAppExecutors();

    /**
     * RxJava错误处理
     * @return
     */
    RxErrorHandler getRxErrorHandler();

    /**
     * 注入
     * @param delegate
     */
    void inject(AppDelegate delegate);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder globalConfigModule(GlobalConfigModule globalConfigModule);

        AppComponent build();
    }

}
