package com.github.datalking.aop.framework;

import com.github.datalking.aop.Advisor;
import com.github.datalking.aop.TargetSource;
import com.github.datalking.beans.PropertyValues;
import com.github.datalking.beans.factory.BeanFactory;
import com.github.datalking.beans.factory.BeanFactoryAware;
import com.github.datalking.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

import java.beans.PropertyDescriptor;

/**
 * @author yaoo on 4/18/18
 */
public abstract class AbstractAutoProxyCreator extends ProxyConfig
        implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {

    // 不使用代理
    protected static final Object[] DO_NOT_PROXY = null;

    // 拦截器 默认为空
    private String[] interceptorNames = new String[0];

    private BeanFactory beanFactory;

    protected int order = -99999999;
    private boolean proxyTargetClass = false;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected BeanFactory getBeanFactory() {
        return this.beanFactory;
    }


    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) {

        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        if (bean != null) {
            // 根据给定的 bean 的 class 和 name 构建出个 key，格式：beanClassName_beanName
            // Object cacheKey = getCacheKey(bean.getClass(), beanName);

            // if (!this.earlyProxyReferences.contains(cacheKey)) {

            // 如果它适合被代理，则需要封装指定 bean
            return wrapIfNecessary(bean, beanName);
            //}
        }
        return bean;
    }


    protected Object wrapIfNecessary(Object bean, String beanName) {

        // 是否已经处理过
//        if (beanName != null && this.targetSourcedBeans.contains(beanName)) {
//            return bean;
//        }

        // 无需增强
//        if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
//            return bean;
//        }

        // 给定的 bean 类是否代表一个基础设施类，基础设施类不应代理，或者配置了指定 bean 不需要自动代理
//        if (isInfrastructureClass(bean.getClass()) || shouldSkip(bean.getClass(), beanName)) {
//            this.advisedBeans.put(cacheKey, Boolean.FALSE);
//            return bean;
//        }

        // Create proxy if we have advice.
        // 如果Bean是要被代理的对象的话，取得Bean相关的Interceptor
        Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);

        // 如果获取到了增强则需要针对增强创建代理
        if (specificInterceptors != DO_NOT_PROXY) {
            //this.advisedBeans.put(cacheKey, Boolean.TRUE);

            //==== 创建代理
            Object proxy = createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
            //this.proxyTypes.put(cacheKey, proxy.getClass());

            return proxy;
        }

        //this.advisedBeans.put(cacheKey, Boolean.FALSE);
        return bean;
    }


    protected Object createProxy(Class<?> beanClass, String beanName, Object[] specificInterceptors, TargetSource targetSource) {

//        if (this.beanFactory instanceof ConfigurableListableBeanFactory) {
//            AutoProxyUtils.exposeTargetClass((ConfigurableListableBeanFactory) this.beanFactory, beanName, beanClass);
//        }

        ProxyFactory proxyFactory = new ProxyFactory();
        //拷贝当前类中的相关属性
        //proxyFactory.copyFrom(this);

        //判定给定的bean是否代理Class
//        if (!proxyFactory.isProxyTargetClass()) {
//
//            if (shouldProxyTargetClass(beanClass, beanName)) {
//                proxyFactory.setProxyTargetClass(true);
//            } else {
//                evaluateProxyInterfaces(beanClass, proxyFactory);
//            }
//        }

        // 添加Advisors
        Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
        for (Advisor advisor : advisors) {
            proxyFactory.addAdvisor(advisor);
        }

        // 设置目标类
        proxyFactory.setTargetSource(targetSource);
        // 定制代理
        //customizeProxyFactory(proxyFactory);
        // 设置是否冻结，默认为false即代理设置后不允许修改代理的配置
        //proxyFactory.setFrozen(this.freezeProxy);
//        if (advisorsPreFiltered()) {
//            proxyFactory.setPreFiltered(true);
//        }

        return proxyFactory.getProxy();
    }


    protected abstract Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource);


}


