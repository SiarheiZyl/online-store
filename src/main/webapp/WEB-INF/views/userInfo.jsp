<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="com.online_market.entity.enums.Roles"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>

    <link href="<c:url value="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"/>" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>


    <link href="<c:url value='../../resources/css/registration.css' />" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <header class="card-header">
                <h4 class="card-title mt-1">User Info</h4>
            </header>
            <article class="card-body">
<form:form id="infoForm" modelAttribute="user" action="/updateUser" method="post" var="placeHolderForPassword" >
    <div>
        <form:hidden path="id" name="id" id="id"  />
    </div>

    <div class="form-row">
        <div class="col form-group">
            <label>First name </label>
            <form:input path="firstName" name="firstName" id="firstName" class="form-control" />
        </div> <!-- form-group end.// -->
        <div class="col form-group">
            <label>Last name</label>
            <form:input path="lastName" name="lastName" id="lastName" class="form-control"/>
        </div> <!-- form-group end.// -->
    </div>

    <div class="form-group">
        <label>Username</label>
        <form:input path="login" name="login" id="username" class="form-control"/>
    </div>

    <div class="form-group">
        <label>Email address</label>
        <form:input path="email" name="email" id="email" type="email" class="form-control"/>
        <small class="form-text text-muted">We'll never share your email with anyone else.</small>
    </div>

    <div class="form-group">
        <label>Birthdate</label>
        <form:input path="birthdate" type="date" name="birthdate" id="birthdate" class="form-control"/>
    </div>

    <div class="form-row">
        <div class="form-group col-md-6">
            <label>Role</label>
            <form:label path="role" name="role" id="role">${user.role}</form:label>
        </div>
    </div>

    <div class="form-group">
        <label>Create password</label>
        <form:password path="password" name="password" id="password" class="form-control" placeholder="${placeHolderForPassword}" />
    </div>

    <div class="form-group">
        <form:button id="edit" name="edit" class="btn btn-primary btn-block">Save</form:button>
    </div>
</form:form>
            </article>
            <div class="border-top card-body text-center"><a href="/user/${user.id}/address">Edit address</a></div>
        </div>
    </div>
    <c:if test="${user.role==Roles.ADMIN}">
        <tr>
            <td></td>
            <td><a href="/user/${user.id}/editOrders">Orders</a>
            </td>
        </tr>
    </c:if>
</div>
</body>
</html>