package com.hsy.study.baselibrary.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 文件操作类
 * @author haosiyuan
 * @date 2019/1/28 1:45 PM
 */
public class UtilFile {


    /**
     * 创建未存在的文件夹
     * @param file
     * @return
     */
    public static File makeDirs(File file){
        if (!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取缓存文件夹
     * @param context
     * @return
     */
    public static File getCacheFile(Context context){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File file = null;
            //获取系统缓存文件夹
            file = context.getExternalCacheDir();
            if (file == null){
                //获取自定义缓存文件夹
                file = new File(getCacheFilePath(context));
                makeDirs(file);
            }

            return file;
        } else {
            return context.getCacheDir();
        }
    }

    /**
     * 获取自定义缓存文件地址
     * @param context
     * @return
     */
    public static String getCacheFilePath(Context context) {
        String packageName = context.getPackageName();
        return "/mnt/sdcard/" + packageName;
    }

}
