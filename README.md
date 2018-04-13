# play-ioc   
>简单可控的IoC容器    

## target
- 稳定健壮的IoC和AOP功能（ing）
- 与Spring相同的使用方式

## overview
- 支持从xml中读取bean配置
- 支持从注解中读取bean配置
- ApplicationContext加载bean默认采用立即初始化，暂不支持懒加载开启，DefaultListableBeanFactory默认采用懒加载
- 仅支持单例bean，不支持多实例
- 目前暂不支持：
    - 不支持将bean的value类型配置为set,list,map，仅支持字符串和ref  
    - 不支持为bean指定别名
    - 不支持xml中指定扫描指定包，仅支持注解扫描指定包
    - 不支持构造注入与方法注入，仅支持属性注入
    - 不支持创建和使用除 `play-ioc支持的xml配置说明` 外的自定义xml标签
    - 不支持将嵌套子元素作为属性，仅支持统一使用扁平方式指定属性  
        - 不支持innerBean的写法，建议扁平化定义bean，使用ref属性引用其他bean
        - `<property name="fieldName" value="valueHere"/>`
        - ~~`<property name="fieldName"> <value="valueHere"></value> </property>`~~   
        - ~~`<property name="person"> <bean class="a.b.Person.class"></bean> </property>`~~   
    - 不支持xml格式校验和属性名校验，请手动检查
    - 不支持FactoryBean
    - 不支持属性编辑器，默认自动转换基本类型对象，需要在源码上定制处理Date、File等字段
    - ...

- 源码参考了 [spring-framework](https://github.com/spring-projects/spring-framework) 和[tiny-spring](https://github.com/code4craft/tiny-spring)
 
## dev 
```sh
 git clone https://github.com/datalking/play-ioc.git
 cd play-ioc/
 ./start-build-dev.sh
```

## demo
#### 使用 [AnnotationConfigApplicationContext](https://github.com/datalking/play-ioc/blob/master/src/test/java/com/github/datalking/context/annotation/AnnotationConfigApplicationContextTest.java)
```
// 指定要扫描的包名
ApplicationContext ctx = new AnnotationConfigApplicationContext("com.github.datalking.bean");
BeanAllStr beanAllStr = (BeanAllStr) applicationContext.getBean("beanAllStr");
System.out.println(beanAllStr);
```
#### 使用 [ApplicationContext](https://github.com/datalking/play-ioc/blob/master/src/test/java/com/github/datalking/context/ApplicationContextTest.java)
```
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans-primitive.xml");
BeanAllStr beanAllStr = (BeanAllStr) applicationContext.getBean("beanAllStr");
System.out.println(beanAllStr);
```

#### 使用 [DefaultListableBeanFactory](https://github.com/datalking/play-ioc/blob/master/src/test/java/com/github/datalking/beans/BeanFactoryTest.java)
```
// 初始化BeanFactory
AbstractBeanFactory beanFactory = new DefaultListableBeanFactory();
// 读取配置
XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) beanFactory);
xmlBeanDefinitionReader.loadBeanDefinitions("beans-primitive.xml");
BeanAllStr beanAllStr = (BeanAllStr) beanFactory.getBean("beanAllStr");
System.out.println(beanAllStr);
```

## todo

- [ ] 支持@ComponentScan   
- [ ] 基本aop功能 
- [ ] aop织入顺序 @Order  
- [ ] getBean By class   
- [ ] xml 支持constructor-args元素   
- [ ] 支持BeanPostProcessor   
- [ ] 支持别名   
- [ ] 注解支持 `@Named`, `@Injected`   
- [ ] 处理嵌套bean的问题   
- [ ] 解决多重嵌套依赖问题   
- [ ] xml中同名bean抛出异常   
- [ ] 扫描指定包时利用asm实现所有子包.class文件的不加载，最初是预加载指定包获取bean信息   

- [x] 手动通过注解注册bean生成BeanDefinition: @Configuration  @Bean   
- [x] 各种BeanDefinition转换成RootBeanDefinition   
- [x] ApplicationContext支持开启与关闭懒加载   

## later
- [ ] 使用@Configuration、@Bean时支持调用带参数的构造函数   
- [ ] 使用@Configuration、@Bean时支持static方法   

## user guide
- play-ioc支持的xml配置说明
    - 顶层标签为 `<beans>`
    - `<bean>` 元素可配置 id,name,class属性，class必须，id和name都可选
    - `<property>` 元素可配置 name,value,ref属性，name必须，value和ref二选一
- GenericBeanDefinition保存bean属性元数据，包括beanClass和propertyValues
    - beanClass在xml读取阶段是字符串，在实例创建阶段是class对象
    - propertyValues存储属性键值对，在xml读取阶段是都是字符串，特殊的是ref属性会处理成RuntimeBeanReference
- BeanDefinitionReader读取bean配置  
    - 最终存储到DefaultListableBeanFactory的beanDefinitionMap
    - 此时bean所对应的class未加载，也未实例化
- AbstractAutowireCapableBeanFactory的doCreate()方法会创建bean实例
    - bean实例最终保存在DefaultSingletonBeanRegistry的singletonObjects中


## License
[MIT](http://opensource.org/licenses/MIT)




