package com.anjuke.mobile;

import android.util.Log;

import com.anjuke.mobile.sign.SignUtil;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.Header;

/**
 * @author haosiyuan
 * @date 2019-04-22 21:04
 */
public class AnjukeInterceptor implements Interceptor {

    private String sig = "";
    private String nsign = "";
    private String get_md5 = "";
    private String nsign_uuid = "";

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        HttpUrl url = request.url();
        setSig(url);
        Request requestNew = setHeader(url, request);

        url = requestNew.url();
        for (String key : url.queryParameterNames()) {
            Log.e("test", key +">>>" + url.queryParameterValues(key).get(0));
        }


        Log.e("test", "header nsign>>>" + requestNew.header("nsign"));
        Log.e("test", "header get_md5>>>" + requestNew.header("get_md5"));
        Log.e("test", "headern sign_uuid >>> " + requestNew.header("nsign_uuid"));
        return chain.proceed(requestNew);
    }

    private Request setHeader(HttpUrl url, Request request) {

        Map<String, String> localHashMap = urlToMap(url);
        localHashMap.put("map_type", "1");
        localHashMap.put("lng", "126.516167");
        localHashMap.put("lat", "45.794713");
        localHashMap.put("page_size", "20");
        localHashMap.put("city_id", "48");

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
        localHashMap.put("qtime", simpleDateFormat.format(new Date()));
        localHashMap.put("cv", "12.0.2");
        localHashMap.put("v", "4.4.2");
        localHashMap.put("from", "mobile");
        localHashMap.put("pm", "b04");
        localHashMap.put("androidid", "488ad2aa586c2624");
        localHashMap.put("_chat_id", "0");
        localHashMap.put("cid", "48");
        localHashMap.put("sig", sig);

        String uuid = UUID.randomUUID().toString();
        nsign = SignUtil.a(url.url().getPath(),null, localHashMap,
                uuid);

        get_md5 = md5(url.url().getPath() + "?" + url.url().getQuery()) + SignUtil.e(localHashMap,
                url.url().getQuery());
        nsign_uuid = uuid;
        Log.e("test", nsign);

        return request.newBuilder().url(add(url, localHashMap))
                .addHeader("nsign",nsign)
                .addHeader("get_md5",get_md5)
                .addHeader("nsign_uuid",nsign_uuid)
                .build();


    }

    private HttpUrl add(HttpUrl httpUrl, Map<String, String> localHashMap) {
        HttpUrl.Builder builder = httpUrl.newBuilder();

        for (String key : localHashMap.keySet()) {
            builder.addQueryParameter(key, localHashMap.get(key));
        }

        return builder.build();
    }


    private void setSig(HttpUrl url) {

        Map<String, String> localHashMap = urlToMap(url);
        localHashMap.put("map_type", "1");
        localHashMap.put("lng", "126.516167");
        localHashMap.put("lat", "45.794713");
        localHashMap.put("page_size", "20");
        localHashMap.put("city_id", "48");

        sig = md5(sigMapToString(localHashMap) + "andriod#AiFang.coM$");
    }

    public static String md5(String str) {
        if (str == null || str.trim().equals("")) {
            return str;
        }
        try {
            char[] cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF8"));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                stringBuilder.append(cArr[(b & 240) >>> 4]);
                stringBuilder.append(cArr[b & 15]);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return str;
        }
    }

    private Map<String, String> urlToMap(HttpUrl paramr)
    {
        HashMap localHashMap = new HashMap();
        Iterator localIterator1 = paramr.queryParameterNames().iterator();
        while (localIterator1.hasNext())
        {
            String str = (String)localIterator1.next();
            Iterator localIterator2 = paramr.queryParameterValues(str).iterator();
            while (localIterator2.hasNext()) {
                //name , values
                localHashMap.put(str, (String)localIterator2.next());
            }
        }

        return localHashMap;
    }

    public static String sigMapToString(Map<String, String> localObject)
    {
        List<String> paramr = new ArrayList();

        for (String key : localObject.keySet()) {

            paramr.add(key + "=" + localObject.get(key));
        }

        Collections.sort(paramr);

        StringBuffer localStringBuffer = new StringBuffer();

        for (int i = 0; i < paramr.size(); i++) {

            String str = paramr.get(i);
            if (localStringBuffer.toString().length() == 0) {
                localStringBuffer.append(str);
            } else {
                localStringBuffer.append("&" + str);
            }
        }

        return localStringBuffer.toString();
    }


}
