package com.github.datalking.aop.framework;

import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 * @author yaoo on 4/18/18
 */
public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {

    @Override
    public AopProxy createAopProxy(AdvisedSupport config) {

        //if (config.isProxyTargetClass() || config.isOptimize() ||

        Class<?> targetClass = config.getTargetClass();
        if (targetClass == null) {
            try {
                throw new Exception("TargetSource cannot determine target class: " + "Either an interface or a target is required for proxy creation.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!targetClass.isInterface()) {
            return new CglibAopProxy(config);
        }


        // 默认使用jdk动态代理
        return new JdkDynamicAopProxy(config);
    }
}
