package com.hsy.study.myproject.viewmodel;


import android.app.Application;

import com.hsy.study.baselibrary.dagger.scope.AppScope;
import com.hsy.study.baselibrary.database.entity.User;
import com.hsy.study.baselibrary.utils.CommonUtil;
import com.hsy.study.baselibrary.viewmodel.BaseViewModel;
import com.hsy.study.myproject.UserContract;

import java.util.List;

import javax.inject.Inject;


/**
 * @author haosiyuan
 * @date 2019/2/20 2:32 PM
 */
@AppScope
public class UserViewModel extends BaseViewModel<UserContract.View, UserContract.Model> {

    @Inject
    public UserViewModel(Application applicatio, UserContract.View rootView) {
        super(applicatio, rootView);
    }

    public void test() {
        rootView.showUser();
    }

    public void insertUser() {

        CommonUtil.getAppComponent(mApplication)
                .getAppDatabase()
                .userDao()
                .insertUsers(new User(1,"15561541897","haosiyuan","1.0","0"));

    }

    public List<User> query() {
        return CommonUtil.getAppComponent(mApplication)
                .getAppDatabase()
                .userDao()
                .loadAllUsers();
    }

    public void queryrx() {
//        model.getUsers();
    }

    public void getData() {

    }
}
