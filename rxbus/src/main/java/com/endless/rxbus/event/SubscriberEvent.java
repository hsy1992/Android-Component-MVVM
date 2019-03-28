package com.endless.rxbus.event;

import android.util.Log;

import com.endless.rxbus.handler.EventThreadFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.BackpressureStrategy;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 包装订阅的事件
 * @author haosiyuan
 * @date 2019/3/26 8:35 PM
 */
public class SubscriberEvent extends Event {

    private Subject subject;

    public SubscriberEvent(Object target, Method method, int eventThread) {
        super(target, method, eventThread);
        initObservable();
    }

    /**
     * 初始化 采用背压 避免处理数据时过慢
     * 分发事件
     */
    private void initObservable() {
        subject = PublishSubject.create();
        subject.toFlowable(BackpressureStrategy.BUFFER)
               .observeOn(EventThreadFactory.getScheduler(eventThread))
               .subscribe(new Consumer() {
                   @Override
                   public void accept(Object object) throws Exception {
                       //事件处理
                       if (valid) {
                           try {
                               handleEvent(object);
                           } catch (Throwable e) {
                               throwRuntimeException("Could not dispatch event: " + object.getClass() +
                                       " to subscriber " + SubscriberEvent.this, e);
                           }
                       }
                   }
               });
    }

    public void handle(Object object) {
        subject.onNext(object);
    }

    /**
     * 处理事件
     * 执行方法 event为参数
     * @param object 生产分发下的对象
     */
    private void handleEvent(Object object) throws Throwable {
        if (!valid) {
            //不是有效的
            throw new IllegalStateException(toString() + " has been invalidated and can no produce events.");
        }
        Log.d("Logger", "handleEvent");
        try {
            if (object == null) {
                method.invoke(target);
            } else {
                method.invoke(target, object);
            }
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw e.getCause();
            }
            throw e;
        }
    }



    public Subject getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "[SubscriberEvent " + method.getName() + "]";
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
