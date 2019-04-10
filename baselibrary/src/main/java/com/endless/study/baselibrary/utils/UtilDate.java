package com.endless.study.baselibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期
 * @author haosiyuan
 * @date 2019/4/9 3:55 PM
 */
public class UtilDate {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public static String getDateWithYMDHMS(){
        return simpleDateFormat.format(new Date());
    }

}
