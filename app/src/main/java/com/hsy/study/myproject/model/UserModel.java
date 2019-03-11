package com.hsy.study.myproject.model;

import android.app.Application;

import com.hsy.study.baselibrary.dagger.scope.AppScope;
import com.hsy.study.baselibrary.database.entity.User;
import com.hsy.study.baselibrary.model.BaseModel;
import com.hsy.study.baselibrary.utils.logger.Logger;
import com.hsy.study.baselibrary.viewmodel.IRepositoryManager;
import com.hsy.study.myproject.UserContract;

import java.util.List;

import javax.inject.Inject;

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
    public List<User> getUsers() {
//        return Observable
//                .just(mAppDatabase.userDao().loadAllUsersByRxJava())
//                .flatMap(new Function<Flowable<List<User>>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(Flowable<List<User>> listFlowable) throws Exception {
//                        return listFlowable.;
//                    }
//                });

        mAppDatabase.userDao().loadAllUsersByRxJava()
                .doOnSubscribe(subscription -> Logger.errorInfo(Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> Logger.errorInfo(Thread.currentThread().getName()))
                .map(new Function<List<User>, List<User>>() {
                    @Override
                    public List<User> apply(List<User> users) throws Exception {
                        Logger.errorInfo(Thread.currentThread().getName());
                        return users;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        Logger.errorInfo(Thread.currentThread().getName());
                    }
                });

        return null;
    }
}
