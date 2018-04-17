package com.github.datalking.context.support;

import com.github.datalking.beans.factory.config.BeanFactoryPostProcessor;
import com.github.datalking.beans.factory.config.ConfigurableListableBeanFactory;
import com.github.datalking.beans.factory.support.BeanDefinitionRegistry;
import com.github.datalking.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * beanFactoryPostProcessor相关功能执行的代理类
 * <p>
 * 一是触发beanFactoryPostProcessor，二是注册beanFactoryPostProcessor
 *
 * @author yaoo on 4/13/18
 */
public class PostProcessorRegistrationDelegate {

    public static void invokeBeanFactoryPostProcessors(
            ConfigurableListableBeanFactory beanFactory,
            List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {


        /// 如果beanFactory可注册BeanDefinition
        if (beanFactory instanceof BeanDefinitionRegistry) {

            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;


            String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class);

            // 实例化默认的后置处理器，包括ConfigurationClassPostProcessor
            List<BeanDefinitionRegistryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
            for (String ppName : postProcessorNames) {

                try {
                    priorityOrderedPostProcessors.add((BeanDefinitionRegistryPostProcessor) beanFactory.getBean(ppName));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            // 扫描@Configuration、@Bean、@ComponentScan
            // 注册AnnotationAwareAspectJAutoProxyCreator的BeanDefinition
            invokeBeanDefinitionRegistryPostProcessors(priorityOrderedPostProcessors, registry);

        }

        /// 如果beanFactory不可注册BeanDefinition
        else {
            invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
        }

    }


    private static void invokeBeanDefinitionRegistryPostProcessors(
            Collection<? extends BeanDefinitionRegistryPostProcessor> postProcessors,
            BeanDefinitionRegistry registry) {

        for (BeanDefinitionRegistryPostProcessor postProcessor : postProcessors) {
            postProcessor.postProcessBeanDefinitionRegistry(registry);
        }
    }


    private static void invokeBeanFactoryPostProcessors(
            Collection<? extends BeanFactoryPostProcessor> postProcessors,
            ConfigurableListableBeanFactory beanFactory) {

        for (BeanFactoryPostProcessor postProcessor : postProcessors) {
            postProcessor.postProcessBeanFactory(beanFactory);
        }

    }


}
