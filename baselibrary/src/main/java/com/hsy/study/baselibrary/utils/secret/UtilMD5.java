package com.hsy.study.baselibrary.utils.secret;

import java.security.MessageDigest;

/**
 * md5加密工具
 * @author haosiyuan
 * @date 2019/3/22 1:47 PM
 */
public class UtilMD5 {

    /**
     * 盐，用于混交md5
     */
    private static final String slat = "&%5123***&&%%$$#@";

    public static String getMD5(String dataStr) {
        try {
            dataStr = dataStr + slat;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF-8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
