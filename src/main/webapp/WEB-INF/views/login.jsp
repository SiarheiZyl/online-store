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
</head>
<body>

<div style="margin:10px">
  <h2>${STATUS_MESSAGE}</h2>
    <form:form id="logForm" action="loginProcess" method="post" modelAttribute="user" >
        <table align="center">
            <tr>
                <td>
                    <form:label path="login">Username</form:label>
                </td>
                <td>
                    <form:input path="login" name="login" id="login" />
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="password">Password</form:label>
                </td>
                <td>
                    <form:password path="password" name="password" id="password" />
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <form:button id="log" name="log">Log in</form:button>
                </td>
            </tr>
            <tr></tr>
            <tr>
                <td></td>
                <td><a href="register">Not registred yet?</a>
                </td>
            </tr>
        </table>
    </form:form>
</div>

</body>
</html>