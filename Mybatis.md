# Mybatis

## 配置环境

1. ## 先创建子工程(创建com.mybatis 包目录)、在父工程中添加需要的依赖：

   ## 注意添加build 语句，否则Resources不会生成

   ~~~xml
   //父工程pom.xml
   <dependencies>
       <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
           <version>8.0.23</version>
       </dependency>
       <dependency>
           <groupId>org.mybatis</groupId>
           <artifactId>mybatis</artifactId>
           <version> 3.5.2</version>
       </dependency>
       <dependency>
           <groupId>junit</groupId>
           <artifactId>junit</artifactId>
           <version>4.12</version>
       </dependency>
   </dependencies>
   //注意添加一下语句，否则resources 资源文件不会生成
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
   ~~~

2. ## 在Resources 文件夹下添加主配置文件

   ## 注意注册Mapper！！！！！

   ~~~xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <environments default="development">
           <environment id="development">
               <transactionManager type="JDBC"/>
               <dataSource type="POOLED">
                   <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                   <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useUnicode=true&amp;characterEncodeing=UTF-8&amp;serverTimezone=UTC"/>
                   <property name="username" value="root"/>
                   <property name="password" value="198045"/>
               </dataSource>
           </environment>
       </environments>
   	//注册mapper很重要！！！！！！！！！！！！！！！！
       <mappers>
           <mapper resource="com/DIYmybatis/Dao/UserMapper.xml"/>
       </mappers>
   </configuration>
   ~~~

   

3. ## 创建com.mybatis.utils.MybatisUtils 用于连接数据库。（包目录java. com.utils.MybatisUtils）

   ~~~java
   public class MybatisUtils {
       private  static SqlSessionFactory sqlSessionFactory;
       static{
           try{
               String resource ="mybatis-config.xml";
               InputStream inputStream = Resources.getResourceAsStream(resource);
               sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
           }catch (IOException e){
               e.printStackTrace();
           }
       }
       public static SqlSession getSqlSession(){
           return  sqlSessionFactory.openSession();
       }
   }
   ~~~

   

4. ## 分别建立 ： 实体类，Dao接口，Dao实现类

   - ## 实体类

     ~~~java
     //entity 
     
     public class User {
         private int id;
         private String name;
         private String pwd;
     
         public int getId() {
             return id;
         }
     
         public void setId(int id) {
             this.id = id;
         }
     
         public String getName() {
             return name;
         }
     
         public void setName(String name) {
             this.name = name;
         }
     
         public String getPwd() {
             return pwd;
         }
     
         public void setPwd(String pwd) {
             this.pwd = pwd;
         }
     
         @Override
         public String toString() {
             return "User{" +
                     "id=" + id +
                     ", name='" + name + '\'' +
                     ", pwd='" + pwd + '\'' +
                     '}';
         }
     }
     
     ~~~

   

   - ## Dao接口（Mybatis 中习惯使用 Mapper来命名）

     ~~~java
     public interface UserDao {
         List<User> getUserList();
     }
     //Mybatis习惯命名形式
     public interface UserMapper{
         List<User> getUserList();
     }
     ~~~

     

   - ## Dao实现类。使用Mapper （一个XML文件）来实习配置。

     ~~~xml
     <?xml version="1.0" encoding="UTF-8" ?>
     <!DOCTYPE mapper
             PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
             "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
     <mapper namespace="com.DIYmybatis.Dao.UserDao"> <! 此处命名空间为待实现的Dao /Mapper 接口的全限定类名 要一致 要一致 要一致 >
     	<select id="getUserList" resultType="com.DIYmybatis.pojo.User"><!返回类型对应Entity的全限定类名，ID是Dao中对应的方法名>
             select * from mybatis.user
         </select>
     </mapper>
     ~~~

4. ## 测试类

   ~~~java
   public class UserDaoTest {
       @Test
       public  void test(){
           //使用工具类 获取Session
           SqlSession sqlSession = MybatisUtils.getSqlSession();
   		//推荐形式，官方有一种老的使用形式，可以去官网找。
           UserDao userDao = sqlSession.getMapper(UserDao.class);
           List<User> userList = userDao.getUserList();
           for (User user : userList) {
               System.out.println(user);
           }
       }
   }
   ~~~

   

## CRUD

