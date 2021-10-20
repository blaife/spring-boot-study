package com.blaife.config.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 数组注入演示
 * @Author: magd39318
 * @Date: 2021/10/20 18:12
 */
@Component
@ConfigurationProperties(prefix = "my")
public class UserForArray {

    private List<User> user = new ArrayList<>();

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
