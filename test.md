# 标题

```java


<!--引入外部配置文件-->
    <properties resource="db.properties">
        <!--下面这个属性实际上没用，优先使用外部文件的值-->
        <property name="password" value="198045"/>
    </properties>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>

    </environments>
```







