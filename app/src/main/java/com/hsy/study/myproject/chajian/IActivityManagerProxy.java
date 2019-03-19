package com.hsy.study.myproject.chajian;

import android.content.Intent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.security.auth.Subject;

/**
 * IActivityManager的代理类 hook点
 * @author haosiyuan
 * @date 2019/3/17 8:33 PM
 */
public class IActivityManagerProxy implements InvocationHandler {

    private Object activityManager;

    public IActivityManagerProxy(Object activityManager) {
        this.activityManager = activityManager;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {

        if ("startActivity".equals(method.getName())) {
            //启动Activity时
            Intent intent = null;

            int index = 0;

            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof  Intent) {
                    index = i;
                    break;
                }
            }
            //替换为占坑activity
            intent = (Intent) args[index];
            Intent newIntent = new Intent();
            String packageName = "com.hsy.study.myproject";
            newIntent.setClassName(packageName, packageName + ".Test1");
            newIntent.putExtra("target", intent);
            args[index] = newIntent;
        }

        return method.invoke(activityManager, args);

    }
}
