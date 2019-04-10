package com.endless.study.baselibrary.utils;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.List;

import androidx.annotation.NonNull;


/**
 * app 的相关信息
 * @author haosiyuan
 * @date 2019/3/22 2:34 PM
 */
public class UtilApp {

    private UtilApp() {}

    /**
     * 获取包信息
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * 获取版本信息
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);

        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return "";
    }

    /**
     * 是否安装某个包名的 App
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstanll(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

        for (PackageInfo packageInfo : packageInfos) {

            if (packageInfo.packageName.equalsIgnoreCase(packageName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 当前的包是否存在
     *
     * @param context
     * @param pckName
     * @return
     */
    public static boolean isPackageExist(Context context, String pckName) {
        try {
            PackageInfo pckInfo = context.getPackageManager()
                    .getPackageInfo(pckName, 0);
            if (pckInfo != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return false;
    }

    /**
     * 查看某个服务是否运行
     * @param context
     * @param serviceIntent
     * @return
     */
    public static boolean isServiceRunning(Context context, @NonNull Intent serviceIntent) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        PendingIntent pendingIntent = activityManager.getRunningServiceControlPanel(serviceIntent.getComponent());

        return pendingIntent != null;
    }

}
