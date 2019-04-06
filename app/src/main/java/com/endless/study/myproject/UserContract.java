package com.endless.study.myproject;

import com.endless.study.baselibrary.database.entity.SystemUser;
import com.endless.study.baselibrary.mvvm.model.IModel;
import com.endless.study.baselibrary.mvvm.view.IView;
import com.endless.study.baselibrary.repository.DataResource;

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
        LiveData<DataResource<SystemUser>> getUsers();
    }
}