在Mapper接口对应的配置文件中

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.DIYmybatis.Dao.UserMapper">
    <select id="getUserList" resultType="com.DIYmybatis.pojo.User">
        select * from mybatis.user
    </select>
    
    <select id="getUserById" resultType="com.DIYmybatis.pojo.User" parameterType="int">
        select * from mybatis.user where id = #{id}
    </select>
    
    <insert id="insertUser" parameterType="com.DIYmybatis.pojo.User">
        insert into mybatis.user ( id, name, pwd) values (#{id}, #{name} , #{pwd})
    </insert>
    
    <delete id="deleteUserById" parameterType="int">
        delete from mybatis.user where id = #{id}
    </delete>
    
    <update id="updateUser" parameterType="com.DIYmybatis.pojo.User">
        update mybatis.user set name = #{name}, pwd = #{pwd} where id = #{id}
    </update>
</mapper>
~~~

## 配置解析（mybatis-config.xml）

## properties

用于简化数据库连接信息的配置（url、username、password）

- ## db.properties(放在resources 目录下，与mabatis-config.xml 在同一目录。)

~~~properties

driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncodeing=UTF-8&serverTimezone=UTC
username=root
password=198045
~~~

- ## mybatis-config.xml

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    
    <properties resource="db.properties"/>
	//还可以动态添加属性 添加的属性如果重复了，不会覆盖原属性。
   <properties resource="db.properties">
      <property name="username" value="etst"/>
      <property name="password" value="tes"/>
    </properties>	 
//......省略
</configuration>
~~~



## settings （完整版看官方文档）

| 设置名             | 描述                                                         | 有效值                                                       | 默认值 |
| :----------------- | :----------------------------------------------------------- | :----------------------------------------------------------- | :----- |
| cacheEnabled       | 全局性地开启或关闭所有映射器配置文件中已配置的任何缓存。     | true \| false                                                | true   |
| lazyLoadingEnabled | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置 `fetchType` 属性来覆盖该项的开关状态。 | true \| false                                                | false  |
| logImpl            | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。        | SLF4J \| LOG4J \| LOG4J2 \| JDK_LOGGING \| COMMONS_LOGGING \| STDOUT_LOGGING \| NO_LOGGING | 未设置 |



## typeAliases 

别名，提供缩写功能。用于在mapper.xml中缩写JavaBean的名字

- ## mybatis-config.xml (第一种)

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    //第一种形式
    <typeAliases>
        <typeAlias alias="user" type="com.DIYmybatis.pojo.User"/>
    </typeAliases>

//......省略
</configuration>
~~~

- ## mybatis-config.xml (第二种)

~~~xml

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    //第二种形式
    <typeAliases>
        <package name="com.DIYmybatis.pojo"/>
    </typeAliases>

//......省略
</configuration>
~~~

每一个在包 `com.DIYmybatis.pojo` 中的 Java Bean，在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名。 比如 `com.DIYmybatis.pojo.User` 的别名为 `user`；若有注解，则别名为其注解值。见下面的例子：

```java
@Alias("userA")//若有注解，优先使用注解别名
public class UserMapper {
    ...
}
```

## environments

可以配置多个环境

- ### 为了指定创建哪种环境，只要将它作为可选的参数传递给 SqlSessionFactoryBuilder 即可。可以接受环境配置的两个方法签名是：

~~~java
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment);
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment, properties);

~~~

- ## 如果忽略了环境参数，那么将会加载默认环境，如下所示：

~~~java
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, properties);
~~~



- ## mybatis-config.xml

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
//*****省略
    <environments default="test">//默认环境的id
        <environment id="development">//通过环境来选择环境
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value= "${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
        <environment id="test">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value= "${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>

//****省略
</configuration>
~~~



## Mapper 注册

- ## 方式一：直接注册mapper.xml文件

- ## 	mybatis-config.xml

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
    <mapper resource="com/DIYmybatis/Dao/UserMapper.xml"/>
</mappers>
```

- ## 方式二

- ## 	mybatis-config.xml

  

~~~xml
<mappers>
    <mapper class="com.DIYmybatis.Dao.UserMapper"/> //使用这种方式时，接口和Mapper.xml文件必须同名，而且在同一个包下
</mappers>
~~~



- ## 方式三

- ## 	mybatis-config.xml 

~~~xml
<mappers>
     <package name="com.DIYmybatis.Dao"/>//使用这种方式时，接口和Mapper.xml文件必须同名，而且在同一个包下
</mappers>
~~~





