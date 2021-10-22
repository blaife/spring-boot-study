package com.blaife.config.config;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @Description: 全局数据预处理
 * @Author: magd39318
 * @Date: 2021/10/22 11:26
 */
@ControllerAdvice
public class GlobalDataPreprocess {

    @InitBinder("b")
    public void b(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("b.");
    }

    @InitBinder("s")
    public void s(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("s.");
    }

}
