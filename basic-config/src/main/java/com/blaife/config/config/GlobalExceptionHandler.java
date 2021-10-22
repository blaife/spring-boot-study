package com.blaife.config.config;

import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 全局异常处理
 * @Author: magd39318
 * @Date: 2021/10/22 10:34
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String customerException(Exception e) {
        e.printStackTrace();
        return "系统异常";
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public String customerArithmeticException(Exception e) {
        e.printStackTrace();
        return "by zero";
    }

}
