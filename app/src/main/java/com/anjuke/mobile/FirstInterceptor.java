package com.anjuke.mobile;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * xinfang
 * @author haosiyuan
 * @date 2019-04-29 11:25
 */
public class FirstInterceptor implements Interceptor {

    private String sig = "";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        HttpUrl url = request.url();
        //第一个拦截器
        setSig(url);

        return null;
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
