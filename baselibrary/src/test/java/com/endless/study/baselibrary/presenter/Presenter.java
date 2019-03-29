package com.endless.study.baselibrary.presenter;


/**
 * @author haosiyuan
 * @date 2019/2/18 5:00 PM
 */
public class Presenter extends BasePresenter<TestView> {

    public void getData(){
        rootView.back("返回值了");
    }
}
