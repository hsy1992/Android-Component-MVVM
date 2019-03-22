package com.hsy.study.myproject.dagger;


import com.hsy.study.baselibrary.dagger.scope.AppScope;
import com.hsy.study.myproject.User;
import com.hsy.study.myproject.UserContract;
import com.hsy.study.myproject.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author haosiyuan
 * @date 2019/2/20 2:35 PM
 */
@Module
public abstract class UserModule {

    @AppScope
    @Provides
    static List<User> provideUserList() {
        return new ArrayList<>();
    }

    @AppScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(UserContract.View rootView) {
        return new LinearLayoutManager(rootView.getContext());
    }

//
//    @AppScope
//    abstract ViewModel provideUserViewModel(UserViewModel viewModel);

    @Binds
    abstract UserContract.Model provideUserModel(UserModel model);
}
