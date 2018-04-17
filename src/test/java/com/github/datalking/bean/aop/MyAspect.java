package com.github.datalking.bean.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author yaoo on 4/10/18
 */

@Aspect
public class MyAspect {

    @Before("execution(* bean..*.*(..))")
    public void advice() {

        System.out.println("====print this before calling method in package bean..*.*");
    }

}

