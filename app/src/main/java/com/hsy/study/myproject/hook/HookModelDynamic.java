package com.hsy.study.myproject.hook;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author haosiyuan
 * @date 2019/3/11 8:43 PM
 */
public class HookModelDynamic implements InvocationHandler {

    private Object object;

    public HookModelDynamic(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass().getName() +">>>>" + method.getName() +">>>" + args);
        Object result = method.invoke(object, args);
        return result;
    }
}
