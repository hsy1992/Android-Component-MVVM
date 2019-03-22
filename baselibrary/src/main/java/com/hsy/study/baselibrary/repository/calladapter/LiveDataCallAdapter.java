package com.hsy.study.baselibrary.repository.calladapter;

import com.hsy.study.baselibrary.http.response.ApiResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 将返回类型转换为 {@link androidx.lifecycle.LiveData}
 * @author haosiyuan
 * @date 2019/3/22 10:37 AM
 */
class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {

    private Type responseType;

    LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {
        AtomicBoolean started = new AtomicBoolean(false);

        LiveData<ApiResponse<R>> liveData = new LiveData(){
            @Override
            protected void onActive() {
                //至为活动状态
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            postValue(ApiResponse.create(response));
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable t) {
                            postValue(ApiResponse.create(t));
                        }
                    });
                }
            }
        };

        return liveData;
    }
}
