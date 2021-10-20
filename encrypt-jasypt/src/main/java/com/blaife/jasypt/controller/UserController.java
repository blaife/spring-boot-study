package com.blaife.jasypt.controller;

import com.blaife.jasypt.entity.User;
import com.blaife.jasypt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author blaife
 * @date 2021/5/9 9:58
 */
@RestController
@RequestMapping("/jasypt")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/findUserById")
    public User findUserById() {
        return userMapper.findUserById(1);
    }
}
