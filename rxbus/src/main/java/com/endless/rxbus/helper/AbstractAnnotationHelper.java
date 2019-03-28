package com.endless.rxbus.helper;

import android.util.Log;

import com.endless.rxbus.annotation.Producer;
import com.endless.rxbus.annotation.Subscriber;
import com.endless.rxbus.annotation.Tag;
import com.endless.rxbus.entity.EventTypeEntity;
import com.endless.rxbus.entity.SourceMethodEntity;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 实现公共方法
 * @author haosiyuan
 * @date 2019/3/27 2:19 PM
 */
abstract class AbstractAnnotationHelper implements IAnnotationHelper {

    protected static final int DEFAULT_INITIALIZATION = 10;

    /**
     * 缓存每个类的 {@link Producer} 防止多次反射获取
     */
    protected static final ConcurrentMap<Class<?>, Map<EventTypeEntity, Set<SourceMethodEntity>>> PRODUCERS_CACHE =
            new ConcurrentHashMap<>();

    /**
     * 缓存每个类的 {@link Subscriber} 防止多次反射获取 会有多个订阅但是消息源是唯一的
     */
    protected static final ConcurrentMap<Class<?>, Map<EventTypeEntity, Set<SourceMethodEntity>>> SUBSCRIBERS_CACHE =
            new ConcurrentHashMap<>();

    /**
     * 加载注解方法
     * @param listenerClass 当前类
     * @param producerMethods 生成方法
     * @param subscriberMethods 订阅方法
     */
    protected void loadAnnotatedMethods(Class<?> listenerClass,
                                             Map<EventTypeEntity, Set<SourceMethodEntity>> producerMethods,
                                             Map<EventTypeEntity, Set<SourceMethodEntity>> subscriberMethods) {

        //遍历类中所有方法
        for (Method method : listenerClass.getDeclaredMethods()) {

            if (method.isBridge()) {
                continue;
            }

            //检查
            checkHaveBothAnnotation(method);
            //获取参数
            Class<?>[] parameterTypes = method.getParameterTypes();

            if (method.isAnnotationPresent(Subscriber.class)) {

                checkSubscriber(parameterTypes, method, listenerClass);

                Subscriber subscriberAnnotation = method.getAnnotation(Subscriber.class);
                int thread = subscriberAnnotation.thread();
                Tag[] tags = subscriberAnnotation.tags();

                loadAnnotated(thread, tags, method, parameterTypes.length > 0 ? parameterTypes[0] : null,
                        subscriberMethods);

                Log.d("Logger", "Subscriber 注解 >> method " + method.getName());
            }

            if (method.isAnnotationPresent(Producer.class)) {

                checkProducer(parameterTypes, method, listenerClass);

                Producer producerAnnotation = method.getAnnotation(Producer.class);
                int thread = producerAnnotation.thread();
                Tag[] tags = producerAnnotation.tags();

                loadAnnotated(thread, tags, method, method.getReturnType().equals(Void.TYPE) ? null : method.getReturnType(),
                        producerMethods);

                Log.d("Logger", "Producer 注解 >> method " + method.getName());
            }
        }

        PRODUCERS_CACHE.put(listenerClass, producerMethods);
        SUBSCRIBERS_CACHE.put(listenerClass, subscriberMethods);
    }

    /**
     * 抽出公共方法
     * @param thread 线程
     * @param tags tags注解
     * @param method 方法
     * @param eventTypeClass event的class subscriber时为参数 producer 为返回值
     * @param methodsMap 源方法集合
     */
    private void loadAnnotated(int thread, Tag[] tags, Method method, Class<?> eventTypeClass,
                               Map<EventTypeEntity, Set<SourceMethodEntity>> methodsMap) {

        int tagLength = (tags == null ? 0 : tags.length);

        //根据tag 的数量加入缓存 EventTypeEntity为key
        do {
            String tag = Tag.DEFAULT;

            if (tagLength > 0) {
                tag = tags[tags.length - 1].value();
            }

            EventTypeEntity type = new EventTypeEntity(tag, eventTypeClass);

            Set<SourceMethodEntity> sourceMethodEntitySet = methodsMap.get(type);

            if (sourceMethodEntitySet == null) {
                sourceMethodEntitySet = new HashSet<>();
                methodsMap.put(type, sourceMethodEntitySet);
            }

            sourceMethodEntitySet.add(new SourceMethodEntity(thread, method));

            tagLength --;
        } while (tagLength > 0);
    }

    /**
     * 检查Subscriber 支持一个参数或者没有参数
     * @param parameterTypes
     * @param method
     * @param listenerClass
     */
    private void checkSubscriber(Class<?>[] parameterTypes, Method method, Class listenerClass) {

        if (parameterTypes.length > 1) {
            throw new IllegalArgumentException("Method " + method.getName() + " has @Subscribe annotation but requires "
                    + parameterTypes.length + " arguments.  Methods must require a single argument or null.");
        }

        if (parameterTypes.length > 0) {

            Class<?> parameterClazz = parameterTypes[0];
            if (parameterClazz.isInterface()) {
                throw new IllegalArgumentException("Method " + method.getName() + " has @Subscribe annotation on " +
                        listenerClass.getSimpleName() + " which is an interface.  Subscription must be on a concrete class type.");
            }
        }

        if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
            throw new IllegalArgumentException("Method " + method.getName() + " has @Subscribe annotation on "
                    + listenerClass.getSimpleName() + " but is not 'public'.");
        }
    }

    /**
     * 检查Producer
     * @param parameterTypes
     * @param method
     * @param listenerClass
     */
    private void checkProducer(Class<?>[] parameterTypes, Method method, Class listenerClass) {

        if (parameterTypes.length != 0) {
            throw new IllegalArgumentException("Method " + method.getName() + "has @Produce annotation but requires "
                    + parameterTypes.length + " arguments.  Methods must require zero arguments.");
        }

        Class<?> parameterClazz = method.getReturnType();

        if (parameterClazz.isInterface()) {
            throw new IllegalArgumentException("Method " + method.getName() + " has @Produce annotation on " + listenerClass.getSimpleName()
                    + " which is an interface.  Producers must return a concrete class type.");
        }

        if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
            throw new IllegalArgumentException("Method " + method.getName() + " has @Produce annotation on " + listenerClass.getSimpleName()
                    + " but is not 'public'.");
        }
    }

    /**
     * 不可以同时含有两个注解
     * @param method
     */
    private void checkHaveBothAnnotation(Method method) {

        if (method.getAnnotation(Subscriber.class) != null && method.getAnnotation(Producer.class) != null) {
            throw new RuntimeException("Method " + method.getName() + " have @Subscribe annotation and @Producer annotation");
        }
    }

}
