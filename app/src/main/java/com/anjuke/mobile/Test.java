package com.anjuke.mobile;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haosiyuan
 * @date 2019-04-20 20:06
 */
public class Test {

    //nsign


    //sig
//sig cdac26c2cf3c1e142b9978b2fc1ce3c4
    /**
     *          sig
     *         localHashMap.put("map_type", "1");
     *         localHashMap.put("page", "1");
     *         localHashMap.put("lng", "0.0");
     *         localHashMap.put("lat", "0.0");
     *         localHashMap.put("page_size", "20");
     *         localHashMap.put("city_id", "48");
     * @param args
     */
    public static void main(String[] args) {

        HashMap localHashMap = new HashMap();
        localHashMap.put("map_type", "1");
        localHashMap.put("page", "1");
        localHashMap.put("lng", "126.516167");
        localHashMap.put("lat", "45.794713");
        localHashMap.put("page_size", "20");
        localHashMap.put("city_id", "48");
//        localHashMap.put("api_key", "androidkey");
//        localHashMap.put("app", "a-ajk");
//        localHashMap.put("_pid", "13084");
//        localHashMap.put("macid", "0f607264fc6318a92b9e13c65db7cd3c");
//        localHashMap.put("version_code", "321859");
//        localHashMap.put("i", "82c16543d4c7dbc1");
//        localHashMap.put("m", "Android-STF-AL10");
//        localHashMap.put("uuid", "c4476c21-f43f-4557-86db-fc344a00f4f8");
//        localHashMap.put("manufacturer", "HUAWEI");
//        localHashMap.put("o", "STF-AL10-user%208.0.0%20HUAWEISTF-AL10%20359(C00GT)%20release-keys");
//        localHashMap.put("qtime", "20190420184419");
//        localHashMap.put("cv", "12.0.2");
//        localHashMap.put("v", "8.0.0");
//        localHashMap.put("from", "mobile");
//        localHashMap.put("pm", "b61");
//        localHashMap.put("androidid", "82c16543d4c7dbc1");
//        localHashMap.put("_chat_id", "0");
//        localHashMap.put("cid", "-1");
        // key=value&key=value andriod#AiFang.coM$
        //map_type=1&page=1&lng=0.0&lat=0.0&page_size=20&city_id=48&api_key=androidkey&app=a-ajk&_pid=13084&macid=0f607264fc6318a92b9e13c65db7cd3c&version_code=321859&i=82c16543d4c7dbc1&m=Android-STF-AL10&uuid=c4476c21-f43f-4557-86db-fc344a00f4f8&manufacturer=HUAWEI&o=STF-AL10-user%208.0.0%20HUAWEISTF-AL10%20359(C00GT)%20release-keys&qtime=20190420184419&cv=12.0.2&v=8.0.0&from=mobile&pm=b61&androidid=82c16543d4c7dbc1&_chat_id=0&cid=-1andriod#AiFang.coM$
        String string = "map_type=1&page=1&lng=0.0&lat=0.0&page_size=20&city_id=48" +
                "&api_key=androidkey&app=a-ajk&_pid=13084&macid=0f607264fc6318a92b9e13c65db7cd3c" +
                "&version_code=321859&i=82c16543d4c7dbc1&m=Android-STF-AL10&uuid=c4476c21-f43f-4557-86db-fc344a00f4f8" +
                "&manufacturer=HUAWEI&o=STF-AL10-user%208.0.0%20HUAWEISTF-AL10%20359(C00GT)%20release-keys" +
                "&qtime=20190420184419&cv=12.0.2&v=8.0.0&from=mobile&pm=b61&androidid=82c16543d4c7dbc1&_chat_id=0" +
                "&cid=-1andriod#AiFang.coM$";

        String string1 = b(localHashMap) + "andriod#AiFang.coM$";
        System.out.println(ku(string1));

        URL url = null;
        try {
            url = new URL("https://api.anjuke.com/xinfang/m/android/1.3/loupan/newlistv2/?city_id=48&page_size=20&lng=126.516167&page=1&lat=45.794713&map_type=1&sig=cdac26c2cf3c1e142b9978b2fc1ce3c4&api_key=androidkey&macid=f0ca3da7e17f3a2aa9ebb4194cab4e98&app=a-ajk&_pid=786&o=SM-G955F-user%204.4.2%20JLS36C%20381180615%20release-keys&from=mobile&m=Android-SM-G955F&cv=12.0.2&cid=48&version_code=321859&i=355757010481386&v=4.4.2&androidid=488ad2aa586c2624&manufacturer=samsung&qtime=20190422171307&pm=b04&uuid=8f80b0e6-34c2-4d0d-827d-0ef2f0a81530&_chat_id=0");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //get_md5   fa5ce6a79506d81e9bb735539c96d9c3019a019a
//                    fa5ce6a79506d81e9bb735539c96d9c3019a019a
        System.out.println("path>>>>>>>>>" + url.getPath());


    }

    public static String ku(String str) {
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

    public static String b(Map<String, String> localObject)
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

//    /mobile/v5/sale/filter/get?
//    city_id=48&version=1554284586&app=a-ajk
//    &_pid=20185&macid=0f607264fc6318a92b9e13c65db7cd3c&version_code=321859
//    &i=82c16543d4c7dbc1&m=Android-STF-AL10&uuid=c5456bcc-1aa0-46b4-b995-b927b3cd1c31
//    &manufacturer=HUAWEI&o=STF-AL10-user%208.0.0%20HUAWEISTF-AL10%20359(C00GT)%20release-keys
//    &qtime=20190422221924&cv=12.0.2&v=8.0.0&from=mobile&pm=b61&androidid=82c16543d4c7dbc1&_chat_id=0&cid=48


}
