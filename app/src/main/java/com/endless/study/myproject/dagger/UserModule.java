package com.endless.study.myproject.dagger;


import com.endless.study.baselibrary.dagger.scope.AppScope;
import com.endless.study.myproject.User;
import com.endless.study.myproject.UserContract;
import com.endless.study.myproject.model.UserModel;

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


    @Binds
    abstract UserContract.Model provideUserModel(UserModel model);
}
