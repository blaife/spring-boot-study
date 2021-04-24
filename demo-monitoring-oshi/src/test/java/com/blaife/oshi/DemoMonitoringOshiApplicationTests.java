package com.blaife.oshi;

import com.blaife.oshi.entity.SystemHardwareInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoMonitoringOshiApplicationTests {

    @Test
    void contextLoads() {

    }

    /**
     * 初次尝试
     */
    @Test
    void firstAttempt() {
        SystemHardwareInfo sys = new SystemHardwareInfo();
        sys.copyTo();
        System.out.println(sys.getCpu());
        System.out.println(sys.getJvm());
    }
}
