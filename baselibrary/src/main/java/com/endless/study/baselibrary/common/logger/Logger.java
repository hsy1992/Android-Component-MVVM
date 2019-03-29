package com.endless.study.baselibrary.common.logger;

import android.text.TextUtils;
import android.util.Log;

/**日志工具类
 * @author haosiyuan
 * @date 2019/1/27 11:28 AM
 */
public class Logger {

    private static final String DEFAULT_TAG = "Logger";
    private static boolean isLog = true;

    private Logger() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static boolean isLog() {
        return isLog;
    }

    public static void setLog(boolean isLog) {
        Logger.isLog = isLog;
    }

    public static void debugInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.d(tag, msg);
    }

    public static void debugInfo(String msg) {
        debugInfo(DEFAULT_TAG, msg);
    }

    public static void warnInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.w(tag, msg);
    }

    public static void warnInfo(String msg) {
        warnInfo(DEFAULT_TAG, msg);
    }


    public static void errorInfo(String msg) {
        errorInfo(DEFAULT_TAG, msg);

    }

    public static void errorInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.e(tag, msg);
    }
    /**
     * 这里使用自己分节的方式来输出足够长度的 message
     *
     * @param tag 标签
     * @param msg 日志内容
     */
    public static void debugLongInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) return;
        msg = msg.trim();
        int index = 0;
        int maxLength = 3500;
        String sub;
        while (index < msg.length()) {
            if (msg.length() <= index + maxLength) {
                sub = msg.substring(index);
            } else {
                sub = msg.substring(index, index + maxLength);
            }

            index += maxLength;
            Log.d(tag, sub.trim());
        }
    }

    public static void debugLongInfo(String msg) {
        debugLongInfo(DEFAULT_TAG, msg);
    }
}
