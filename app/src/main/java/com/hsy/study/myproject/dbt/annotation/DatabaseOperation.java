package com.hsy.study.myproject.dbt.annotation;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**数据库操作类
 * @author haosiyuan
 * @date 2019/1/18 8:46 AM
 */
public interface DatabaseOperation {

    int CREATE_TABLE = 0;

    int QUERY = 1;

    int INSERT = 2;

    int UPDATE = 3;

    int DELETE = 4;

    @IntDef({
            CREATE_TABLE,
            QUERY,
            INSERT,
            UPDATE,
            DELETE
    })

    @Retention(SOURCE)
    @interface Operation{
    }

}
