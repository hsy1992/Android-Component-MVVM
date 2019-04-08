package com.endless.permission.aop;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.endless.permission.PermissionActivity;
import com.endless.permission.annotation.Permission;
import com.endless.permission.annotation.PermissionCancel;
import com.endless.permission.annotation.PermissionContext;
import com.endless.permission.annotation.PermissionNeed;
import com.endless.permission.annotation.PermissionRefuse;
import com.endless.permission.entity.PermissionCancelEntity;
import com.endless.permission.entity.PermissionRefuseEntity;
import com.endless.permission.entity.ResultEntity;
import com.endless.permission.interfaces.IPermissionCallBack;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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

    private boolean hasInstallPackage = false;

    /**
     * Pointcut 需要的
     */
    private static final String PERMISSION_REQUEST_POINT_CUT =
                "execution(@com.endless.permission.annotation.PermissionNeed * *(..))";

    @Pointcut(PERMISSION_REQUEST_POINT_CUT + " && @annotation(permissionNeed)")
    public void permissionNeed(PermissionNeed permissionNeed) {

    }

    @Around("permissionNeed(permissionNeed)")
    public Object aroundPoint(final ProceedingJoinPoint joinPoint, PermissionNeed permissionNeed) {

        final Object object = joinPoint.getThis();
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

        String[] permissions = permissionNeed.permission();
        if (permissions.length == 0) {
            throw new RuntimeException("PermissionNeed is not need");
        }

        List<String> permissionList = Arrays.asList(permissions);

        if (permissionList.contains(Permission.PhoneGroup.REQUEST_INSTALL_PACKAGES)
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            permissionList.remove(Permission.PhoneGroup.REQUEST_INSTALL_PACKAGES);
            hasInstallPackage = true;
        }


        String[] strings = new String[permissionList.size()];
        permissionList.toArray(strings);

        PermissionActivity.requestPermission(context, strings, hasInstallPackage, new IPermissionCallBack() {
            @Override
            public void permissionGranted() {
                //权限同意
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void permissionDenied(PermissionRefuseEntity refuseEntity) {

                getAnnotationAndInvoke(object, PermissionRefuse.class, refuseEntity);
            }

            @Override
            public void permissionCanceled(PermissionCancelEntity cancelEntity) {

                getAnnotationAndInvoke(object, PermissionCancel.class, cancelEntity);
            }
        });

        return object;
    }

    /**
     * 获取注解并执行
     * @param object
     * @param annotationClazz
     * @param entity
     * @param <E>
     */
    private void getAnnotationAndInvoke(Object object,
                                Class<? extends Annotation> annotationClazz, ResultEntity entity) {

        Class<?> clazz = object.getClass();

        Method[] methods = clazz.getDeclaredMethods();

        if (methods.length == 0) {
            return;
        }

        for (Method method : methods) {

            if (method.isAnnotationPresent(annotationClazz)) {

                method.setAccessible(true);

                //获取方法类型
                Class<?>[] types = method.getParameterTypes();
                if (types.length != 1) {
                    return;
                }

                Annotation annotation = method.getAnnotation(annotationClazz);

                if (annotation == null) {
                    return;
                }

                try {

                    method.invoke(object, entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 从对象中获取
     * @param object
     * @return
     */
    private Context getContextFromObject(Object object) {

        Context context = null;
        Class<?> clazz = object.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.getAnnotation(PermissionContext.class) != null) {

                try {
                    context = (Context) field.get(object);
                    break;
                } catch (Exception e) {

                }
            }
        }

        return context;
    }

}
