package com.hsy.study.baselibrary.http.log;

import com.hsy.study.baselibrary.http.GlobalHttpHandler;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.IntDef;
import okhttp3.Interceptor;
import okhttp3.Response;

/**请求拦截器
 * @author haosiyuan
 * @date 2019/1/27 9:32 AM
 */
@Singleton
public class RequestInterceptor implements Interceptor {

    @Inject
    GlobalHttpHandler mHandler;
    /**
     * 打印处理
     */
    @Inject
    FormatPrinter formatPrinter;
    @Inject
    @LogLevel
    int printLevel;

    @Inject
    public RequestInterceptor(){

    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }


    /**
     * 打印等级
     */
    @IntDef({
            LogLevel.NONE,
            LogLevel.REQUEST,
            LogLevel.RESPONSE,
            LogLevel.ALL
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogLevel{

        /**
         * 不打印
         */
        int NONE = 0;
        /**
         * 打印请求
         */
        int REQUEST = 1;
        /**
         * 打印返回
         */
        int RESPONSE = 2;
        /**
         * 全部打印
         */
        int ALL = 3;

    }
}
