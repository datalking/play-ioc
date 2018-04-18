package com.github.datalking.aop.aspectj;

import com.github.datalking.aop.Advisor;
import com.github.datalking.beans.factory.config.ConfigurableListableBeanFactory;
import com.github.datalking.util.Assert;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author yaoo on 4/17/18
 */
public class AnnotationAwareAspectJAutoProxyCreator extends AspectJAwareAdvisorAutoProxyCreator {

    //    private List<Pattern> includePatterns;
    private AspectJAdvisorFactory aspectJAdvisorFactory;
    private BeanFactoryAspectJAdvisorsBuilder aspectJAdvisorsBuilder;

    public void setAspectJAdvisorFactory(AspectJAdvisorFactory aspectJAdvisorFactory) {
        Assert.notNull(aspectJAdvisorFactory, "AspectJAdvisorFactory must not be null");
        this.aspectJAdvisorFactory = aspectJAdvisorFactory;
    }


    @Override
    protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.initBeanFactory(beanFactory);
        if (this.aspectJAdvisorFactory == null) {
            this.aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory(beanFactory);
        }
        this.aspectJAdvisorsBuilder = new BeanFactoryAspectJAdvisorsBuilder(beanFactory, this.aspectJAdvisorFactory);
    }


    /**
     * 获取所有增强器
     */
    @Override
    protected List<Advisor> findCandidateAdvisors() {

        // 先调用父类的方法获取xml文件中配置的AOP advisor
        List<Advisor> advisors = super.findCandidateAdvisors();

        // 获取有@Aspect的Bean上的注解增强
        advisors.addAll(this.aspectJAdvisorsBuilder.buildAspectJAdvisors());
        return advisors;
    }


}
