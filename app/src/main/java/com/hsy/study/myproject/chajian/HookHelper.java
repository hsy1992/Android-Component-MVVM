package com.hsy.study.myproject.chajian;

import com.hsy.study.baselibrary.common.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * @author haosiyuan
 * @date 2019/3/17 8:42 PM
 */
public class HookHelper {


    public static void hookAMS() throws Exception {
        Object defaultSingleton = null;
        Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
        //获取ActivityManager中的IActivityManager实例
        defaultSingleton = FieldUtil.getField(activityManagerClazz, null, "IActivityManagerSingleton");

        Class<?> singleton = Class.forName("android.util.Singleton");

        Field mInstanceField = FieldUtil.getField(singleton, "mInstance");
        //得到 IActivityManagerSingleton
        Object iActivityManager = mInstanceField.get(defaultSingleton);

        Class<?> iActivityManagerClazz = Class.forName("android.app.IActivityManager");

        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
        , new Class[]{iActivityManagerClazz}, new IActivityManagerProxy(iActivityManager));

        mInstanceField.set(defaultSingleton, proxy);

    }

}
