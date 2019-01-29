package com.hsy.study.baselibrary.lifecycle;


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
        mLifecycleSubject.onNext(ActivityEvent.CREATE);
    }

    public void onStart(){
        mLifecycleSubject.onNext(ActivityEvent.START);
    }

    public void onResume(){
        mLifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    public void onPause(){
        mLifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    public void onStop(){
        mLifecycleSubject.onNext(ActivityEvent.STOP);
    }

    public void onDestroy(){
        mLifecycleSubject.onNext(ActivityEvent.DESTROY);
    }
}
