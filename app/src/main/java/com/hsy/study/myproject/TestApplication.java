package com.hsy.study.myproject;

import com.hsy.study.baselibrary.base.AppApplication;
import com.hsy.study.networkclientstate.NetWorkManager;

/**
 * @author haosiyuan
 * @date 2019/3/1 9:15 AM
 */
public class TestApplication extends AppApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        NetWorkManager.getInstance().startReceiver(this);
    }
}
