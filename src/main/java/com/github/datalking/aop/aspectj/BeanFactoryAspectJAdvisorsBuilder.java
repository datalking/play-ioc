package com.github.datalking.aop.aspectj;

import com.github.datalking.aop.Advisor;
import com.github.datalking.beans.factory.ListableBeanFactory;

import java.util.List;

/**
 * 为了方便创建自动代理的工具类
 *
 * @author yaoo on 4/18/18
 */
public class BeanFactoryAspectJAdvisorsBuilder {

    private final ListableBeanFactory beanFactory;

    private final AspectJAdvisorFactory advisorFactory;

    private volatile List<String> aspectBeanNames;


    public BeanFactoryAspectJAdvisorsBuilder(ListableBeanFactory beanFactory) {
        this(beanFactory, new ReflectiveAspectJAdvisorFactory(beanFactory));
    }

    public BeanFactoryAspectJAdvisorsBuilder(ListableBeanFactory beanFactory, AspectJAdvisorFactory advisorFactory) {
        this.beanFactory = beanFactory;
        this.advisorFactory = advisorFactory;
    }

    /**
     * 在beanFactory中寻找@Aspect注解标注的bean，并创建advisor
     */
    public List<Advisor> buildAspectJAdvisors() {

        return null;
    }


}
