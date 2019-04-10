package com.endless.study.baselibrary.common.download;


import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;

import com.endless.study.baselibrary.database.entity.DownloadEntity;

import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * {@link okhttp3.OkHttpClient} 返回体
 * @author haosiyuan
 * @date 2019/4/5 1:29 PM
 */
class DownloadResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private DownloadCallback downloadCallback;
    private BufferedSource mBufferedSource;
    private long isDownLength;
    private Handler handler = new Handler(Looper.getMainLooper());
    private DownloadEntity downloadEntity;

    DownloadResponseBody(ResponseBody responseBody, String range, DownloadCallback downloadCallback, DownloadEntity downloadEntity) {
        this.responseBody = responseBody;
        this.downloadCallback = downloadCallback;
        this.downloadEntity = downloadEntity;

        if (TextUtils.isEmpty(range)) {
            throw new IllegalArgumentException("Range can not be null");
        }

        isDownLength = Long.parseLong(range.replace("bytes=","").replace("-",""));
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null){
            mBufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return mBufferedSource;
    }

    private Source source(BufferedSource source) {

        //更新下载总数
        receiveTotalLength(isDownLength + responseBody.contentLength());

        return new ForwardingSource(source) {

            //下载总数
            long totalFileLength = isDownLength + responseBody.contentLength();
            //目前下载总数
            long totalBytesRead = isDownLength;
            //当前接受数
            long receiveLen = 0L;
            //开始时间
            long startTime = System.currentTimeMillis();
            //计算速度
            long speed = 0L;
            //技术
            int count = 0;
            //回调时间
            long currentTime;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {

                final long bytesRead = super.read(sink,byteCount);

                totalBytesRead += bytesRead != -1 ? bytesRead : 0;

                receiveLen += bytesRead != -1 ? bytesRead : 0;
                ++ count;

                //5000次或者超过百分之10 进行状态回调
                if (receiveLen * 10 / totalFileLength > 1 || count >= 5000) {
                    currentTime = System.currentTimeMillis();

                    if (downloadCallback != null){
                        synchronized (downloadCallback){
                            handler.post(() -> {
                                try {
                                    downloadCallback.onCurrentSizeChanged(downloadEntity.getId(),
                                            totalBytesRead / totalFileLength * 100,
                                            receiveLen / (currentTime - startTime) * 1000);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        receiveLen = 0L;
                        count = 0;
                    }
                }

                return bytesRead;
            }
        };
    }

    /**
     * 更新下载总数
     * @param totalLength
     */
    private void receiveTotalLength(long totalLength) {

        if (downloadCallback != null){

            synchronized (this.downloadCallback){
                handler.post(() -> {
                    try {
                        downloadCallback.onTotalLengthReceived(downloadEntity.getId(), totalLength);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

}
