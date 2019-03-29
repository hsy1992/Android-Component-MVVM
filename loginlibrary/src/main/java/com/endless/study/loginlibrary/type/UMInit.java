package com.endless.study.loginlibrary.type;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * 友盟初始化
 * @author haosiyuan
 * @date 2019/3/17 10:09 AM
 */
public class UMInit {

    UMInit() {
    }

    public void initUM(Context context, String umKey) {
        UMConfigure.init(context, umKey, "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    public UMInit withQQ(String appId, String appSecret) {
        PlatformConfig.setQQZone(appId, appSecret);
        return this;
    }

    public UMInit withWeChat(String appId, String appSecret) {
        PlatformConfig.setWeixin(appId, appSecret);
        return this;
    }

    public UMInit withWeiBo(String appId, String appSecret, String url) {
        PlatformConfig.setSinaWeibo(appId, appSecret, url);
        return this;
    }

}
