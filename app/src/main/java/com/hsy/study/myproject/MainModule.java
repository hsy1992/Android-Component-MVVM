package com.hsy.study.myproject;

import android.app.Application;

import com.hsy.study.myproject.db.DbModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author haosiyuan
 * @date 2019/1/6 下午8:33
 */
@Module(includes = {
        DbModule.class
})
public final class MainModule {

    private final Application application;

    MainModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return application;
    }
}
