package com.endless.study.myproject.viewmodel;

import com.endless.study.baselibrary.common.exception.AppException;
import com.endless.study.baselibrary.dagger.scope.AppScope;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * {@link ViewModel} 工厂
 * @author haosiyuan
 * @date 2019/3/7 4:16 PM
 */
@AppScope
public class AppViewModelFactory implements ViewModelProvider.Factory {

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public AppViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        for (Class<? extends ViewModel> clazz : creators.keySet()) {

            if (clazz.isAssignableFrom(modelClass) && clazz == modelClass) {
                return (T) creators.get(modelClass).get();
            }
        }
        throw new AppException("modelClass can not create");
    }
}
