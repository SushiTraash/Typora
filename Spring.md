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

## Spring作用以及使用案例

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



<!------
-------
------Property 设置
-------->
<bean id = "addr" class="com.spring.pojo.Address">
    <property name="address" value="testAddress"/>
</bean>
<bean id ="stu" class="com.spring.pojo.Student">

    <!--普通值注入-->
    <property name="name" value="testString"/>

    <!--Bean 注入-->
    <property name="address" ref="addr"/>

    <!--数组注入-->
    <property name="books">
        <array>
            <value>book1</value>
            <value>book2</value>
            <value>book3</value>
        </array>
    </property>

    <!--List注入-->
    <property name="hobbys">
        <list>
            <value>hob1</value>
            <value>hob2</value>
            <value>hob3</value>
        </list>
    </property>

    <!--Map-->
    <property name="card">
        <map>
            <entry key="key1" value="123123123"/>
            <entry key="key2" value="321312312"/>
        </map>
    </property>

    <!--Set-->
    <property name="games">
        <set>
            <value>GAME1</value>
            <value>GAME2</value>
            <value>GAME3</value>
        </set>
    </property>

    <!--NULL-->
    <property name="nil">
        <null></null>
    </property>
    <!--properties-->
    <property name="info">
        <props>
            <prop key="key1">value1</prop>
            <prop key="key2">value2</prop>
        </props>
    </property>
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

- ## 对应Bean.xml使用有参构造

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

## set方式注入

- ## 使用Bean的properties设置注入（实质上是调用Set方法）

~~~xml
<!------
-------
------Property 设置
-------->
<bean id = "addr" class="com.spring.pojo.Address">
    <property name="address" value="testAddress"/>
</bean>
<bean id ="stu" class="com.spring.pojo.Student">

    <!--普通值注入-->
    <property name="name" value="testString"/>

    <!--Bean 注入-->
    <property name="address" ref="addr"/>

    <!--数组注入-->
    <property name="books">
        <array>
            <value>book1</value>
            <value>book2</value>
            <value>book3</value>
        </array>
    </property>

    <!--List注入-->
    <property name="hobbys">
        <list>
            <value>hob1</value>
            <value>hob2</value>
            <value>hob3</value>
        </list>
    </property>

    <!--Map-->
    <property name="card">
        <map>
            <entry key="key1" value="123123123"/>
            <entry key="key2" value="321312312"/>
        </map>
    </property>

    <!--Set-->
    <property name="games">
        <set>
            <value>GAME1</value>
            <value>GAME2</value>
            <value>GAME3</value>
        </set>
    </property>

    <!--NULL-->
    <property name="nil">
        <null></null>
    </property>
    <!--properties-->
    <property name="info">
        <props>
            <prop key="key1">value1</prop>
            <prop key="key2">value2</prop>
        </props>
    </property>
</bean>
~~~

## 拓展注入

- ## 使用c命名空间和p命名空间进行缩写

~~~xml
 <!--p命名空间-->
<bean name="classic" class="com.example.ExampleBean">
    <property name="email" value="someone@somewhere.com"/>
</bean>
<bean name="p-namespace" class="com.example.ExampleBean"
      p:email="someone@somewhere.com"/>


<!--c命名空间-->
<!-- traditional declaration with optional argument names -->
<bean id="beanOne" class="x.y.ThingOne">
    <constructor-arg name="thingTwo" ref="beanTwo"/>
    <constructor-arg name="thingThree" ref="beanThree"/>
    <constructor-arg name="email" value="something@somewhere.com"/>
</bean>

<!-- c-namespace declaration with argument names -->
<bean id="beanOne" class="x.y.ThingOne" c:thingTwo-ref="beanTwo"
      c:thingThree-ref="beanThree" c:email="something@somewhere.com"/>

~~~

# Bean自动装配

## Bean.xml

~~~xml
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="dog" class="com.kuang.pojo.Dog"/>
    <bean id="cat" class="com.kuang.pojo.Cat"/>
    <bean id="user" class="com.kuang.pojo.User">
    	<property name="cat" ref="cat"/>
    	<property name="dog" ref="dog"/>
    	<property name="str" value="qinjiang"/>
    </bean>
</beans>
~~~

## ByName

使用Set方法名里面的属性名，如SetCat 匹配id 为“cat”（要保证id 唯一）

