package com.github.datalking.context.annotation;

import com.github.datalking.annotation.Component;
import com.github.datalking.annotation.EnableAspectJAutoProxy;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.lang.annotation.Annotation;


/**
 * ConfigurationClassParser Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 17, 2018</pre>
 */
@Component
@EnableAspectJAutoProxy
public class ConfigurationClassParserTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testParseEnableXxxAnno() throws Exception {

        Annotation[] annos = ConfigurationClassParserTest.class.getAnnotations();

        for (Annotation anno : annos) {
            System.out.println(anno.annotationType().getName());

            for (Annotation a2 : anno.annotationType().getAnnotations()) {
                System.out.println("==== " + a2.annotationType().getName());

            }
            System.out.println();
        }

    }


    /**
     * Method: parse(Set<BeanDefinitionHolder> configCandidates)
     */
    @Test
    public void testParseConfigCandidates() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: parse(Class<?> clazz, String beanName)
     */
    @Test
    public void testParseForClazzBeanName() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: getConfigurationClasses()
     */
    @Test
    public void testGetConfigurationClasses() throws Exception {
//TODO: Test goes here...
    }


    /**
     * Method: parse(AnnotationMetadata metadata, String beanName)
     */
    @Test
    public void testParse() throws Exception {
//TODO: Test goes here...
/*
try {
   Method method = ConfigurationClassParser.getClass().getMethod("parse", AnnotationMetadata.class, String.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}
*/
    }

    /**
     * Method: processConfigurationClass(ConfigurationClass configClass)
     */
    @Test
    public void testProcessConfigurationClass() throws Exception {
//TODO: Test goes here...
/*
try {
   Method method = ConfigurationClassParser.getClass().getMethod("processConfigurationClass", ConfigurationClass.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}
*/
    }

    /**
     * Method: doProcessConfigurationClass(ConfigurationClass configClass)
     */
    @Test
    public void testDoProcessConfigurationClass() throws Exception {
//TODO: Test goes here...
/*
try {
   Method method = ConfigurationClassParser.getClass().getMethod("doProcessConfigurationClass", ConfigurationClass.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}
*/
    }

    /**
     * Method: getImports(ConfigurationClass confClass)
     */
    @Test
    public void testGetImports() throws Exception {
//TODO: Test goes here...
/*
try {
   Method method = ConfigurationClassParser.getClass().getMethod("getImports", ConfigurationClass.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}
*/
    }

    /**
     * Method: retrieveBeanMethodMetadata(ConfigurationClass configClass)
     */
    @Test
    public void testRetrieveBeanMethodMetadata() throws Exception {
//TODO: Test goes here...
/*
try {
   Method method = ConfigurationClassParser.getClass().getMethod("retrieveBeanMethodMetadata", ConfigurationClass.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}
*/
    }

    /**
     * Method: attributesForRepeatable(AnnotationMetadata metadata, Class<?> containerClass, Class<?> annotationClass)
     */
    @Test
    public void testAttributesForRepeatable() throws Exception {
//TODO: Test goes here...
/*
try {
   Method method = ConfigurationClassParser.getClass().getMethod("attributesForRepeatable", AnnotationMetadata.class, Class<?>.class, Class<?>.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}
*/
    }

}
