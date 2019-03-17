package com.hsy.study.loginlibrary.type;

import android.app.Activity;

import com.hsy.study.loginlibrary.LoginConfig;
import com.hsy.study.loginlibrary.type.callback.ThreeAuthCallBack;
import com.hsy.study.loginlibrary.type.interfaces.ILoginThree;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 微信登录
 * @author haosiyuan
 * @date 2019/3/11 4:44 PM
 */
public class WeChatLogin implements ILoginThree {

    @Override
    public void login(LoginConfig config, Activity activity, ThreeAuthCallBack callBack) {
        UMShareAPI.get(config.getApplication()).getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, callBack);
    }
}
