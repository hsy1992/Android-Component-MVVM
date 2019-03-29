package com.endless.study.myproject.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author haosiyuan
 * @date 2019/3/22 1:27 PM
 */
@Entity(tableName = "tb_test")
public class Test {

    @PrimaryKey
    private int id;

    public Test(int id) {
        this.id = id;
    }
}
