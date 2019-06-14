package com.anjuke.mobile;

import android.os.Bundle;
import android.util.Log;

import com.anjuke.mobile.sign.SignUtil;
import com.endless.study.baselibrary.common.rxjava.scheduler.SchedulersUtil;
import com.endless.study.baselibrary.http.log.RequestInterceptor;
import com.endless.study.myproject.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author haosiyuan
 * @date 2019-04-22 16:32
 */
public class TestActivity extends AppCompatActivity {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    static {
        System.loadLibrary("signutil");
    }

    //     100041b944aa274a72fae076f2e4166f427f019a0019000057306561
    //正确 100041b944aa274a72fae076f2e4166f427f019a0019000057306561
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Map<String, String> localHashMap = new HashMap<>();
        localHashMap.put("page", "1");
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new GetSecretPhone())
//                .addInterceptor(new AnjukeInterceptor())
                .addInterceptor(logInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.anjuke.com")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofit.create(AnjukeService.class).get_secret_phone(getScretPhoneMap())
                .compose(SchedulersUtil.ioToMain())
                .subscribe(new Observer<XinFangModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(XinFangModel xinFangModel) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
//    /mobile/v5/common/get_secret_phone?called=18945005080&prop_id=82277011
//            &user_id=56794816&biz_type=zf&macid=a37f503c8d0c5f37f310b5cbef075914&app=a-ajk
//    &_pid=4007&o=samsung-user%204.4.2%20KOT49H%203.8.017.0602%20release-keys&from=mobile
//    &m=Android-SM-E7000&cv=12.0.2&cid=0&version_code=321859&i=864394010164947&v=4.4.2&androidid=A45E60CEDFF70000
//    &manufacturer=samsung&qtime=20190429145211&pm=b61&uuid=9a38ec53-8f4e-42e7-b7c7-b473de337134&_chat_id=0

    private Map getScretPhoneMap() {
        Map<String, String> localHashMap = new HashMap<>();
        localHashMap.put("called","18945005080");
        localHashMap.put("biz_type","zf");
        localHashMap.put("user_id","56794816");
        localHashMap.put("prop_id","82277011");


        return localHashMap;
    }

    private Map getMap() {
        HashMap localHashMap = new HashMap();

        localHashMap.put("api_key", "androidkey");
        localHashMap.put("app", "a-ajk");
        localHashMap.put("_pid", "786");
        localHashMap.put("macid", "f0ca3da7e17f3a2aa9ebb4194cab4e98");
        localHashMap.put("version_code", "321859");
        localHashMap.put("i", "355757010481386");
        localHashMap.put("m", "Android-SM-G955F");
        localHashMap.put("uuid", "8f80b0e6-34c2-4d0d-827d-0ef2f0a81530");
        localHashMap.put("manufacturer", "samsung");
        localHashMap.put("o", "SM-G955F-user%204.4.2%20JLS36C%20381180615%20release-keys");
        localHashMap.put("qtime", "20190422170812");
        localHashMap.put("cv", "12.0.2");
        localHashMap.put("v", "4.4.2");
        localHashMap.put("from", "mobile");
        localHashMap.put("pm", "b04");
        localHashMap.put("androidid", "488ad2aa586c2624");
        localHashMap.put("_chat_id", "0");
        localHashMap.put("cid", "48");
        return localHashMap;
    }

    //https://api.anjuke.com/xinfang/m/android/1.3/loupan/newlistv2/?city_id=48
    // &page_size=20&lng=126.516167&page=1&lat=45.794713&map_type=1&sig=cdac26c2cf3c1e142b9978b2fc1ce3c4
    // &api_key=androidkey&macid=f0ca3da7e17f3a2aa9ebb4194cab4e98&app=a-ajk
    // &_pid=786&o=SM-G955F-user%204.4.2%20JLS36C%20381180615%20release-keys&from=mobile
    // &m=Android-SM-G955F&cv=12.0.2&cid=48&version_code=321859&i=355757010481386&v=4.4.2
    // &androidid=488ad2aa586c2624&manufacturer=samsung&qtime=20190422170812&pm=b04&uuid=8f80b0e6-34c2-4d0d-827d-0ef2f0a81530
    // &_chat_id=0
}
