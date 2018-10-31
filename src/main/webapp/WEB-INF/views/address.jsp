<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Siarhei
  Date: 29.10.2018
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Address</title>
    <jsp:include page="layout.jsp"/>
</head>
<body>

<jsp:include page="navbar.jsp"/>
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <header class="card-header">
                <h4 class="card-title mt-1">Address</h4>
            </header>
            <article class="card-body">
<form:form id="addressForm" modelAttribute="address" action="addressProcess" method="post">
    <div>
        <form:hidden path="addressId" name="addressId" id="addressId"  />
    </div>

    <div class="form-group">
        <label>Country</label>
        <form:input path="country" name="country" id="country" class="form-control" />
    </div>

    <div class="form-group">
        <label>City/town</label>
        <form:input path="town" name="town" id="town" class="form-control"/>
    </div>

    <div class="form-group">
        <label>ZipCode</label>
        <form:input path="zipCode" name="zipCode" id="zipCode" class="form-control"/>
        <small class="form-text text-muted">We'll never share your email with anyone else.</small>
    </div>

    <div class="form-group">
        <label>Street</label>
        <form:input path="street" name="street" id="street" class="form-control" />
    </div>

    <div class="form-row">
        <div class="col form-group">
            <label>Bulding</label>
            <form:input path="building" name="building" id="building" class="form-control"/>
        </div>
        <div class="col form-group">
            <label>Apartment</label>
            <form:input path="apartment" name="apartment" id="apartment" class="form-control"/>
        </div>
    </div>

    <div class="form-group">
        <form:button id="editAddr" name="editAddr" class="btn btn-primary btn-block">Save</form:button>
    </div>
</form:form>
            </article>
        </div>
    </div>
</div>

</body>
</html>
