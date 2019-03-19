package com.hsy.study.baselibrary.base.delegate;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import com.hsy.study.baselibrary.base.IApp;
import com.hsy.study.baselibrary.cache.local.IntelligentCache;
import com.hsy.study.baselibrary.config.IConfigModule;
import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.baselibrary.dagger.component.DaggerAppComponent;
import com.hsy.study.baselibrary.dagger.module.GlobalConfigModule;
import com.hsy.study.baselibrary.utils.ManifestParserUtil;
import com.hsy.study.baselibrary.utils.PreconditionsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;

/**
 * {@link Application} 代理类 代理类
 * @author haosiyuan
 * @date 2019/2/14 1:48 PM
 */
public class AppDelegate implements IAppLifecycle, IApp {

    private AppComponent mAppComponent;
    private Application mApplication;
    @Inject
    @Named("ActivityLifecycle")
    protected Application.ActivityLifecycleCallbacks mActivityLifecycle;
    /**
     * 自定义{@link android.app.Activity}生命周期事件
     */
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycleList = new ArrayList<>();
    /**
     * {@link Application} 生命周期集合
     */
    private List<IAppLifecycle> mAppLifecycleList = new ArrayList<>();
    /**
     * 内存管理回调
     */
    private AppComponentCallbacks mAppComponentCallbacks;
    /**
     * 自定义配置
     */
    private List<IConfigModule> mModules;


    public AppDelegate(@NonNull Context mContext) {

        //反射获取 AndroidManifest 中带有 ConfigModule 标签的 class
        this.mModules = new ManifestParserUtil(mContext).parse();

        for (IConfigModule configModule : mModules) {

            //将外部的实现的 Application生命周期 传入 mAppLifecycleList (未注册)
            configModule.injectAppLifecycle(mContext, mAppLifecycleList);
            //将外部的实现的 Activity生命周期 传入 mActivityLifecycleList (未注册)
            configModule.injectActivityLifecycle(mContext, mActivityLifecycleList);
        }
    }

