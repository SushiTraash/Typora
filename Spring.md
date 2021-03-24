# SPRING

# IOC

- ## 	控制翻转,是一种编程的设计思想，实现方法之一是DI（依赖注入）

- 依赖注入例子 ：使用set方法即可实现依赖注入

~~~java
public class userServiceImpl implements userService{
    private userDao user ;
    public void setUser(userDao user){
        this.user = user;
    }
    @Override
    public void getUserSer() {
        user.getUser();
    }
}
~~~



# 容器

- ### 容器是一种为某种特定组件的运行提供必要支持的一个软件环境。例如，Tomcat就是一个Servlet容器，它可以为Servlet的运行提供运行环境。类似Docker这样的软件也是一个容器，它提供了必要的Linux环境以便运行一个特定的Linux进程。

- ### 通常来说，使用容器运行组件，除了提供一个组件运行环境之外，容器还提供了许多底层服务。例如，Servlet容器底层实现了TCP连接，解析HTTP协议等非常复杂的服务，如果没有容器来提供这些服务，我们就无法编写像Servlet这样代码简单，功能强大的组件。早期的JavaEE服务器提供的EJB容器最重要的功能就是通过声明式事务服务，使得EJB组件的开发人员不必自己编写冗长的事务处理代码，所以极大地简化了事务处理。

# Spring IoC 容器

## 工作原理图

![container magic](Spring.assets/container-magic.png)

## 使用案例

Configuration Metadata 配置元数据 可以用xml来配置 

- ## spring IoC容器根据配置信息创建对象，实质上Spring 仍然是使用set方法（DI）实现IoC，对应的

- ## beans.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--spring 创建的对象 叫做bean
    创建对象的工作托付给spring 容器
    下面是创建对象需要的信息 称为配置元数据
    一个bean 对应一个对象
    id 是bean的名字，用于标识不同的bean
    class是要创建对象的全限定类名
    property 用于设置对象的属性
    -->
    <bean id="hello" class="com.spring.pojo.hello">
        <property name="name" value="name1"/><!---->
    </bean>

</beans>
~~~

- hello类

~~~java
public class hello {
    private String name;

    public String getName() {
        return name;
    }
	//一定要设置Set方法，否则spring容器无法创建bean对象
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "hello{" +
                "name='" + name + '\'' +
                '}';
    }

~~~

## 另外一个使用案例

~~~java
//Dao 接口 以及实现类
public interface userDao {
    public void getUser();
}
////
public class userDaoImpl implements userDao{
    public void getUser(){
        System.out.println("Implementation");
    }
}
/////
public class userDaoImplMysql implements userDao{

    @Override
    public void getUser() {
        System.out.println("Mysql");
    }
}
//service 接口以及实现类
public interface userService {
    public void getUserSer();
    public void setUser(userDao user);
}
//
public class userServiceImpl implements userService{
    private userDao user ;
    public void setUser(userDao user){
        this.user = user;
    }
    @Override
    public void getUserSer() {
        user.getUser();
    }
}
~~~

- ## 使用spring容器  beans.xml

~~~xml
//省略...
<bean id="default" class="com.DIYspring.dao.userDaoImpl"/>
<bean id="Mysql" class="com.DIYspring.dao.userDaoImplMysql"/>
<bean id ="service" class="com.DIYspring.service.userServiceImpl">
    <property name="user" ref="default"/>//ref 引用另外一个bean 用户可以改配置文件的这个属性 来实现IoC(控制权在用户手上)
</bean>
///省略...
~~~

- ## test测试

~~~java
public class test {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        userService service = (userService) context.getBean("service");
        service.getUserSer();
    }
}
~~~

## IoC 容器创建对象的方式

1. 默认使用无参构造

2. 可以设置使用有参构造

   ~~~xml
   <!--调用有参构造函数创建对象-->
   <!--1. 使用下标-->
   <bean id="hello" class="com.spring.pojo.hello">
       <constructor-arg index="0" value="name_arg_index0"/>
   
   </bean>
   <!--2. 使用参数类型-->
   <bean id="hello" class="com.spring.pojo.hello">
       <constructor-arg type="java.lang.String" value="name_arg_Type"/>
   </bean>
   <!--3. 使用参数名称-->
   <bean id="hello" class="com.spring.pojo.hello">
       <constructor-arg name="name" value="name_arg_name"/>
   </bean>
   ~~~

- ## 配置文件加载时，容器管理的对象就已经初始化了（不管用不用都实例化了，单例模式）

# Spring配置

## 别名

~~~xml
    <bean id="hello" class="com.spring.pojo.hello">
        <constructor-arg name="name" value="name_arg_name"/>
    </bean>
    <alias name="hello" alias="helloAlias"/>
~~~

测试

~~~JAVA
    public static void main(String[] args) {
        //获取Spring的上下文对象
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        hello h = (hello)context.getBean("helloAlias");
        System.out.println(h.getName());
    }
~~~



## bean配置

~~~xml
    <!--id：bean 的唯一标识
     class: 对应pojo对象的全限定类名
     name: 别名 可以设置多个别名，别名用空格，逗号，分号（;）隔开
	scope 设置单例模式..原型..
     -->
    <bean id="hello" class="com.spring.pojo.hello" name="user u2 u3 u4,u5;u6">
    </bean>
~~~

## import

可以导入多个配置文件，合并多个bean到当前配置文件

- ## applicationContext.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--可以导入多个bean.xml-->
    <import resource="bean.xml"/>
    <import resource="bean2.xml"/>
</beans>
~~~

# 依赖注入DI

## 构造器注入

- ## 有参构造，构造对象的时候注入依赖

~~~java
public class SimpleMovieLister {

    // the SimpleMovieLister has a dependency on a MovieFinder
    private MovieFinder movieFinder;

    // a constructor so that the Spring container can inject a MovieFinder
    public SimpleMovieLister(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // business logic that actually uses the injected MovieFinder is omitted...
}
~~~



## set方式注入

## 拓展注入