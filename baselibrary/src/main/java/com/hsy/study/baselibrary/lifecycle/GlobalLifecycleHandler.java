package com.hsy.study.baselibrary.lifecycle;


import android.util.Log;

import com.hsy.study.baselibrary.utils.logger.Logger;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LifecycleObserver;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author haosiyuan
 * @date 2019/1/28 3:46 PM
 */
@Singleton
public class GlobalLifecycleHandler implements LifecycleObserver {

    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();

    @Inject
    public GlobalLifecycleHandler(){

    }

    public void onCreate(){
        Logger.debugInfo("onCreate");
        mLifecycleSubject.onNext(ActivityEvent.CREATE);
    }

    public void onStart(){
        Logger.debugInfo("onStart");
        mLifecycleSubject.onNext(ActivityEvent.START);
    }

    public void onResume(){
        Logger.debugInfo("onResume");
        mLifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    public void onPause(){
        Logger.debugInfo("onPause");
        mLifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    public void onStop(){
        Logger.debugInfo("onStop");
        mLifecycleSubject.onNext(ActivityEvent.STOP);
    }

    public void onDestroy(){
        Logger.debugInfo("onDestroy");
        mLifecycleSubject.onNext(ActivityEvent.DESTROY);
    }
}
