package com.blaife.config.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;


import java.util.Map;

/**
 * @Description: 自定义异常返回信息
 * @Author: magd39318
 * @Date: 2021/10/22 14:08
 */
@Component
public class BasicErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, options);
        if ((Integer) map.get("status") == 500) {
            map.put("message", "服务器内部错误");
        }
        return map;
    }
}
