package com.hsy.study.baselibrary.dagger.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**dagger2 注解
 * @author haosiyuan
 * @date 2018/12/31 下午4:18
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScope {}
