# SpringBoot 作用

## @Conditonal 注解

@conditional(? implement condition ) 括号中是实现condition接口的实现类

假设一个 @Condition( ConditionImpl )

~~~java
public class ConditionImpl implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {  // (1) condition 接口有一个matched方法，用于判断是否要加载这个bean
        return oracleJdbcDriverOnClassPath() && databaseUrlSet(context); // (2)
    }

    private boolean databaseUrlSet(ConditionContext context) { // (3)
        return context.getEnvironment().containsProperty("spring.datasource.url");
    }

    private boolean oracleJdbcDriverOnClassPath() { // (4)
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
~~~

## Condition 类

~~~java
@FunctionalInterface
public interface Condition {
	//matches 返回值为：
    //True: (继续 解析/注册)构建相应的@Bean/@Component/@Configuration
	//False: (停止 解析/注册)不要构建相应的@Bean/@Component/@Configuration
    boolean matches(ConditionContext var1, AnnotatedTypeMetadata var2);
}

~~~



# 环境配置

## maven安装和配置

1. 官网下载安装即可
2. 配置按照教程 https://blog.csdn.net/pan_junbiao/article/details/104264644
   - 配置系统变量
   - 创建本地仓库

# SpringBoot-- HelloWorld

## 创建项目

1. ### 使用官网initializer

   <img src="SpringBoot.assets/image-20210514171825762.png" alt="image-20210514171825762" style="zoom: 67%;" />

2. ### 使用idea插件 new--project

   <img src="SpringBoot.assets/image-20210514172058563.png" alt="image-20210514172058563" style="zoom: 67%;" />

3. ### 目录结构（要求controller等目录要和入口Application在同一包下）

   ![image-20210514174717787](SpringBoot.assets/image-20210514174717787.png)

   - 将项目打包成jar包运行

     ![image-20210514175850433](SpringBoot.assets/image-20210514175850433.png)

     ![image-20210514175904559](SpringBoot.assets/image-20210514175904559.png)

   - 运行

     ​	<img src="SpringBoot.assets/image-20210514175929175.png" alt="image-20210514175929175" style="zoom: 80%;" />

# 自动装配原理

## 初步理解自动配置

![image-20210524210350634](SpringBoot.assets/image-20210524210350634.png)

- ### AutoConfuguration: @EnableConfigurationProperties({ServerProperties.class})  注解引入对应的属性类Properties

![image-20210525204006284](SpringBoot.assets/image-20210525204006284.png)

- ### 	Properties中是可以在yaml或者properties文件中赋值的属性

- ### @ConfigurationProperties 注解中的prefix指明了配置的前缀为server

- <img src="SpringBoot.assets/image-20210525204419479.png" alt="image-20210525204419479" style="zoom:67%;" />

- properties文件：

- <img src="SpringBoot.assets/image-20210525204737620.png" alt="image-20210525204737620" style="zoom:67%;" />

  

# SpringBoot web 开发

## web项目结构复习

<img src="SpringBoot.assets/bVbes4j" alt="clipboard.png" style="zoom: 67%;" />

- java和resources文件夹下的文件都会放在编译后的classes文件夹下，pom引入的依赖都在lib下，编译器WEB-INF下的文件编译后还是在WEB-INF下

## 静态资源路径

静态资源自动配置路径：

- webjars
- resources文件夹下：public <static <resource (加载优先级)

## 首页 

### 静态资源配置首页

![image-20210525211459036](SpringBoot.assets/image-20210525211459036.png)

### 使用template 模板引擎 thymeleaf

- ### 导入依赖

  ~~~xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
  </dependency>
  ~~~

  

- ### thymeleaf语法

  ~~~html
  <div th:text="${msg}">
      <!-- th:  thymeleaf-->
  </div>
  ~~~

## MVC配置

### spring mvc 拓展

![image-20210525223318623](SpringBoot.assets/image-20210525223318623.png)

- ### debug 查看dispatchServlet 斷點，發現自定義的視圖解析器被加載到了resolvers中

![image-20210525223504574](SpringBoot.assets/image-20210525223504574.png)

## xxxProperties類 的作用

配合@ConfigurationProperties 实现配置绑定：xxxProperties类中的属性和配置文件中特定前缀prefix的属性绑定

# 入门部分

## 底层注解

### @SpringBootApplication   启动主程序

- 这个注解相当于 三个注解： @SpringBootConfigura...下面三个画横线的注解（前面四个是元注解，不重要）

<img src="SpringBoot.assets/image-20210527102049995.png" alt="image-20210527102049995" style="zoom:67%;" />

默认扫描Bean的路径是 主程序所在包及其子包

- ## 修改bean扫描路径：
  - ### 方式一：![image-20210722132628325](黑马SpringBoot.assets/image-20210722132628325.png)

  - ### 方式二：ComponentScan![image-20210722133103662](黑马SpringBoot.assets/image-20210722133103662.png)

    ​	

### @Configuration 

- ### @configuration 配合 @bean 使用，一个Configuration 类相当于一个bean.xml （bean配置文件）

- ### @configuration 类和 bean.xml  结构 对比

  <img src="SpringBoot.assets/image-20210527104152463.png" alt="image-20210527104152463" style="zoom: 50%;" />

  <img src="SpringBoot.assets/image-20210527104551983.png" alt="image-20210527104551983" style="zoom: 50%;" />

- ### @Configuration作用


1. ### @configration 用于配置bean，单例模式

2. ### @configuration 底层是 @Component

3. #### @configuration 的proxyBeanMode 属性 (代理  @bean 方法)

   - true:Full 全量模式
     - springboot会检查@bean 方法 注册的 bean的依赖关系。调用这个bean方法返回的就是容器中的bean（感觉有点像单例模式）
   - fasle :Lite 轻量模式
     - springboot不检查bean的依赖关系。调用这个bean方法返回的bean不一定是容器中的bean

### @Import

- ### 作用
  - ### 用于导入类，容器会创建导入的组件，@import 导入组件的名字是类的全限定类名

### @Conditional

- ### 作用
  - ### 是@Conditionalonxxx的底层实现，根据某种条件决定是否被构建 @conditional 修饰的组件

### @ImportResource

- ### 作用
  - ### 用于导入beans.xml文件，解析并且构建beans.xml 中的bean

## 配置绑定注解 

### 作用： 

- ### 配置绑定类里面的属性和配置文件里对应的属性绑定。读取yaml 或者 properties文件中的属性，并且在容器中创建组件。

2种用法：

1. @ConfigurationProperties + @EnableConfigurationProperties 两个类

   ~~~java
   @ConfigurationProperties(prefix = "mycar")
   public class carProperties{
       //...
   }
   
   @Configuration
   @EnableConfigurationProperties(car.class)
   //1.开启car配置绑定功能
   //2.把car组件注册到容器中
   public class Myconfig{
       //...
   }
   ~~~

   

2. @ConfigurationProperties + @Component 同一个类

   ~~~java
   @Componet
   //配置文件：application.yaml application.properties
   //在配置文件中，使用mycar前缀配置这个类
   @ConfigurationProperties(prefix = "mycar")
   public class carProperties{
       //...
   }
   ~~~

   

## 自动配置

### @SpringBootApplication

组成：

- 元注解 不重要 

  - @Target
  - @Retention
  - @Documented
  - @Inherited

- 重要注解

  ```java
  @SpringBootConfiguration//相当于@Configuration
  @EnableAutoConfiguration//自动配置的关键
  @ComponentScan//扫描包 
  ```

<img src="SpringBoot.assets/image-20210527201819042.png" alt="image-20210527201819042" style="zoom:67%;" />

### @EnableAutoConfiguration	

~~~java
@AutoConfigurationPackage
@Import({AutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {
    ///...
}
~~~

- #### 	@AutoConfigurationPackage	

  ```java
  @Import({Registrar.class})
  //导入了Registrar
  //Registrar用于将MainApplication 包下的 组件导入容器
  public @interface AutoConfigurationPackage {
  	//..
  }
  
  ```

- #### @Import({AutoConfigurationImportSelector.class})

~~~java
getAutoConfigurationEntry-->getCandidateConfigurations-->loadFactoryNames-->loadSpringFactories
~~~

- ### org\springframework\boot\spring-boot-autoconfigure\2.5.0\spring-boot-autoconfigure-2.5.0.jar 下是要导入的组件（很多AutoConfiguration 类）

- ### 图中画圈部分和注解同名

![image-20210527215102152](SpringBoot.assets/image-20210527215102152.png)

- 给@Bean 方法传入一个对象参数，那么spring就会自动在容器中找对应的对象，旧版Spring的MultypartResolver

  

### 总结：

- SpringBoot会加载中所有的组件（AutoConfigurationImportSelector 中的SpringFactoriesLoader） 自动配置类 xxxAutoConfiguratuin，但是不是所有组件都会生效
- @Conditional衍生出来的注解决定了加载了的组件是否生效，生效的组件才会装载到Spring容器中。组件都有默认绑定配置 xxxProperties。xxxProperties和配置文件绑定
- SpringBoot 在底层配置使用默认参数配置了所有的组件，但是用户的配置更优先
  - 使用@Bean 装载组件
  - @xxxProperties 配置绑定，用户在yaml或properties配置文件中配置组件属性

xxxAutoConfiguration ---> 组件 --- > xxxProperties --->yaml 或 properties

## 最佳实践

- 引入对应场景的starter

- 查看生效组件

  - debug = true Positive :生效组件 Negative: 不生效

    ![image-20210530142201621](SpringBoot.assets/image-20210530142201621.png)

- 修改配置

  - 看官方文档---Application Properties
  - 使用@Bean、@Component 自己装载组件
  - xxxCustomizer

# 开发技巧

1. ## lombok

   ~~~java
   @Data //getter setter
   @AllArgsConstructor //全参构造器
   @NoArgsConstructor //无参构造器
   @ToString //重写ToString方法
   @EqualsAndHashCode//重写equals 和hascode方法 ，使用类属性字段
   @slf4j
   public class test{
       public Integer id;
       public String name;
   }
   
   ~~~

2. ## Dev-tools

   实际上是重启，如果要实现热更新，使用JRebel

   ~~~xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-devtools</artifactId>
       <optional>true</optional>
   </dependency>
   ~~~

3. ## Spring Initializer IDEA 自带

#   配置文件

可以使用yaml 或者properties 。相同属性在两种文件中都被配置的话，properties文件中的属性优先有效。yaml 语法不赘述，找教程即可。 

# web开发

## 静态资源

1. ### 静态资源路径 static-path-pattern 修改访问静态资源的url前缀

- 对于一个请求，springboot先找有没有对于的controller处理对应的url
- 如果没有对应conroller，再查找有没有对应的静态资源
- 如果没有对应静态资源，报404错误

~~~yaml
spring:
  mvc:
	static-path-pattern: "/resources/**"
~~~

2. ### 静态资源位置 static-locations  存放静态资源的路径，可以配置多个

~~~yaml
spring:
  web:
    resources:
      static-locations: [classpath:/haha/,classpath:/xixi/] #路径为resources下的xixi和haha注意classpath: 冒号后面不带空格
~~~

3. ### webjar 

   - /webjars/** 前缀路径访问被打包成war包的jar包 例如： /webjars/jquery/jquery.min.js

## 欢迎页

在静态资源中的index.html默认作为首页。但是要注意不要更改静态资源路径pattern，否则index无法访问，不会成为主页。

## 自定义Favicon

不重要，用到再找教程

## 静态资源配置原理
