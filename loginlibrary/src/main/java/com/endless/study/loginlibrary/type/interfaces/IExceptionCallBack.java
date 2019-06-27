package com.endless.study.loginlibrary.type.interfaces;

/**
 * 异常回调
 * @author haosiyuan
 * @date 2019/3/15 4:07 PM
 */
public interface IExceptionCallBack {

    /**
     * 异常回调
     * @param tag
     * @param message
     */
    void onCallBack(String tag, String message);
}
