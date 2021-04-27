<%--
  Created by IntelliJ IDEA.
  User: sushi
  Date: 2021/4/27
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>首页</title>
    <style>
      a{
        text-decoration: none;
        color: purple;
        font-size: 20px;
      }
      h3{
        width: 400px;
        margin: 100px;
        text-align: center;
        line-height: 40px;
        background: black;
        border-radius: 8px;
      }
    </style>
  </head>
  <body>
  <h3>
    <a href="${pageContext.request.contextPath}/book/allBook">查询全部书籍</a>
  </h3>
  </body>
</html>
