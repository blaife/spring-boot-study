package com.blaife.config.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * @Description: 用于演示基础的属性注入
 * @Author: magd39318
 * @Date: 2021/10/20 15:09
 */
@Component
@PropertySource("classpath:user.properties")
public class UserForBasic {

    @Value("${user.basic.name}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserForBasic{" +
                "name='" + name + '\'' +
                '}';
    }
}
