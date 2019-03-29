package com.endless.study.baselibrary.lifecycle;

import com.endless.study.baselibrary.common.logger.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * 全局生命周期 处理
 * @author haosiyuan
 * @date 2019/1/28 3:46 PM
 */
@Singleton
public class GlobalLifecycleHandler implements LifecycleObserver, ILifecycleProvide<Lifecycle.Event> {

    private final BehaviorSubject<Lifecycle.Event> mLifecycleSubject = BehaviorSubject.create();

    @Inject
    public GlobalLifecycleHandler() {}

    public void onCreate(){
        Logger.debugInfo("onCreate");
        mLifecycleSubject.onNext(Lifecycle.Event.ON_CREATE);
    }

    public void onStart(){
        Logger.debugInfo("onStart");
        mLifecycleSubject.onNext(Lifecycle.Event.ON_START);
    }

    public void onResume(){
        Logger.debugInfo("onResume");
        mLifecycleSubject.onNext(Lifecycle.Event.ON_RESUME);
    }

    public void onPause(){
        Logger.debugInfo("onPause");
        mLifecycleSubject.onNext(Lifecycle.Event.ON_PAUSE);
    }

    public void onStop(){
        Logger.debugInfo("onStop");
        mLifecycleSubject.onNext(Lifecycle.Event.ON_STOP);
    }

    public void onDestroy(){
        Logger.debugInfo("onDestroy");
        mLifecycleSubject.onNext(Lifecycle.Event.ON_DESTROY);
    }

    @NonNull
    @Override
    public Observable lifecycle() {
        return mLifecycleSubject.hide();
    }

    @NonNull
    @Override
    public LifecycleTransformer bindLifecycle() {
        return RxLifecycle.bind(mLifecycleSubject, Functions.LIFECYCLE_EVENT);
    }

    @NonNull
    @Override
    public LifecycleTransformer bindUntilEvent(@NonNull Lifecycle.Event event) {
        return RxLifecycle.bindUntilEvent(mLifecycleSubject, event);
    }

}
