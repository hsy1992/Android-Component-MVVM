package com.endless.study.loginlibrary.entity;

/**
 * 用户信息
 * @author haosiyuan
 * @date 2019/3/17 11:10 AM
 */
public class UserEntity {

    private String uid;

    private String name;

    private String gender;

    private String iconUrl;

    public UserEntity(String uid, String name, String gender, String iconUrl) {
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.iconUrl = iconUrl;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
