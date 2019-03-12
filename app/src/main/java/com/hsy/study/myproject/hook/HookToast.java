package com.hsy.study.myproject.hook;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import androidx.core.app.NotificationManagerCompat;

/**
 * 解决 通知被禁用无法弹出的问题  hook伪装系统toast
 * @author haosiyuan
 * @date 2019/3/12 3:06 PM
 */
public class HookToast {

    private static Object iNotificationManagerObj;
    private static Toast toast;

    /**
     * 消息是否开启
     * @param context
     * @return
     */
    private static boolean isNotificationEnabled(Context context) {

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
        return areNotificationsEnabled;
    }

    public static void toast(Context context, String message) {

        if (TextUtils.isEmpty(message)) {
            return;
        }

        //后setText 兼容小米默认会显示app名称的问题
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        }

        toast.setText(message);

        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (isNotificationEnabled(context)) {
                    Log.e("test",">>>>>>>");
                    toast.show();
                } else {
                    Log.e("test","hook>>>>>>>");
                    //不允许通知hook
                    showSystemMethod(toast);
                }
            }
        });
    }

    private static void showSystemMethod(Toast toast) {
        try {
            Method getServiceMethod = Toast.class.getDeclaredMethod("getService");
            getServiceMethod.setAccessible(true);

            if (iNotificationManagerObj == null) {
                //不传参数
                iNotificationManagerObj = getServiceMethod.invoke(toast);
                Class iNotificationManagerCls = Class.forName("android.app.INotificationManager");

                Object iNotificationManagerProxy = Proxy.newProxyInstance(toast.getClass().getClassLoader(), new Class[]{iNotificationManagerCls},
                        (proxy, method, args) -> {
                            //强制使用系统Toast
                            if ("enqueueToast".equals(method.getName())
                                    || "enqueueToastEx".equals(method.getName())) {  //华为p20 pro上为enqueueToastEx
                                args[0] = "android";
                            }
                            Log.e("test", "强制使用系统Toast>>>>>>>>>");
                            return method.invoke(iNotificationManagerObj, args);
                        });

                Field sServiceFiled = Toast.class.getDeclaredField("sService");
                sServiceFiled.setAccessible(true);
                sServiceFiled.set(toast, iNotificationManagerProxy);
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
