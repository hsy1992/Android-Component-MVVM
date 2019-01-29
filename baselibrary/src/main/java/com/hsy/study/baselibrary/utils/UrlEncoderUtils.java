package com.hsy.study.baselibrary.utils;

/**
 * url 解析与编码
 * @author haosiyuan
 * @date 2019/1/28 9:51 AM
 */
public class UrlEncoderUtils {

    private UrlEncoderUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 判断str是否被 URLEncoder.encode()过
     * @param str 需要判断的内容
     * @return
     */
    public static boolean hasUrlEncoded(String str){
        boolean encode = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '%' && (i + 2) < str.length()){
                //判断是否符合urlEncode规范
                char c1 = str.charAt(i + 1);
                char c2 = str.charAt(i + 2);
                if (isValidHexChar(c1) && isValidHexChar(c2)){
                    encode = true;
                }
                break;
            }
        }
        return encode;
    }

    /**
     * 判断 c 是否是 16 进制的字符
     *
     * @param c 需要判断的字符
     * @return 返回 {@code true} 为 16 进制的字符
     */
    private static boolean isValidHexChar(char c) {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
    }
}
