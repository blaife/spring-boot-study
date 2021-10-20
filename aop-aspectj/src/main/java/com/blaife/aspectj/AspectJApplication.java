package com.blaife.aspectj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class AspectJApplication {

    public static void main(String[] args) {
        SpringApplication.run(AspectJApplication.class, args);
    }

}
