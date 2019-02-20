package com.hsy.study.myproject.di;

import android.content.Context;

import com.hsy.study.baselibrary.dagger.scope.ActivityScope;
import com.hsy.study.myproject.User;
import com.hsy.study.myproject.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.Module;
import dagger.Provides;

/**
 * @author haosiyuan
 * @date 2019/2/20 2:35 PM
 */
@Module
public abstract class UserModule {

    @ActivityScope
    @Provides
    static List<User> provideUserList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @ActivityScope
    @Provides
    static UserViewModel provideUserViewModel(Context context) {
        return ViewModelProviders.of((FragmentActivity) context).get(UserViewModel.class);
    }


}
