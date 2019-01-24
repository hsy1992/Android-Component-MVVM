package com.hsy.study.myproject

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

/**
 *
 * @author haosiyuan
 * @date 2019/1/14 4:26 PM
 */
class dbtest{

    fun getDb(context: Context){
        val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "test.db")

    }

}

