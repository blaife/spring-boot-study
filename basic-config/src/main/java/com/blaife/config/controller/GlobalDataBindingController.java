package com.blaife.config.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description: 全局数据绑定Controller
 * @Author: magd39318
 * @Date: 2021/10/22 11:00
 */
@RestController
public class GlobalDataBindingController {

    @RequestMapping("/getLogData")
    public Map getLogData(Model model) {
        return (Map) model.asMap().get("logData");
    }

    @RequestMapping("/getLogData2")
    public Map getLogData2(Model model) {
        return (Map) model.getAttribute("logData");
    }

}
