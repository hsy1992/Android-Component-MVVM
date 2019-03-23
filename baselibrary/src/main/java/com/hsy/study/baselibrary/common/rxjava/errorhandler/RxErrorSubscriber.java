package com.hsy.study.baselibrary.common.rxjava.errorhandler;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * RxJava 订阅
 * @author haosiyuan
 * @date 2019/3/23 7:40 PM
 */
public abstract class RxErrorSubscriber<T> implements Observer<T> {

    private RxErrorHandlerFactory rxErrorHandlerFactory;

    public RxErrorSubscriber(RxErrorHandler rxErrorHandler) {
        this.rxErrorHandlerFactory = rxErrorHandler.getHandlerFactory();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        rxErrorHandlerFactory.handleError(e);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }
}
