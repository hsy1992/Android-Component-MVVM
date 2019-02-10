package com.hsy.study.baselibrary.utils;

/**
 * 断言
 * @author haosiyuan
 * @date 2019/2/2 5:02 PM
 */
public class Preconditions {

    private Preconditions() {
        throw new AssertionError("No instances.");
    }

    /**
     * 不为空
     * @param value
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T value, String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }


}
