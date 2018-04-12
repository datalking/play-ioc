# note-spring
Spring笔记

## summary


- BeanDefinition
    - 对于GenericBD和ChildBD，parentName可以有
    - 对于RootBD，parentName不存在，getParentName()始终返回空
- spring扩展点
    - 用BeanPostProcessor定制bean实例化后的初始化逻辑，发生在 `doCreateBean()` -> `initializeBean()` 
        - 实现该接口可提供自定义（或默认地来覆盖容器）的实例化逻辑、依赖解析逻辑等
        - 如果配置了多个BeanPostProcessor，那么可以通过设置 `order` 属性来控制执行次序
        - BeanPostProcessor仅对所在容器中的bean进行后置处理，将不会对定义在另一个容器中的bean进行后置处理
        - AOP自动代理实现了BeanPostProcessor，所以BeanPostProcessors或bean的直接引用不会被自动代理
    - 用BeanFactoryPostProcessor定制BeanDefinition元数据，发生在`refresh()` -> `invokeBeanFactoryPostProcessors()`
        - BeanFactoryPostProcessor在容器实际实例化任何其它的bean之前读取配置元数据，并有可能修改它
    - FactoryBean接口是插入到Spring IoC容器用来定制实例化逻辑的一个接口点
    
- spring createBean()顺序
    - doCreateBean()之前，`resolveBeforeInstantiation()` 可提前返回 beanPostProcessor处理过得bean
    - beanFactoryAware和beanNameAware的处理是在 `doCreateBean() -> initializeBean()`
    - `initializeBean()` 方法中先执行 xxAware，再执行 afterPropertiesSet()
    
- - spring注解扫描解析
      - @ComponentScan: ConfigurationClassParser.doProcessConfigurationClass()
      - @Component: ClassPathScanningCandidateComponentProvider.findCandidateComponents()
      - @Configuration: ConfigurationClassPostProcessor.processConfigBeanDefinitions()
      - @Bean: ConfigurationClassParser.doProcessConfigurationClass()
      
- AbstractApplicationContext类 refresh() 
    1) prepareRefresh();
        - 初始化前的准备工作，如对系统属性或者环境进行准备及验证
    2) ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        - 初始化BeanFactory，并进行XML文件读取
    3) prepareBeanFactory(beanFactory);
        - 对BeanFactory进行各种功能填充
    4) postProcessBeanFactory(beanFactory);
        - 初始化前的准备工作，如对系统属性或者环境进行准备及验证
    5) invokeBeanFactoryPostProcessors(beanFactory);
        - 激活各种BeanFactory处理器
        - 扫描 `@Configuration` 和 `@Bean` 
    6) registerBeanPostProcessors(beanFactory);
        - 拦截bean创建的bean处理器，这里只是注册，真正的调用是在getBean时候
        - 将各种BeanDefnition转换成RootBeanDefinition，如Annotated, Scanned
        - 扫描 `@Bean` ，将各种BeanDefnition转换成RootBeanDefinition，包括 `ConfigurationClassBeanDefinition`
    7) initMessageSource();
        - 为上下文初始化Message源，即对不同语言的消息体进行国际化处理
    8) initApplicationEventMulticaster();
        - 初始化应用消息广播器，并放入 `applicationEventMulticaster` bean中
    9) onRefresh();
        - 留给子类来初始化其他的bean
    10) registerListeners();
        - 在所有注册的bean中查找listenerbean，注册到消息广播器中
    11) finishBeanFactoryInitialization(beanFactory);
        - 初始化剩下的单例(非懒加载)
    12) finishRefresh();
        - 完成刷新过程，通知生命周期处理器lifecycleProcessor刷新过程，同时发出ContextRefreshEvent通知别人
    
- spring aop概念
    - aspect：切面
    - advice：增强处理的通知
    - pointcut：切入点，通过切入点表达式定义，添加了增强处理通知的方法调用处
    - joinpoint：连接点，所有方法调用处
    
- 扫描basePackage  
    - ComponentScanBeanDefinitionParser解析xml中配置的 `<context:component-scan base-package="a.b" />`
    - ClassPathBeanDefinitionScanner解析直接传入的包名
    
- dependenciesForBeanMap记录bean之间的依赖关系有两方面的作用：  
    - 在单例情况下，可以指定相互依赖bean之间的销毁顺序
    - 避免循环依赖
    
- spring doGetBean()方法执行顺序  
    - getSingleton()
    - new ObjectFactory(){}
    - createBean()
    
- spring bean配置不同类型方法的执行顺序，从先到后：
    - postProcessBeforeInitialization
    - afterPropertiesSet
    - init-method
    - postProcessAfterInitialization
    
- spring doCreateBean()方法中3个重要的任务
    - 创建实例：createBeanInstance(beanName, mbd, args); -> BeanWrapper
    - 注入属性：populateBean(beanName, mbd, instanceWrapper); -> void
    - 执行初始化方法：initializeBean(beanName, exposedObject, mbd); -> Object(bean instance)    
        - 先执行实现了InitializingBean接口的afterPropertiesSet()，再执行配置的init-method名称的方法

- spring autowired 注入方式
    -@Autowired默认通过byType注入，若存在多个实现类，byType有歧义，则需通过byName的方式来注入，name默认就是根据变量名来的
    - @Autowired只有required属性可以设置，默认为true
    - 如果想通过指定具体的bean的名称，可以使用@Qualifier
    - @Autowired的解析器是AutowiredAnnotationBeanPostProcessor
    
- PropertyEditor  
    - 由于Bean属性通过配置文档以字符串了方式为属性赋值，属性编辑器负责将这个字符串转换为属性的直接对象，如属性的类型为int，那编辑器的工作就是 `int i = Integer.parseInt("1");` 
    - Spring为一般的属性类型提供了默认的编辑器，BeanWrapperImpl负责对注入的Bean进行包装化的管理，常见属性类型对应的编辑器即在该类中定义
    
- spring对于没有无参构造器的bean就利用CGLIB生成实例，否则就直接反射成实例

- spring xml中id和name区别  
    - name可配置多个别名  
    - The id attribute allows you to specify exactly one id. Conventionally these names are alphanumeric ('myBean', 'fooService', etc), but may special characters as well. If you want to introduce other aliases to the bean, you can also specify them in the name attribute, separated by a comma (,), semicolon (;), or white space. 
    
- 重要实现模块
    - BeanDefinition
    - BeanDefinitionReader
    - Resource
    - BeanFactory
    - ApplicationContext
    
- BeanDefinitionReader
    - BeanDefinitionRegistry接口一次只能注册一个BeanDefinition，而且只能自己构造BeanDefinition类来注册。   
    - BeanDefinitionReader解决了这些问题，它一般可以使用一个BeanDefinitionRegistry构造，然后通过#loadBeanDefinitions（..）等方法，把“配置源”转化为多个BeanDefinition并注册到BeanDefinitionRegistry中  
    - 可以说BeanDefinitionReader帮助BeanDefinitionRegistry实现了高效、方便的注册BeanDefinition。
    
- spring3个核心组件
    - core：工具包
    - context：运行环境，读取bean配置、管理bean关系
    - beans：bean定义、解析、创建
    
- BeanFactory
    - BeanFactory 有三个子类：ListableBeanFactory、HierarchicalBeanFactory 和 AutowireCapableBeanFactory。      
    - 最终的默认实现类是 DefaultListableBeanFactory，实现了所有的接口  
