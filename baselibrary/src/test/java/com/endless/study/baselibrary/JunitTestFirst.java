package com.endless.study.baselibrary;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author haosiyuan
 * @date 2019/2/18 10:48 AM
 */
public class JunitTestFirst {

    @Rule
    public TestRule testRule = new TestRule();

    @Test
    public void testa(){

        Assert.assertEquals("123", "123");
    }

}
