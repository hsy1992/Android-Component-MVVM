package com.endless.rxbus.helper;

import com.endless.rxbus.annotation.Subscriber;
import com.endless.rxbus.entity.EventTypeEntity;
import com.endless.rxbus.entity.SourceMethodEntity;
import com.endless.rxbus.event.SubscriberEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 查找 注解{@link Subscriber}
 * @author haosiyuan
 * @date 2019/3/27 2:10 PM
 */
class SubscriberHelper extends AbstractAnnotationHelper {


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
        if (!methods.isEmpty()) {

            for (Map.Entry<EventTypeEntity, Set<SourceMethodEntity>> entry : methods.entrySet()) {

                Set<SubscriberEvent> subscribers = new HashSet<>();

                for (SourceMethodEntity methodEntity : entry.getValue()) {

                    subscribers.add(new SubscriberEvent(listener, methodEntity.getMethod(), methodEntity.getThread()));
                }

                subscribersInMethod.put(entry.getKey(), subscribers);
            }
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
