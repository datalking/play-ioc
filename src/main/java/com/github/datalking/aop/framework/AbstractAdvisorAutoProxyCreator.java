package com.github.datalking.aop.framework;

import com.github.datalking.aop.Advisor;
import com.github.datalking.aop.TargetSource;
import com.github.datalking.beans.factory.BeanFactory;
import com.github.datalking.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.List;

/**
 * @author yaoo on 4/18/18
 */
public abstract class AbstractAdvisorAutoProxyCreator extends AbstractAutoProxyCreator {

    private BeanFactoryAdvisorRetrievalHelper advisorRetrievalHelper;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        initBeanFactory((ConfigurableListableBeanFactory) beanFactory);
    }

    protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        this.advisorRetrievalHelper = new BeanFactoryAdvisorRetrievalHelper(beanFactory);
    }

    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource targetSource) {

        // 找到适用于bean的增强
        List<Advisor> advisors = findEligibleAdvisors(beanClass, beanName);
        // 找不到增强返回 DO_NOT_PROXY = null
        if (advisors.isEmpty()) {
            return DO_NOT_PROXY;
        }
        return advisors.toArray();
    }

    protected List<Advisor> findEligibleAdvisors(Class<?> beanClass, String beanName) {
        // 寻找所有增强
        List<Advisor> candidateAdvisors = findCandidateAdvisors();

        // 寻找所有增强中 适用于bean的增强
        List<Advisor> eligibleAdvisors = findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);

//        extendAdvisors(eligibleAdvisors);
//        if (!eligibleAdvisors.isEmpty()) {
//            eligibleAdvisors = sortAdvisors(eligibleAdvisors);
//        }

        return eligibleAdvisors;
    }

    /**
     * 从beanFactory中查找所有advisor
     *
     * @return 所有advisor的list
     */
    protected List<Advisor> findCandidateAdvisors() {
        return this.advisorRetrievalHelper.findAdvisorBeans();
    }

    protected List<Advisor> findAdvisorsThatCanApply(List<Advisor> candidateAdvisors, Class<?> beanClass, String beanName) {
        // 寻找所有增强器中适用于当前class的增强器
        return AopUtils.findAdvisorsThatCanApply(candidateAdvisors, beanClass);
    }


}
