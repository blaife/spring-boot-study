package com.blaife.aspectj;

import com.blaife.aspectj.service.AspectjService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author blaife
 * @description 自定义加密
 * @date 2021/06/30
 */
@SpringBootTest
class AspectjApplicationTests {

    @Autowired
    private AspectjService aspectjService;

    @Test
    void contextLoads() {
        aspectjService.testAspectJ("1");
    }

}
