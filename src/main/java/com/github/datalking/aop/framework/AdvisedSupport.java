package com.github.datalking.aop.framework;

import com.github.datalking.aop.Advisor;
import com.github.datalking.aop.EmptyTargetSource;
import com.github.datalking.aop.TargetSource;
import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * proxy配置管理器
 *
 * @author yaoo on 4/18/18
 */
public class AdvisedSupport extends ProxyConfig implements Advised {

    public static final TargetSource EMPTY_TARGET_SOURCE = EmptyTargetSource.INSTANCE;

    TargetSource targetSource = EMPTY_TARGET_SOURCE;

    // 代理要实现的接口
    private List<Class<?>> interfaces = new ArrayList<Class<?>>();

    private List<Advisor> advisors = new LinkedList<>();

    AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();


    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = (targetSource != null ? targetSource : EMPTY_TARGET_SOURCE);
    }

    public Class<?> getTargetClass() {
        return this.targetSource.getTargetClass();
    }

    public void setTargetClass(Class<?> targetClass) {

    }

    public void addAdvice(Advice advice) {

    }


}
