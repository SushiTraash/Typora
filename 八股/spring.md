- 1.spring 中都用到了哪些设计模式?
- 2.spring 中有哪些核心模块?
- 3.说一下你理解的 IOC 是什么?
- 4.spring 中的 IOC 容器有哪些?有什么区别?
- 5.那 BeanFactory 和 FactoryBean 又有什么区别?
- 6.@Repository、@Service、@Compent、@Controller它们有什么区别?
- 7.那么 DI 又是什么?
- 8.说说 AOP 是什么?
- 9.动态代理和静态代理有什么区别?
- 10.JDK 动态代理和 CGLIB 代理有什么区别？
- 11.Spring AOP 和 AspectJ AOP 有什么区别？
- 12.spring 中 Bean 的生命周期是怎样的?
- 13.spring 是怎么解决循环依赖的?
- 14.为什么要使用三级缓存，二级缓存不能解决吗?
- 15.@Autowired 和 @Resource 有什么区别?
- 16.spring 事务隔离级别有哪些?
- 17.spring 事务的传播机制有哪些?
- 18.springBoot 自动装配原理?



------

## 1.spring 中都用到了哪些设计模式?

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8ZsEhVvXtGSYDtNMPDN8EQ6t9TUVAPY1FdM4EPgnsHJbCfeib4ucWZbHxG4PYfB9Oh6oicWLZBE0HDg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- **「1.工厂设计模式」**: 比如通过 BeanFactory 和 ApplicationContext 来生产 Bean 对象
- **「2.代理设计模式」**:  AOP 的实现方式就是通过代理来实现，Spring主要是使用 JDK 动态代理和 CGLIB 代理
- **「3.单例设计模式」**: Spring 中的 Bean 默认都是单例的
- **「4.模板方法模式」**: Spring 中 jdbcTemplate 等以 Template 结尾的对数据库操作的类，都会使用到模板方法设计模式，一些通用的功能
- **「5.包装器设计模式」**: 我们的项目需要连接多个数据库，而且不同的客户在每次访问中根据需要会去访问不同的数据库。这种模式让我们可以根据客户的需求能够动态切换不同的数据源
- **「6.观察者模式」**: Spring 事件驱动模型观察者模式的
- **「7.适配器模式」**:Spring AOP 的增强或通知(Advice)使用到了适配器模式

## 2.spring 中有哪些核心模块?

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8ZsEhVvXtGSYDtNMPDN8EQ6Gfjm7GstDnOomibYhWrTyk1iaA2cIwBZaiajia9wGOHwREPvNib5HsQT2Dw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 1.**「Spring Core」**：Spring核心，它是框架最基础的部分，提供IOC和依赖注入DI特性
- 2.**「Spring Context」**：Spring上下文容器，它是 BeanFactory 功能加强的一个子接口
- 3.**「Spring Web」**：它提供Web应用开发的支持
- 4.**「Spring MVC」**：它针对Web应用中MVC思想的实现
- 5.**「Spring DAO」**：提供对JDBC抽象层，简化了JDBC编码，同时，编码更具有健壮性
- 6.**「Spring ORM」**：它支持用于流行的ORM框架的整合，比如：Spring + Hibernate、Spring + iBatis、Spring + JDO的整合等
- 7.**「Spring AOP」**：即面向切面编程，它提供了与AOP联盟兼容的编程实现

## 3.说一下你理解的 IOC 是什么?

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8ZsEhVvXtGSYDtNMPDN8EQ6LEwxroqFKa1pTFfZqnse4xK1xw4RVawwjSpbd17JNLLQuO7X8P2Ykg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

首先 IOC 是一个**「容器」**，是用来装载对象的，它的核心思想就是**「控制反转」**

那么究竟**「什么是控制反转」**?

控制反转就是说，**「把对象的控制权交给了 spring，由 spring 容器进行管理」**，我们不进行任何操作

那么为**「什么需要控制反转」**?

我们想象一下，没有控制反转的时候，我们需要**「自己去创建对象，配置对象」**，还要**「人工去处理对象与对象之间的各种复杂的依赖关系」**，当一个工程的量起来之后，这种关系的维护是非常令人头痛的，所以就有了控制反转这个概念，将对象的创建、配置等一系列操作交给 spring 去管理，我们在使用的时候只要去取就好了

## 4.spring 中的 IOC 容器有哪些?有什么区别?

