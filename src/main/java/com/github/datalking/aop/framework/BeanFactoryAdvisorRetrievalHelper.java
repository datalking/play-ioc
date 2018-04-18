package com.github.datalking.aop.framework;

import com.github.datalking.aop.Advisor;
import com.github.datalking.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.List;

/**
 * @author yaoo on 4/18/18
 */
public class BeanFactoryAdvisorRetrievalHelper {

    private final ConfigurableListableBeanFactory beanFactory;

    private String[] cachedAdvisorBeanNames;

    public BeanFactoryAdvisorRetrievalHelper(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public List<Advisor> findAdvisorBeans() {

        return null;
    }


}
