package com.hsy.study.myproject;

import android.app.Application;

import com.hsy.study.baselibrary.dagger.scope.ActivityScope;
import com.hsy.study.baselibrary.database.entity.User;
import com.hsy.study.baselibrary.utils.CommonUtil;
import com.hsy.study.baselibrary.utils.logger.Logger;
import com.hsy.study.baselibrary.viewmodel.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author haosiyuan
 * @date 2019/2/20 2:32 PM
 */
@ActivityScope
public class UserViewModel extends BaseViewModel<UserView> {

    @Inject
    public UserViewModel(@NonNull Application application) {
        super(application);
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
        CommonUtil.getAppComponent(mApplication)
                .getAppDatabase()
                .userDao()
                .loadAllUsersByRxJava()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    for (User user : users) {
                        Logger.errorInfo(user.toString());
                    }
                });

    }
}