~~~xml
<bean id="dog" class="com.kuang.pojo.Dog"/>
    <bean id="cat" class="com.kuang.pojo.Cat"/>
<bean id="user" class="com.kuang.pojo.User" autowire="byName">
	<property name="str" value="qinjiang"/>
</bean>
~~~

## ByType

根据类型匹配，匹配Set方法中形参的类型

~~~xml
<bean id="user" class="com.kuang.pojo.User" autowire="byType">
	<property name="str" value="qinjiang"/>
</bean>
~~~

## 注解自动装配

- ## 导入约束。配置注解的支持

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    
	<bean id="dog" class="com.spring.pojo.Dog"/>
    <bean id="cat" class="com.spring.pojo.Cat"/>
    <bean id="people" class="com.spring.pojo.People" />
    
    <context:annotation-config/><!--注解支持-->

</beans>
~~~

- ## People类注解

  - AutoWired 默认按类型ByType装配 使用Qualifier可以改成ByName 方式装配

~~~java
public class People {
    @Autowired
    private Cat cat;
    @Autowired
    private Dog dog;

    private String name;

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "People{" +
                "cat=" + cat +
                ", dog=" + dog +
                ", name='" + name + '\'' +
                '}';
    }
}
~~~

- ## 测试类

~~~java
public class AnnoTest {
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        People people = context.getBean("people",People.class);
        people.getCat().yell();
        people.getDog().yell();
    }

}
~~~

## @Qualifier

~~~java
public class People {
    @Autowired
    @Qualifier(value = "cat1")//ByName 自动装配 要求 bean.xml 中有 ID 为cat1 的Bean
    private Cat cat;
~~~

## @Resource 注解

- @Autowired先byType，结合@Qulifier 可做到ByName。@Resource先 byName，若找不到，会变为ByType寻找Bean，更加智能，也消耗更多资源。

# 注解开发 component-scan

## @Componet 的使用

~~~java
//@Component 等价于：
//<bean id="user" class="com.spring.pojo.User"/>
@Component
public class User {
    @Value("testAnnoComponent")//用于给下面一行的属性赋值
    public String name;
}
~~~

- ## 使用注解需要指定扫描包

~~~xml
<!--指定扫描的包 扫描到的注解会生效    -->
<context:component-scan base-package="com.spring.pojo"/>
<context:annotation-config/>

~~~



## 衍生注解

- dao 使用@repository 

- controller 使用@Controller

- service 使用 @Service

  实际上作用和@Component一样: 注册bean

## 作用域注解

- ## @scope 

- singleton：默认的，Spring会采用单例模式创建这个对象。关闭 工厂 ，所有的对象都会销毁。
-  prototype：多例模式。关闭工厂 ，所有的对象不会销毁。内部的垃圾回收机制会回收

~~~java
@Controller("user")
@Scope("prototype")
public class User {
    @Value("秦疆")
    public String name;
}

~~~

# 使用java 注解配置

- 需要在对应的类中加入@Component注解

~~~java
//配置类
@Configuration
@ComponentScan("com.spring.pojo")
public class config {
    @Bean
    public User getUser(){
        return new User();
    }
}
//等价于 
<beans>
    <bean id="getUser" class="com.spring.pojo.User"/>
</beans>
~~~



# AOP

## 方式一 配置增强类

- ### 导入 织入包 的依赖

~~~xml
<dependency>
     <groupId>org.aspectj</groupId>
     <artifactId>aspectjweaver</artifactId>
     <version>1.9.6</version>
</dependency>
~~~

- ### 接口 Service

~~~java
public interface UserService {
    public void add();
    public void delete();
    public void update();
    public void select();

}
~~~

- ### 实现类SeriviceImpl

~~~java
public class UserServiceImpl implements UserService {
    @Override
    public void add() {
        System.out.println("add");
    }

    @Override
    public void delete() {
        System.out.println("delete");
    }

    @Override
    public void update() {
        System.out.println("update");
    }

    @Override
    public void select() {
        System.out.println("select");
    }
}
~~~

- ### 将要织入的日志类（通用逻辑功能） ：增强类( pacakge：com.spring.log)

~~~java
//在接口方法调用前织入
public class log implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object target) throws Throwable {
        System.out.println(target.getClass().getName()+" "+method.getName());
    }
}
//在接口方法返回值之后 织入
public class AfterLog implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println(method.getName()+"  res: "+returnValue);
    }
}
~~~

