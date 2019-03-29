package com.endless.study.baselibrary.dagger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;

/**
 * 自定义注解 Model 为 key
 * 修饰 Method
 * @author haosiyuan
 * @date 2019/3/7 3:57 PM
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}
