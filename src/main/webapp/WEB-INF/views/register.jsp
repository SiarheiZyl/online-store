<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.online_market.entity.enums.Roles"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>

    <link href="<c:url value="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"/>" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">

    <link href="<c:url value='../../resources/css/registration.css' />" rel="stylesheet">
</head>
<body>
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <header class="card-header">
                <a href="login" class="float-right btn btn-outline-primary mt-1">Log in</a>
                <h4 class="card-title mt-2">Sign up</h4>
            </header>
            <article class="card-body">
<form:form id="regForm" modelAttribute="user" action="registerProcess" method="post" var="Placeholder" >
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

            <form:select path="role" class="form-control">
                <form:option value="NONE" label="--- Select ---" />
                <form:options items="${roles}"/>
            </form:select>
        </div> <!-- form-group end.// -->
    </div>

    <div class="form-group">
        <label>Create password</label>
        <form:password path="password" name="password" id="password" class="form-control"/>
    </div>

    <div class="form-group">
        <form:button id="register" name="register" class="btn btn-primary btn-block">Register</form:button>
    </div>
    <small class="text-muted">By clicking the 'Sign Up' button, you confirm that you accept our <br> Terms of use and Privacy Policy.</small>
</form:form>
            </article>
            <div class="border-top card-body text-center">Have an account? <a href="login">Log In</a></div>
        </div>
    </div>

</div>


</body>
</html>