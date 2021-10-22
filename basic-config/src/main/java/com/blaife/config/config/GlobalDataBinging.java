package com.blaife.config.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import sun.net.util.IPAddressUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 全局数据绑定
 * @Author: magd39318
 * @Date: 2021/10/22 10:52
 */
@ControllerAdvice
public class GlobalDataBinging {

    @ModelAttribute(value = "logData")
    public Map<String, String> logData(){
        HashMap<String, String> map = new HashMap<>();
        map.put("id","4681348354568");
        map.put("name", "blaife");
        map.put("role", "admin");
        return map;
    }

}
