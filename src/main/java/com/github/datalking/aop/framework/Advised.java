package com.github.datalking.aop.framework;

import com.github.datalking.aop.Advisor;
import com.github.datalking.aop.TargetSource;
import org.aopalliance.aop.Advice;

/**
 * @author yaoo on 4/18/18
 */
public interface Advised {


    boolean isProxyTargetClass();
    Class<?> getTargetClass();

//    Class<?>[] getProxiedInterfaces();
//    boolean isInterfaceProxied(Class<?> intf);
//    void setTargetSource(TargetSource targetSource);
//    Advisor[] getAdvisors();
//    void addAdvisor(Advisor advisor) ;
//    void addAdvisor(int pos, Advisor advisor) ;
//    boolean removeAdvisor(Advisor advisor);
//    int indexOf(Advisor advisor);
//    boolean replaceAdvisor(Advisor a, Advisor b) ;
//
//    void addAdvice(Advice advice) ;
//    void addAdvice(int pos, Advice advice) ;
//    boolean removeAdvice(Advice advice);
//    int indexOf(Advice advice);

}
