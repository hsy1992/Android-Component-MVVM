package com.endless.study.loginlibrary.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author haosiyuan
 * @date 2019/3/14 5:23 PM
 */
public class StringUtil {
    /**
     * 手机号正则
     */
    private static Pattern phonePattern = Pattern.compile("^(13[0-9]|14[0-9]|15([0-9])|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\d{8}$");

    public static boolean isPhoneNum(String phoneNumber) {
        Matcher m = phonePattern.matcher(phoneNumber);
        return m.matches();
    }

}
