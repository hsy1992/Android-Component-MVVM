package com.hsy.study.myproject.hook;

/**
 * @author haosiyuan
 * @date 2019/3/11 8:42 PM
 */
public class HookModel implements IHook {


    @Override
    public void printHello(String message) {
        System.out.println(message);

    }
}
