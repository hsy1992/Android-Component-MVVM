package com.endless.study.myproject.viewmodel;

import com.endless.study.baselibrary.dagger.scope.AppScope;
import com.endless.study.baselibrary.database.entity.User;
import com.endless.study.baselibrary.utils.UtilCommon;
import com.endless.study.baselibrary.mvvm.viewmodel.BaseViewModel;
import com.endless.study.myproject.UserContract;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;


/**
 * @author haosiyuan
 * @date 2019/2/20 2:32 PM
 */
@AppScope
public class UserViewModel extends BaseViewModel<UserContract.View, UserContract.Model> {

    @Inject
    List<com.endless.study.myproject.User> users;

    @Inject
    public UserViewModel(UserContract.View rootView, UserContract.Model model) {
        super(rootView, model);
    }

    public void test() {
        users.size();
        rootView.showUser();
    }

    public void insertUser(int id) {
        UtilCommon.getAppComponent(mApplication)
                .getAppDatabase()
                .userDao()
                .insertUsers(new User(id,"15561541897","haosiyuan","1.0","0"));

    }

    public List<User> query() {
        return UtilCommon.getAppComponent(mApplication)
                .getAppDatabase()
                .userDao()
                .loadAllUsers();
    }

    public LiveData<List<User>> queryrx() {
        return null;
//        model.getUsers();
    }

    public void getData() {

    }
}
