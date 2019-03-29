package com.hsy.study.networkclientstate;

import com.hsy.study.networkclientstate.annotation.OnMobile;
import com.hsy.study.networkclientstate.annotation.OnNetReload;
import com.hsy.study.networkclientstate.annotation.OnRegister;
import com.hsy.study.networkclientstate.annotation.OnNoNet;
import com.hsy.study.networkclientstate.annotation.OnWifi;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 网络切点
 * @author haosiyuan
 * @date 2019/3/28 2:18 PM
 */
@Aspect
public class NetworkAspect {

    private static final String TAG = "NetworkAspect";

    private static final int DEFAULT_INITIALIZATION = 10;

    /**
     * 对应关系缓存
     */
    private static final ConcurrentMap<Class<?>, Map<Class<?>, Method>> NETWORK_MAPPING_CACHE = new ConcurrentHashMap<>();

    @Pointcut("execution(@com.hsy.study.networkclientstate.annotation.OnRegister * *(..))")
    public void onRegister() {

    }

    @Around("onRegister()")
    public Object handlePoint(ProceedingJoinPoint point) throws Throwable {

        //方法执行前
        MethodSignature methodSignature = (MethodSignature) point.getSignature();

        //拿到注解
        OnRegister onNetListener = methodSignature.getMethod().getAnnotation(OnRegister.class);
        //拿到当前对象
        final Object thisObject = point.getThis();

        NetWorkManager.getInstance().addNetworkChangeListener(new NetworkChangeListener() {
           @Override
           public void onNoNetwork() {
           }

           @Override
           public void onNetReload(NetworkState state) {

           }

           @Override
           public void onNetChange(NetworkState state) {
               switch (state) {
                   case MOBILE:
                       loadMappingAndInvoke(thisObject, OnMobile.class, state);
                       break;
                   case WIFI:
                       loadMappingAndInvoke(thisObject, OnWifi.class, state);
                       break;
                   case NO_NET:
                       loadMappingAndInvoke(thisObject, OnNoNet.class, state);
                       break;
                    default:
                        break;
               }

           }
        });

        //继续执行方法
        Object object = null;
        try{
            object = point.proceed();
        }catch (Exception e){

        }

        return object;
    }

    /**
     * 查找注解的方法有的话 调用
     * @param object 本类
     * @param clazz 注解类
     * @param state
     */
    private void loadMappingAndInvoke(Object object, Class<?> clazz, NetworkState state) {

        if (object == null) {
            throw new IllegalArgumentException("Object can not be null");
        }

        Class<?> thisClass = object.getClass();

        Map<Class<?>, Method> map = NETWORK_MAPPING_CACHE.get(thisClass);
        Method method;

        if (map == null || map.size() == 0) {
            Method[] allMethods = thisClass.getDeclaredMethods();
            map = new HashMap<>(DEFAULT_INITIALIZATION);
            for (Method m : allMethods) {

                m.setAccessible(true);

                if (m.getAnnotation(OnNoNet.class) != null) {
                    map.put(OnNoNet.class, m);
                } else if (m.getAnnotation(OnMobile.class) != null) {
                    map.put(OnMobile.class, m);
                } else if (m.getAnnotation(OnWifi.class) != null) {
                    map.put(OnWifi.class, m);
                } else if (m.getAnnotation(OnNetReload.class) != null) {
                    map.put(OnNetReload.class, m);
                }
            }

            NETWORK_MAPPING_CACHE.put(thisClass, map);
        }

        method = map.get(clazz);

        if (method != null) {

            checkMethod(method);
            try {
                if (clazz == OnNetReload.class) {
                    method.invoke(object, state);
                } else {
                    method.invoke(object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 检查方法
     * @param method
     */
    private void checkMethod(Method method) {

        if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
            throw new IllegalArgumentException("Method " + method.getName() + " is not a public method");
        }
    }

    @Pointcut("execution(@com.hsy.study.networkclientstate.annotation.OnUnRegister * *(..))")
    public void onUnRegister() throws Throwable {

    }

    @Around("onUnRegister()")
    public Object handlePointUn(ProceedingJoinPoint point) throws Throwable {
        //拿到当前对象
        final Object thisObject = point.getThis();

        NetWorkManager.getInstance().removeNetworkChangeListener((NetworkChangeListener) thisObject);

        //继续执行方法
        Object object = null;
        try{
            object = point.proceed();
        }catch (Exception e){

        }
        return object;
    }
}
