package com.blaife.aspectj.aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author blaife
 * @description TODO
 * @data 2021/6/27 18:52
 */
@Aspect
@Component
@Log
public class LogAspect {
    @Pointcut("execution(* com.blaife.aspectj.service.impl.AspectjServiceImpl.testAspectJ(..))")

    public void stringParam(){

    }


    @Before(value="execution(* testAspectJ(*)) && args(param)", argNames="param")
    public void before1(String param) {
        System.out.println("===param:" + param);
    }

    @Before("stringParam()")
    public void stringParamBefore(JoinPoint joinPoint) {
        System.out.println(joinPoint.getArgs().length);
        for (Object thisParm : joinPoint.getArgs()) {
            System.out.println(thisParm);
        }
    }
}
