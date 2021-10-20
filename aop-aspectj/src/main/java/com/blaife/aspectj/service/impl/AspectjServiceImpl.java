package com.blaife.aspectj.service.impl;

import com.blaife.aspectj.service.AspectjService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

/**
 * @author blaife
 * @description TODO
 * @data 2021/6/27 16:52
 */
@Log
@Service
public class AspectjServiceImpl implements AspectjService {

    @Override
    public String testAspectJ(String param) {
        System.out.println(param);
        return param+": 返回值";
    }
}
