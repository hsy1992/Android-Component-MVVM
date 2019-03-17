package com.hsy.study.loginlibrary.type;

import android.app.Activity;

import com.hsy.study.loginlibrary.LoginConfig;
import com.hsy.study.loginlibrary.type.callback.ThreeAuthCallBack;
import com.hsy.study.loginlibrary.type.interfaces.ILoginThree;
import com.hsy.study.loginlibrary.type.interfaces.IThreeAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * QQ登录
 * @author haosiyuan
 * @date 2019/3/11 4:43 PM
 */
public class QQLogin implements ILoginThree {

    @Override
    public void login(LoginConfig config, Activity activity, ThreeAuthCallBack callBack) {
        UMShareAPI.get(config.getApplication()).getPlatformInfo(activity, SHARE_MEDIA.QQ, callBack);
    }
}
