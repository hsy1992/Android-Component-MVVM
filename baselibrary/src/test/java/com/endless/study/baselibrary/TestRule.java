package com.endless.study.baselibrary;


import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author haosiyuan
 * @date 2019/2/18 11:28 AM
 */
public class TestRule implements org.junit.rules.TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                System.out.println(description.getMethodName() + "开始");

                base.evaluate();

                System.out.println(description.getMethodName() + "结束");
            }
        };
    }
}
