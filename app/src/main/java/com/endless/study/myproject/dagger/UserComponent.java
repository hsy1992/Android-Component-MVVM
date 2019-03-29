package com.endless.study.myproject.dagger;


import com.endless.study.baselibrary.dagger.component.AppComponent;
import com.endless.study.baselibrary.dagger.scope.AppScope;
import com.endless.study.myproject.ui.MainActivity;
import com.endless.study.myproject.UserContract;

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
