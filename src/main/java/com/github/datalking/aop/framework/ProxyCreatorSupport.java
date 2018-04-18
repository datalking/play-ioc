package com.github.datalking.aop.framework;

import com.github.datalking.util.Assert;

/**
 * 提供创建代理的方法
 *
 * @author yaoo on 4/18/18
 */
public class ProxyCreatorSupport extends AdvisedSupport {


    private AopProxyFactory aopProxyFactory;

    public ProxyCreatorSupport() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    public ProxyCreatorSupport(AopProxyFactory aopProxyFactory) {
        Assert.notNull(aopProxyFactory, "AopProxyFactory must not be null");
        this.aopProxyFactory = aopProxyFactory;
    }

    public AopProxyFactory getAopProxyFactory() {
        return aopProxyFactory;
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }


    protected final synchronized AopProxy createAopProxy() {

        return getAopProxyFactory().createAopProxy(this);
    }

}
