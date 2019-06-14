package com.anjuke.mobile;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * @author haosiyuan
 * @date 2019-04-22 21:00
 */
public interface AnjukeService {

    @GET("/xinfang/m/android/1.3/loupan/newlistv2/")
    Observable<XinFangModel> getXinFang(@QueryMap Map<String, String> map);

    @GET("/mobile/v5/common/get_secret_phone")
    Observable<XinFangModel> get_secret_phone(@QueryMap Map<String, String> map);
}
