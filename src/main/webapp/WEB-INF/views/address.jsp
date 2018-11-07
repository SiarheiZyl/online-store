<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Address</title>
    <jsp:include page="layout.jsp"/>
   <%--  <script src="/resources/js/address.js" type="text/javascript"/>--%>
    <script type="text/javascript">
        $(function() {
            /*  Submit form using Ajax */
            $('button[type=submit]').click(function(e) {

                //Prevent default submission of form
                e.preventDefault();

                $('input').next().remove();
                $.post({
                    url : 'addressProcess',
                    data : $('form[name=addressForm]').serialize()

                })
            });
        });
    </script>
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
<form action="user/${id}/addressProcess" method="post" name="addressForm">
    <div>
        <input type="number" name="addressId" value="${address.addressId}">
    </div>

    <div class="form-group">
        <label>Country</label>
        <input type="text" name="country" class="form-control">
    </div>

    <div class="form-group">
        <label>City/town</label>
        <input name="town" id="town" class="form-control"/>
    </div>

    <div class="form-group">
        <label>ZipCode</label>
        <input name="zipCode" id="zipCode" class="form-control"/>
    </div>

    <div class="form-group">
        <label>Street</label>
        <input name="street" id="street" class="form-control" />
    </div>

    <div class="form-row">
        <div class="col form-group">
            <label>Bulding</label>
            <input name="building" id="building" class="form-control"/>
        </div>
        <div class="col form-group">
            <label>Apartment</label>
            <input name="apartment" id="apartment" class="form-control"/>
        </div>
    </div>

    <div class="form-group">
        <button id="editAddr" name="editAddr" type="submit" class="btn btn-primary btn-block">Save</button>
    </div>
</form>
            </article>
        </div>
    </div>
</div>

</body>
</html>
</body>
</html>--%>
