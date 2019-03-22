package com.hsy.study.baselibrary.repository.calladapter;

import com.hsy.study.baselibrary.common.exception.AppException;
import com.hsy.study.baselibrary.http.response.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * 网络返回{@link androidx.lifecycle.LiveData} 工厂
 * @author haosiyuan
 * @date 2019/3/22 10:55 AM
 */
public class LiveDataCallAdapterFatory extends CallAdapter.Factory {

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        if (CallAdapter.Factory.getRawType(returnType) != LiveData.class) {
            return null;
        }

        Type observableType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);
        //LiveData 中类型也应该为 ApiResponse
        Class<?> rawObservableType = CallAdapter.Factory.getRawType(observableType);
        if (rawObservableType != ApiResponse.class) {
            throw new IllegalArgumentException("type be a resource");
        }

        if (!(observableType instanceof ParameterizedType)){
            throw new IllegalArgumentException("resource must be ParameterizedType");
        }

        Type bodyType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) observableType);

        return new LiveDataCallAdapter<>(bodyType);
    }
}
