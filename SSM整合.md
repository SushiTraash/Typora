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