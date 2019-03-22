package com.hsy.study.baselibrary.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hsy.study.baselibrary.utils.UtilApp;
import com.hsy.study.baselibrary.utils.secret.ISecret;
import com.hsy.study.baselibrary.utils.secret.SecretAESDESede;
import com.hsy.study.baselibrary.utils.secret.UtilMD5;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * 首选项 保存本地数据 Key,Value
 * @author haosiyuan
 * @date 2019/3/22 2:08 PM
 */
public class AppPreferences {

    /**
     * 加密
     */
    private ISecret secret;
    /**
     * 修改
     */
    private SharedPreferences.Editor editor;
    /**
     * 存储数据
     */
    private SharedPreferences sharedPreferences;

    private Gson gson;

    public AppPreferences(Context context, String name) {
        secret = new SecretAESDESede(UtilMD5.getMD5(UtilApp.getPackageInfo(context).packageName), SecretAESDESede.AES_CBC_PKCS7PADDING);
        sharedPreferences = context.getSharedPreferences(UtilMD5.getMD5(name), 0);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void putBoolean(String key, boolean value) {
        this.editor.putBoolean(UtilMD5.getMD5(key), value).commit();
    }

    public void putFloat(String key, float value) {
        this.editor.putFloat(UtilMD5.getMD5(key), value).commit();
    }

    public void putInt(String key, int value) {
        this.editor.putInt(UtilMD5.getMD5(key), value).commit();
    }

    public void putLong(String key, long value) {
        this.editor.putLong(UtilMD5.getMD5(key), value).commit();
    }

    public void putString(String key, String value) {
        this.editor.putString(UtilMD5.getMD5(key), this.secret.encrypt(value)).commit();
    }

    public void putStringSet(String key, Set<String> value) {
        this.editor.putStringSet(UtilMD5.getMD5(key), value).commit();
    }

    public <T> void putObject(String key, T t) {
        String json = gson.toJson(t);
        this.putString(key, json);
    }

    public boolean getBoolean(String key, boolean value) {
        return this.sharedPreferences.getBoolean(UtilMD5.getMD5(key), value);
    }

    public float getFloat(String key, float value) {
        return this.sharedPreferences.getFloat(UtilMD5.getMD5(key), value);
    }

    public int getInt(String key, int value) {
        return this.sharedPreferences.getInt(UtilMD5.getMD5(key), value);
    }

    public long getLong(String key, long value) {
        return this.sharedPreferences.getLong(UtilMD5.getMD5(key), value);
    }

    public String getString(String key, String value) {
        return this.secret.decrypt(this.sharedPreferences.getString(UtilMD5.getMD5(key),
                this.secret.encrypt(value)));
    }

    public Set<String> getStringSet(String key, Set<String> value) {
        return this.sharedPreferences.getStringSet(UtilMD5.getMD5(key), value);
    }

    public <T> T getObject(String key, Type type) {
        String json = getString(key, "");
        return gson.fromJson(json, type);
    }

    /**
     * 清除
     */
    public void clear() {
        this.editor.clear().commit();
    }

}
