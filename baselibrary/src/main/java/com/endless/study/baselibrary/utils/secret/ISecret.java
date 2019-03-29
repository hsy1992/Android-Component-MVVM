package com.endless.study.baselibrary.utils.secret;

/**
 * 加密接口
 * @author haosiyuan
 * @date 2019/3/22 2:21 PM
 */
public interface ISecret {

    /**
     * 加密
     * @param encryptStr
     * @return
     */
    String encrypt(String encryptStr);

    /**
     * 解密
     * @param decryptStr
     * @return
     */
    String decrypt(String decryptStr);

}
