package com.endless.study.myproject.viewmodel;

import android.app.Application;

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
public class UserViewModel extends BaseViewModel<UserContract.Model> {

    @Inject
    List<com.endless.study.myproject.User> users;

    @Inject
    public UserViewModel(Application application, UserContract.Model model) {
        super(application, model);
    }

    public void test() {
        users.size();
    }

}
