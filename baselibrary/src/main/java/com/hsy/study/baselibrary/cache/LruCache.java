package com.hsy.study.baselibrary.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import androidx.annotation.Nullable;

/**
 * 自定义 LRU 缓存淘汰算法
 * @author haosiyuan
 * @date 2019/2/11 5:15 PM
 */
public class LruCache<K, V> implements ICache<K, V> {

    private final LinkedHashMap<K, V> cache = new LinkedHashMap<>(100, 0.75f,true);
    private final int initialMaxSize;
    private int maxSize;
    private int currentSize;

    /**
     * @param size 缓存的最大能接受的size
     */
    public LruCache(int size) {
        this.initialMaxSize = size;
        this.maxSize = size;
    }

    /**
     * 返回每个 {@code item} 所占用的 size,默认为1,这个 size 的单位必须和构造函数所传入的 size 一致
     * 子类可以重写这个方法以适应不同的单位,比如说 bytes
     *
     * @param item 每个 {@code item} 所占用的 size
     * @return 单个 item 的 {@code size}
     */
    protected int getItemSize(V item) {
        return 1;
    }

    /**
     * 当缓存中有被驱逐的条目时,会回调此方法,默认空实现,子类可以重写这个方法
     *
     * @param key   被驱逐条目的 {@code key}
     * @param value 被驱逐条目的 {@code value}
     */
    protected void onItemEvicted(K key, V value) {
        // optional override
    }

    @Override
    public synchronized int size() {
        return currentSize;
    }

    @Override
    public synchronized int getMaxSize() {
        return maxSize;
    }

    @Nullable
    @Override
    public synchronized V get(K key) {
        return cache.get(key);
    }

    @Override
    public synchronized boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    @Override
    public synchronized Set<K> keySet() {
        return cache.keySet();
    }

    /**
     * 清除所有缓存
     */
    @Override
    public void clear() {
        trimToSize(0);
    }

    /**
     * 将key 和 value 加入缓存如果已经缓存过将被新的替换并返回 如果为null则为一个新的缓存
     * @param key {@code key}
     * @param value {@code value}
     * @return 如果key已经缓存过，返回result,没有缓存过则返回null
     */
    @Nullable
    @Override
    public synchronized V put(K key, V value) {
        final int itemSize = getItemSize(value);
        if (itemSize >= maxSize){
            onItemEvicted(key, value);
            return null;
        }

        final V result = cache.put(key, value);
        if (value != null){
            currentSize += getItemSize(value);
        }
        if (result != null){
            currentSize -= getItemSize(result);
        }
        evict();

        return result;
    }

    /**
     * 移除缓存 并释放size
     * @param key {@code key}
     * @return
     */
    @Nullable
    @Override
    public synchronized V remove(K key) {
        final V value = cache.remove(key);
        if (value != null){
            currentSize -= getItemSize(value);
        }
        return value;
    }

    /**
     * 占用缓存是否超过最大size
     */
    private void evict(){
        trimToSize(maxSize);
    }

    /**
     * 当指定的 size 小于当前缓存已占用的总 size 时，会开始清除缓存中使用最少的缓存
     * @param size
     */
    protected synchronized void trimToSize(int size){
        Map.Entry<K, V> last;
        while (currentSize > size){
            //找出使用最少的value
            last = cache.entrySet().iterator().next();
            final V toRemove = last.getValue();
            currentSize -= getItemSize(toRemove);
            final K key = last.getKey();
            cache.remove(key);
            onItemEvicted(key, toRemove);
        }
    }

    /**
     * 设置乘数 修改maxSize 并清理缓存
     * @param multiplier
     */
    public synchronized void setSizeMultiplier(float multiplier){
        if (multiplier < 0){
            throw new IllegalArgumentException("Multiplier must be >= -");
        }

        maxSize = Math.round(initialMaxSize * multiplier);
        evict();
    }

}