    @Override
    public void attachBaseContext(@NonNull Context base) {

        //调用 Application 生命周期实现类
        for (IAppLifecycle appLifecycle : mAppLifecycleList) {
            appLifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(@NonNull Application application) {
        this.mApplication = application;
        mAppComponent = DaggerAppComponent
                .builder()
                .application(mApplication)
                .globalConfigModule(getGlobalConfigModule(mApplication, mModules))
                .build();
        mAppComponent.inject(this);

        //将所有 ConfigModule 的实现类放入全局缓存 extras Cache中
        mAppComponent.extras().put(IntelligentCache.getKeyOfKeep(IConfigModule.class.getName()), mModules);
        this.mModules = null;

        //注册框架实现的 Activity 生命周期逻辑
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

        for (Application.ActivityLifecycleCallbacks activityLifecycle : mActivityLifecycleList) {
            mApplication.registerActivityLifecycleCallbacks(activityLifecycle);
        }

        //注册内存回调
        mAppComponentCallbacks = new AppComponentCallbacks(mApplication, mAppComponent);
        mApplication.registerComponentCallbacks(mAppComponentCallbacks);

        //调用 Application 生命周期实现类
        for (IAppLifecycle appLifecycle : mAppLifecycleList) {
            appLifecycle.onCreate(application);
        }
    }

    /**
     * {@link Application} 结束
     * @param application
     */
    @Override
    public void onTerminate(@NonNull Application application) {

        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }

        //解除注册 AppComponentCallbacks Application 的内存回调
        if (mAppComponentCallbacks != null) {
            mApplication.unregisterComponentCallbacks(mAppComponentCallbacks);
        }

        //解除注册的外部 Activity 生命周期
        if (mActivityLifecycleList != null && mActivityLifecycleList.size() > 0) {
            for (Application.ActivityLifecycleCallbacks mActivityLifecycle : mActivityLifecycleList) {
                mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
            }
        }

        //调用 Application 生命周期实现类
        if (mAppLifecycleList != null && mAppLifecycleList.size() > 0) {
            for (IAppLifecycle appLifecycle : mAppLifecycleList) {
                appLifecycle.onTerminate(application);
            }
        }

        this.mAppComponent = null;
        this.mApplication = null;
        this.mActivityLifecycle = null;
        this.mAppComponentCallbacks = null;
        this.mActivityLifecycleList = null;
        this.mAppLifecycleList = null;
    }

    /**
     * 将全局配置放入Dagger2中
     * 将自定义配置加入 全局配置
     */
    private GlobalConfigModule getGlobalConfigModule(Context context, List<IConfigModule> modules) {
        GlobalConfigModule.Builder builder = new GlobalConfigModule.Builder();

        for (IConfigModule module : modules) {
            module.applyOptions(context, builder);
        }
        return builder.build();
    }

    /**
     * 提供{@link AppComponent}
     * @return
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        PreconditionsUtil.checkNotNull(mAppComponent,"AppComponent is null");
        return mAppComponent;
    }

    /**
     * 内存管理回调
     */
    private static class AppComponentCallbacks implements ComponentCallbacks2 {

        private Application mApplication;
        private AppComponent mAppComponent;

        public AppComponentCallbacks(Application mApplication, AppComponent mAppComponent) {
            this.mApplication = mApplication;
            this.mAppComponent = mAppComponent;
        }

        /**
         * 在你的 App 生命周期的任何阶段, {@link ComponentCallbacks2#onTrimMemory(int)} 发生的回调都预示着你设备的内存资源已经开始紧张
         * 你应该根据 {@link ComponentCallbacks2#onTrimMemory(int)} 发生回调时的内存级别来进一步决定释放哪些资源
         * @param level 内存级别
         * @see <a href="https://developer.android.com/reference/android/content/ComponentCallbacks2.html#TRIM_MEMORY_RUNNING_MODERATE">level 官方文档</a>
         */
        @Override
        public void onTrimMemory(int level) {
            //状态1. 当开发者的 App 正在运行
            //设备开始运行缓慢, 不会被 kill, 也不会被列为可杀死的, 但是设备此时正运行于低内存状态下, 系统开始触发杀死 LRU 列表中的进程的机制
//                case TRIM_MEMORY_RUNNING_MODERATE:


            //设备运行更缓慢了, 不会被 kill, 但请你回收 unused 资源, 以便提升系统的性能, 你应该释放不用的资源用来提升系统性能 (但是这也会直接影响到你的 App 的性能)
//                case TRIM_MEMORY_RUNNING_LOW:


            //设备运行特别慢, 当前 App 还不会被杀死, 但是系统已经把 LRU 列表中的大多数进程都已经杀死, 因此你应该立即释放所有非必须的资源
            //如果系统不能回收到足够的 RAM 数量, 系统将会清除所有的 LRU 列表中的进程, 并且开始杀死那些之前被认为不应该杀死的进程, 例如那个包含了一个运行态 Service 的进程
//                case TRIM_MEMORY_RUNNING_CRITICAL:


            //状态2. 当前 App UI 不再可见, 这是一个回收大个资源的好时机
//                case TRIM_MEMORY_UI_HIDDEN:


            //状态3. 当前的 App 进程被置于 Background LRU 列表中
            //进程位于 LRU 列表的上端, 尽管你的 App 进程并不是处于被杀掉的高危险状态, 但系统可能已经开始杀掉 LRU 列表中的其他进程了
            //你应该释放那些容易恢复的资源, 以便于你的进程可以保留下来, 这样当用户回退到你的 App 的时候才能够迅速恢复
//                case TRIM_MEMORY_BACKGROUND:


            //系统正运行于低内存状态并且你的进程已经已经接近 LRU 列表的中部位置, 如果系统的内存开始变得更加紧张, 你的进程是有可能被杀死的
//                case TRIM_MEMORY_MODERATE:


            //系统正运行于低内存的状态并且你的进程正处于 LRU 列表中最容易被杀掉的位置, 你应该释放任何不影响你的 App 恢复状态的资源
            //低于 API 14 的 App 可以使用 onLowMemory 回调
//                case TRIM_MEMORY_COMPLETE:
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        /**
         * 当系统开始清除 LRU 列表中的进程时, 尽管它会首先按照 LRU 的顺序来清除, 但是它同样会考虑进程的内存使用量, 因此消耗越少的进程则越容易被留下来
         * {@link ComponentCallbacks2#onTrimMemory(int)} 的回调是在 API 14 才被加进来的, 对于老的版本, 你可以使用 {@link ComponentCallbacks2#onLowMemory} 方法来进行兼容
         * {@link ComponentCallbacks2#onLowMemory} 相当于 {@code onTrimMemory(TRIM_MEMORY_COMPLETE)}
         *
         * @see #TRIM_MEMORY_COMPLETE
         */
        @Override
        public void onLowMemory() {
            //系统正运行于低内存的状态并且你的进程正处于 LRU 列表中最容易被杀掉的位置, 你应该释放任何不影响你的 App 恢复状态的资源
        }
    }
}
