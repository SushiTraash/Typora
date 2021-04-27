<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询结果</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>书籍列表----------------所有书籍</small>
                </h1>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 column">
<%--                跳到增加书籍的页面--%>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddPage">添加书籍</a>
                 <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/allBook">全部书籍</a>
                <span style="color: red;font-weight: bold;">${error}</span>
            </div>
            <div class="col-md-4 column">

            </div>
            <div class="col-md-4 column">
                    <%--查询书籍--%>
                    <form class="form-inline" action="${pageContext.request.contextPath}/book/searchBook" method="post">
                        <input name="bookName" type="text" class="form-control" placeholder="输入查询书籍名称" >
                        <input type="submit" value="搜索" class="btn btn-primary">
                    </form>

            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th>书籍id</th>
                        <th>书籍名称</th>
                        <th>书籍数量</th>
                        <th>书籍描述</th>
                        <th>操作</th>
                    </tr>
                </thead>


                <tbody>
                    <c:forEach var="books" items="${list}">
                        <tr>
                            <td>${books.id}</td>
                            <td>${books.name}</td>
                            <td>${books.count}</td>
                            <td>${books.detail}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/book/toUpdateBookPage?id=${books.id}">修改 </a>
                                &nbsp;|&nbsp;
                                <a href="${pageContext.request.contextPath}/book/deleteBook/${books.id}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
