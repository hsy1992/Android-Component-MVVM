package com.endless.permission.aop;

import android.app.Activity;
import android.content.Context;

import com.endless.permission.annotation.PermissionContext;
import com.endless.permission.annotation.PermissionNeed;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import androidx.fragment.app.Fragment;

/**
 * 权限
 * @author haosiyuan
 * @date 2019/3/29 4:25 PM
 */
@Aspect
public class PermissionAspect {

    /**
     * 注解Context时的缓存
     */
    private ConcurrentMap<Class<?>, Context> CONTEXT_CACHE = new ConcurrentHashMap<>();
    /**
     * Pointcut 需要的
     */
    private static final String PERMISSION_REQUEST_POINT_CUT =
                "execution(@com.endless.permission.annotation.PermissionNeed * *（..)";

    @Pointcut(PERMISSION_REQUEST_POINT_CUT + " &&  @annotation(permissionNeed)")
    public void permissionNeed(PermissionNeed permissionNeed) {

    }

    @Around("permissionNeed(permissionNeed)")
    public Object aroundPoint(final ProceedingJoinPoint joinPoint, PermissionNeed permissionNeed) {

        Object object = joinPoint.getThis();
        Context context = null;

        if (object == null) {
            throw new RuntimeException("Object can not be null");
        }

        if (object instanceof Activity) {
            context = (Context) object;
        } else if (object instanceof Fragment) {
            context = ((Fragment)object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            context = ((android.app.Fragment)object).getActivity();
        } else {
            context = getContextFromObject(object);
        }

        if (context == null) {
            throw new RuntimeException("Context can not be null");
        }



        return object;
    }


    /**
     * 从对象中获取
     * @param object
     * @return
     */
    private Context getContextFromObject(Object object) {

        Context context = null;

        Set<Class<?>> classes = getClassesFor(object.getClass());

        //注解在父类时 查看父类是否有缓存
        for (Class<?> clazz : classes) {

            if (CONTEXT_CACHE.containsKey(clazz)) {
                context = CONTEXT_CACHE.get(clazz);
                break;
            } else {
                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {
                    if (field.getAnnotation(PermissionContext.class) != null) {

                        try {
                            Object clazzObject = clazz.newInstance();
                            context = (Context) field.get(clazzObject);
                            break;
                        } catch (Exception e) {
                            continue;
                        }
                    }
                }
            }
        }

        return context;
    }

    /**
     * 获取所有父类Class
     * @param concreteClass
     * @return
     */
    private Set<Class<?>> getClassesFor(Class<?> concreteClass) {

        List<Class<?>> parents = new LinkedList<>();
        Set<Class<?>> classes = new HashSet<>();

        parents.add(concreteClass);

        while (!parents.isEmpty()) {
            Class<?> clazz = parents.remove(0);
            classes.add(clazz);

            Class<?> parent = clazz.getSuperclass();
            if (parent != null) {
                parents.add(parent);
            }
        }
        return classes;
    }

}
