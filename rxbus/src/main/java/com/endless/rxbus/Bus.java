package com.endless.rxbus;

import com.endless.rxbus.entity.BindEventEntity;
import com.endless.rxbus.entity.EventTypeEntity;
import com.endless.rxbus.event.ProducerEvent;
import com.endless.rxbus.event.SubscriberEvent;
import com.endless.rxbus.handler.ThreadStrategy;
import com.endless.rxbus.helper.AnnotationHelper;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author haosiyuan
 * @date 2019/3/26 3:42 PM
 */
public class Bus {

    public static final String TAG = Bus.class.getSimpleName();

    public static final String DEFAULT_NAME = "default";

    /**
     * 所有的订阅事件
     */
    private ConcurrentMap<EventTypeEntity, Set<SubscriberEvent>> subscriberEventMap = new ConcurrentHashMap<>();

    /**
     * 所有的提供事件
     */
    private ConcurrentMap<EventTypeEntity, ProducerEvent> produceEventMap = new ConcurrentHashMap<>();

    /**
     * 一个 {@link EventTypeEntity} 对应 一个 {@link ProducerEvent} 对应多个 {@link SubscriberEvent}
     */
    private ConcurrentMap<EventTypeEntity, BindEventEntity> bindEventMap = new ConcurrentHashMap<>();

    /**
     * 线程策略
     */
    private ThreadStrategy threadStrategy;

    /**
     * 改bus的name
     */
    private String name;


    public Bus() {
        this(DEFAULT_NAME, ThreadStrategy.ANY);
    }

    public Bus(ThreadStrategy threadStrategy) {
        this(DEFAULT_NAME, threadStrategy);
    }

    public Bus(String name, ThreadStrategy threadStrategy) {
        this.threadStrategy = threadStrategy;
        this.name = name;
    }


    /**
     * 注册所有 subscriber
     * @param object
     */
    public void register(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object to register must not be null.");
        }

        threadStrategy.handle(this);

        registerProducer(object);

        registerSubscriber(object);

        //事件分发绑定
        for (Map.Entry<EventTypeEntity, BindEventEntity> entry : bindEventMap.entrySet()) {

            for (SubscriberEvent subscriberEvent : entry.getValue().getSubscriberEventSet()) {

                dispatchProducerResult(subscriberEvent, entry.getValue().getProducerEvent());
            }
        }
    }

    /**
     * 完成 Producer的绑定关系
     * @param object
     */
    private void registerProducer(Object object) {
        //查找对象中 所有 Producer
        Map<EventTypeEntity, Set<ProducerEvent>> producersMapFromObject
                = AnnotationHelper.getProducesEvent(object);

        for (EventTypeEntity type : producersMapFromObject.keySet()) {

            Set<ProducerEvent> producerEvents = producersMapFromObject.get(type);

            ProducerEvent producerEvent;
            if (producerEvents != null && producerEvents.size() > 0) {

                producerEvent = producerEvents.iterator().next();
                //如果存在key就不进行替换 返回之前的 一个Type只允许有一个 producerEvent
                ProducerEvent previousProducerEvent = produceEventMap.putIfAbsent(type, producerEvent);
                if (previousProducerEvent != null) {
                    throw new IllegalArgumentException("Producer method for type " + type
                            + " found on type " + producerEvent.getTarget().getClass()
                            + ", but already registered by type " + previousProducerEvent.getTarget().getClass() + ".");
                }

                //该eventType下所有订阅的
                Set<SubscriberEvent> subscriberEvents = subscriberEventMap.get(type);
                if (subscriberEvents != null && !subscriberEvents.isEmpty()) {
                    //加入对象到 bindEventMap
                    bindEventMap.put(type, new BindEventEntity(producerEvent, subscriberEvents));
                }
            }
        }
    }

    /**
     * 完成 Subscriber的绑定关系
     * @param object
     */
    private void registerSubscriber(Object object) {
        //查找对象中所有 Subscriber
        Map<EventTypeEntity, Set<SubscriberEvent>> subscribersMapFromObject
                = AnnotationHelper.getSubscribersEvent(object);

        for (Map.Entry<EventTypeEntity, Set<SubscriberEvent>> entry : subscribersMapFromObject.entrySet()) {

            EventTypeEntity type = entry.getKey();

            Set<SubscriberEvent> subscribers = subscriberEventMap.get(type);

            if (subscribers == null) {
                //同步数据到 subscriberEventMap
                Set<SubscriberEvent> subscribersCreation = new CopyOnWriteArraySet<>();

                subscribers = subscriberEventMap.putIfAbsent(type, subscribersCreation);
                if (subscribers == null) {
                    subscribers = subscribersCreation;
                }
            }

            //该Object中 返回的所有 subscribers
            final Set<SubscriberEvent> foundSubscribers = subscribersMapFromObject.get(type);

            if (!subscribers.addAll(foundSubscribers)) {
                throw new IllegalArgumentException("Object already registered.");
            }

            ProducerEvent producer = produceEventMap.get(type);
            if (producer != null && producer.isValid()) {
                //加入对象到 bindEventMap
                bindEventMap.put(type, new BindEventEntity(producer, subscriberEventMap.get(type)));
            }
        }
    }

    /**
     * 遍历分发事件
     * @param subscriber
     * @param producerEvent
     */
    private void dispatchProducerResult(SubscriberEvent subscriber, ProducerEvent producerEvent) {
        producerEvent.produce().subscribe();
    }

}
