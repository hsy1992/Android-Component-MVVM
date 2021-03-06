package com.endless.rxbus.helper;

import android.util.Log;

import com.endless.rxbus.annotation.Subscriber;
import com.endless.rxbus.entity.EventTypeEntity;
import com.endless.rxbus.entity.SourceMethodEntity;
import com.endless.rxbus.event.SubscriberEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 查找 注解{@link Subscriber}
 * @author haosiyuan
 * @date 2019/3/27 2:10 PM
 */
class SubscriberHelper extends AbstractAnnotationHelper {

    /**
     * 缓存每个类的 Class 与Map<EventTypeEntity, Set<SubscriberEvent>> 之间的关系因为他们是不变的
     */
    private static final ConcurrentMap<Class<?>, Map<EventTypeEntity, Set<SubscriberEvent>>> MAPPING_CACHE =
            new ConcurrentHashMap<>();

    @Override
    public Map<EventTypeEntity, Set<SubscriberEvent>> findAllAnnotation(Object listener) {
        Map<EventTypeEntity, Set<SubscriberEvent>> subscribersInMethod = new HashMap<>(DEFAULT_INITIALIZATION);

        Class<?> listenerClass = listener.getClass();

        Map<EventTypeEntity, Set<SourceMethodEntity>> methods = SUBSCRIBERS_CACHE.get(listenerClass);

        if (null == methods) {
            //不含有缓存 去加载class
            methods = new HashMap<>(DEFAULT_INITIALIZATION);
            loadAnnotatedSubscriberMethods(listenerClass, methods);
        }

        //封装成Event 返回
        if (MAPPING_CACHE.get(listenerClass) == null) {
            if (!methods.isEmpty()) {

                for (Map.Entry<EventTypeEntity, Set<SourceMethodEntity>> entry : methods.entrySet()) {

                    Set<SubscriberEvent> subscribers = new HashSet<>();

                    for (SourceMethodEntity methodEntity : entry.getValue()) {

                        subscribers.add(new SubscriberEvent(listener, methodEntity.getMethod(), methodEntity.getThread()));
                    }

                    subscribersInMethod.put(entry.getKey(), subscribers);

                }
                Log.d("Logger", "SubscriberHelper 加载 >> methods " + subscribersInMethod.size());

            }
            MAPPING_CACHE.put(listenerClass, subscribersInMethod);
        } else {
            subscribersInMethod = MAPPING_CACHE.get(listenerClass);
            Log.d("Logger", "SubscriberHelper 缓存 >> class " + listenerClass.getSimpleName());
        }

        return subscribersInMethod;
    }

    /**
     * 统一调用 {@code loadAnnotatedMethods}
     * @param listenerClass
     * @param subscriberMethods
     */
    private void loadAnnotatedSubscriberMethods(Class<?> listenerClass,
                                                Map<EventTypeEntity, Set<SourceMethodEntity>> subscriberMethods) {

        Map<EventTypeEntity, Set<SourceMethodEntity>> producerMethods = new HashMap<>(DEFAULT_INITIALIZATION);
        loadAnnotatedMethods(listenerClass, producerMethods, subscriberMethods);
    }
}
