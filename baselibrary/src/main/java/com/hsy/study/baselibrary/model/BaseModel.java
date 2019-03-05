package com.hsy.study.baselibrary.model;

import com.hsy.study.baselibrary.config.AppConfig;
import com.hsy.study.baselibrary.viewmodel.IRepositoryManager;


/**
 * @author haosiyuan
 * @date 2019/3/4 5:18 PM
 */
public class BaseModel implements IModel {

    protected IRepositoryManager mRepositoryManager;
    protected int PAGE_SIZE = AppConfig.PAGE_SIZE;


    public BaseModel(IRepositoryManager mRepositoryManager) {
        this.mRepositoryManager = mRepositoryManager;
    }

    protected <T> T getData(String url) {
        //TODO 获取数据库 数据库 -》 HTTP请求 -》数据库 -》更新UI    缓存-》HTTP -》数据库 -》更新UI
        


        return null;
    }


    @Override
    public void onDestroy() {
        mRepositoryManager = null;
    }
}
