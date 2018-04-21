package com.github.datalking.aop.aspectj;

import com.github.datalking.aop.Advisor;
import com.github.datalking.aop.aspectj.jadvice.AbstractAspectJAdvice;
import com.github.datalking.aop.framework.AbstractAdvisorAutoProxyCreator;

import java.util.List;

/**
 * @author yaoo on 4/18/18
 */
public class AspectJAwareAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator {

    public List<Advisor> sortAdvisors(List<Advisor> advisors) {


        return null;
    }

    @Override
    protected boolean shouldSkip(Class<?> beanClass, String beanName) {
        // TODO:缓存名称
        List<Advisor> candidateAdvisors = findCandidateAdvisors();

        for (Advisor advisor : candidateAdvisors) {
            if (advisor instanceof AspectJPointcutAdvisor) {
                if (((AbstractAspectJAdvice) advisor.getAdvice()).getAspectName().equals(beanName)) {
                    return true;
                }
            }
        }

        return super.shouldSkip(beanClass, beanName);
    }



}
