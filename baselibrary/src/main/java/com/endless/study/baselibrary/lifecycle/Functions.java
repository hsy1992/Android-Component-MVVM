package com.endless.study.baselibrary.lifecycle;

import java.util.concurrent.CancellationException;

import androidx.lifecycle.Lifecycle;
import io.reactivex.Completable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * rxJava functions
 * @author haosiyuan
 * @date 2019/2/2 5:27 PM
 */
class Functions {

    private Functions() {
        throw new AssertionError("No instances!");
    }

    static final Function<Throwable, Boolean> RESUME_FUNCTION = new Function<Throwable, Boolean>() {
        @Override
        public Boolean apply(Throwable throwable) throws Exception {
            if (throwable instanceof OutsideLifecycleException){
                return true;
            }

            Exceptions.propagate(throwable);
            return false;
        }
    };

    static final Predicate<Boolean> SHOULD_COMPLETE = new Predicate<Boolean>() {
        @Override
        public boolean test(Boolean shouldComplete) throws Exception {
            return shouldComplete;
        }
    };

    static final Function<Object, Completable> CANCEL_COMPLETABLE = new Function<Object, Completable>() {
        @Override
        public Completable apply(Object ignore) throws Exception {
            return Completable.error(new CancellationException());
        }
    };

    static final Function<Lifecycle.Event, Lifecycle.Event> LIFECYCLE_EVENT =
            new Function<Lifecycle.Event, Lifecycle.Event>() {
                @Override
                public Lifecycle.Event apply(Lifecycle.Event lastEvent) throws Exception {
                    switch (lastEvent) {
                        case ON_CREATE:
                            return Lifecycle.Event.ON_DESTROY;
                        case ON_START:
                            return Lifecycle.Event.ON_STOP;
                        case ON_RESUME:
                            return Lifecycle.Event.ON_PAUSE;
                        case ON_PAUSE:
                            return Lifecycle.Event.ON_STOP;
                        case ON_STOP:
                            return Lifecycle.Event.ON_DESTROY;
                        case ON_DESTROY:
                            throw new OutsideLifecycleException("Cannot bind to Activity lifecycle when outside of it.");
                        default:
                            throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
                    }
                }
            };



}
