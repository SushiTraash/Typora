
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>
    <script>
      function refresh(){
        $.post({
            url:"${pageContext.request.contextPath}/a1",
            data:{"name": $("#inputName").val()},
            success: function (data) {
                alert(data);
            }
        })
      }
    </script>
  </head>
  <body>
  <input type="text" id="inputName" onblur="refresh()">
  </body>
</html>
