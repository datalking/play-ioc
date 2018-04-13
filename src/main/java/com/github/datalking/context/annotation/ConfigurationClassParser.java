package com.github.datalking.context.annotation;

import com.github.datalking.annotation.Bean;
import com.github.datalking.annotation.meta.AnnotationMetadata;
import com.github.datalking.annotation.meta.MethodMetadata;
import com.github.datalking.beans.factory.config.AnnotatedBeanDefinition;
import com.github.datalking.beans.factory.config.BeanDefinition;
import com.github.datalking.beans.factory.config.BeanDefinitionHolder;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author yaoo on 4/13/18
 */
public class ConfigurationClassParser {

    private final Map<ConfigurationClass, ConfigurationClass> configurationClasses = new LinkedHashMap<>();

    public void parse(Set<BeanDefinitionHolder> configCandidates) {

        for (BeanDefinitionHolder holder : configCandidates) {
            BeanDefinition bd = holder.getBeanDefinition();

            if (bd instanceof AnnotatedBeanDefinition) {
                parse(((AnnotatedBeanDefinition) bd).getMetadata(), holder.getBeanName());
            }

        }

    }

    private void parse(AnnotationMetadata metadata, String beanName) {
        processConfigurationClass(new ConfigurationClass(metadata, beanName));
    }

    private void processConfigurationClass(ConfigurationClass configClass) {
        // ==== 真正解析@Configuration和@ComponentScan
        doProcessConfigurationClass(configClass);
        this.configurationClasses.put(configClass, configClass);

    }


    /**
     * 扫描@ComponentScan、@Bean
     *
     * @param configClass 存放扫描结果
     */
    private void doProcessConfigurationClass(ConfigurationClass configClass) {

        // ==== 扫描@ComponentScan

        // ==== 扫描@Bean
        Set<MethodMetadata> beanMethods = retrieveBeanMethodMetadata(configClass);

        for (MethodMetadata methodMetadata : beanMethods) {
            configClass.addBeanMethod(new BeanMethod(methodMetadata, configClass));
        }


    }

    private Set<MethodMetadata> retrieveBeanMethodMetadata(ConfigurationClass configClass) {

        AnnotationMetadata original = configClass.getMetadata();
        Set<MethodMetadata> beanMethods = original.getAnnotatedMethods(Bean.class);
        return beanMethods;


    }

    public Set<ConfigurationClass> getConfigurationClasses() {
        return this.configurationClasses.keySet();
    }


}
