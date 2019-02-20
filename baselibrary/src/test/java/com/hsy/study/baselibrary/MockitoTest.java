package com.hsy.study.baselibrary;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**Mockito 的使用
 * @author haosiyuan
 * @date 2019/2/18 1:26 PM
 */
public class MockitoTest {


    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    public Person mPerson;

    @Test
    public void checkNull() {
        //assertNotNull(mPerson);
    }

    /**
     * 状态
     */
    @Test
    public void testPersonReturn() {

        Mockito.when(mPerson.getName()).thenReturn("小明");
        Mockito.when(mPerson.getAge()).thenThrow(new NullPointerException("no age"));

        System.out.println(mPerson.getName());
        System.out.println(mPerson.getAge());
    }

    /**
     * 验证
     */
    @Test
    public void verify() {

        Mockito.when(mPerson.getAge()).thenReturn(18);
        System.out.println(mPerson.getAge());
        System.out.println(System.currentTimeMillis());

        //延时100ms
        Mockito.verify(mPerson, Mockito.after(100)).getAge();
        //最多验证2次
//        Mockito.verify(mPerson, Mockito.atLeast(2)).getAge();
        mPerson.getAge();
        mPerson.getAge();
        //验证方法调用2次
//        Mockito.verify(mPerson, Mockito.times(2)).getAge();
    }

}
