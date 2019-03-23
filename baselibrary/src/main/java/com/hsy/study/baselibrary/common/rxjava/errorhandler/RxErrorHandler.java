package com.hsy.study.baselibrary.common.rxjava.errorhandler;

import android.content.Context;

/**
 * RxJava错误控制
 * @author haosiyuan
 * @date 2019/3/23 6:59 PM
 */
public class RxErrorHandler {

    public final String TAG = this.getClass().getSimpleName();

    private RxErrorHandlerFactory mHandlerFactory;

    private RxErrorHandler(Builder builder) {
        this.mHandlerFactory = builder.errorHandlerFactory;
    }

    public static Builder builder() {
        return new Builder();
    }

    public RxErrorHandlerFactory getHandlerFactory() {
        return mHandlerFactory;
    }

    public static final class Builder {

        private Context context;
        private RxResponseErrorListener mResponseErrorListener;
        private RxErrorHandlerFactory errorHandlerFactory;

        private Builder() {
        }

        public Builder with(Context context) {
            this.context = context;
            return this;
        }

        public Builder responseErrorListener(RxResponseErrorListener responseErrorListener) {
            this.mResponseErrorListener = responseErrorListener;
            return this;
        }

        public RxErrorHandler build() {
            if (context == null)
                throw new IllegalStateException("Context is required");

            if (mResponseErrorListener == null)
                throw new IllegalStateException("ResponseErrorListener is required");

            this.errorHandlerFactory = new RxErrorHandlerFactory(context, mResponseErrorListener);

            return new RxErrorHandler(this);
        }
    }
}
