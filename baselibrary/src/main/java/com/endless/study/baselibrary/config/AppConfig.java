package com.endless.study.baselibrary.config;

/**
 * App的一些配置
 * @author haosiyuan
 * @date 2019/3/5 1:22 PM
 */
public interface AppConfig {

    /**
     * 系统数据库 用于未登录的用户
     */
    String DATABASE_NAME = "app_system.db";

    /**
     * 每页数量
     */
    int PAGE_SIZE = 10;

}
