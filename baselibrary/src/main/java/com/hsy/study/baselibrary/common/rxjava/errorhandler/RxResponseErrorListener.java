package com.hsy.study.baselibrary.common.rxjava.errorhandler;

import android.content.Context;

/**
 * 回调接口
 * @author haosiyuan
 * @date 2019/3/23 7:00 PM
 */
public interface RxResponseErrorListener {

    /**
     * 处理错误
     * @param context
     * @param t
     */
    void handlerResponseError(Context context, Throwable t);

    RxResponseErrorListener EMPTY = (context, t) -> {

    };

}
