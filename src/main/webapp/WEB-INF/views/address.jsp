<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Address</title>
    <jsp:include page="layout.jsp"/>
    <%--  <script src="/resources/js/address.js" type="text/javascript"/>--%>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
            integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
            crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
</head>
<body>
<script src=/resources/js/address.js type="text/javascript"></script>
<jsp:include page="navbar.jsp"/>
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <header class="card-header">
                <h4 class="card-title mt-1">Address</h4>
            </header>
            <article class="card-body">
                <form action="user/${id}/addressProcess" method="post" name="addressForm">
                    <div>
                        <input type="hidden" name="addressId" value="${address.addressId}">
                    </div>

                    <div class="form-group">
                        <label>Country</label>
                        <input type="text" name="country" value="${address.country}" data-validation="text"
                               required="required" class="form-control">
                    </div>

                    <div class="form-group">
                        <label>City/town</label>
                        <input name="town" id="town" value="${address.town}" data-validation="text" required="required"
                               class="form-control"/>
                    </div>

                    <div class="form-group">
                        <label>ZipCode</label>
                        <input name="zipCode" id="zipCode" value="${address.zipCode}" data-validation="text"
                               required="required" class="form-control"/>
                    </div>

                    <div class="form-group">
                        <label>Street</label>
                        <input name="street" id="street" value="${address.street}" data-validation="text"
                               required="required" class="form-control"/>
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Bulding</label>
                            <input name="building" id="building" value="${address.building}" data-validation="text"
                                   required="required" class="form-control"/>
                        </div>
                        <div class="col form-group">
                            <label>Apartment</label>
                            <input name="apartment" id="apartment" value="${address.apartment}" data-validation="text"
                                   required="required" class="form-control"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <button id="editAddr" name="editAddr" type="submit" class="btn btn-outline-dark btn-block">Save
                        </button>
                    </div>
                </form>
            </article>
        </div>
    </div>
</div>

</body>
</html>
