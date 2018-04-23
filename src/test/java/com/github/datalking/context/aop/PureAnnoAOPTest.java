package com.github.datalking.context.aop;

import com.github.datalking.annotation.Bean;
import com.github.datalking.annotation.Configuration;
import com.github.datalking.annotation.EnableAspectJAutoProxy;
import com.github.datalking.bean.FooInterface;
import com.github.datalking.bean.FooService;
import com.github.datalking.bean1.aop.MyAspect;
import com.github.datalking.context.ApplicationContext;
import com.github.datalking.context.annotation.AnnotationConfigApplicationContext;
import com.github.datalking.util.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Configuration
@EnableAspectJAutoProxy
public class PureAnnoAOPTest {

    @Bean
    public FooInterface fooService() {
        return new FooService();

    }

    // the Aspect itself must also be a Bean
    @Bean
    public MyAspect myAspect() {
        return new MyAspect();
    }


    @Test
    public void testJDKProxyBefore() throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(PureAnnoAOPTest.class);
        FooInterface bean = (FooInterface) ctx.getBean("fooService");
        String s=bean.printInnerText();
        //System.out.println(s);
        assertEquals(2, StringUtils.countWordFromStr(s,"print"));

    }


}





