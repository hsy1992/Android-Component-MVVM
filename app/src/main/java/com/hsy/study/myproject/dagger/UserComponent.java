package com.hsy.study.myproject.dagger;


import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.baselibrary.dagger.scope.AppScope;
import com.hsy.study.myproject.ui.MainActivity;
import com.hsy.study.myproject.UserContract;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author haosiyuan
 * @date 2019/2/20 2:35 PM
 */
@AppScope
@Component(modules = {UserModule.class, ViewModelModule.class}, dependencies = AppComponent.class)
public interface UserComponent {
    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserComponent.Builder view(UserContract.View rootView);
        UserComponent.Builder appComponent(AppComponent appComponent);
        UserComponent build();
    }

}
