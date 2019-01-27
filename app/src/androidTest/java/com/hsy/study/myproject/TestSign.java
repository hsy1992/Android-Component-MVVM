package com.hsy.study.myproject;

import android.text.TextUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author haosiyuan
 * @date 2019/1/25 3:32 PM
 */
public class TestSign {

    public static void main(String[] arg){

        Map<String, String> map = new HashMap<>();


        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("url", "/hsAdapter/list");
        hashMap.put("deviceId", "21212312323");
        hashMap.put("deviceType", "android");
        final StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        sb.append("");
        hashMap.put("timestamp", sb.toString());
        hashMap.put("version", "3.5.1");

//        hashMap.put("token", "用户login_token");


        String sign = a(a(b(map).getBytes(), "homeplus"));

        System.out.print(sign);

    }

    public static String b(Map<String, String> map){

        String[] strArr = new String[map.size()];
        int i = 0;

        for (String str : map.keySet()) {
            if (str != null) {
                strArr[i] = str;
            }
            i++;
        }
        Arrays.sort(strArr);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 0; i2 < map.size(); i2++) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(strArr[i2]);
            stringBuilder2.append((String) map.get(strArr[i2]));
            stringBuilder.append(stringBuilder2.toString());
        }
        return stringBuilder.toString().replaceAll("\\}, \\{", "},{");
    }

    private static final String[] a = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static byte[] a(byte[] bArr, String str) {
        try {
            Key secretKeySpec = new SecretKeySpec(str.getBytes(), "HmacMD5");
            Mac instance = Mac.getInstance(secretKeySpec.getAlgorithm());
            instance.init(secretKeySpec);
            return instance.doFinal(bArr);
        } catch (Throwable e) {
            return null;
        }
    }

    public static String a(byte b) {
        int b2 = 0;
        if (b2 < (byte) 0) {
            b2 = b2 + 256;
        }
        int i = b2 / 16;
        b2 %= 16;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(a[i]);
        stringBuilder.append(a[b2]);
        return stringBuilder.toString();
    }

    public static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte a : bArr) {
            stringBuffer.append(a(a));
        }
        return stringBuffer.toString();
    }

//    private static String a(final Context context) {
//        String deviceId;
//        if (TextUtils.isEmpty((CharSequence)(deviceId = ((TelephonyManager)context.getSystemService("phone")).getDeviceId()))) {
//            deviceId = "21212312323";
//        }
//        return deviceId;
//    }

//    private static String b(final Context context) {
//        try {
//            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
//        }
//        catch (Exception ex) {
//            com.google.a.a.a.a.a.a.a((Throwable)ex);
//            return "";
//        }
//    }

}
