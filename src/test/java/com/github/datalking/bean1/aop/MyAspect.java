package com.github.datalking.bean1.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author yaoo on 4/10/18
 */

@Aspect
public class MyAspect {

    @Before("execution(* com.github.datalking.bean..*.*(..))")
    public void before1() {

        System.out.println("====print before method called in package bean..*.*");
    }

}

