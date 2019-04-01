package com.endless.study.baselibrary.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.view.ViewConfiguration;

/**
 * 设备相关
 * @author haosiyuan
 * @date 2019/4/1 1:58 PM
 */
public class UtilDevice {

    private static Boolean _hasCamera = null;
    public static boolean GTE_HC;
    public static boolean GTE_ICS;
    public static boolean PRE_HC;

    static {
        GTE_ICS = Build.VERSION.SDK_INT >= 14;
        GTE_HC = Build.VERSION.SDK_INT >= 11;
        PRE_HC = Build.VERSION.SDK_INT < 11;
    }

    /**
     * 设备是否有相机
     *
     * @param context
     * @return
     */
    public static final boolean hasCamera(Context context) {
        if (_hasCamera == null) {
            PackageManager pckMgr = context
                    .getPackageManager();
            boolean flag = pckMgr
                    .hasSystemFeature("android.hardware.camera.front");
            boolean flag1 = pckMgr.hasSystemFeature("android.hardware.camera");
            boolean flag2;
            if (flag || flag1) {
                flag2 = true;
            } else {
                flag2 = false;
            }

            _hasCamera = Boolean.valueOf(flag2);
        }
        return _hasCamera.booleanValue();
    }

    /**
     * 设备是否有实体菜单
     *
     * @param context
     * @return
     */
    public static boolean hasHardwareMenuKey(Context context) {
        boolean flag = false;
        if (PRE_HC)
            flag = true;
        else if (GTE_ICS) {
            flag = ViewConfiguration.get(context).hasPermanentMenuKey();
        } else
            flag = false;
        return flag;
    }

    /**
     * 当前是否有网
     *
     * @param context
     * @return
     */
    public static boolean hasInternet(Context context) {
        boolean flag;
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null && manager.getActiveNetworkInfo() != null)
            flag = true;
        else
            flag = false;
        return flag;
    }
}
