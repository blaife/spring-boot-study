package com.blaife.config.entity;

/**
 * @Description: 用于数组演示
 * @Author: magd39318
 * @Date: 2021/10/20 18:12
 */
public class User {

    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
