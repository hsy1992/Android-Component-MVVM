package com.hsy.study.myproject.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.hsy.study.baselibrary.common.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author haosiyuan
 * @date 2019/3/11 8:52 PM
 */
public class HookStartActivity extends Instrumentation{

    private Instrumentation instrumentation;

    public HookStartActivity(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                            Intent intent, int requestCode, Bundle options) {
        Logger.errorInfo("hook 成功");

        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, Integer.class, Bundle.class);

            return (ActivityResult) execStartActivity.invoke(instrumentation, who, contextThread,token,
                    target, intent, requestCode, options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void replaceActivityInstrumentation(Activity activity) {
        Field field = null;
        try {
            field = Activity.class.getDeclaredField("mInstrumentation");

            field.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) field.get(activity);
            //用反射替换Activity中Instrumentation
            Instrumentation hookStartActivity = new HookStartActivity(instrumentation);
            field.set(activity, hookStartActivity);
            Logger.errorInfo(">>>>>>>>>>>>");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.errorInfo("Exception>>>>>>>>>>>>");
        }
    }
}
