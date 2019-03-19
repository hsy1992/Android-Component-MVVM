package com.hsy.study.baselibrary.utils;

import android.content.Context;

import com.hsy.study.baselibrary.base.IApp;
import com.hsy.study.baselibrary.dagger.component.AppComponent;

/**
 * 通用工具类
 * @author haosiyuan
 * @date 2019/2/19 2:30 PM
 */
public class CommonUtil {

    /**
     * 获取 AppComponent
     * @param context
     * @return
     */
    public static AppComponent getAppComponent(Context context) {

        PreconditionsUtil.checkNotNull(context, "Context can not be null");
        PreconditionsUtil.checkState(context.getApplicationContext() instanceof IApp, "context is not instanceof App");
        return ((IApp) context.getApplicationContext()).getAppComponent();
    }

}
