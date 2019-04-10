package com.endless.study.baselibrary.database.dao;

import com.endless.study.baselibrary.database.entity.DownloadEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;

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
    Flowable<List<DownloadEntity>> loadAll();

    /**
     * 根据文件路径与url查找
     * @param url
     * @param filePath
     * @return
     */
    @Query("SELECT * FROM tb_download WHERE filePath = :filePath and url = :url")
    List<DownloadEntity> loadByFilePath(String url, String filePath);

    /**
     * 根据文件路径与url查找
     * @param url
     * @param filePath
     * @return
     */
    @Query("SELECT * FROM tb_download WHERE filePath = :filePath and url = :url")
    Flowable<List<DownloadEntity>> loadByFilePathByFlowable(String url, String filePath);

    /**
     * 根据文件路径查找
     * @param filePath
     * @return
     */
    @Query("SELECT * FROM tb_download WHERE filePath = :filePath")
    List<DownloadEntity> loadByFilePath(String filePath);

    /**
     * 根据下载Id查找
     * @param downloadId
     * @return
     */
    @Query("SELECT * FROM tb_download WHERE id = :downloadId")
    DownloadEntity loadById(long downloadId);

    /**
     * 根据文件路径查找
     * @param filePath
     * @return
     */
    @Query("SELECT * FROM tb_download WHERE filePath = :filePath")
    Flowable<List<DownloadEntity>> loadByFilePathFlowable(String filePath);

    /**
     *
     * @param downloadEntity
     * @return
     */
    @Insert
    long addDownload(DownloadEntity downloadEntity);

    /**
     *
     * @param downloadEntity
     * @return
     */
    @Update
    int updateDownload(DownloadEntity downloadEntity);

    /**
     *
     * @param id
     * @param downloadStatus
     * @return
     */
    @Query("UPDATE tb_download SET status = :downloadStatus WHERE id = :id")
    void updateDownloadStatusById(long id, int downloadStatus);
}
