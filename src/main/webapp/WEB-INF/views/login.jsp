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

    <link href="<c:url value="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"/>" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

    <link href="<c:url value='../../resources/css/login.css' />" rel="stylesheet">

</head>
<body>

<h2>${STATUS_MESSAGE}</h2>
<form:form id="logForm" action="loginProcess" method="post" modelAttribute="user" class="form-signin" >
        <h1 class="h3 mb-3 font-weight-normal" align="center">Please sign in</h1>
        <label for="login" class="sr-only">Login</label>
        <form:input path="login" name="login" id="login" class="form-control" placeholder="Login"  />
        <label for="password" class="sr-only">Password</label>

        <form:password path="password" name="password" id="password" class="form-control" placeholder="Password"/>

        <form:button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</form:button>

<a href="register" id="notReg">Not registred yet?</a>
</form:form>


</body>
</html>