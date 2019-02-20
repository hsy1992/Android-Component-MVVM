package com.hsy.study.myproject;

import android.app.Application;

import com.hsy.study.baselibrary.dagger.scope.ActivityScope;
import com.hsy.study.baselibrary.utils.logger.Logger;
import com.hsy.study.baselibrary.viewmodel.BaseViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;

/**
 * @author haosiyuan
 * @date 2019/2/20 2:32 PM
 */
@ActivityScope
public class UserViewModel extends BaseViewModel {

    @Inject
    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void test() {
    }
}
