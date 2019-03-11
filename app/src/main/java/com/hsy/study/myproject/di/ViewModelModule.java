package com.hsy.study.myproject.di;

import com.hsy.study.baselibrary.dagger.annotation.ViewModelKey;
import com.hsy.study.baselibrary.viewmodel.AppViewModelFactory;
import com.hsy.study.myproject.viewmodel.UserViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * {@link ViewModel} 的 Module
 * @author haosiyuan
 * @date 2019/3/7 3:10 PM
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(value = UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(AppViewModelFactory factory);
}
