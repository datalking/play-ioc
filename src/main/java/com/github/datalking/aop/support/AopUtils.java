package com.github.datalking.aop.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yaoo on 4/18/18
 */
public interface AopUtils {


    static Object invokeJoinpointUsingReflection(Object target, Method method, Object[] args)
            throws Throwable {

        try {
            //ReflectionUtils.makeAccessible(method);
            return method.invoke(target, args);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }

}
