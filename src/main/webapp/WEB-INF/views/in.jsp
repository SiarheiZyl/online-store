<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Siarhei
  Date: 19.10.2018
  Time: 23:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Online store</title>

    <jsp:include page="layout.jsp"/>
    <link href="<c:url value='../../resources/css/index.css' />" rel="stylesheet">

</head>
<body>
<h1 align="center" class="display-1" style="color: #5949c8;">Welcome to art shop</h1>
<div align="center" style="font-size: 20pt; margin-top: 10%">
<a  href="login">Login</a>
<a href="register">Registration</a>
<a href="/catalog">Art shop</a>
</div>
</body>
</html>
