package com.blaife.aspectj.controller;

import com.blaife.aspectj.service.AspectjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author blaife
 * @description TODO
 * @data 2021/6/27 19:39
 */
@RestController
@RequestMapping("/aspectj")
public class AspectjController {

    @Autowired
    AspectjService aspectjService;

    @RequestMapping("/aspectjTest")
    public String aspectjTest() {
        return aspectjService.testAspectJ("asd");
    }

}
