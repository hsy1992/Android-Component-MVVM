package com.endless.rxbus.entity;

import com.endless.rxbus.event.ProducerEvent;
import com.endless.rxbus.event.SubscriberEvent;

import java.util.Set;

/**
 * 绑定 {@link ProducerEvent} 与 Set<SubscriberEvent>
 * 一个 {@link ProducerEvent} 对应多个 {@link SubscriberEvent}
 * @author haosiyuan
 * @date 2019/3/27 8:46 PM
 */
public class BindEventEntity {

    private ProducerEvent producerEvent;

    private Set<SubscriberEvent> subscriberEventSet;

    public BindEventEntity(ProducerEvent producerEvent, Set<SubscriberEvent> subscriberEventSet) {
        this.producerEvent = producerEvent;
        this.subscriberEventSet = subscriberEventSet;
    }

    public ProducerEvent getProducerEvent() {
        return producerEvent;
    }

    public Set<SubscriberEvent> getSubscriberEventSet() {
        return subscriberEventSet;
    }

    public void setProducerEvent(ProducerEvent producerEvent) {
        this.producerEvent = producerEvent;
    }

    public void setSubscriberEventSet(Set<SubscriberEvent> subscriberEventSet) {
        this.subscriberEventSet = subscriberEventSet;
    }
}