- ### 配置对应增强类

~~~xml

方式一 
<aop:config>
    <!--切入点 execution(类似正则表达式： *（返回值） 类名.* (代表所有方法)  (..) 代表参数)-->
    <aop:pointcut id="poi" expression="execution(* com.spring.service.UserServiceImpl.*(..))"/>
    <!--环绕环境增强-->
    <aop:advisor advice-ref="log" pointcut-ref="poi"/>
    <aop:advisor advice-ref="afterLog" pointcut-ref="poi"/>
</aop:config>
    
~~~



## 方式二 使用自定义切面织入

- ### 自定义增强类

~~~java
public class diyPoi {
    public void befor(){
        System.out.println("before");

    }
    public void after(){
        System.out.println("after");
    }
}
~~~

- ### spring 中 配置

~~~xml
方式二 ： 自定义类
<bean id="diy" class="com.spring.diy.diyPoi"/>
<aop:config>
    <aop:aspect ref="diy">
        <!--            切入点-->
        <aop:pointcut id="point" expression="execution(* com.spring.service.UserServiceImpl.*(..))"/>
        <!--            通知 即切面要执行的方法-->
        <aop:before method="befor" pointcut-ref="point"/>
        <aop:after method="after" pointcut-ref="point"/>
    </aop:aspect>
</aop:config>
~~~

## 方式三 注解实现

- ### 增强类

~~~java
@Aspect //标注为切面
public class annotationPoi {
    @Before("execution(* com.spring.service.UserServiceImpl.* (..))")
    public void before(){
        System.out.println("annotation before");
    }

    @After("execution(* com.spring.service.UserServiceImpl.* (..))")
    public void after(){
        System.out.println("annotation after");
    }
    @Around("execution(* com.spring.service.UserServiceImpl.* (..))")
    public void around(ProceedingJoinPoint jp) throws Throwable{
        System.out.println("befor around" );
        Object o = jp.proceed();
        System.out.println("after around" );
    }

}
~~~

- ### 在spring 中配置

~~~xml
<!--    方式三 注解实现AOP-->
<bean id="annotationPoi" class="com.spring.diy.annotationPoi"/>
<!--    注解支持-->
<aop:aspectj-autoproxy/>
~~~



# 整合Mybatis

## 方式一

- ## Mybatis-config.xml(Resources 文件夹下)

- ### 在Mybatis-Config.xml 中只留下别名 和设置 在Mybatis 中设置了的属性不能在Spring 的Beans 中重复设置

~~~xml
<!-- Mybatis-config.xml-->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--在Mybatis 中只留下别名 和设置 在Mybatis 中设置了的属性不能在Spring 的Beans 中重复设置-->

    <!--别名在mybatis    -->
    <typeAliases>
        <package name="com.spring.pojo"/>
    </typeAliases>

    <!--settings   -->

</configuration>

~~~

- ## spring-dao.xml

- ### spring-dao.xml 关注数据库的连接配置 导入Mybatis.config.xml的补充信息   (别名 和 Setting )

