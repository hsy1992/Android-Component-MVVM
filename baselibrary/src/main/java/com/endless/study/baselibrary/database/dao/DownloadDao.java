package com.endless.study.baselibrary.database.dao;

import com.endless.study.baselibrary.database.entity.Download;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

/**
 * 下载
 * @author haosiyuan
 * @date 2019/4/8 4:56 PM
 */
@Dao
public interface DownloadDao {

    /**
     * 查找全部
     * @return
     */
    @Query("SELECT * FROM tb_download")
    List<Download> loadAll();

    /**
     * 根据文件路径查找
     * @param filePath
     * @param fileName
     * @return
     */
    @Query("SELECT * FROM TB_DOWNLOAD WHERE filePath = :filePath and fileName = :fileName")
    Download loadByFilePath(String filePath, String fileName);
}
