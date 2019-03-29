package com.endless.study.baselibrary.common.rxjava.errorhandler;

import android.content.Context;

/**
 * RxJava Error 工厂
 * @author haosiyuan
 * @date 2019/3/23 7:00 PM
 */
public class RxErrorHandlerFactory {

    private Context context;
    private RxResponseErrorListener listener;

    public RxErrorHandlerFactory(Context context, RxResponseErrorListener listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * 处理错误
     * @param throwable
     */
    public void handleError(Throwable throwable) {
        listener.handlerResponseError(context, throwable);
    }
}
