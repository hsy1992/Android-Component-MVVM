package com.endless.rxbus.helper;

import com.endless.rxbus.annotation.Producer;
import com.endless.rxbus.annotation.Subscriber;
import com.endless.rxbus.entity.SourceMethodEntity;
import com.endless.rxbus.entity.EventTypeEntity;
import com.endless.rxbus.event.ProducerEvent;
import com.endless.rxbus.event.SubscriberEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 查找 {@link Producer} 与 {@link Subscriber}
 * @author haosiyuan
 * @date 2019/3/27 10:01 AM
 */
public class AnnotationHelper {

    private static ProducerHelper producerHelper;
    private static SubscriberHelper subscriberHelper;

    public static Map<EventTypeEntity, Set<ProducerEvent>> getProducesEvent(Object target) {

        if (producerHelper == null) {
            producerHelper = new ProducerHelper();
        }

        return producerHelper.findAllAnnotation(target);
    }

    public static Map<EventTypeEntity, Set<SubscriberEvent>> getSubscribersEvent(Object target) {

        if (subscriberHelper == null) {
            subscriberHelper = new SubscriberHelper();
        }

        return subscriberHelper.findAllAnnotation(target);
    }

}
