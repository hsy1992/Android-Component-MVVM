package com.hsy.study.myproject.hook;

import java.lang.reflect.Proxy;

/**
 * @author haosiyuan
 * @date 2019/3/11 8:44 PM
 */
public class Client {


    public static void main(String[] args) {

        IHook model = new HookModel();
        IHook iHook = (IHook) Proxy.newProxyInstance(model.getClass().getClassLoader(),
                new Class[]{IHook.class}, new HookModelDynamic(model));
        iHook.printHello("sadfsadf");
    }
}
