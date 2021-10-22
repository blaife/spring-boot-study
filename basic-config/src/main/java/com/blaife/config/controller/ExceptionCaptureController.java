package com.blaife.config.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 异常捕获 Controller
 * @Author: magd39318
 * @Date: 2021/10/22 10:40
 */
@RestController
public class ExceptionCaptureController {

    @RequestMapping("/verifyException")
    public String verifyException() {

        int i = 10 / 0;
        return "1";
    }

}
