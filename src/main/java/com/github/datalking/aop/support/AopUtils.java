package com.github.datalking.aop.support;

import com.github.datalking.aop.framework.Advised;
import com.github.datalking.aop.framework.AdvisedSupport;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yaoo on 4/18/18
 */
public interface AopUtils {


    static Object invokeJoinpointUsingReflection(Object target, Method method, Object[] args) {

        try {
            //ReflectionUtils.makeAccessible(method);
            return method.invoke(target, args);
        } catch (InvocationTargetException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return null;
    }



    static Class<?>[] completeProxiedInterfaces(AdvisedSupport advised) {

        Class<?>[] specifiedInterfaces = advised.getProxiedInterfaces();

        if (specifiedInterfaces.length == 0) {
            // 检查targetClass是否是接口
            Class<?> targetClass = advised.getTargetClass();

            if (targetClass != null) {
                if (targetClass.isInterface()) {
                    advised.setInterfaces(targetClass);
                } else if (Proxy.isProxyClass(targetClass)) {
                    advised.setInterfaces(targetClass.getInterfaces());
                }
                specifiedInterfaces = advised.getProxiedInterfaces();
            }
        }

        boolean addAdvised = !advised.isInterfaceProxied(Advised.class);
        int nonUserIfcCount = 0;

        if (addAdvised) {
            nonUserIfcCount++;
        }

        Class<?>[] proxiedInterfaces = new Class<?>[specifiedInterfaces.length + nonUserIfcCount];
        System.arraycopy(specifiedInterfaces, 0, proxiedInterfaces, 0, specifiedInterfaces.length);
        int index = specifiedInterfaces.length;

        if (addAdvised) {
            proxiedInterfaces[index] = Advised.class;
            index++;
        }

        return proxiedInterfaces;
    }

//     static Method getMostSpecificMethod(Method method, Class<?> targetClass) {
//        Method resolvedMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
//        // If we are dealing with method with generic parameters, find the original method.
//        return BridgeMethodResolver.findBridgedMethod(resolvedMethod);
//    }


}
