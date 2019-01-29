package com.hsy.study.baselibrary.viewmodel;

import android.app.Application;

import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @author haosiyuan
 * @date 2019/1/28 5:09 PM
 */
public class BaseViewModel extends AndroidViewModel {


    public BaseViewModel(@NonNull Application application) {
        super(application);
    }



    @Override
    protected void onCleared() {

    }
}
