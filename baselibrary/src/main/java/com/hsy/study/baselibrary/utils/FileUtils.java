package com.hsy.study.baselibrary.utils;

import java.io.File;

/**
 * 文件操作类
 * @author haosiyuan
 * @date 2019/1/28 1:45 PM
 */
public class FileUtils {


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
}