~~~xml
<!-- Beans.xml    -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/aop
           http://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--DataSource 数据源-->
    <bean id="datasource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useUnicode=true&amp;characterEncodeing=UTF-8&amp;serverTimezone=UTC"/>
        <property name="username" value="root"/>
        <property name="password" value="198045"/>
    </bean>
    <!-- SqlSessionFactory   关联Mybatis 中的configuration -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="datasource" />
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/spring/mapper/*.xml"/>
     </bean>

    <!--sqlSessionTemplate    -->
    <bean id ="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <!-- 构造器注入        -->
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>


</beans>
~~~

- ## applicationContext.xml  

- ### applicationContext.xml 关注实现类Impl的Bean配置 导入Beans.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/aop
           http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--导入Beans 这个xml 只关注实现类Impl的Bean创建即可   -->

    <import resource="spring-dao.xml"/>
    <!--   注册实现类UserImpl -->
    <bean id = "userDao" class="com.spring.mapper.UserMapperImpl"><!--实现类具体代码见下-->
        <property name="sqlSession" ref="sqlSession"/>
    </bean>
</beans>
~~~

- ### 实现类UserMapperIMpl.class 需要在ApplicationContext中配置Bean

~~~java
public class UserMapperImpl implements  UserMapper{

    private SqlSessionTemplate sqlSession;
    public void setSqlSession(SqlSessionTemplate sqlSession){
        this.sqlSession = sqlSession;
    }
    @Override
    public List<User> selectUser() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.selectUser();
    }
}
~~~

- 测试

~~~java
    @Test
    public void test2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = (UserMapper) context.getBean("userDao");
        List<User> userList = userMapper.selectUser();
        for (User user : userList) {
            System.out.println(user);
        }
    }
~~~

## 方式二

- ### 实现类 编写方式二

~~~java
public class UserMapperImpl2 extends SqlSessionDaoSupport implements UserMapper{


    public List<User> selectUser() {
//        SqlSession sqlSession = getSqlSession();
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        userMapper.selectUser();
        
        return getSqlSession().getMapper(UserMapper.class).selectUser();//以上三行精简为一行
    }
}
~~~

- ### Spring-dao.xml

~~~xml
<bean id = "userDao2" class="com.spring.mapper.UserMapperImpl2">
    <property name="sqlSessionFactory" ref="sqlSessionFactory"/><!--父类需要一个SqlSessionFactory建立SqlSession-->
</bean>
~~~

# 事务



## 环境搭建

- ## 接口UserMapper

~~~java
public interface UserMapper {
    List<User> selectUser();
    int addUser(User user);
    int deleteUser(int id);
}
~~~

- ## 实现类UserMapperImpl

~~~java
public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper {
    @Override
    public List<User> selectUser() {
        User user = new User(10,"testRollback","12333221");
        UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);
        addUser(user);
        userMapper.deleteUser(10);
        
        return getSqlSession().getMapper(UserMapper.class).selectUser();
    }
    @Override
    public int addUser(User user) {
        return getSqlSession().getMapper(UserMapper.class).addUser(user);
    }
    @Override
    public int deleteUser(int id) {
        return getSqlSession().getMapper(UserMapper.class).deleteUser(id);
    }
}

~~~

- ## UserMapper.xml sql语句映射（有错误,用于测试回滚）

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.spring.mapper.UserMapper">
    <select id="selectUser" resultType="user">
        select * from mybatis.user
    </select>
    <insert id="addUser" parameterType="user">
        insert into mybatis.user (id, name, pwd) values (#{id}, #{name}, #{pwd})
    </insert>
    <delete id="deleteUser" >
        deletes form mybatis.user where id = #{id} <!--故意制造错误，测试事务回滚-->
    </delete>
</mapper>
~~~

- ## 测试

~~~java
    @Test
    public void test3(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = (UserMapper) context.getBean("usermapper");
        List<User> userList = userMapper.selectUser();
        for (User user : userList) {
            System.out.println(user);
        }
    }
~~~

## 运行结果

- ## 数据成功插入 而没有删除，没有保证原子性

## Spring 的两种事务实现机制

- **编程式事务管理：** 通过Transaction Template手动管理事务，实际应用中很少使用，
- **使用XML配置声明式事务：** 推荐使用（代码侵入性最小），实际是通过AOP实现

## Spring 声明式事务

- ### 配置

~~~XML

<!--配置声明式事务-->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <constructor-arg ref="datasource" />
</bean>
<!-- 结合AOP实现事务 切面切入   -->
<!-- 配置事务通知advice  -->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <tx:attributes>
        <!--配置哪些方法使用什么样的事务,配置事务的传播特性-->
        <tx:method name="add" propagation="REQUIRED"/>
        <tx:method name="delete" propagation="REQUIRED"/>
        <tx:method name="update" propagation="REQUIRED"/>
        <tx:method name="search*" propagation="REQUIRED"/>
        <tx:method name="get" read-only="true"/>
        <tx:method name="*" propagation="REQUIRED"/>

    </tx:attributes>
</tx:advice>

<!--配置aop 织入事务    -->
<aop:config>
    <aop:pointcut id="txPoi" expression="execution(* com.spring.mapper.*.* (..))"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="txPoi"/>
</aop:config>
~~~

- ### 测试类

~~~java
@Test
public void test3(){
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    UserMapper userMapper = (UserMapper) context.getBean("usermapper");
    List<User> userList = userMapper.selectUser();
    for (User user : userList) {
        System.out.println(user);
    }
}
~~~

