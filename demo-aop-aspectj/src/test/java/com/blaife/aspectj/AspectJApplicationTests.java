package com.blaife.aspectj;

import com.blaife.aspectj.service.AspectjService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AspectJApplicationTests {

    @Autowired
    private AspectjService aspectjService;

    @Test
    void contextLoads() {
        aspectjService.testAspectJ("1");
    }

}
