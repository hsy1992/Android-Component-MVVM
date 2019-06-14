package com.anjuke.mobile.sign;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haosiyuan
 * @date 2019-04-22 16:49
 */
public class SignUtil {

    private static final HashMap<String, String> eLh = new HashMap();
    private static final byte[] eLi = new byte[0];

    /**
     * path null 参数map uuid
     * @param str  path
     * @param bArr null
     * @param map 参数map
     * @param str2 uuid
     * @return
     */
    public static String a(String str, byte[] bArr, Map<String, String> map, String str2) {
        Map<String, String> map2 = map;
        if (bArr == null) {
            bArr = eLi;
        }
        if (map2 == null) {
            map2 = eLh;
        }

        for (String str3 : map2.keySet()) {
            if (map2.get(str3) == null) {
                map2.put(str3, "");
            }
        }

        Map hashMap = new HashMap();
        for (String str32 : map2.keySet()) {
            hashMap.put(str32, map2.get(str32).getBytes(Charset.forName("UTF-8")));
        }
        // path md5 new byte[0] 参数hashMap uuid 0
        return getSign0(str, s(bArr), hashMap, str2, bArr.length);
    }

    public static String s(byte[] bArr) {
        try {
            char[] cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] digest = MessageDigest.getInstance("MD5").digest(bArr);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                stringBuilder.append(cArr[(digest[i] & 240) >>> 4]);
                stringBuilder.append(cArr[digest[i] & 15]);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "";
        }
    }
    private static native String getSign0(String str, String str2, Map<String, byte[]> map, String str3, int i);


    public static String e(Map<String, String> map, String str) {
        int i = 0;
        for (String str2 : map.keySet()) {
            i = ((String) map.get(str2)).getBytes(Charset.forName("UTF-8")).length + (i + str2.length());
        }
        if (str == null) {
            str = "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(nA(i));
        int i2 = 0;
        for (String split : str.split("&")) {
            String[] split2 = split.split("=", 2);
            if (split2.length == 2) {
                try {
                    i2 += URLDecoder.decode(split2[1], "UTF-8").getBytes().length;
                    i2 += split2[0].length();
                } catch (Exception e) {
                }
            } else {
                i2 += split2[0].length();
            }
        }
        stringBuilder.append(nA(i2));
        return stringBuilder.toString();
    }

    private static String nA(int i) {
        byte[] bytes = "0123456789abcdef".getBytes();
        return new String(new byte[]{bytes[(61440 & i) >> 12], bytes[(i & 3840) >> 8], bytes[(i & 240) >> 4], bytes[i & 15]});
    }
}
