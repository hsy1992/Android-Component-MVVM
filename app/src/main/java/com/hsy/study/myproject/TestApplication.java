package com.hsy.study.myproject;

import android.content.Context;

import com.hsy.study.baselibrary.base.AppApplication;
import com.hsy.study.myproject.chajian.HookHelper;
import com.hsy.study.networkclientstate.NetWorkManager;

/**
 * @author haosiyuan
 * @date 2019/3/1 9:15 AM
 */
public class TestApplication extends AppApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            HookHelper.hookAMS();
            HookHelper.hookHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        NetWorkManager.getInstance().startReceiver(this);


    }
}
