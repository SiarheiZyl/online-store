<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.online_market.entity.enums.Roles"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>
</head>
<body>
<form:form id="regForm" modelAttribute="user" action="registerProcess" method="post" var="Placeholder" >
    <table align="center">
        <tr>
            <td>
                <form:label path="login">Username</form:label>
            </td>
            <td>
                <form:input path="login" name="login" id="username" />
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
            <td>
                <form:label path="firstName">FirstName</form:label>
            </td>
            <td>
                <form:input path="firstName" name="firstName" id="firstName" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="lastName">LastName</form:label>
            </td>
            <td>
                <form:input path="lastName" name="lastName" id="lastName" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="email">Email</form:label>
            </td>
            <td>
                <form:input path="email" name="email" id="email" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="birthdate">Birthday</form:label>
            </td>
            <td>
                <form:input path="birthdate" type="date" name="birthdate" id="birthdate" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="role">Role</form:label>
            </td>
            <td>
                <form:select path="role">
                    <form:option value="NONE" label="--- Select ---" />
                    <form:options items="${roles}"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <form:button id="register" name="register">Register</form:button>
            </td>
        </tr>
        <tr></tr>
        <tr>
            <td></td>
            <td><a href="login">Log in</a>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>