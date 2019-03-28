package com.endless.rxbus.event;

import com.endless.rxbus.handler.EventThread;
import com.endless.rxbus.handler.EventThreadFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * 包装生成的事件
 * @author haosiyuan
 * @date 2019/3/26 8:34 PM
 */
public class ProducerEvent extends Event {

    public ProducerEvent(Object target, Method method, @EventThread int eventThread) {
        super(target, method, eventThread);
    }

    /**
     * 生成一个 {@link Observable}
     * @return
     */
    public Observable produce() {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {

                try {
                    emitter.onNext(produceEvent());
                    emitter.onComplete();
                } catch (Throwable e) {
                    throwRuntimeException("Producer " + ProducerEvent.this + " throw an exception.", e);
                }
            }
        }).subscribeOn(EventThreadFactory.getScheduler(eventThread));
    }

    /**
     * 执行得到 方法
     * @return
     */
    public Object produceEvent() throws Throwable {
        if (!valid) {
            //不是有效的
            throw new IllegalStateException(toString() + " has been invalidated and can no produce events.");
        }

        try {
            return method.invoke(target);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw e.getCause();
            }
            throw e;
        }
    }

    @Override
    public String toString() {
        return "[ProducerEvent " + method.getName() + "]";
    }
}
