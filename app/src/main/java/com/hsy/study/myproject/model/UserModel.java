package com.hsy.study.myproject.model;

import android.app.Application;

import com.hsy.study.baselibrary.dagger.scope.AppScope;
import com.hsy.study.baselibrary.database.entity.User;
import com.hsy.study.baselibrary.http.response.ApiResponse;
import com.hsy.study.baselibrary.http.response.ApiSuccessResponse;
import com.hsy.study.baselibrary.mvvm.model.BaseModel;
import com.hsy.study.baselibrary.common.logger.Logger;
import com.hsy.study.baselibrary.repository.DataResource;
import com.hsy.study.baselibrary.repository.IRepositoryManager;
import com.hsy.study.baselibrary.repository.NetworkBoundResource;
import com.hsy.study.myproject.UserContract;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
    public LiveData<DataResource<User>> getUsers() {
        return new NetworkBoundResource<User, User>(appExecutors) {
            @Override
            protected LiveData<User> loadFromDb() {
                return null;
            }

            @Override
            protected boolean shouldFetchData(User user) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(User item) {


            }
        }.asLiveData();
    }

}
