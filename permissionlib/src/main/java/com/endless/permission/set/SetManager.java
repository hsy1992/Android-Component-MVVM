package com.endless.permission.set;

import android.content.Context;
import android.os.Build;

import com.endless.permission.interfaces.ISet;

/**
 * 去设置
 * @author haosiyuan
 * @date 2019/3/29 3:42 PM
 */
public class SetManager {

    /**
     * 华为
     */
    private static final String MANUFACTURER_HUAWEI = "Huawei";
    /**
     * 魅族
     */
    private static final String MANUFACTURER_MEIZU = "Meizu";
    /**
     * 小米
     */
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";
    /**
     * 索尼
     */
    private static final String MANUFACTURER_SONY = "Sony";
    /**
     * OPPO
     */
    private static final String MANUFACTURER_OPPO = "OPPO";
    /**
     * LG
     */
    private static final String MANUFACTURER_LG = "LG";
    /**
     * vivo
     */
    private static final String MANUFACTURER_VIVO = "vivo";
    /**
     * 三星
     */
    private static final String MANUFACTURER_SAMSUNG = "samsung";
    /**
     * 乐视
     */
    private static final String MANUFACTURER_LETV = "Letv";
    /**
     * 中兴
     */
    private static final String MANUFACTURER_ZTE = "ZTE";
    /**
     * 酷派
     */
    private static final String MANUFACTURER_YULONG = "YuLong";
    /**
     * 联想
     */
    private static final String MANUFACTURER_LENOVO = "LENOVO";

    /**
     * 去设置页面
     * @param context
     */
    public static void goSet(Context context) {

        if (context != context.getApplicationContext()) {
            throw new IllegalArgumentException("You must use Application Context");
        }

        ISet set = getSet(context);
        set.goSet();
    }

    private static ISet getSet(Context context) {
        ISet set;
        String phoneType = Build.MANUFACTURER;

        switch (phoneType) {
            case MANUFACTURER_VIVO:
                set = new ViVoSet(context);
                break;
            default:
                set = new DefaultSet(context);
                break;
        }

        return set;
    }
}
