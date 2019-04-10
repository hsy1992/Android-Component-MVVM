package com.endless.study.baselibrary.common.download;

import com.endless.study.baselibrary.database.entity.DownloadEntity;

import java.util.Comparator;

/**
 * 比较器
 * @author haosiyuan
 * @date 2019/4/10 11:13 AM
 */
public class DownloadPriorityComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (((DownloadEntity)o1).getPriority() > ((DownloadEntity)o2).getPriority()) {
            return 1;
        } else {
            return -1;
        }
    }
}
