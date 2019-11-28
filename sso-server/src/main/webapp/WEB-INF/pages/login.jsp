<%--
  Created by IntelliJ IDEA.
  User: 98216
  Date: 2019/8/12
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SSO服务登录页面</title>
</head>
<body>

<form action="/login" method="post">
    用户名：<input name="userName" /><br/>
    密码：<input name="password" type="password" /><br/>
    <button type="submit">登录</button>
</form>

</body>
</html>
