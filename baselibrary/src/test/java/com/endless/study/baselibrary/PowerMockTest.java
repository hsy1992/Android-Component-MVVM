package com.endless.study.baselibrary;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * PowerMock test  支持static final等
 * @author haosiyuan
 * @date 2019/2/18 2:59 PM
 */
@RunWith(PowerMockRunner.class)
public class PowerMockTest {

    @Test
    @PrepareForTest({PoliceMen.class})
    public void testStaticMethod() throws Exception {

        //1 使用mockito
        PowerMockito.mockStatic(PoliceMen.class); //<-- mock静态类
        Mockito.when(PoliceMen.getColor()).thenReturn("绿色");
        Assert.assertEquals("绿色", PoliceMen.getColor());

        //2 改变 static 变量
        Whitebox.setInternalState(PoliceMen.class, "newColor", "红色的");
        Assert.assertEquals("绿色", PoliceMen.getColor());

        //3 mock私有方法 修改私有
        PoliceMen policeMen = PowerMockito.mock(PoliceMen.class);
        PowerMockito.when(policeMen.getBananaInfo()).thenCallRealMethod();
        PowerMockito.when(policeMen,"flavor").thenReturn("难看");
        Assert.assertEquals("难看绿色",policeMen.getBananaInfo());
        //验证flavor是否调用了一次
        PowerMockito.verifyPrivate(policeMen).invoke("flavor");

        //4 跳过flavor方法直接返回
        PoliceMen skipPoliceMen = new PoliceMen();
        MemberModifier.suppress(PowerMockito.method(PoliceMen.class,"flavor"));
        Assert.assertEquals("null绿色",skipPoliceMen.getBananaInfo());

        //5 更改父类私有变量
        PoliceMen fPoliceMen = new PoliceMen();
        MemberModifier.field(PoliceMen.class,"hair").set(fPoliceMen,"没头发");
        Assert.assertEquals("没头发",fPoliceMen.getHair());

        //6 修改final方法
//        PoliceMen finalPoliceMen = new PoliceMen();
//        PowerMockito.when(finalPoliceMen.isPower()).thenReturn(false);
//        Assert.assertFalse(finalPoliceMen.isPower());

        //7 mock构造方法
        PoliceMen cPoliceMen = PowerMockito.mock(PoliceMen.class);
        PowerMockito.when(cPoliceMen.getHair()).thenReturn("揪头发");
        //如果new新对象，则返回这个上面设置的这个对象
        PowerMockito.whenNew(PoliceMen.class).withNoArguments().thenReturn(cPoliceMen);
        PoliceMen newPoliceMen = new PoliceMen();
        Assert.assertEquals("揪头发", newPoliceMen.getHair());

    }

}
