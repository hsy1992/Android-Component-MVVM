package com.hsy.study.myproject.di;

import android.content.Context;

import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.baselibrary.dagger.scope.ActivityScope;
import com.hsy.study.myproject.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import dagger.BindsInstance;
import dagger.Component;

/**
 * @author haosiyuan
 * @date 2019/2/20 2:35 PM
 */
@ActivityScope
@Component(modules = UserModule.class, dependencies = AppComponent.class)
public interface UserComponent {
    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserComponent.Builder context(Context context);
        UserComponent.Builder appComponent(AppComponent appComponent);
        UserComponent build();
    }

}
