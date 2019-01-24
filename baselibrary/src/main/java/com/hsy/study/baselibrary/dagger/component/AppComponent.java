package com.hsy.study.baselibrary.dagger.component;

import com.hsy.study.baselibrary.dagger.module.ClientModule;
import com.hsy.study.baselibrary.dagger.module.GlobalConfigModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author haosiyuan
 * @date 2018/12/31 下午3:49
 */
@Singleton
@Component(modules = {ClientModule.class, GlobalConfigModule.class})
public interface AppComponent {


}
