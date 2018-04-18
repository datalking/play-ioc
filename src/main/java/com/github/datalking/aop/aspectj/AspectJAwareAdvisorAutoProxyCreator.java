package com.github.datalking.aop.aspectj;

import com.github.datalking.aop.Advisor;
import com.github.datalking.aop.framework.AbstractAdvisorAutoProxyCreator;

import java.util.List;

/**
 * @author yaoo on 4/18/18
 */
public class AspectJAwareAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator {

    public List<Advisor> sortAdvisors(List<Advisor> advisors) {


        return null;
    }

    public boolean shouldSkip(Class<?> beanClass, String beanName) {

        return false;
    }


    }
