package com.endless.study.myproject.database.dao;


import com.endless.study.myproject.database.entity.Test;

import androidx.room.Dao;
import androidx.room.Insert;

/**
 * @author haosiyuan
 * @date 2019/3/22 1:34 PM
 */
@Dao
public interface TestDao {

    @Insert
    void insertTest(Test... test);

}
