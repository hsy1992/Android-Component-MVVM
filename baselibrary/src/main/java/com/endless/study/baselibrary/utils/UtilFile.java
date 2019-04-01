package com.endless.study.baselibrary.utils;

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


    /**
     * 使用递归获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                //递归调用继续统计
                dirSize += getDirSize(file);
            }
        }
        return dirSize;
    }

    /**
     * 使用递归删除文件夹
     *
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                //递归调用继续删除
                deleteDir(file);
            }
        }
        return true;
    }

}
