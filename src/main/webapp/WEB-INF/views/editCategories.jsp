<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%@page import="com.online_market.entity.enums.OrderStatus" %>
<%@page import="com.online_market.entity.enums.PaymentStatus" %>
<%@page import="com.online_market.utils.MD5Util" %>

<%@ taglib prefix="gravatar" uri="http://www.paalgyula.hu/schemas/tld/gravatar" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Managing</title>
    <jsp:include page="layout.jsp"/>
    <link href="<c:url value='../../resources/css/orderHistory.css' />" rel="stylesheet">

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
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>

</head>
<body>
<script src=/resources/js/editCategories.js type="text/javascript"></script>
<script type="text/javascript">
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#upload-image')
                    .attr('src', e.target.result)
                    .show();
            };

            reader.readAsDataURL(input.files[0]);
        }
    }


    $(document).ready(function() {
        // bind 'myForm' and provide a simple callback function
        $('#formItem').ajaxForm(function() {
            $('#item').val('');
            $('#author').val('');
            $('#country').val('');
            $('#height').val('');
            $('#width').val('');
            $('#avalCount').val('');
            $('#price').val('');
            $('#upload-image').hide();
            alert("New item was successfully added!!");

        });
    });


</script>
<jsp:include page="navbar.jsp"/>
<div class="panel panel-default panel-order ">
    <div class="panel-body">
        <div class="row" style="margin-left: 10%; margin-top: 2%; margin-right: 10%;">
            <div class="col-md-6">
                <div class="row">
                    <h4 class="display-5">Add Category</h4>
                </div>
                <div class="row" style="margin-top: 10px">
                    <div class="col-md-6">
                        <label><i>Category name</i></label>
                        <input name="categName" id="categName" class="form-control" placeholder="New category"/>
                    </div>
                </div>
                <button id="newCateg" class="btn btn-outline-dark btn-sm pull-bottom" onclick="addCategory()"
                        style="margin-top: 10px">Add category
                </button>
            </div>

            <div class="col-md-6 float-right">
                <div class="row">
                    <h4 class="display-5">Add new item</h4>
                </div>
                <form:form action="/addNewItemProcess" method="post" id="formItem" enctype="multipart/form-data">
                    <div class="image-slider">
                        <img id="upload-image" class="img-fluid img-thumbnail" width="300" height="200" style="display: none;" src="/resources/images/2.jpg" alt="Item">
                    </div>
                    <div class="custom-file" id="customFile" lang="es">
                        <input type="file"  class="custom-file-input"  id="imageFile" onchange="readURL(this);" accept="image/jpeg, image/png, image/gif" name="image">
                        <label class="custom-file-label" for="imageFile">
                            Select file...
                        </label>
                    </div>
                <div class="form-row">
                    <div class="col form-group">
                        <label><i>Name</i></label>
                        <input type="text" id="item" name="itemName" class="form-control">
                    </div>
                    <div class="col form-group">
                        <label><i>Category</i></label>
                        <select id="itemCateg" name="itemCateg" class="form-control">
                            <c:forEach var="categ" items="${listCategories}">
                                <option value="${categ}">${categ}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="col form-group">
                        <label><i>Author</i></label>
                        <input type="text" name="author" id="author" class="form-control">
                    </div>
                    <div class="col form-group">
                        <label><i>Country</i></label>
                        <input type="text" name="country" id="country" class="form-control">
                    </div>
                </div>

                <div class="form-row">
                    <div class="col form-group">
                        <label><i>Height</i></label>
                        <input type="number" name="height" id="height" class="form-control">
                    </div>
                    <div class="col form-group">
                        <label><i>Width</i></label>
                        <input type="number" name="width" id="width" class="form-control">
                    </div>
                </div>

                <div class="form-row">
                    <div class="col form-group">
                        <label><i>Availible count</i></label>
                        <input type="number" name="avalCount" id="avalCount" class="form-control">
                    </div>
                    <div class="col form-group">
                        <label><i>Price($)</i></label>
                        <input type="number" name="price" id="price" class="form-control">
                    </div>
                </div>

                <button id="addItem" class="btn btn-outline-dark btn-sm pull-bottom" type="submit">Add item
                </button>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>