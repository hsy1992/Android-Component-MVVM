package com.endless.permission.set;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import com.endless.permission.interfaces.ISet;

/**
 * ViVo 跳转设置
 * @author haosiyuan
 * @date 2019/3/29 4:07 PM
 */
public class ViVoSet implements ISet {

    private Context context;

    ViVoSet(Context context) {
        this.context = context;
    }

    @Override
    public void goSet() {
        Intent appIntent = context.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
        if (appIntent != null && Build.VERSION.SDK_INT < 23) {
            context.startActivity(appIntent);
            return;
        }

        Intent vIntent = new Intent();
        vIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        vIntent.setAction(Settings.ACTION_SETTINGS);
        context.startActivity(vIntent);
    }
}
