package com.endless.study.myproject;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author haosiyuan
 * @date 2019/1/25 3:32 PM
 */
public class TestSign {

    public static void main(String[] arg){


        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("url", "/hsAdapter/list");
        hashMap.put("deviceId", "862526039347290");
        hashMap.put("deviceType", "android");
        final StringBuilder sb = new StringBuilder();
//        sb.append(System.currentTimeMillis());
        sb.append("1548318753477");
        hashMap.put("timestamp", sb.toString());
        hashMap.put("version", "3.5.1");

//        hashMap.put("token", "用户login_token");


        String sting = sortMap(hashMap);
        System.out.println(sting);

        String sign = aSign(sting, "homeplus");

        System.out.println(sign);
        System.out.println(byteArrayToHexString(encryptHMAC(sting.getBytes(), "homeplus")));
    }

    public static String sortMap(Map<String, String> map){

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

    public static String aSign(String str, String str2) {
        return (str == null || str.equals("")) ? null : a(a(str.getBytes(), str2));
    }

    private static final String[] a = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static byte[] a(byte[] bArr, String str) {
        try {
            Key secretKeySpec = new SecretKeySpec(str.getBytes(), "HmacMD5");
            System.out.println("key::" + secretKeySpec.getAlgorithm());
            Mac instance = Mac.getInstance(secretKeySpec.getAlgorithm());
            instance.init(secretKeySpec);
            return instance.doFinal(bArr);
        } catch (Throwable e) {
            return null;
        }
    }

    public static String a(byte b) {
        int b2 = b;
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
        System.out.println(new String(bArr));
        StringBuffer stringBuffer = new StringBuffer();
        for (byte a : bArr) {
            stringBuffer.append(a(a));
        }
        return stringBuffer.toString();
    }


    /*byte数组转换为HexString*/
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }
    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptHMAC(byte[] data, String key) {

        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "HmacMD5");
        Mac mac = null;
        try {
            mac = Mac.getInstance(secretKey.getAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {

            mac.init(secretKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return mac.doFinal(data);

    }
}
