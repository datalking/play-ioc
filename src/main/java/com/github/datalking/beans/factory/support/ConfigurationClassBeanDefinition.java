package com.github.datalking.beans.factory.support;

import com.github.datalking.annotation.Bean;
import com.github.datalking.annotation.meta.AnnotationMetadata;
import com.github.datalking.beans.factory.config.AnnotatedBeanDefinition;

import java.lang.reflect.Method;

/**
 * 由@Configuration、@Bean注解扫描到的方法生成的BeanDefinition
 * 与 {@link AnnotatedGenericBeanDefinition}类似
 *
 * @author yaoo on 4/11/18
 */
public class ConfigurationClassBeanDefinition extends RootBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata annotationMetadata;

    public ConfigurationClassBeanDefinition(AnnotationMetadata annotationMetadata) {
        super();
        this.annotationMetadata = annotationMetadata;
    }

    @Override
    public boolean isFactoryMethod(Method candidate) {
        // 检查方法是否有@Bean注解
        return (super.isFactoryMethod(candidate) && candidate.isAnnotationPresent(Bean.class));
    }


    @Override
    public AnnotationMetadata getMetadata() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o || (o instanceof ConfigurationClassBeanDefinition && super.equals(o)));
    }

    @Override
    public String toString() {
        return "ConfBDef: " + super.toString();
    }


}
