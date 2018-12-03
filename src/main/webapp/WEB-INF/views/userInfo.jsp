<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="com.online_market.entity.enums.Roles"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Info</title>
    <jsp:include page="layout.jsp"/>
    <link href="<c:url value='../../resources/css/registration.css' />" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</head>
<body>
<script src=/resources/js/userInfo.js type="text/javascript"></script>
<jsp:include page="navbar.jsp"/>
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <header class="card-header">
                <h4 class="card-title mt-1">User Info</h4>
            </header>
            <article class="card-body">
    <form action="/updateUser" method="post" name="infoForm">
    <div>
        <input type="hidden"  name="id" id="id" value="${user.id}" />
    </div>

    <div class="form-row">
        <div class="col form-group">
            <label>First name </label>
            <input name="firstName" id="firstName" class="form-control" value="${user.firstName}" />
        </div> <!-- form-group end.// -->
        <div class="col form-group">
            <label>Last name</label>
            <input name="lastName" id="lastName" class="form-control" value="${user.lastName}"/>
        </div> <!-- form-group end.// -->
    </div>

    <div class="form-group">
        <label>Username</label>
        <input name="login" id="username" class="form-control" value="${user.login}"/>
    </div>

    <div class="form-group">
        <label>Email address</label>
        <input name="email" id="email" type="email" class="form-control" value="${user.email}"/>
        <small class="form-text text-muted">We'll never share your email with anyone else.</small>
    </div>

    <div class="form-group">
        <label>Birthdate</label>
        <input type="date" name="birthdate" id="birthdate" class="form-control" value="${user.birthdate}"/>
    </div>

    <div class="form-row">
        <div class="form-group col-md-6">
            <label>Role: ${user.role}</label>
            <input type="hidden" name="role" id="role"></in>
        </div>
    </div>

    <div class="form-group">
        <label>Create password</label>
        <input type="password" name="password" id="password" class="form-control" placeholder="${placeHolderForPassword}" />
    </div>

    <div class="form-group">
        <button id="editInfo" name="editInfo" type="submit" class="btn btn-primary btn-block">Save</button>
    </div>
    </form>
            </article>
            <c:if test="${user.role==Roles.USER || user.role == Roles.ADMIN}">
                    <div class="border-top card-body text-center"><a href="/user/${user.id}/address">Edit address</a></div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>