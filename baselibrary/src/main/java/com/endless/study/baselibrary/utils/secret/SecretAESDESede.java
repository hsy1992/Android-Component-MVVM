package com.endless.study.baselibrary.utils.secret;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密
 * @author haosiyuan
 * @date 2019/3/22 2:24 PM
 */
public class SecretAESDESede implements ISecret{

    public static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    public static final String AES_CBC_PKCS7PADDING = "AES/CBC/PKCS7Padding";
    public static final String DESEDE_CBC_NOPADDING = "DESede/CBC/NoPadding";
    public static final String DESEDE_CBC_PKCS5PADDING = "DESede/CBC/PKCS5Padding";
    public static final String DESEDE_CBC_PKCS7PADDING = "DESede/CBC/PKCS7Padding";

    /**
     * 加密类型
     */
    private Cipher cipher;
    /**
     * 加密Key
     */
    private SecretKeySpec keySpec;
    /**
     * 参数
     */
    private IvParameterSpec ivParameterSpec;

    /**
     * @param md5 md5字符串
     * @param cipherType 密码类型
     */
    public SecretAESDESede(String md5, String cipherType) {
        this(md5.substring(0, 15), md5.substring(16, 31), cipherType);
    }

    /**
     * @param secretKey
     * @param iv
     * @param cipherType
     */
    public SecretAESDESede(String secretKey, String iv, String cipherType) {

        try {
            String str = cipherType.split("/")[0];
            this.cipher = Cipher.getInstance(cipherType);
            this.keySpec = new SecretKeySpec(getStringBytes(secretKey,
                    "AES".equals(str) ? 16 : 24), str);
            this.ivParameterSpec = new IvParameterSpec(getStringBytes(iv,
                    "AES".equals(str) ? 16 : 8));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 加密
     * @param message
     * @return
     */
    @Override
    public String encrypt(String message) {
        String string = "";

        try {
            cipher.init(1, keySpec, ivParameterSpec);
            string = new String(Base64.encode(cipher.doFinal(message.getBytes()), 0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return string;
    }

    /**
     * 解密
     * @param message
     * @return
     */
    @Override
    public String decrypt(String message) {
        String string = "";

        try {
            cipher.init(2, keySpec, ivParameterSpec);
            string = new String(Base64.decode(cipher.doFinal(message.getBytes()), 0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return string;
    }

    /**
     * @param string
     * @param size
     * @return
     */
    private byte[] getStringBytes(String string, int size) {
        byte[] stringBytes = string.getBytes();
        byte[] bytes = new byte[size];

        for(int i = 0; i < stringBytes.length && i < bytes.length; ++i) {
            bytes[i] = stringBytes[i];
        }

        return bytes;
    }
}
