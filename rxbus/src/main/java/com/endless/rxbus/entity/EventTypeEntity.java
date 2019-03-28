package com.endless.rxbus.entity;

import android.text.TextUtils;

/**
 * Event tag 类型 作为key tag class method都为一样时默认为同一个
 * @author haosiyuan
 * @date 2019/3/26 5:11 PM
 */
public class EventTypeEntity {

    /**
     * Event Tag
     */
    private final String tag;

    /**
     * Event Class
     */
    private final Class<?> clazz;

    /**
     * Object hashCode
     */
    private final int hashCode;

    public EventTypeEntity(String tag, Class<?> clazz) {

        if (TextUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("Event Tag can not be empty");
        }

        this.tag = tag;
        this.clazz = getRealClass(clazz);

        //哈希值 尽量避免在Map中 碰撞导致数据丢失
        final int prime = 31;
        this.hashCode = (prime + tag.hashCode()) * prime + clazz.hashCode();
    }

    @Override
    public String toString() {
        return "[EventType " + tag + " && " + clazz + "]";
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final EventTypeEntity other = (EventTypeEntity) obj;

        return tag.equals(other.tag) && clazz == other.clazz;
    }

    /**
     * 获取类型 基本类型
     * @param clazz
     * @return
     */
    private Class<?> getRealClass(Class<?> clazz) {

        if (clazz == null) {
            return null;
        }

        String clsName = clazz.getName();

        if (int.class.getName().equals(clsName)) {
            clazz = Integer.class;
        } else if (double.class.getName().equals(clsName)) {
            clazz = Double.class;
        } else if (float.class.getName().equals(clsName)) {
            clazz = Float.class;
        } else if (long.class.getName().equals(clsName)) {
            clazz = Long.class;
        } else if (byte.class.getName().equals(clsName)) {
            clazz = Byte.class;
        } else if (short.class.getName().equals(clsName)) {
            clazz = Short.class;
        } else if (boolean.class.getName().equals(clsName)) {
            clazz = Boolean.class;
        } else if (char.class.getName().equals(clsName)) {
            clazz = Character.class;
        }

        return clazz;
    }



}
