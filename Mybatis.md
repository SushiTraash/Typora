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



## XML配置（mybatis-config.xml）

### properties

- # properties

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



### settings （完整版看官方文档）

- # settings

| 设置名             | 描述                                                         | 有效值                                                       | 默认值 |
| :----------------- | :----------------------------------------------------------- | :----------------------------------------------------------- | :----- |
| cacheEnabled       | 全局性地开启或关闭所有映射器配置文件中已配置的任何缓存。     | true \| false                                                | true   |
| lazyLoadingEnabled | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置 `fetchType` 属性来覆盖该项的开关状态。 | true \| false                                                | false  |
| logImpl            | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。        | SLF4J \| LOG4J \| LOG4J2 \| JDK_LOGGING \| COMMONS_LOGGING \| STDOUT_LOGGING \| NO_LOGGING | 未设置 |

### 日志 (在Mybatis-config 中)

~~~XML
<settings>
    <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
~~~



### typeAliases 

- # typeAliases 

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

### environments

- # environments

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



### Mapper 注册

- # Mapper 注册

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



## XML映射（Mapper.xml）

### 输入参数映射

- ## 使用Map传递输入参数

Mapper接口中

~~~java
public interface UserMapper {
    List<User> getUserList();
    User getUserById(int id);
    int insertUser(User user);
    int deleteUserById(int id);
    int updateUser(User user);
    User selectUser2(Map<String,Object> map);

}
~~~

mapper.xml

~~~xml
<select id="selectUser2" parameterType="map" resultType="user"> //注意：此处在mybatis.config 中增加了user的别名
    select * from mybatis.user where id = #{id}
</select>
~~~

测试类

~~~java
@Test
public void testSelect2(){
    Map<String,Object> map = new HashMap<>();//使用了Map
    map.put("id",1);//键名 就是sql中的输入参数名
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    User user =  userMapper.selectUser2(map);
    System.out.println(user);
}
~~~

- ## 使用@Param 注解

~~~JAVA
User getUserById(@Param("id2") int id); id 被映射为id2 在sql中 用#{id2}
对应SQL:
"select * from mybatis.user where id = #{id2}"
~~~



### 结果映射

- # 结果映射

保证JavaBean (POJO)的属性名和数据库表中的列名一致，Mybatis 就会自动生成resultMap

~~~xml
<mapper namespace="com.DIYmybatis.Dao.UserMapper">
    <select id="getUserList" resultType="user">
        select * from mybatis.user
    </select>
    <select id="getUserById" resultType="user" parameterType="int">
        select * from mybatis.user where id = #{id}
    </select>
</mapper>
~~~

也可以自定义ResultMap:

- ## 实体类

~~~java
public class User {
    private int id;
    private String name;
    private String pass;//将pwd 改为pass
///.....省略
}

~~~

- ## 自定义ResultMap

~~~xml
<resultMap id="userMap" type="User">
    <result property="pass" column="pwd"/>
</resultMap>
~~~

- ## ResultMap使用（注意用了ResultMap就不能用ResultType )

在引用它的语句中设置 resultMap 属性就行了（注意我们去掉了 resultType 属性）。

~~~xml
<select id="getUserList" resultMap="userMap">
    select * from mybatis.user
</select>
~~~



- ## 自定义的resultMap是在系统自动生成的ResultMap的基础上进行覆盖的

~~~xml
<resultMap id="userMap" type="User">
    <result property="pass" column="pwd"/>
    <result property="id" column="aa"/>//新增加的两对映射 的列名 是错误 查询出来的结果为 id 0 name 为null 证明系统的ResultMap 被覆盖了
    <result property="name" column="aa"/>
</resultMap>
~~~

## 日志

- ## 日志配置文件 log4j.properties

~~~properties
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下
#面的代码
log4j.rootLogger=DEBUG,console,file
#控制台输出的相关设置
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%c]-%m%n
#文件输出的相关设置
#第一一行代表日志输出覆盖原日志文件
log4j.appender.file.Append = false 
log4j.appender.file = org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/logTest.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n
#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG

~~~

- ## 标准日志工厂实现 mybatis-config.xml

~~~xml
<settings>
    <setting name="logImpl" value="LOG4J"/>
</settings>
~~~

- ## log4j的使用

  mybatisTest.class类中：

  ~~~java
  
  public class UserMapperTest {
      static Logger logger = Logger.getLogger(UserMapperTest.class);
      @Test
      public void testLog4j(){
          //优先级从低到高
          logger.trace("trace");
          logger.debug("debug");
          logger.info("info ");
          logger.warn("warn");
          logger.error("error");
          logger.fatal("fatal");
      }
  }
  
  ~~~

## 分页 （先跳过）

## 注解CRUD

~~~java
//结果映射和SQL操作绑定（一对一的），property是mapper类的属性名 column是数据库列名
@Results(
    @Result(column = "pwd",property = "pass")
)
@Select("select * from mybatis.user")
List<User> getUserList();
//select by ID
@Select("select * from mybatis.user where id = #{id2}")
@Results(
    @Result(column = "pwd",property = "pass")
)
User getUserById(@Param("id2") int id);
//insert user
@Insert("insert into mybatis.user values(#{id},#{name},#{pass})")
int insertUser(User user);
//delete user
@Delete("delete from mybatis.user where id = #{id}")
int deleteUser(int id);
//update user
@Update("update mybatis.user  set name = #{name} , pwd = #{pass} where id = #{id}")
int updateUser(User user);
~~~

## Lombok简化POJO

~~~java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private String pass;
    
}
~~~

## 一对多多对一（待学）