package com.endless.study.baselibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

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


}
