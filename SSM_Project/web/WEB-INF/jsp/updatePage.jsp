<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改书籍</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改书籍</small>
                </h1>
            </div>
        </div>

    </div>
    <form action="${pageContext.request.contextPath}/book/updateBook" method="post">
<%--        修改书籍失败的原因是： 没有设置id，修改的书籍对于id = 0  使用隐藏域解决这一问题--%>
        <input type="hidden" name="id" value="${books.id}">
        <div class="form-group">
            <label >书籍名称:</label>
            <input type="text" class="form-control" name="name" value="${books.name}" required>
        </div>
        <div class="form-group">
            <label >书籍数量:</label>
            <input type="text" class="form-control" name="count" value="${books.count}" required>
        </div>
        <div class="form-group">
            <label >书籍描述:</label>
            <input type="text" class="form-control" name="detail" value="${books.detail}" required>
        </div>

        <input type="submit" class="form-control" value="修改">

    </form>
</div>

</body>
</html>