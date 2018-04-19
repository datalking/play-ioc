package com.github.datalking.aop.framework;

import java.io.Serializable;

/**
 * AOP代理创建工厂 默认实现类
 * 决定创建代理的方式
 *
 * @author yaoo on 4/18/18
 */
public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {

    @Override
    public AopProxy createAopProxy(AdvisedSupport config) {

        //if (config.isProxyTargetClass() || config.isOptimize() ||

        Class<?> targetClass = config.getTargetClass();

        if (targetClass == null) {
            try {
                throw new Exception("创建代理的对象不能为空");
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
