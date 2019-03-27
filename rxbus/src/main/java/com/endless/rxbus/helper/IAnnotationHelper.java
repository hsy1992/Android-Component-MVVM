package com.endless.rxbus.helper;

import com.endless.rxbus.entity.EventTypeEntity;
import com.endless.rxbus.event.Event;

import java.util.Map;
import java.util.Set;

/**
 * 注解查找
 * @author haosiyuan
 * @date 2019/3/27 2:14 PM
 */
public interface IAnnotationHelper {

    /**
     * 查找所有注解
     * @param listener
     * @param <E>
     * @return
     */
    <E extends Event> Map<EventTypeEntity, Set<E>> findAllAnnotation(Object listener);
}
