# 常用依赖

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>SSM_Project</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>
<!--依赖：junit,mysql,c3p0连接池,servlet,jsp,mybatis,mybatis-spring,spring-->
    <dependencies>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
<!--        Mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.23</version>
        </dependency>
<!--        数据库连接池-->
        <!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.5</version>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.6</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version> 4.1.7.RELEASE</version>
        </dependency>
    </dependencies>
<!--静态资源导出-->
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
</project>
~~~

# 环境搭建流程

1. ## 创建项目

2. ## 导入依赖

3. ## 连接数据库

4. ## 建立目录结构（pojo，dao，service,controller）以及mybatis和spring的配置文件（mybatis-config.xml database.properties和 applicationContext.xml）

   - ### 配置文件

   - ### mybatis-config.xml

     ~~~xml
     <?xml version="1.0" encoding="UTF-8" ?>
     <!DOCTYPE configuration
     PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
     "http://mybatis.org/dtd/mybatis-3-config.dtd">
     <configuration>
         
     </configuration>
     ~~~

   - ### applicationContext.xml

     ~~~xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
     
     </beans>
     ~~~

   - ### database.properties

     ~~~properties
     jdbc.driver=com.mysql.jdbc.Driver
     jdbc.url=jdbc:mysql://localhost:3306/ssmproject?useSSL=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/shanghai
     jdbc.username=root
     jdbc.password=123456
     ~~~

# Mybatis层

- ### mybatis 层主要做的是Model层：Dao， Service 和entity

- ### 要做的工作有：pojo实体类对象的建立，dao的接口和对应mapper.xml，service的接口和实现类的建立

## pojo

~~~java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {
    private int id;
    private String name;
    private int count;
    private String detail;

}
~~~

## dao

- ## 接口

~~~java
public interface BooksMapper {
    //增加
    public int addBook(Books table);

    //删除
    int deleteBookById(@Param("bookId") int id);

    //更新
    int updateBook(Books table);

    //查询
    Books selectBookById(@Param("bookId") int id);

    List<Books> selectAllBooks(@Param("bookId") int id);
}
~~~

- ## mapper.xml

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ssm.dao.BooksMapper">
    <insert id="addBook" parameterType="Books">
        insert int ssmproject.books
        values (#{id},#{name},#{count},#{detail});
    </insert>
    <delete id="deleteBookById" >
        delete form book where id = #{bookId};
    </delete>
    <update id="updateBook" parameterType="Books">
        update books set name = #{bookName},count = #{bookCount},detail = #{bookDetail}
        where id = #{bookId};
    </update>
    <select id="selectBookById" resultType="Books">
        select * from books
        where id = #{bookId};
    </select>
    <select id="selectAllBooks" resultType="Books">
        select * from books;
    </select>
</mapper>
~~~



## service层

~~~java
public interface BookService {
    //增加
    public int addBook(Books table);

    //删除
    int deleteBookById(int id);

    //更新
    int updateBook(Books table);

    //查询
    Books selectBookById(int id);

    List<Books> selectAllBooks();

}
~~~

~~~java
public class BookServiceImpl implements BookService{

    //service调用Dao层
    private BooksMapper booksMapper;


    @Override
    public int addBook(Books table) {

        return booksMapper.addBook(table);
    }

    @Override
    public int deleteBookById(int id) {
        return booksMapper.deleteBookById(id);
    }

    @Override
    public int updateBook(Books table) {
        return booksMapper.updateBook(table);
    }

    @Override
    public Books selectBookById(int id) {
        return booksMapper.selectBookById(id);
    }

    @Override
    public List<Books> selectAllBooks() {
        return booksMapper.selectAllBooks();
    }
}
~~~

# Spring 层

## Spring 层主要工作

- ### 主要工作是配置Spring-dao.xml  和Spring-service.xml，其中

  - ### Spring-dao.xml	

    - 主要做的工作有 关联数据库配置文件（db.properties)
    - 创建数据库连接池对象datasource
    - 创建mybatis的对象工厂sqlsessionFactory
      - 扫描DaoComponent

  - ### Spring-service.xml

    - 扫描Service层的Component
    - 注入Dao的Mapper
    - 创建事务管理器

## spring - dao.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--1. 关联数据库配置文件    -->
    <context:property-placeholder location="classpath:database.properties"/>
    <!--2. 连接池    -->
    <bean id="dataSource"
          class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!-- 配置连接池属性 -->
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <!-- c3p0连接池的私有属性 -->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!-- 关闭连接后不自动commit -->
        <property name="autoCommitOnClose" value="false"/>
        <!-- 获取连接超时时间 -->
        <property name="checkoutTimeout" value="10000"/>
        <!-- 当获取连接失败重试次数 -->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

    <!-- 3.配置SqlSessionFactory对象 -->
    <bean id="sqlSessionFactory"
          class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- 注入数据库连接池 -->
        <property name="dataSource" ref="dataSource"/>
        <!-- 配置MyBaties全局配置文件:mybatis-config.xml -->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!-- 4.配置扫描Dao接口包，动态实现Dao接口注入到spring容器中
        -->
    <!--解释 ： https://www.cnblogs.com/jpfss/p/7799806.html-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 注入sqlSessionFactory -->
        <property name="sqlSessionFactoryBeanName"
                  value="sqlSessionFactory"/>
        <!-- 给出需要扫描Dao接口包 -->
        <property name="basePackage" value="com.ssm.dao"/>
    </bean>
</beans>
~~~

## Spring-Service.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--  1.扫描包下注解@component  -->
    <context:component-scan base-package="com.ssm.service"/>


    <bean id="BookServiceImpl" class="com.ssm.service.BookServiceImpl">
        <!--SetBooksMapper方法的名字 name 是booksMapper  要注入的ref是dao层的BooksMapper   -->
        <property name="booksMapper" ref="booksMapper"/><!--这行用于注入-->
    </bean>
    <!--配置事务管理器，注入数据源-->
    <bean id="tranctionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--注入数据源        -->
        <property name="dataSource" ref="dataSource"/>
    </bean>
</beans>
~~~





# SpringMVC层

## 配置Spring-mvc.xml和web.xml

- ### Spring mvc 主要配置注解驱动（用于配置处理器映射器和处理器适配器，@RequestMapping），静态资源过滤，扫描controller的component以及配置视图解析器

- ### web.xml主要配置dispatchservlet

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-context.xsd">
    <!--1.注解驱动    -->
    <mvc:annotation-driven/>
    <!--2.静态资源过滤    -->
    <mvc:default-servlet-handler/>
    <!--3.扫描component    -->
    <context:component-scan base-package="com.ssm.controller"/>

    <!--4.视图解析器    -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
~~~

~~~xml
<!--web.xml 配置dispatchServlet以及过滤器-->
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
         version="5.0">
    <servlet>
        <servlet-name>DispatchSer</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>DispatchSer</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    <!--encodingFilter-->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>
            org.springframework.web.filter.CharacterEncodingFilter
        </filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!--Session过期时间-->
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>
</web-app>
~~~

# 综合Spring三层

## applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <import resource="spring-dao.xml"/>
    <import resource="spring-mvc.xml"/>
    <import resource="spring-service.xml"/>
</beans>
```



