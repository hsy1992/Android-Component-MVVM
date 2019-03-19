package com.hsy.study.baselibrary.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 各种类型转换
 * @author haosiyuan
 * @date 2019/3/5 11:40 AM
 */
public class TransformationUtil {

    /**
     * 基础类型转换
     */
    public static class BasicType {

        /**
         * 通用 数组 转 List
         * @param es
         * @param <E>
         * @return
         */
        public static <E> List<E> arrayToList(E[] es) {
            PreconditionsUtil.checkNotNull(es, "array can not be null");
            return Arrays.asList(es);
        }
    }

}
