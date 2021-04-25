package com.blaife.oshi;

import com.blaife.oshi.entity.MainServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoMonitoringOshiApplicationTests {

    @Test
    void contextLoads() {

    }

    /**
     * 测试方法
     */
    @Test
    void firstAttempt() {
        MainServer sys = new MainServer();
        sys.copyTo();
        System.out.println(sys.getCpu());
        System.out.println(sys.getJvm());
        System.out.println(sys.getMem());
        System.out.println(sys.getSys());
        System.out.println(sys.getSysFiles());
    }
}
