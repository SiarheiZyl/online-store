<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login Page</title>

    <jsp:include page="layout.jsp"/>
    <link href="<c:url value='../../resources/css/login.css' />" rel="stylesheet">

</head>
<body>

<form:form id="logForm" action="loginProcess" method="post" modelAttribute="user" class="form-signin" >
        <h1 class="h3 mb-3 font-weight-normal" align="center">Please sign in</h1>
        <h6 style="color: red">${STATUS_MESSAGE}</h6>
        <label for="login" class="sr-only">Login</label>
        <form:input path="login" name="login" id="login" class="form-control" placeholder="Login"  />
        <label for="password" class="sr-only">Password</label>

        <form:password path="password" name="password" id="password" class="form-control" placeholder="Password"/>

        <form:button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</form:button>

<a href="register" id="notReg">Not registred yet?</a>
</form:form>


</body>
</html>