package com.hsy.study.loginlibrary.type.interfaces;

import android.app.Activity;

import com.hsy.study.loginlibrary.LoginConfig;
import com.hsy.study.loginlibrary.type.callback.ThreeAuthCallBack;

/**
 * 三方登录
 * @author haosiyuan
 * @date 2019/3/15 9:20 AM
 */
public interface ILoginThree extends ILogin {

    /**
     * 登录
     * @param config
     * @param callBack
     * @param activity
     */
    void login(LoginConfig config, Activity activity, ThreeAuthCallBack callBack);

}