spring 主要提供了**「两种 IOC 容器」**，一种是 **「BeanFactory」**，还有一种是 **「ApplicationContext」**

它们的区别就在于，BeanFactory **「只提供了最基本的实例化对象和拿对象的功能」**，而 ApplicationContext 是继承了 BeanFactory 所派生出来的产物，是其子类，它的作用更加的强大，比如支持注解注入、国际化等功能

## 5.那 BeanFactory 和 FactoryBean 又有什么区别?

这两个是**「不同的产物」**

**「BeanFactory 是 IOC 容器」**，是用来承载对象的

**「FactoryBean 是一个接口」**，为 Bean 提供了更加灵活的方式，通过代理一个Bean对象，对方法前后做一些操作。

## 6.@Repository、@Service、@Compent、@Controller它们有什么区别?

这四个注解的**「本质都是一样的，都是将被该注解标识的对象放入 spring　容器当中，只是为了在使用上区分不同的应用分层」**

- @Repository:dao层
- @Service:service层
- @Controller:controller层
- @Compent:其他不属于以上三层的统一使用该注解

## 7.那么 DI 又是什么?

DI 就是依赖注入，其实和 IOC 大致相同，只不过是**「同一个概念使用了不同的角度去阐述」**

DI 所描述的**「重点是在于依赖」**，我们说了 **「IOC 的核心功能就是在于在程序运行时动态的向某个对象提供其他的依赖对象」**，而这个功能就是依靠 DI 去完成的，比如我们需要注入一个对象 A，而这个对象 A 依赖一个对象 B，那么我们就需要把这个对象 B 注入到对象 A 中，这就是依赖注入

spring 中有三种注入方式

- 接口注入
- 构造器注入
- set注入

## 8.说说 AOP 是什么?

AOP 意为：**「面向切面编程，通过预编译方式和运行期间动态代理实现程序功能的统一维护的一种技术」**。

AOP 是 **「OOP(面向对象编程) 的延续」**，是 Spring 框架中的一个重要内容，是函数式编程的一种衍生范型。利用 AOP 可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

**「AOP 实现主要分为两类:」**

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8ZsEhVvXtGSYDtNMPDN8EQ6FE0FGaWt964RGo1GMOFNZbe3LhZ4tN7wb8zCndCH5UYFRib1ps85ddQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- **「静态 AOP 实现」**， AOP 框架**「在编译阶段」**对程序源代码进行修改，生成了静态的 AOP 代理类（生成的 *.class 文件已经被改掉了，需要使用特定的编译器），比如 AspectJ
- **「动态 AOP 实现」**， AOP 框架**「在运行阶段」**对动态生成代理对象（在内存中以 JDK 动态代理，或 CGlib 动态地生成 AOP 代理类），如 SpringAOP

spring 中 AOP 的实现是**「通过动态代理实现的」**，如果是实现了接口就会使用 JDK 动态代理，否则就使用 CGLIB 代理。

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8ZsEhVvXtGSYDtNMPDN8EQ6C2bQDL0ootiaMicTMBPaJXuGehzMavEY8ybIz1utESULxAmQ9ZA8s2NA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

**「有 5 种通知类型:」**

- **「@Before」**:在目标方法调用前去通知
- **「@AfterReturning」**:在目标方法返回或异常后调用
- **「@AfterThrowing」**:在目标方法返回后调用
- **「@After」**:在目标方法异常后调用
- **「@Around」**:将目标方法封装起来，自己确定调用时机

## 9.动态代理和静态代理有什么区别?

**「静态代理」**

- 由程序员创建或由特定工具自动生成源代码，再对其编译。在程序运行前，代理类的.class文件就已经存在了
- 静态代理通常只代理一个类
- 静态代理事先知道要代理的是什么

**「动态代理」**

- 在程序运行时，运用反射机制动态创建而成
- 动态代理是代理一个接口下的多个实现类
- 动态代理不知道要代理什么东西，只有在运行时才知道

## 10.JDK 动态代理和 CGLIB 代理有什么区别？

JDK 动态代理时业务类**「必须要实现某个接口」**，它是**「基于反射的机制实现的」**，生成一个实现同样接口的一个代理类，然后通过重写方法的方式，实现对代码的增强。

CGLIB 动态代理是使用字节码处理框架 ASM，其原理是通过字节码技术为一个类**「创建子类，然后重写父类的方法」**，实现对代码的增强。

