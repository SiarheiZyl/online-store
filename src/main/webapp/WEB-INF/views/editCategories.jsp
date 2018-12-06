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

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>
</head>
<body>
<script>
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

    $.validate({
        borderColorOnError: '#c80e0e',
        addValidClassOnAll: true,
        lang: 'en',
        validateOnBlur: false, // disable validation when input looses focus
        errorMessagePosition: 'top', // Instead of 'inline' which is default
        scrollToTopOnError: false // Set this property to true on longer forms
    });

    function addCategory() {
        $.ajax({
            type: 'POST',//тип запроса
            data: {categName: $("#categName").val()},//параметры запроса
            url: "/addNewCategoryProcess",//url адрес обработчика
            success: function (res) {
                alert("New category was successfully added!");
                $('#itemCateg').append("<option value=" + res + ">" + res + "</option>");
                $('#visibleCateg').append("<option value=" + res + ">" + res + "</option>");
                $('#categName').val('');
                $('#categName').attr("placeholder", "New category");

            }//возвращаемый результат от сервера
        });
    }

    function hideCategory() {

        var category = $("#visibleCateg").val();
        $.ajax({
            type: 'GET',//тип запроса
            data: {categName: category},//параметры запроса
            url: "/changeVisibilityOfCategory",//url адрес обработчика
            success: function (res) {
                alert(category+" is hidden!");
                $('#invisibleCateg').append("<option value=" + category + ">" + category + "</option>");

                $("select#visibleCateg option").filter("[value="+category+"]").remove();
                $("select#itemCateg option").filter("[value="+category+"]").remove();


            }//возвращаемый результат от сервера
        });
    }

    function showCategory() {

        var category = $("#invisibleCateg").val();
        $.ajax({
            type: 'GET',//тип запроса
            data: {categName: category},//параметры запроса
            url: "/changeVisibilityOfCategory",//url адрес обработчика
            success: function (res) {
                alert(category+" is availible!");
                $('#itemCateg').append("<option value=" + category + ">" + category + "</option>");
                $('#visibleCateg').append("<option value=" + category + ">" + category + "</option>");
                $("select#invisibleCateg option").filter("[value="+category+"]").remove();

                $("#invisCateg").prop('disabled', false);
            }//возвращаемый результат от сервера
        })
    }

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
</script>
<script src=/resources/js/editCategories.js type="text/javascript"></script>
<jsp:include page="navbar.jsp"/>
<div class="panel panel-default panel-order ">
    <div class="panel-body">
        <div class="row" style="margin-left: 10%; margin-top: 2%; margin-right: 10%;">
            <div class="col-md-6">
                <div class="row">
                    <h4 class="display-4">Add category</h4>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <label><i>Category name</i></label>
                        <input name="categName" id="categName" class="form-control" placeholder="New category"/>
                    </div>
                    <div class="col-md-6">
                        <button id="newCateg" class="btn btn-outline-dark btn-sm pull-bottom" onclick="addCategory()"
                                style="height: 38px; width: 110px; margin-top: 31px">Add category
                        </button>
                    </div>
                </div>

                <div class="row" style="margin-top: 38px">
                    <h4 class="display-4">Hide/show category</h4>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <select id="visibleCateg" name="visibleCateg" class="form-control">
                            <c:forEach var="categ" items="${visibleCategories}">
                                <option value="${categ}">${categ}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <button id="visCateg" class="btn btn-outline-dark btn-sm pull-bottom" onclick="hideCategory()"
                                style="height: 38px; width: 110px" }>Hide category
                        </button>
                    </div>
                </div>
                <div class="row" style="margin-top: 19px">
                    <div class="col-md-6">
                        <select id="invisibleCateg" name="visibleCateg" class="form-control">
                            <c:forEach var="categ" items="${invisibleCategories}">
                                <option value="${categ}">${categ}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <button id="invisCateg" class="btn btn-outline-dark btn-sm pull-bottom" onclick="showCategory()"
                                style="height: 38px;width: 110px" }>Show category
                        </button>
                    </div>
                </div>
            </div>

            <div class="col-md-6 float-right">
                <div class="row">
                    <h4 class="display-4">Add new item</h4>
                </div>
                <form:form action="/addNewItemProcess" method="post" id="formItem" enctype="multipart/form-data">
                    <div class="image-slider">
                        <img id="upload-image" class="img-fluid img-thumbnail" width="300" height="200"
                             style="display: none;" src="/resources/images/2.jpg" alt="Item">
                    </div>
                    <div class="custom-file" id="customFile" lang="es">
                        <input type="file" class="custom-file-input" id="imageFile" data-validation="file"
                               required="required" onchange="readURL(this);" accept="image/jpeg, image/png, image/gif"
                               name="image">
                        <label class="custom-file-label" for="imageFile">
                            Select file...
                        </label>
                    </div>
                    <div class="form-row">
                        <div class="col form-group">
                            <label><i>Name</i></label>
                            <input type="text" id="item" name="itemName" data-validation="text" required="required"
                                   class="form-control">
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
                            <input type="text" name="author" id="author" data-validation="text" required="required"
                                   class="form-control">
                        </div>
                        <div class="col form-group">
                            <label><i>Country</i></label>
                            <input type="text" name="country" id="country" data-validation="text" required="required"
                                   class="form-control">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label><i>Height</i></label>
                            <input type="number" name="height" id="height" data-validation="number" required="required"
                                   min="0" class="form-control">
                        </div>
                        <div class="col form-group">
                            <label><i>Width</i></label>
                            <input type="number" name="width" id="width" data-validation="number" required="required"
                                   min="0" class="form-control">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label><i>Availible count</i></label>
                            <input type="number" name="avalCount" id="avalCount" data-validation="number"
                                   required="required" min="0" class="form-control">
                        </div>
                        <div class="col form-group">
                            <label><i>Price($)</i></label>
                            <input type="number" name="price" id="price" data-validation="number" required="required"
                                   min="0" class="form-control">
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