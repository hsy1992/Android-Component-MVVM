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
import java.util.Set;
import java.util.UUID;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.HTTP;

/**
 * @author haosiyuan
 * @date 2019-04-29 10:34
 */
public class GetSecretPhone implements Interceptor {

    public  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private String sig = "";

    public Request getSecret(Request request) {
        String sL2 = "";
        HttpUrl httpUrl = request.url();


        String path = httpUrl.url().getPath();

        Map<String, String> mapCd = getCd();

        List b = b(httpUrl);

        b.addAll(i(mapCd));

        sL2 = p(b);

        sL2 = path + sL2 + "5d41a9e970273bca";

        String ku = md5(sL2);

        sig = ku;
        //479986dc45248559dd8771870cb0570d
        Log.e("test", "sig>>>>>" + sig);

        Request request1 = request.newBuilder()
                .url(a(httpUrl, mapCd))
                .addHeader("sig", ku)
                .addHeader("key", "eb8cd4ef60fde7580260cf9cf4250a24")
                .addHeader("AuthToken","")
                .addHeader("MemberToken","")
                .build();

        return request1;
    }

    private HttpUrl a(HttpUrl rVar, Map<String, String> hashMap) {
        for (String str : hashMap.keySet()) {
            rVar = rVar.newBuilder().addQueryParameter(str, (String) hashMap.get(str)).build();
        }
        return rVar;
    }


    private static List<String> b(HttpUrl aUI)
    {
        Object localObject = httpurlToMap(aUI).entrySet();
        List paramr = new ArrayList();
        localObject = ((Set)localObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
            Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
            paramr.add(localEntry.getKey() + "=" + localEntry.getValue());

        }
        return paramr;
    }

//    /mobile/v5/common/get_secret_phone?called=18945005080&prop_id=82277011
//            &user_id=56794816&biz_type=zf&macid=a37f503c8d0c5f37f310b5cbef075914&app=a-ajk
//    &_pid=4007&o=samsung-user%204.4.2%20KOT49H%203.8.017.0602%20release-keys&from=mobile
//    &m=Android-SM-E7000&cv=12.0.2&cid=0&version_code=321859&i=864394010164947&v=4.4.2&androidid=A45E60CEDFF70000
//    &manufacturer=samsung&qtime=20190429145211&pm=b61&uuid=9a38ec53-8f4e-42e7-b7c7-b473de337134&_chat_id=0

    public Map getCd() {
        Map<String, String> localHashMap = new HashMap<>();
        localHashMap.put("i", "864394010164947");
        localHashMap.put("macid", "a37f503c8d0c5f37f310b5cbef075914");
        localHashMap.put("androidid", "A45E60CEDFF70000");
        localHashMap.put("m", "Android-SM-E7000");
        localHashMap.put("manufacturer", "samsung");
        localHashMap.put("version_code", "321859");
        localHashMap.put("o", "samsung-user 4.4.2 KOT49H 3.8.017.0602 release-keys");
        localHashMap.put("v", "4.4.2");
        localHashMap.put("cv", "12.0.2");
        localHashMap.put("app", "a-ajk");
        localHashMap.put("pm", "b61");
        localHashMap.put("cid", "0");
        localHashMap.put("_chat_id", "0");
        localHashMap.put("from", "mobile");
        localHashMap.put("qtime", simpleDateFormat.format(new Date()));
//        localHashMap.put("qtime", "20190429145211");
        localHashMap.put("uuid", "9a38ec53-8f4e-42e7-b7c7-b473de337134");
        localHashMap.put("_pid", "4007");

        return localHashMap;
    }

    //b.i
    public static List<String> i(Map<String, String> paramMap)
    {
        ArrayList localArrayList = new ArrayList();
        Iterator iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext())
        {
            Object localObject = iterator.next();
            String str = (String)((Map.Entry)localObject).getKey();
            localObject = ((Map.Entry)localObject).getValue();
            if (localObject != null) {
                localArrayList.add(str + "=" + localObject);
            }
        }
        return localArrayList;
    }


    //b.a
    private static Map<String, String> httpurlToMap(HttpUrl paramr)
    {
        HashMap localHashMap = new HashMap();
        Iterator localIterator1 = paramr.queryParameterNames().iterator();
        while (localIterator1.hasNext())
        {
            String str = (String)localIterator1.next();
            Iterator localIterator2 = paramr.queryParameterValues(str).iterator();
            while (localIterator2.hasNext()) {
                localHashMap.put(str, (String)localIterator2.next());
            }
        }
        return localHashMap;
    }

    //b.p
    public static String p(List<String> list) {
        Collections.sort(list);
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : list) {
            if (stringBuffer.toString().length() == 0) {
                stringBuffer.append(str);
            } else {
                stringBuffer.append("&" + str);
            }
        }
        return stringBuffer.toString();
    }

    //b.ku
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

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request requestNew  = getSecret(chain.request());

        //sig 正确
        HttpUrl url = requestNew.url();

        Map<String, String> localHashMap = httpurlToMap(url);

