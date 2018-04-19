package com.github.datalking.aop.framework;

import com.github.datalking.aop.support.AopUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * AOP Alliance MethodInvocation 基于反射调用方法
 *
 * @author yaoo on 4/18/18
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object proxy;

    protected final Object target;

    protected final Method method;

    protected Object[] arguments;

    private final Class<?> targetClass;

    protected final List<?> interceptorsAndDynamicMethodMatchers;

    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments, Class<?> targetClass
            , List<Object> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.targetClass = targetClass;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public final Object getProxy() {
        return this.proxy;
    }

    @Override
    public final Object getThis() {
        return this.target;
    }

    @Override
    public final AccessibleObject getStaticPart() {
        return this.method;
    }

    @Override
    public final Method getMethod() {
        return this.method;
    }

    @Override
    public final Object[] getArguments() {
        return (this.arguments != null ? this.arguments : new Object[0]);
    }

    public void setArguments(Object... arguments) {
        this.arguments = arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        //	We start with an index of -1 and increment early.
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return invokeJoinpoint();
        }


        Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);

        if (interceptorOrInterceptionAdvice instanceof InterceptorAndDynamicMethodMatcher) {
            // Evaluate dynamic method matcher here: static part will already have been evaluated and found to match.
            InterceptorAndDynamicMethodMatcher dm = (InterceptorAndDynamicMethodMatcher) interceptorOrInterceptionAdvice;

            if (dm.methodMatcher.matches(this.method, this.targetClass, this.arguments)) {
                return dm.interceptor.invoke(this);
            } else {
                // Dynamic matching failed.  Skip this interceptor and invoke the next in the chain.
                return proceed();
            }

        } else {
            // It's an interceptor, so we just invoke it:
            // The pointcut will have been evaluated statically before this object was constructed.
            return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
        }
    }

    protected Object invokeJoinpoint() throws Throwable {
        return AopUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments);
    }


}
