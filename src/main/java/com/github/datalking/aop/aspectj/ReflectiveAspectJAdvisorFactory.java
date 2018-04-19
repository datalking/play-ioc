package com.github.datalking.aop.aspectj;

import com.github.datalking.aop.Advisor;
import com.github.datalking.beans.factory.BeanFactory;
import org.aopalliance.aop.Advice;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author yaoo on 4/18/18
 */
public class ReflectiveAspectJAdvisorFactory extends AbstractAspectJAdvisorFactory implements Serializable {

    private final BeanFactory beanFactory;

    public ReflectiveAspectJAdvisorFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ReflectiveAspectJAdvisorFactory() {
        this(null);
    }


    public List<Advisor> getAdvisors(MetadataAwareAspectInstanceFactory aspectInstanceFactory) {


    }

    public Advice getAdvice(Method candidateAdviceMethod,
                            AspectJExpressionPointcut expressionPointcut,
                            MetadataAwareAspectInstanceFactory aspectInstanceFactory,
                            int declarationOrder,
                            String aspectName) {

    }



}