//        localHashMap.putAll(getCd());
//        String uuid = UUID.randomUUID().toString();
//        localHashMap.put("sig",sig);

        for (String key : localHashMap.keySet()) {
            Log.e("test", "key>>>" + key +">>>>value>>>>" + localHashMap.get(key));
        }


//        String uuid = "e31e69e4-c7d2-4836-88cf-0e96c75628c2";
        String uuid = UUID.randomUUID().toString();
        //1000bebaff9a0f457d28ae3c82b913b39f25016600150000e31e69e4
        String nsign = SignUtil.a(url.url().getPath(),null, localHashMap,
                uuid);

        //f145524fe5fd7972072e8791e38cdd60 01660166 目标
        //70b8689dbcb517f477b29b039b491197 01660166
        String get_md5 = md5(url.url().getPath() + "?" + url.url().getQuery()) + SignUtil.e(localHashMap,
                url.url().getQuery());
        String nsign_uuid = uuid;

        Log.e("test", "header url.url().getPath()>>>" + url.url().getPath() + "?" + url.url().getQuery());
        Log.e("test", "header url.url().getQuery()>>>" + url.url().getQuery());

        Log.e("test", "header nsign>>>" + nsign);//正确
        Log.e("test", "header get_md5>>>" + get_md5);
        Log.e("test", "headern sign_uuid >>> " + nsign_uuid);

        return chain.proceed(requestNew.newBuilder().url(add(url, localHashMap))
                .addHeader("nsign",nsign)
                .addHeader("get_md5",get_md5)
                .addHeader("nsign_uuid",nsign_uuid)
                .build());

    }

    private HttpUrl add(HttpUrl httpUrl, Map<String, String> localHashMap) {
        HttpUrl.Builder builder = httpUrl.newBuilder();

        for (String key : localHashMap.keySet()) {
            builder.addQueryParameter(key, localHashMap.get(key));
        }

        return builder.build();
    }
//    /mobile/v5/common/get_secret_phone?called=13313612392&prop_id=82321250&user_id=153542346&biz_type=zf&macid=a37f503c8d0c5f37f310b5cbef075914&app=a-ajk&_pid=2838&o=samsung-user%204.4.2%20KOT49H%203.8.017.0602%20release-keys&from=mobile&m=Android-SM-E7000&cv=12.0.2&cid=0&version_code=321859&i=864394010164947&v=4.4.2&androidid=A45E60CEDFF70000&manufacturer=samsung&qtime=20190430163538&pm=b61&uuid=e18aa081-bbfd-4506-8215-daae58b39f52&_chat_id=0
//    /mobile/v5/common/get_secret_phone?called=18945005080&prop_id=82277011&user_id=56794816&biz_type=zf&app=a-ajk&_pid=4007&macid=a37f503c8d0c5f37f310b5cbef075914&version_code=321859&i=864394010164947&m=Android-SM-E7000&uuid=9a38ec53-8f4e-42e7-b7c7-b473de337134&o=samsung-user%204.4.2%20KOT49H%203.8.017.0602%20release-keys&manufacturer=samsung&qtime=20190429145211&cv=12.0.2&v=4.4.2&from=mobile&pm=b61&androidid=A45E60CEDFF70000&_chat_id=0&cid=0
//                                         called=18945005080&user_id=56794816&prop_id=82277011&biz_type=zf&app=a-ajk&_pid=4007&macid=a37f503c8d0c5f37f310b5cbef075914&version_code=321859&i=864394010164947&m=Android-SM-E7000&uuid=9a38ec53-8f4e-42e7-b7c7-b473de337134&manufacturer=samsung&o=samsung-user%204.4.2%20KOT49H%203.8.017.0602%20release-keys&qtime=20190429145211&cv=12.0.2&v=4.4.2&from=mobile&pm=b61&androidid=A45E60CEDFF70000&_chat_id=0&cid=0
}
