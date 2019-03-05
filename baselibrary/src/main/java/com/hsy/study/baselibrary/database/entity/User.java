package com.hsy.study.baselibrary.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 登录用户
 * @Ignore 标识不会映射到表中
 *
 *
 * @author haosiyuan
 * @date 2019/3/5 9:08 AM
 */
@Entity(tableName = "tb_user")
public class User {

    @PrimaryKey
    public int id;

    public String phone;

    public String name;

    public String version;

    /**
     * 是否登录 0 为已登录  1 为未登录
     */
    public String isLogin;

    public User(int id, String phone, String name, String version, String isLogin) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.version = version;
        this.isLogin = isLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", isLogin='" + isLogin + '\'' +
                '}';
    }
}
