package com.github.datalking.aop.support;

import com.github.datalking.aop.ClassFilter;
import com.github.datalking.aop.MethodMatcher;
import com.github.datalking.aop.Pointcut;
import com.github.datalking.util.Assert;

import java.io.Serializable;

/**
 * 可组合的Pointcut
 * <p>
 * Pointcut pc = new ComposablePointcut().union(classFilter).intersection(methodMatcher).intersection(pointcut);
 *
 * @author yaoo on 4/19/18
 */
public class ComposablePointcut implements Pointcut, Serializable {

    private ClassFilter classFilter;

    private MethodMatcher methodMatcher;


    public ComposablePointcut() {
        this.classFilter = ClassFilter.TRUE;
        this.methodMatcher = MethodMatcher.TRUE;
    }

    public ComposablePointcut(Pointcut pointcut) {
        Assert.notNull(pointcut, "Pointcut must not be null");
        this.classFilter = pointcut.getClassFilter();
        this.methodMatcher = pointcut.getMethodMatcher();
    }

    public ComposablePointcut(ClassFilter classFilter) {
        Assert.notNull(classFilter, "ClassFilter must not be null");
        this.classFilter = classFilter;
        this.methodMatcher = MethodMatcher.TRUE;
    }

    public ComposablePointcut(MethodMatcher methodMatcher) {
        Assert.notNull(methodMatcher, "MethodMatcher must not be null");
        this.classFilter = ClassFilter.TRUE;
        this.methodMatcher = methodMatcher;
    }

    public ComposablePointcut(ClassFilter classFilter, MethodMatcher methodMatcher) {
        Assert.notNull(classFilter, "ClassFilter must not be null");
        Assert.notNull(methodMatcher, "MethodMatcher must not be null");
        this.classFilter = classFilter;
        this.methodMatcher = methodMatcher;
    }


    public ComposablePointcut union(ClassFilter other) {
        this.classFilter = ClassFilters.union(this.classFilter, other);
        return this;
    }

    public ComposablePointcut intersection(ClassFilter other) {
        this.classFilter = ClassFilters.intersection(this.classFilter, other);
        return this;
    }

    public ComposablePointcut union(MethodMatcher other) {
        this.methodMatcher = MethodMatchers.union(this.methodMatcher, other);
        return this;
    }

    public ComposablePointcut intersection(MethodMatcher other) {
        this.methodMatcher = MethodMatchers.intersection(this.methodMatcher, other);
        return this;
    }


}