## 11.Spring AOP 和 AspectJ AOP 有什么区别？

Spring AOP 是运行时增强，是通过**「动态代理实现」**的

AspectJ AOP 是编译时增强，需要特殊的编译器才可以完成，是通过**「修改代码来实现」**的，支持**「三种织入方式」**

- **「编译时织入」**:就是在编译字节码的时候织入相关代理类
- **「编译后织入」**:编译完初始类后发现需要 AOP 增强，然后织入相关代码
- **「类加载时织入」**:指在加载器加载类的时候织入

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8ZsEhVvXtGSYDtNMPDN8EQ6PFnmyy2O96lnhTXlOSUZWQZSVvAaxSyysrL29eibrXXQzeHiamOBEk5w/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

| 主要区别 |        Spring AOP        |             AspecjtJ AOP |
| :------- | :----------------------: | -----------------------: |
| 增强方式 |        运行时增强        |               编译时增强 |
| 实现方式 |         动态代理         |                 修改代码 |
| 编译器   |          javac           |         特殊的编译器 ajc |
| 效率     | 较低(运行时反射损耗性能) |                     较高 |
| 织入方式 |          运行时          | 编译时、编译后、类加载时 |

## 12.spring 中 Bean 的生命周期是怎样的?

SpringBean 生命周期大致分为4个阶段：

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8ZsEhVvXtGSYDtNMPDN8EQ6icibuib62Mr1iaIc1LAjCEOVNT8iaHvjHl5YfByefXekPnPSknWYzeYPnng/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 1.**「实例化」**，实例化该 Bean 对象

- 2.**「填充属性」**，给该 Bean 赋值

- 3.**「初始化」**

- - 如果实现了 Aware 接口，会通过其接口获取容器资源
  - 如果实现了 BeanPostProcessor 接口，则会回调该接口的前置和后置处理增强
  - 如果配置了 init-method 方法，]会执行该方法

- 4.**「销毁」**

- - 如果实现了 DisposableBean 接口，则会回调该接口的 destroy 方法
  - 如果配置了 destroy-method 方法，则会执行 destroy-method 配置的方法

## 13.spring 是怎么解决循环依赖的?

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8ZsEhVvXtGSYDtNMPDN8EQ6lCzur7dsNvAcu6uUwcGsiaLLVH6Fajib8w4BzOTicrxB406EibzlR5bicGw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

循环依赖就是说两个对象相互依赖，形成了一个环形的调用链路

spring 使用三级缓存去解决循环依赖的，其**「核心逻辑就是把实例化和初始化的步骤分开，然后放入缓存中」**，供另一个对象调用

- **「第一级缓存」**：用来保存实例化、初始化都完成的对象
- **「第二级缓存」**：用来保存实例化完成，但是未初始化完成的对象
- **「第三级缓存」**：用来保存一个对象工厂，提供一个匿名内部类，用于创建二级缓存中的对象

------

当 A、B 两个类发生循环引用时 大致流程

- 1.A 完成实例化后，去**「创建一个对象工厂，并放入三级缓存」**当中

- - 如果 A 被 AOP 代理，那么通过这个工厂获取到的就是 A 代理后的对象
  - 如果 A 没有被 AOP 代理，那么这个工厂获取到的就是 A 实例化的对象

- 2.A 进行属性注入时，去**「创建 B」**

- 3.B 进行属性注入，需要 A ，则**「从三级缓存中去取 A 工厂代理对象」**并注入，然后删除三级缓存中的 A 工厂，将 A 对象放入二级缓存

- 4.B 完成后续属性注入，直到初始化结束，将 B 放入一级缓存

- 5.**「A 从一级缓存中取到 B 并且注入 B」**, 直到完成后续操作，将 A 从二级缓存删除并且放入一级缓存，循环依赖结束

------

spring 解决循环依赖有两个前提条件：

- 1.**「不全是构造器方式」**的循环依赖(否则无法分离初始化和实例化的操作)
- 2.**「必须是单例」**(否则无法保证是同一对象)

## 14.为什么要使用三级缓存，二级缓存不能解决吗?

可以，三级缓存的功能是只有真正发生循环依赖的时候，才去提前生成代理对象，否则只会**「创建一个工厂并将其放入到三级缓存」**中，但是不会去通过这个工厂去真正创建对象。

