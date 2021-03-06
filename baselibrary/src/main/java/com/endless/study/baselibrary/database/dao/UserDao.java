package com.endless.study.baselibrary.database.dao;

import com.endless.study.baselibrary.database.entity.UserEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;

/**
 * 用户表
 * @author haosiyuan
 * @date 2019/3/5 9:55 AM
 */
@Dao
public interface UserDao {

    /**
     * 插入用户 策略
     * 1. OnConflictStrategy.REPLACE：冲突策略是取代旧数据同时继续事务。
     * 2. OnConflictStrategy.ROLLBACK：冲突策略是回滚事务。
     * 3. OnConflictStrategy.ABORT：冲突策略是终止事务。
     * 4. OnConflictStrategy.FAIL：冲突策略是事务失败。
     * 5. OnConflictStrategy.IGNORE：冲突策略是忽略冲突。
     *
     * @param users
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(UserEntity... users);

    @Update
    int updateUsers(UserEntity... users);

    @Delete
    void deleteUsers(UserEntity... users);

    /**
     * 查询全部
     * @return
     */
    @Query("SELECT * FROM tb_user")
    List<UserEntity> loadAllUsers();

    /**
     * 带参查询
     * @param phone
     * @return
     */
    @Query("Select * from tb_user where phone = :phone")
    UserEntity loadAllUsersByPhone(String phone);

    /**
     * Observable的查询
     * @return
     */
    @Query("SELECT * FROM tb_user")
    LiveData<List<UserEntity>> loadAllUsersByLiveData();

    /**
     * rxJava的查询
     * @return
     */
    @Query("SELECT * FROM tb_user")
    Flowable<List<UserEntity>> loadAllUsersByRxJava();
}
