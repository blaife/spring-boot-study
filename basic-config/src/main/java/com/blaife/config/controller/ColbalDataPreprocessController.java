package com.blaife.config.controller;

import com.blaife.config.entity.UserForBasic;
import com.blaife.config.entity.UserForSafety;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 全局数据预处理 Controller
 * @Author: magd39318
 * @Date: 2021/10/22 11:24
 */
@RestController
public class ColbalDataPreprocessController {

    @RequestMapping("toUser")
    public void toUser(@ModelAttribute("b") UserForBasic basic, @ModelAttribute("s") UserForSafety safety) {
        System.out.println(basic);
        System.out.println(safety);
    }

}
