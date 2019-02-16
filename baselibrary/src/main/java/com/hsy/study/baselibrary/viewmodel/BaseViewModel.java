package com.hsy.study.baselibrary.viewmodel;

import android.app.Application;

import com.hsy.study.baselibrary.base.delegate.IViewModel;
import com.hsy.study.baselibrary.cache.Cache;
import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @author haosiyuan
 * @date 2019/1/28 5:09 PM
 */
public abstract class BaseViewModel extends AndroidViewModel implements IViewModel {


    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


    @Override
    protected void onCleared() {

    }

    @Override
    public Cache<String, Object> provideCache() {
        return null;
    }

}
