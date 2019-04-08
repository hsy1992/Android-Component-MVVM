package com.endless.study.baselibrary.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 字符串工具类
 * @author haosiyuan
 * @date 2019/4/8 2:34 PM
 */
public class UtilString {

    /**
     * 获取url host
     * @param link
     * @return
     */
    public static String getHost(String link) {
        URL url;
        String host = "";
        try {
            url = new URL(link);
            host = url.getHost();
        } catch (MalformedURLException e) {
        }
        return host;
    }
}
