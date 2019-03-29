package com.endless.permission.set;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.endless.permission.interfaces.ISet;

/**
 * 默认去设置
 * @author haosiyuan
 * @date 2019/3/29 3:42 PM
 */
class DefaultSet implements ISet {

    private Context context;

    DefaultSet(Context context) {
        this.context = context;
    }

    @Override
    public void goSet() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }
}
