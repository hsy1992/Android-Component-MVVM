package com.hsy.study.baselibrary.common.rxjava;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Api 返回 RxJava
 * @author haosiyuan
 * @date 2019/3/21 4:38 PM
 */
public class ApiSubscriber<T> implements Observer<T> {

    public ApiSubscriber() {
    }

    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(T t) {


    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
