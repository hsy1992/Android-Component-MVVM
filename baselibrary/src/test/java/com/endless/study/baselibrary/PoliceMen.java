package com.endless.study.baselibrary;

/**
 * @author haosiyuan
 * @date 2019/2/18 3:10 PM
 */
public class PoliceMen extends Person {

    private static String newColor = "黄色";

    public static String getColor() {
        return newColor;
    }

    public String getBananaInfo() {
        return flavor() + getColor();
    }

    private String flavor() {
        return "帅气的";
    }

    public final boolean isPower() {
        return true;
    }
}
