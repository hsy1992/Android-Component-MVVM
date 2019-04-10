package com.endless.study.myproject.model;

import android.app.Application;

import com.endless.study.baselibrary.dagger.scope.AppScope;
import com.endless.study.baselibrary.database.entity.UserEntity;
import com.endless.study.baselibrary.http.response.ApiResponse;
import com.endless.study.baselibrary.mvvm.model.BaseModel;
import com.endless.study.baselibrary.repository.DataResource;
import com.endless.study.baselibrary.repository.IRepositoryManager;
import com.endless.study.baselibrary.repository.NetworkBoundResource;
import com.endless.study.myproject.UserContract;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

/**
 * @author haosiyuan
 * @date 2019/3/5 4:07 PM
 */
@AppScope
public class UserModel extends BaseModel implements UserContract.Model {

    @Inject
    public UserModel(IRepositoryManager mRepositoryManager, Application mApplication) {
        super(mRepositoryManager, mApplication);
    }

    @Override
    public LiveData<DataResource<UserEntity>> getUsers() {
        return new NetworkBoundResource<UserEntity, UserEntity>(appExecutors) {
            @Override
            protected LiveData<UserEntity> loadFromDb() {
                return null;
            }

            @Override
            protected boolean shouldFetchData(UserEntity user) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<UserEntity>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(UserEntity item) {


            }
        }.asLiveData();
    }

}
