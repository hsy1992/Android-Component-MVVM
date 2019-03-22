package com.hsy.study.myproject;

import com.hsy.study.baselibrary.database.entity.User;
import com.hsy.study.baselibrary.mvvm.model.IModel;
import com.hsy.study.baselibrary.mvvm.view.IView;
import com.hsy.study.baselibrary.repository.DataResource;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * @author haosiyuan
 * @date 2019/2/28 11:17 AM
 */
public interface UserContract extends IView {

    interface View extends IView {
        void showUser();
    }

    interface Model extends IModel {
        LiveData<DataResource<User>> getUsers();
    }
}
