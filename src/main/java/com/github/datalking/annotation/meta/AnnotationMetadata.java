package com.github.datalking.annotation.meta;

import java.util.Set;

/**
 * 注解元数据
 *
 * @author yaoo on 4/9/18
 */
public interface AnnotationMetadata extends ClassMetadata {


    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationName);

    Set<MethodMetadata> getAnnotatedMethods(String annotationName);

    Set<MethodMetadata> getAnnotatedMethods(Class<?> clazz);

//    boolean hasAnnotatedMethods(String annotationName);


}
