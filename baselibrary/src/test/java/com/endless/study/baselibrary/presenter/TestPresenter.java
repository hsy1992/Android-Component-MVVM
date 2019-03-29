package com.endless.study.baselibrary.presenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author haosiyuan
 * @date 2019/2/18 5:13 PM
 */
@RunWith(PowerMockRunner.class)
public class TestPresenter {


    @Test
    @PrepareForTest(Presenter.class)
    public void test() {

        Presenter presenter = new Presenter();
        TestView testView = new TestView();

        PowerMockito.when(testView).thenCallRealMethod();
    }

}
