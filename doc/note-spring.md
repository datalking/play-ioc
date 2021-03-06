# note-spring
Spring笔记

## summary

- EventListenerMethodProcessor这个beanPostProcessor较特殊
    - 初始化了AspectJExpressionPointcut类的shadowMatchCache

- singletonObjects里面的对象可以比beanDefinitionMaps的多

- 创建代理对象时，aspect切面对象可以不存在

- Advice接口是AOP联盟定义的一个空的标记接口，Advice有几个子接口：
    - BeforeAdvice，前置增强，意思是在我们的目标类之前调用的增强。这个接口也没有定义任何方法。
        - MethodBeforeAdvice接口，是BeforeAdvice的子接口，表示在方法前调用的增强，方法前置增强不能阻止方法的调用，但是能抛异常来使目标方法不继续执行。
    - AfterReturningAdvice，方法正常返回前的增强，该增强可以看到方法的返回值，但是不能更改返回值，该接口有一个方法afterReturning
    - ThrowsAdvice，抛出异常时候的增强，也是一个标志接口，没有定义任何方法。
    - Interceptor，拦截器，也没有定义任何方法，表示一个通用的拦截器，不属于Spring，是AOP联盟定义的接口
        - MethodInterceptor不属于Spring，是AOP联盟定义的接口，是Interceptor的子接口，我们通常叫做环绕增强，抽象方法是 `Object invoke(MethodInvocation invocation) throws Throwable;`
        - MethodInterceptor用来在目标方法调用前后做一些事情，参数MethodInvocation是一个方法调用的连接点，返回的是invocation.proceed()方法的返回值
        - JointPoint接口，是一个通用的运行时连接点，运行时连接点是在一个静态连接点发生的事件
        - Invocation接口是Joinpoint的子接口，表示程序的调用，一个Invocation就是一个连接点，可以被拦截器拦截
        - MethodInvocation接口是Invocation的子接口，用来描述一个方法的调用。
        - ConstructorInvocation接口是Invocation的子接口，描述的是构造器的调用
    - DynamicIntroductionAdvice，动态引介增强，有一个方法implementsInterface。


- 经常使用的内部Bean名称
    - org.springframework.context.annotation.internalConfigurationAnnotationProcessor
        - class org.springframework.context.annotation.ConfigurationClassPostProcessor
    - org.springframework.context.annotation.internalAutowiredAnnotationProcessor
        - class org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor
    - org.springframework.context.annotation.internalCommonAnnotationProcessor
        - class org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
    - org.springframework.context.annotation.internalRequiredAnnotationProcessor
        - class org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor

- BeanPostProcessor
    - postProcessBeforeInitialization 初始化前扩展(执行init-method前)
    - postProcessAfterInitialization 初始化后扩展(执行init-method后)
- InstantiationAwareBeanPostProcessor
    - postProcessBeforeInstantiation 对象实例化前扩展
    - postProcessAfterInstantiation 对象实例化后扩展
    - postProcessPropertyValues 属性依赖注入前扩展
- SmartInstantiationAwareBeanPostProcessor
    - predictBeanType 预测bean的类型，在beanFactory的getType时被调用
    - determineCandidateConstructors 对象实例化时决定要使用的构造函数时被调用
    - getEarlyBeanReference 循环依赖处理时获取Early对象引用时被调用

- aop相关
    - aspect：包含advice和point cut
    - advice：增强处理
    - advisor：将目标对象、增强行为和切入点三者结合起来，即通过Advisor可以定义那些目标对象的那些方法在什么地方使用这些增加的行为
    - join point：要匹配的方法调用处
    - point cut：公共可重用的匹配表达式
    - 创建代理对象
        - JdkDynamicAopProxy
            - getProxy()
        - CglibAopProxy
            - getProxy()
    - 获取匹配advice AnnotationAwareAspectAutoProxyCreator
        - findCandidateAdvisors()
        - findAdvisorsThatCanApply()

- @Bean和@Component区别
    - @Component是lite-mode，bean：class -> 1:1
    - @Bean是显式声明bean定义，bean：class -> N:1
    - @Component用于type，@Bean用于方法

- `@Order` 注解可以控制配置类的加载顺序

- 常用的后处理器
    - BeanFactoryPostProcessor
        - 在容器注册了BeanDefinition之后，实例化之前执行，通过这个接口可以获取Bean定义的元数据并且修改它们，如Bean的scope属性、property值等，也可以操作beanFactory
        - ConfigurationClassPostProcessor
    - BeanPostProcessor
        - 在Bean实例化完毕后执行，所以任何BeanPostProcessor都是在BeanFactoryPostProcessor之后执行的，可以实现自动注入、各种代理(AOP)等

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

- objenesis
    - objenesis是一个小型java类库用来实例化一个特定class的对象。
    - 使用场合：Java已经支持使用Class.newInstance()动态实例化类的实例。但是类必须拥有一个合适的构造器。有很多场景下不能使用这种方式实例化类，比如：
        - 构造器需要参数
        - 构造器有side effects
        - 构造器会抛异常
        - 因此，在类库中经常会有类必须拥有一个默认构造器的限制。Objenesis通过绕开对象实例构造器来克服这个限制。
    - 典型使用:实例化一个对象而不调用构造器是一个特殊的任务，然而在一些特定的场合是有用的：
        - 序列化，远程调用和持久化 -对象需要实例化并存储为到一个特殊的状态，而没有调用代码。
        - 代理，AOP库和Mock对象 -类可以被子类继承而子类不用担心父类的构造器
        - 容器框架 -对象可以以非标准的方式被动态实例化。
