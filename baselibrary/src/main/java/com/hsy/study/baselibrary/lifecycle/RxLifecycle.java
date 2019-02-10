package com.hsy.study.baselibrary.lifecycle;

import com.hsy.study.baselibrary.utils.Preconditions;
import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * rxJava 生命周期
 * @author haosiyuan
 * @date 2019/2/2 4:49 PM
 */
public class RxLifecycle {

    private RxLifecycle() {
        throw new AssertionError("No instances");
    }

    @NonNull
    @CheckReturnValue
    private static <T, R>LifecycleTransformer<T> bind(@NonNull final Observable<R> lifecycle){
        return new LifecycleTransformer<>(lifecycle);
    }


    @NonNull
    @CheckReturnValue
    public static <T, R> LifecycleTransformer<T> bind(@NonNull Observable<R> lifecycle,
                                                      @NonNull final Function<R, R> correspondingEvents){
        Preconditions.checkNotNull(lifecycle, "lifecycle is null");
        Preconditions.checkNotNull(correspondingEvents, "correspondingEvents is null");
        return bind(takeUntilCorrespondingEvent(lifecycle.share(), correspondingEvents));
    }

    /**
     * takeUntil 响应事件
     * @param lifecycle 生命周期
     * @param correspondingEvents 响应事件
     * @param <R>
     * @return
     */
    private static <R> Observable<Boolean> takeUntilCorrespondingEvent(final Observable<R> lifecycle,
                                                                       final Function<R, R> correspondingEvents){

        return Observable.combineLatest(
                lifecycle.take(1).map(correspondingEvents),
                lifecycle.skip(1),
                new BiFunction<R, R, Boolean>() {
                    @Override
                    public Boolean apply(R bindUntilEvent, R lifecycleEvent) throws Exception {
                        return lifecycleEvent.equals(bindUntilEvent);
                    }
                })
                .onErrorReturn(Functions.RESUME_FUNCTION)
                .filter(Functions.SHOULD_COMPLETE);
    }
}
