package com.hsy.study.networkclientstate;

/**
 * 网络速度
 * @author haosiyuan
 * @date 2019/3/1 10:22 AM
 */
public enum NetworkQuality {

    /**
     * 低于150K
     */
    POOR,
    /**
     * 150K - 500k
     */
    MODERATE,
    /**
     * 500K-2000K
     */
    GOOD,
    /**
     * 高于 2000K
     */
    EXCELLENT,
    /**
     * 错误
     */
    UNKNOWN
}
