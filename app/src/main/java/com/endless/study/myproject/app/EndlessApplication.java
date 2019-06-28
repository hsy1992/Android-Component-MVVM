package com.endless.study.myproject.app;

import android.app.Application;
import android.content.Context;

import com.endless.study.baselibrary.base.delegate.IAppLifecycle;
import com.endless.study.baselibrary.common.logger.Logger;

import androidx.annotation.NonNull;

/**
 * @author haosiyuan
 * @date 2019-06-26 17:25
 * info : application 的代理实现类 {@link IAppLifecycle}
 */
public class EndlessApplication implements IAppLifecycle {

    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        Logger.errorInfo("EndlessApplication>>onCreate");
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
