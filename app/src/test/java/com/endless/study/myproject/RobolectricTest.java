package com.endless.study.myproject;

import com.endless.study.myproject.ui.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;

/**
 * @author haosiyuan
 * @date 2019/2/19 9:57 AM
 */
@RunWith(RobolectricTestRunner.class)
public class RobolectricTest {

    private ActivityController<MainActivity> activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(MainActivity.class);
    }

    @Test
    public void testActivity() {
//        Shadows.shadowOf(activity.get()).getContentView().performClick();
    }
}
