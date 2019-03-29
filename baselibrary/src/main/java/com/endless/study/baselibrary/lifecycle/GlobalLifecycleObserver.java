package com.endless.study.baselibrary.lifecycle;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import dagger.Lazy;
import io.reactivex.Observable;

/**
 * 声明周期感知
 * @author haosiyuan
 * @date 2019/1/28 3:20 PM
 */
@Singleton
public class GlobalLifecycleObserver implements GenericLifecycleObserver {

    @Inject
    Lazy<GlobalLifecycleHandler> globalLifecycleHandler;

    @Inject
    public GlobalLifecycleObserver() {}

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {

        switch (event){
            case ON_CREATE:
                globalLifecycleHandler.get().onCreate();
                break;
            case ON_RESUME:
                globalLifecycleHandler.get().onResume();
                break;
            case ON_START:
                globalLifecycleHandler.get().onStart();
                break;
            case ON_STOP:
                globalLifecycleHandler.get().onStop();
                break;
            case ON_PAUSE:
                globalLifecycleHandler.get().onPause();
                break;
            case ON_DESTROY:
                globalLifecycleHandler.get().onDestroy();
                break;
            case ON_ANY:
                break;
            default:
                break;
        }
    }

    /**
     * 绑定生命周期
     * @param <T>
     * @return
     */
    public <T> LifecycleTransformer<T> bindLifecycle(){
        return globalLifecycleHandler.get().bindLifecycle();
    }

    public Observable lifecycle() {
        return globalLifecycleHandler.get().lifecycle();
    }

    public LifecycleTransformer bindUntilEvent(@NonNull Lifecycle.Event event) {
        return globalLifecycleHandler.get().bindUntilEvent(event);
    }
}
