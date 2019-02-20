package com.hsy.study.baselibrary;

/**
 * @author haosiyuan
 * @date 2019/2/18 1:44 PM
 */
public class Person {

    private String name;

    private int age;

    private String sex;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private String hair = "头发";

    public String getHair() {
        return hair;
    }
}
