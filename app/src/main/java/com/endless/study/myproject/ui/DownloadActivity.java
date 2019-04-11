package com.endless.study.myproject.ui;

import android.view.View;

import com.endless.study.baselibrary.base.BaseActivity;
import com.endless.study.baselibrary.common.download.enums.DownloadStopMode;
import com.endless.study.baselibrary.dagger.component.AppComponent;
import com.endless.study.baselibrary.database.entity.DownloadEntity;
import com.endless.study.baselibrary.utils.UtilCommon;
import com.endless.study.myproject.R;

import androidx.annotation.NonNull;

/**
 * @author haosiyuan
 * @date 2019/4/11 2:09 PM
 */
public class DownloadActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpAppComponent(@NonNull AppComponent appComponent) {

    }

    public void download(View view) {
        UtilCommon.getAppComponent(this)
                .downloadManager().download(DownloadEntity.Builder.builder()
        .withUrl("http://imtt.dd.qq.com/16891/B3E711AFC542312E2B1E784BC4906A37.apk?fsname=com.qiyi.video_10.3.5_81260.apk&csr=1bbd")
        .withMFileName("aqy.apk")
        .build());
    }

    public void pause(View view) {
        UtilCommon.getAppComponent(this)
                .downloadManager()
                .pause(DownloadEntity.Builder.builder()
                        .withUrl("http://imtt.dd.qq.com/16891/B3E711AFC542312E2B1E784BC4906A37.apk?fsname=com.qiyi.video_10.3.5_81260.apk&csr=1bbd")
                        .withMFileName("aqy.apk")
                        .build(), DownloadStopMode.hand);

    }
}
