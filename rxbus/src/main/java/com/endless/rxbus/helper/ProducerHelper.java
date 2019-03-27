package com.endless.rxbus.helper;

import com.endless.rxbus.entity.EventTypeEntity;
import com.endless.rxbus.entity.SourceMethodEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.endless.rxbus.annotation.Producer;
import com.endless.rxbus.event.ProducerEvent;

/**
 * 查找 注解{@link Producer}
 * @author haosiyuan
 * @date 2019/3/27 2:10 PM
 */
class ProducerHelper extends AbstractAnnotationHelper{

    @Override
    public Map<EventTypeEntity, Set<ProducerEvent>> findAllAnnotation(Object listener) {
        //单个事件
        Map<EventTypeEntity, Set<ProducerEvent>> producersInMethod = new HashMap<>(DEFAULT_INITIALIZATION);

        Class<?> whichClass = listener.getClass();

        Map<EventTypeEntity, Set<SourceMethodEntity>> methods = PRODUCERS_CACHE.get(whichClass);

        if (methods == null) {
            //不含有缓存 去加载class
            methods = new HashMap<>(DEFAULT_INITIALIZATION);
            loadAnnotatedProducerMethods(whichClass, methods);
        }

        //封装成Event 返回
        if (!methods.isEmpty()) {

            for (Map.Entry<EventTypeEntity, Set<SourceMethodEntity>> entry : methods.entrySet()) {

                Set<ProducerEvent> subscribers = new HashSet<>();

                if (entry.getValue().size() > 1) {
                    throw new RuntimeException("Can only contain one SourceMethodEntity");
                }

                for (SourceMethodEntity methodEntity : entry.getValue()) {

                    subscribers.add(new ProducerEvent(listener, methodEntity.getMethod(), methodEntity.getThread()));
                }

                producersInMethod.put(entry.getKey(), subscribers);
            }
        }

        return producersInMethod;
    }

    /**
     * 统一调用 {@code loadAnnotatedMethods}
     * @param listenerClass
     * @param producerMethods
     */
    private void loadAnnotatedProducerMethods(Class<?> listenerClass,
                                                Map<EventTypeEntity, Set<SourceMethodEntity>> producerMethods) {

        Map<EventTypeEntity, Set<SourceMethodEntity>> subscriberMethods = new HashMap<>(DEFAULT_INITIALIZATION);
        loadAnnotatedMethods(listenerClass, producerMethods, subscriberMethods);
    }
}