如果使用二级缓存解决循环依赖，意味着所有 Bean 在实例化后就要完成 AOP 代理，这样**「违背了 Spring 设计的原则」**，Spring 在设计之初就是在 Bean 生命周期的最后一步来完成 AOP 代理，而不是在实例化后就立马进行 AOP 代理。

## 15.@Autowired 和 @Resource 有什么区别?

- **「@Resource 是 Java 自己的注解」**，@Resource 有两个属性是比较重要的，分是 name 和 type；Spring 将 @Resource 注解的 name 属性解析为 bean 的名字，而 type 属性则解析为 bean 的类型。所以如果使用 name 属性，则使用 byName 的自动注入策略，而使用 type 属性时则使用 byType 自动注入策略。如果既不指定 name 也不指定 type 属性，这时将通过反射机制使用 byName 自动注入策略。
- **「@Autowired 是spring 的注解」**，是 spring2.5 版本引入的，Autowired 只根据 type 进行注入，**「不会去匹配 name」**。如果涉及到 type 无法辨别注入对象时，那需要依赖 @Qualifier 或 @Primary 注解一起来修饰。

## 16.spring 事务隔离级别有哪些?

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8ZsEhVvXtGSYDtNMPDN8EQ6ATaqFjXoQ8Tw635Rn255akmUGdvNcrXM75BQFV84pHuohrE7uNC26w/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- DEFAULT：采用 DB 默认的事务隔离级别
- READ_UNCOMMITTED：读未提交
- READ_COMMITTED：读已提交
- REPEATABLE_READ：可重复读
- SERIALIZABLE：串行化

## 17.spring 事务的传播机制有哪些?

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8ZsEhVvXtGSYDtNMPDN8EQ6BhmRhouYBYVOiaH3gG7ANgTQHdsYwVPMCSg14YW70kBicKuXB5d13g4Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 1.**「propagation_required」**

- - 当前方法**「必须在一个具有事务的上下文中运行」**，如有客户端有事务在进行，那么被调用端将在该事务中运行，否则的话重新开启一个事务。（如果被调用端发生异常，那么调用端和被调用端事务都将回滚）

- 2.**「propagation_supports」**

- - 当前方法不必需要具有一个事务上下文，但是如果有一个事务的话，它也可以在这个事务中运行

- 3.**「propagation_mandatory」**

- - 表示当前方法**「必须在一个事务中运行」**，如果没有事务，将抛出异常

- 4.**「propagation_nested」**

- - 如果当前方法正有一个事务在运行中，则该方法应该**「运行在一个嵌套事务」**中，被嵌套的事务可以独立于被封装的事务中进行提交或者回滚。如果封装事务存在，并且外层事务抛出异常回滚，那么内层事务必须回滚，反之，内层事务并不影响外层事务。如果封装事务不存在，则同propagation_required的一样

- 5.**「propagation_never」**

- - 当方法务不应该在一个事务中运行，如果**「存在一个事务，则抛出异常」**

- 6.**「propagation_requires_new」**

- - 当前方法**「必须运行在它自己的事务中」**。一个新的事务将启动，而且如果有一个现有的事务在运行的话，则这个方法将在运行期被挂起，直到新的事务提交或者回滚才恢复执行。

- 7.**「propagation_not_supported」**

- - 方法不应该在一个事务中运行。**「如果有一个事务正在运行，他将在运行期被挂起，直到这个事务提交或者回滚才恢复执行」**

## 18.springBoot 自动装配原理?

![图片](https://mmbiz.qpic.cn/mmbiz_png/9dwzhvuGc8ZsEhVvXtGSYDtNMPDN8EQ65mSGS0HYUTXdM3TZjDO3Ow4L9A0fztwtPXU0HBVvSXtCK81YusX9Ag/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 1.容器在启动的时候会调用 EnableAutoConfigurationImportSelector.class 的 selectImports方法**「获取一个全面的常用 BeanConfiguration 列表」**
- 2.之后会读取 spring-boot-autoconfigure.jar 下面的spring.factories，**「获取到所有的 Spring 相关的 Bean 的全限定名 ClassName」**
- 3.之后继续**「调用 filter 来一一筛选」**，过滤掉一些我们不需要不符合条件的 Bean
- 4.最后把符合条件的 BeanConfiguration 注入默认的 EnableConfigurationPropertie 类里面的属性值，并且**「注入到 IOC 环境当中」**