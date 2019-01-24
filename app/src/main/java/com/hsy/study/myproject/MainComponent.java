package com.hsy.study.myproject;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author haosiyuan
 * @date 2019/1/6 下午7:29
 */
@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
