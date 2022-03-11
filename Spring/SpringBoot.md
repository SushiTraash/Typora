# 代码规范

业界通用代码规范 -- 记被业界普遍肯定的

编写的代码包括：业务逻辑和边界逻辑。

- 业务逻辑
- 边界逻辑
  - 错误处理
  - 日志
  - 缓存

```JAVA
假设现在有一个业务代码CREATE
CREATE(){
    //业务逻辑调用包括 以下三步
    checkParam();//可以猜想到，这个校验方法会被三个以上的接口调用，甚至可以说每个接口调用前都要调用这个方法。
    			//因此要抽象成一个方法
  
    createMR();//上下文强相关时 而且可重用性不强就 不用 抽象成一个方法
    		//
    approveMR();//
  
    //边界逻辑
    //错误处理可以写在 业务代码里
    //也可以使用Aop注解 
  
    //尽量使用声明式（注解）而不是过程式（抽象为方法）
}
```

# SpringBoot 异步

https://segmentfault.com/a/1190000039097608

## 事件通知 --  发布订阅模式

要想顺利的创建监听器，并起作用，这个过程中需要这样几个角色：
1、事件（event）可以封装和传递监听器中要处理的参数，如对象或字符串，并作为监听器中监听的目标。
2、监听器（listener）具体根据事件发生的业务处理模块，这里可以接收处理事件中封装的对象或字符串。
3、事件发布者（publisher）事件发生的触发者。

## 自定义 事件

需要继承ApplicationEvent

```java
public class MyTestEvent extends ApplicationEvent{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String msg ;

    public MyTestEvent(Object source,String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
```

## 事件发布者

使用ApplicationEventPublisher发布事件

```java
@Component
public class MyTestEventPubLisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     *  事件发布方法
      */
    public void pushListener(String msg) {
        applicationEventPublisher.publishEvent(new MyTestEvent(this, msg));
    }

}
```

## 监听器 -- ApplicationListener 和 @EventListener

监听器有两种使用方式：注解和非注解

### ApplicationListener

```java
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
 
/**
* Handle an application event.
* @param event the event to respond to
*/
void onApplicationEvent(E event);
}
```

- 泛型的类型必须是 ApplicationEvent 及其子类
- 只要实现了这个接口，那么当容器有相应的事件触发时，就能触发 onApplicationEvent 方法。
- ApplicationEvent 类的子类有很多，Spring 框架自带几个。
- **监听事件范围**： **E及其子类 的事件**

### 非注解使用 -- 继承ApplicationListener

```java
@Component
public class MyListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent myEvt) {
        System.out.println("non annotation : " + myEvt.getSource());
    }
}
```

### 注解使用 -- @EventListener

```java
@Configuration
public class config {
    @EventListener
    public void listen(MyEvt evt){
        System.out.println("annotation : " + evt.getMsg());

    }
}
```

## 原理

### @EvenrListener原理

上面的@Eventlistener注解的方法会被封装成 `ApplicationListener`对象,类似

```java
@Component
public class MyListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent myEvt) {
        System.out.println("annotation : " + evt.getMsg());//被@EventListener注解方法
    }
}
```

### ApplicationListener原理

# Spring Boot 持久层

https://www.wdbyte.com/2020/12/springboot/springboot-multiple-datasource/

https://mp.weixin.qq.com/s/-8ytSdjKGmukdNKx_f0jLw

## Mybatis --> Mybatis Generator

在企业开发中一般都用Mybatis，而Mapper.xml和对应dao 接口都是用Mybatis Generator生成。

- 数据源配置在SpringBoot 的 application.propertie中
- 使用 Maven 的 Mybatis-Generator插件生成Mapper.xml和dao接口

## Spring Boot 多数据源配置

https://segmentfault.com/a/1190000038731849

## Application.properties --  配置 数据源连接信息

```yaml
##
##多数据源配置的时候，与单数据源不同点在于spring.datasource之后多设置一个数据源名称
##primary和secondary来区分不同的数据源配置，这个前缀将在后续初始化数据源的时候用到。
########################## 主数据源 ##################################
spring.datasource.primary.jdbc-url=jdbc:mysql://127.0.0.1:3306/demo1?characterEncoding=utf-8&serverTimezone=GMT%2B8
spring.datasource.primary.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.primary.username=root
spring.datasource.primary.password=

########################## 第二个数据源 ###############################
spring.datasource.datasource2.jdbc-url=jdbc:mysql://127.0.0.1:3306/demo2?characterEncoding=utf-8&serverTimezone=GMT%2B8
spring.datasource.datasource2.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.datasource2.username=root
spring.datasource.datasource2.password=

# mybatis
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.wdbyte.domain
```

### 配置类 -- 给对应mapper选择数据源

```java
@Configuration
@MapperScan(basePackages = {"com.wdbyte.mapper.primary"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class PrimaryDataSourceConfig {

    @Bean(name = "dataSource")
    //选择数据源
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
```

- `@ConfigurationProperties(prefix = "spring.datasource.primary")`：使用spring.datasource.primary 开头的配置。
- `@Primary` ：声明这是一个主数据源（默认数据源），多数据源配置时**必不可少**。
- `@Qualifier`：显式选择传入的 Bean。

## Spring boot IoC容器初始化顺序

“static 成员变量 ”--> “非static成员变量” --> “被 `@Autowired`修饰的构造函数” --> “被 `@Autowired`修饰的成员变量b” --> “被 `@PostConstruct`修饰的 `init()`函数”。

https://www.jianshu.com/p/3a8f2dfd8445

坑 ： https://blog.csdn.net/jzy2046/article/details/89948124

https://blog.csdn.net/weixin_39593994/article/details/81627969
