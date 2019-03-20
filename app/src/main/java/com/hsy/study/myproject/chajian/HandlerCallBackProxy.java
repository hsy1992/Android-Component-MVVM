package com.hsy.study.myproject.chajian;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author haosiyuan
 * @date 2019/3/19 9:01 PM
 */
public class HandlerCallBackProxy implements Handler.Callback {

    public static final int LAUNCH_ACTIVITY = 100;

    private Handler handler;

    public HandlerCallBackProxy(Handler handler) {
        this.handler = handler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == LAUNCH_ACTIVITY) {
            Object r = msg.obj;

            //得到消息中的Intent
            Intent intent = (Intent) FieldUtil.getField(r.getClass(), r, "intent");
            Intent target = intent.getParcelableExtra("target");
            //将启动的Intent 替换
            intent.setComponent(target.getComponent());

        }

        handler.handleMessage(msg);
        return true;
    }
}
