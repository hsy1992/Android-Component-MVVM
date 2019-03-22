package com.hsy.study.baselibrary.repository;

import com.hsy.study.baselibrary.common.executor.AppExecutors;
import com.hsy.study.baselibrary.http.response.ApiEmptyResponse;
import com.hsy.study.baselibrary.http.response.ApiErrorResponse;
import com.hsy.study.baselibrary.http.response.ApiResponse;
import com.hsy.study.baselibrary.http.response.ApiSuccessResponse;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * 提供 网络资源 数据库资源
 * RequestType 请求返回的实体
 * ResultType  数据库返回的实体
 * @author haosiyuan
 * @date 2019/3/20 11:38 AM
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

    /**
     * 返回数据
     */
    private MediatorLiveData<DataResource<ResultType>> result;

    /**
     * 生成线程池
     */
    private AppExecutors appExecutors;

    /**
     * 运行在主线程
     * @param appExecutors
     */
    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;

        result = new MediatorLiveData<>();
        //设置起始 加载状态
        result.setValue(DataResource.loading(null));
        //先展示db数据
        LiveData<ResultType> dbSource = loadFromDb();

        result.addSource(dbSource, dbData -> {
            //数据库数据读取成功 移除数据库数据监听
            result.removeSource(dbSource);
            if (shouldFetchData(dbData)) {
                //数据库数据 需要刷新则去网络请求
                fetchFromNetwork(dbSource);
            } else {
                //不需要刷新数据 读取成功返回成功success
                result.addSource(dbSource, dbNewData -> setValue(DataResource.success(dbNewData)));
            }
        });
    }

    /**
     * 获取网络数据
     * @param dbSource
     */
    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        //先将数据库数据传入
        result.addSource(dbSource, newData -> setValue(DataResource.loading(newData)));

        //根据网络请求返回更新数据
        result.addSource(apiResponse, response -> {
            //移除数据监听
            result.removeSource(dbSource);
            result.removeSource(apiResponse);

            if (response instanceof ApiSuccessResponse) {
                //请求成功
                appExecutors.getDiskIO().execute(() -> {
                    //IO线程保存数据
                    saveCallResult(processResponse((ApiSuccessResponse<RequestType>) response));
                    //主线程更新数据 从数据库查出最新的数据
                    appExecutors.getMainExecutor().execute(
                            () -> result.addSource(loadFromDb(),
                                            newData -> setValue(DataResource.success(newData))));

                });
            } else if (response instanceof ApiErrorResponse) {
                //请求错误
                result.addSource(dbSource,
                        newData -> setValue(
                                DataResource
                                        .error(((ApiErrorResponse<RequestType>) response)
                                                .getErrorMessage(), newData)));
            } else if (response instanceof ApiEmptyResponse) {
                //返回为空时 查找数据库数据更新
                appExecutors.getMainExecutor().execute(
                        () -> result.addSource(loadFromDb(),
                                newData -> DataResource.success(newData)));
            }


        });
    }

    /**
     * 更新数据
     * @param newValue
     */
    @MainThread
    private void setValue(DataResource<ResultType> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    /**
     * 获取结果
     * @return
     */
    public LiveData<DataResource<ResultType>> asLiveData() {
        return result;
    }

    /**
     * 读取数据库
     * @return
     */
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    /**
     * 是否需要刷新数据
     * @param resultType
     * @return
     */
    @MainThread
    protected abstract boolean shouldFetchData(ResultType resultType);

    /**
     * 获取Api返回
     * @return
     */
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    /**
     * 保存数据
     * @param item
     */
    @WorkerThread
    protected abstract void saveCallResult(RequestType item);

    @WorkerThread
    protected RequestType processResponse(ApiSuccessResponse<RequestType> response) {
        return response.getBody();
    }
}
