package com.github.datalking.aop.aspectj;

import com.github.datalking.util.Assert;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author yaoo on 4/18/18
 */
public abstract class AbstractAspectJAdvisorFactory implements AspectJAdvisorFactory {

    @Override
    public boolean isAspect(Class<?> clazz) {
        Assert.notNull(clazz, "作为切面的类不能为空");
        return clazz.isAnnotationPresent(Aspect.class);
    }


}
