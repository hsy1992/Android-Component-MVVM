package com.hsy.study.myproject.dbt;

import com.squareup.sqldelight.Transacter;

/**数据库操作接口
 * @author haosiyuan
 * @date 2019/1/18 11:19 AM
 */
public interface DbAction {

    void createQuery(Transacter dbController);

    void createObservableQuery(Transacter dbController);

    void executeUpdate(Transacter dbController);

    void executeInsert(Transacter dbController);

    void executeDelete(Transacter dbController);
}
