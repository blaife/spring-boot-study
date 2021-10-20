package com.blaife.config;

import com.blaife.config.entity.UserForArray;
import com.blaife.config.entity.UserForBasic;
import com.blaife.config.entity.UserForSafety;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicConfigApplicationTests {

    @Autowired
    UserForBasic userForBasic;

    @Autowired
    UserForSafety userForSafety;

    @Autowired
    UserForArray userForArray;

    /**
     * 基本的属性注入
     */
    @Test
    void propertiesIntoForBasic() {
        System.out.println(userForBasic.toString());
    }

    /**
     * 安全的属性注入
     */
    @Test
    void propertiesIntoForSafety() {
        System.out.println(userForSafety.toString());
    }

    /**
     * 数组注入
     */
    @Test
    void propertiesIntoForArray() {
        userForArray.getUser().stream().forEach(a -> System.out.println(a));
    }

}
