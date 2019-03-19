package com.hsy.study.baselibrary.cache.local;

import com.hsy.study.baselibrary.utils.PreconditionsUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 智能缓存
 * 含有可以将数据永远存储在内存中的内存容器，和LruCache
 * 可以根据传入的key 智能判断存储至那个容器
 *
 * @author haosiyuan
 * @date 2019/2/12 10:13 AM
 */
public class IntelligentCache<V> implements ICache<String, V> {

    /**
     * 可以永久存储的存储容器
     */
    private final Map<String, V> mMap;
    /**
     * 达到最大容量时根据LRU算法抛弃不合规的存储容器
     */
    private final ICache<String, V> mCache;
    /**
     * 永久存储数据头
     */
    public static final String KEY_KEEP = "Keep=";

    public IntelligentCache(int size){
        mMap = new HashMap<>();
        mCache = new LruCache<>(size);
    }


    @Override
    public synchronized int size() {
        return mMap.size() + mCache.size();
    }

    @Override
    public synchronized int getMaxSize() {
        return mMap.size() + mCache.getMaxSize();
    }

    @Nullable
    @Override
    public synchronized V get(String key) {
        if (key.startsWith(KEY_KEEP)){
            return mMap.get(key);
        }
        return mCache.get(key);
    }

    @Nullable
    @Override
    public synchronized V put(String key, V value) {
        if (key.startsWith(KEY_KEEP)) {
            return mMap.put(key, value);
        }
        return mCache.put(key, value);
    }

    @Nullable
    @Override
    public synchronized V remove(String key) {
        if (key.startsWith(KEY_KEEP)){
            return mMap.remove(key);
        }
        return mCache.remove(key);
    }

    @Override
    public boolean containsKey(String key) {
        if (key.startsWith(KEY_KEEP)) {
            return mMap.containsKey(key);
        }
        return mCache.containsKey(key);
    }

    @Override
    public Set<String> keySet() {
        Set<String> set = mCache.keySet();
        set.addAll(mMap.keySet());
        return set;
    }

    @Override
    public void clear() {
        mCache.clear();
        mMap.clear();
    }

    /**
     * 返回一个可以永久存储的key
     * @return
     */
    @NonNull
    public static String getKeyOfKeep(@NonNull String key){
        PreconditionsUtil.checkNotNull(key, "key == null");
        return KEY_KEEP + key;
    }
}
