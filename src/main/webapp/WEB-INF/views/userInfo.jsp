<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>
</head>
<body>
<h1>User info</h1>
<form:form id="infoForm" modelAttribute="user" action="/updateUser" method="post" var="placeHolderForPassword" >
    <table align="center">
        <tr>
            <td>
                <form:label path="id">ID</form:label>
            </td>
            <td>
                <form:input path="id" name="id" id="id"  />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="login">Username</form:label>
            </td>
            <td>
                <form:input path="login" name="login" id="username"  />
            </td>
        </tr>
       <tr>
           <td>
                <form:label path="password" type="hidden">Password</form:label>
            </td>
            <td>
                <form:password path="password" name="password" id="password" placeholder="${placeHolderForPassword}"/>
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
                <form:label path="role" >Role</form:label>
            </td>
            <td>
                <form:input path="role" name="role" id="role" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <form:button id="saveChanges" name="saveChanges">Save changes</form:button>
            </td>
        </tr>
        <tr></tr>
        <tr>
            <td></td>
            <td><a href="/login">Logout</a>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><a href="/user/${user.id}/items">ItemList</a>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>