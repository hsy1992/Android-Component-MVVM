package com.hsy.study.myproject.chajian;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射获取
 * @author haosiyuan
 * @date 2019/3/17 8:16 PM
 */
public class FieldUtil {


    public static Method getMethod(Class<?> clazz, String methodStr) {

        try {
            Method method = clazz.getDeclaredMethod(methodStr);
            method.setAccessible(true);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object getField(Class<?> clazz, Object target, String field) {

        try {
            Field field1 = clazz.getDeclaredField(field);
            field1.setAccessible(true);
            return field1.get(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Field getField(Class<?> clazz, String field) {

        try {
            Field field1 = clazz.getDeclaredField(field);
            field1.setAccessible(true);
            return field1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setField(Class<?> clazz,Object object, String field, Object value) {

        try {
            Field field1 = clazz.getDeclaredField(field);
            field1.setAccessible(true);
            field1.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
