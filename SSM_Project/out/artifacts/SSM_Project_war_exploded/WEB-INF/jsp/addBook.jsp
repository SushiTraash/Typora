<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>增加书籍</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>增加书籍</small>
                </h1>
            </div>
        </div>

    </div>
    <form action="${pageContext.request.contextPath}/book/addNewBook" method="post">
        <div class="form-group">
            <label >书籍名称:</label>
            <input type="text" class="form-control" name="name" required>
        </div>
        <div class="form-group">
            <label >书籍数量:</label>
            <input type="text" class="form-control" name="count" required>
        </div>
        <div class="form-group">
            <label >书籍描述:</label>
            <input type="text" class="form-control" name="detail" required>
        </div>

        <input type="submit" class="form-control" value="添加">

    </form>
</div>

</body>
</html>
