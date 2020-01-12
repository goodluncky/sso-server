<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/10
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>Sso-Server:登录</title>
    <link href="${pageContext.request.contextPath}/asserts/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<form class="form-group" action="${pageContext.request.contextPath}/login">
    <input type="hidden" name="redirectUrl" value="${redirectUrl}">
    <h3 class="h3">统一登录中心</h3>
    <input type="text" class="form-control" placeholder="Username" name="username" required="true">
    <input type="password" class="form-control" placeholder="Password" name="password" required="">
    <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
</form>

</body>
</html>
