package com.endless.study.baselibrary.utils;

import okhttp3.MediaType;

/**
 * 请求类型
 * @author haosiyuan
 * @date 2019/1/27 7:52 PM
 */
public class UtilMediaType {

    /**
     * 是否可以解析
     *
     * @param mediaType {@link MediaType}
     * @return {@code true} 为可以解析
     */
    public static boolean isParseable(MediaType mediaType) {
        if (mediaType == null || mediaType.type() == null) {
            return false;
        }
        return isText(mediaType) || isPlain(mediaType)
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType);
    }

    public static boolean isText(MediaType mediaType) {
        if (mediaType == null || mediaType.type() == null) {
            return false;
        }
        return mediaType.type().equals("text");
    }

    public static boolean isPlain(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("plain");
    }

    public static boolean isJson(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("json");
    }

    public static boolean isXml(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("xml");
    }

    public static boolean isHtml(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("html");
    }

    public static boolean isForm(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("x-www-form-urlencoded");
    }
}
