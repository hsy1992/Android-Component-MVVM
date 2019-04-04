package com.endless.rxbus;

import android.util.Log;

import com.endless.rxbus.annotation.Tag;
import com.endless.rxbus.entity.BindEventEntity;
import com.endless.rxbus.entity.EventTypeEntity;
import com.endless.rxbus.event.DeadEvent;
import com.endless.rxbus.event.ProducerEvent;
import com.endless.rxbus.event.SubscriberEvent;
import com.endless.rxbus.handler.ThreadStrategy;
import com.endless.rxbus.helper.AnnotationHelper;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * {@link com.endless.rxbus.annotation.Producer} 应和 post在同一个Object
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
     * 退出时停止 防止内存泄漏
     */
    private ConcurrentMap<SubscriberEvent, Disposable> disposableMao = new ConcurrentHashMap<>();

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
        Log.d("Logger", "register>>" + object.getClass().getSimpleName());
        threadStrategy.handle(this);

        registerProducer(object);

        registerSubscriber(object);

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
        }
    }

    /**
     * 遍历分发事件
     * @param subscriberEvent
     * @param producerEvent
     */
    private void dispatchProducerResult(final SubscriberEvent subscriberEvent, ProducerEvent producerEvent) {
        Log.d("Logger", "dispatchProducerResult");
        Disposable disposable = producerEvent.produce().subscribe(new Consumer() {
            @Override
            public void accept(Object object) throws Exception {
                dispatch(object, subscriberEvent);
            }
        });

        disposableMao.put(subscriberEvent, disposable);
    }

    /**
     * @param object   生成分发的对象
     * @param wrapper wrapper that will call the handle.
     */
    protected void dispatch(Object object, SubscriberEvent wrapper) {
        if (wrapper.isValid()) {
            Log.d("Logger", "dispatch" + object.getClass() +">>");
            wrapper.handle(object);
        }
    }

    /**
     * 对象销毁时 解除改对象的绑定
     * @param object
     */
    public void unRegister(Object object) {
        Log.d("Logger", "unRegister >> " + object.getClass().getSimpleName());
        if (object == null) {
            throw new IllegalArgumentException("Object to unregister must not be null.");
        }

        threadStrategy.handle(this);

        //处理 producer 解除绑定 移除缓存
        Map<EventTypeEntity, Set<ProducerEvent>> producersInListener
                = AnnotationHelper.getProducesEvent(object);

        for (Map.Entry<EventTypeEntity, Set<ProducerEvent>> entry : producersInListener.entrySet()) {
            final EventTypeEntity key = entry.getKey();

            ProducerEvent producerEvent= produceEventMap.get(key);
            Set<ProducerEvent> value = entry.getValue();

            if (value == null || !value.contains(producerEvent)) {
                throw new IllegalArgumentException(
                        "Missing event producer for an annotated method. Is " + object.getClass()
                                + " registered?");
            }
            produceEventMap.remove(key);
        }

        Log.d("Logger", "produceEventMap 剩余>> " + produceEventMap.keySet().size());

        //处理 subscriber 解除绑定 移除缓存
        Map<EventTypeEntity, Set<SubscriberEvent>> subscribersInListener
          = AnnotationHelper.getSubscribersEvent(object);

        for (Map.Entry<EventTypeEntity, Set<SubscriberEvent>> entry : subscribersInListener.entrySet()) {

            Set<SubscriberEvent> currentSubscribers = subscriberEventMap.get(entry.getKey());

            Collection<SubscriberEvent> eventMethodsInListener = entry.getValue();

            if (currentSubscribers == null || !currentSubscribers.containsAll(eventMethodsInListener)) {
                throw new IllegalArgumentException(
                        "Missing event subscriber for an annotated method. Is " + object.getClass()
                                + " registered?");
            }

            currentSubscribers.removeAll(eventMethodsInListener);

            //解除绑定防止内存泄漏
            for (SubscriberEvent event : eventMethodsInListener) {
                if (disposableMao.get(event) != null && disposableMao.get(event).isDisposed()) {
                    disposableMao.get(event).dispose();
                }
            }

        }

        Log.d("Logger", "subscriberEventMap 剩余>> " + subscriberEventMap.keySet().size());
    }

    /**
     * post方法 默认key tag
     * @param clazz 该对象
     */
    public void post(Class clazz) {
        post(Tag.DEFAULT, clazz);
    }

    /**
     * post 执行
     * @param tag 执行的标签
     * @param dispatchClasses 类型
     */
    public boolean post(String tag, @NonNull Class dispatchClasses) {

        Log.d("Logger", "tag " + tag + ">> class " + dispatchClasses.getSimpleName());

        threadStrategy.handle(this);

        boolean dispatched = false;
        EventTypeEntity dispatchType = new EventTypeEntity(tag, dispatchClasses);
        Set<SubscriberEvent> subscriberEvents = subscriberEventMap.get(dispatchType);

        ProducerEvent producerEvents = produceEventMap.get(dispatchType);

        if (subscriberEvents != null && !subscriberEvents.isEmpty() && producerEvents != null) {
            dispatched = true;

            for (SubscriberEvent subscriberEvent : subscriberEvents) {
                dispatchProducerResult(subscriberEvent, producerEvents);
            }
        }

        return dispatched;
    }
}
