package com.github.datalking.annotation.meta;

import com.github.datalking.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 注解元数据 默认实现类
 *
 * @author yaoo on 4/9/18
 */
public class StandardAnnotationMetadata extends StandardClassMetadata implements AnnotationMetadata {

    private final Annotation[] annotations;

    public StandardAnnotationMetadata(Class<?> introspectedClass) {
        super(introspectedClass);
        this.annotations = introspectedClass.getAnnotations();
    }

    @Override
    public Set<String> getAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Annotation ann : this.annotations) {
            types.add(ann.annotationType().getName());
        }
        return types;
    }

    @Override
    public boolean hasAnnotation(String annotationName) {
        for (Annotation ann : this.annotations) {
            if (ann.annotationType().getName().equals(annotationName)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Set<MethodMetadata> getAnnotatedMethods(String annotationName) {

        Method[] methods = getIntrospectedClass().getDeclaredMethods();
        Set<MethodMetadata> annotatedMethods = new LinkedHashSet<>();

        String basePackage = "";
        Class clazz = null;
        try {
            clazz = Class.forName(basePackage + StringUtils.firstLetterUpperCase(annotationName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (Method method : methods) {

            /// 若是 非桥接方法，且方法上存在注解，且方法上有注解annotationName
            if (!method.isBridge() && method.getAnnotations().length > 0 &&
                    method.isAnnotationPresent(clazz)) {

                annotatedMethods.add(new StandardMethodMetadata(method));
            }
        }

        return annotatedMethods;
    }

    public Set<MethodMetadata> getAnnotatedMethods(Class<?> clazz) {

        Method[] methods = getIntrospectedClass().getDeclaredMethods();
        Set<MethodMetadata> annotatedMethods = new LinkedHashSet<>();

        for (Method method : methods) {

            /// 若是 非桥接方法，且方法上存在注解，且方法上有注解annotationName
            if (!method.isBridge() && method.getAnnotations().length > 0 && method.isAnnotationPresent((Class<? extends Annotation>) clazz)) {

                annotatedMethods.add(new StandardMethodMetadata(method));
            }
        }

        return annotatedMethods;
    }


//    public boolean isAnnotated(String annotationName) {
//        return (this.annotations.length > 0 &&
//                AnnotatedElementUtils.isAnnotated(getIntrospectedClass(), annotationName));
//    }


//    @Override
//    public boolean hasAnnotatedMethods(String annotationName) {
//        return false;
//    }


}
