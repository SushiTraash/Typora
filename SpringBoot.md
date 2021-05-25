# SpringBoot 作用

## @Conditonal 注解

@conditional(? implement condition ) 括号中是实现condition接口的实现类

假设一个 @Conditiona( ConditionImpl )

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

- ### AutoConfuguration: @EnableConfigurationProperties({ServerProperties.class})  注解引入对于的属性类Properties

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

## xxxCofiguration類的作用
