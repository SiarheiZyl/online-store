<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="com.online_market.entity.enums.Roles" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>${item.itemName}</title>
    <jsp:include page="layout.jsp"/>
    <link href="<c:url value='../../resources/css/registration.css' />" rel="stylesheet">
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
    // wait for the DOM to be loaded
    $(document).ready(function() {
        // bind 'myForm' and provide a simple callback function
        $('#editItemForm').ajaxForm(function() {
            alert("Item was updated successfully!");
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

    function addItem(itemId){
        $.ajax({
            type:'GET',
            data:{itId: itemId},
            url:"/addItemToOrderProcess" ,
            success: function (res) {

                if(res !== "-1") {
                    $("#lblCartCount").html(Number( $("#lblCartCount").text())+1).show();
                    $("#available").html(res);
                }

            }
        });
    }

    function changeVisibilityOfItem(itemId){
        $.ajax({
            type:'GET',
            data:{itId: itemId},
            url:"/changeVisibilityOfItem" ,
            success: function (res) {
                var text ="";
                if( $("#hideItem").text()=="Hide item") {
                    text = "Show item";
                    $("#buyItem").hide();
                    alert("Item is hidden for users.")
                }
                if($("#hideItem").text()=="Show item") {
                    text = "Hide item";
                    alert("Item is availible for users.")
                }

                $("#hideItem").html("");
                $("#hideItem").html(text);
            }
        });
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
<jsp:include page="navbar.jsp"/>
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <header class="card-header">
                <c:if test="${role==Roles.ADMIN}">
                    <c:if test="${!item.shown}">
                        <button id="hideItem" type="button" onclick="changeVisibilityOfItem(${item.itemId})"
                                class="float-right btn btn-outline-dark mt-1" ${item.availableCount==0 ? 'disabled = "disabled"':''}
                                value="Show item">Show item
                        </button>
                    </c:if>
                    <c:if test="${item.shown}">
                        <button id="hideItem" type="button" onclick="changeVisibilityOfItem(${item.itemId})"
                                class="float-right btn btn-outline-dark mt-1" ${item.availableCount==0 ? 'disabled = "disabled"':''}
                                value="Hide item">Hide item
                        </button>
                    </c:if>
                    <h4 class="display-4">Editing item</h4>
                </c:if>
                <c:if test="${role!=Roles.ADMIN}">
                    <h3 class="display-4">${item.itemName}</h3>
                </c:if>
            </header>
            <article class="card-body">
                <form action="/editItemProcess" method="post" id="editItemForm" name="editItemForm"
                      enctype="multipart/form-data">
                    <div>
                        <input type="hidden" name="itemId" id="itemId" value="${item.itemId}"/>
                    </div>


                    <div class="form-group">
                        <img id="upload-image" class="card-img-top img-fluid img-thumbnail" src="/image/${item.itemId}"
                             alt="${item.itemName}">
                    </div>

                    <c:if test="${role == Roles.ADMIN}">
                        <div class="custom-file" id="customFile" lang="es">
                            <input type="file" class="custom-file-input" id="imageFile" onchange="readURL(this);"
                                   accept="image/jpeg, image/png, image/gif" name="image">
                            <label class="custom-file-label" for="imageFile">
                                Select file...
                            </label>
                        </div>
                    </c:if>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Name</label>
                            <c:if test="${role==Roles.ADMIN}">
                                <input name="itemName" id="itemName" class="form-control" data-validation="text"
                                       required="required" value="${item.itemName}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label class="form-control">${item.itemName}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Category</label>
                            <c:if test="${role==Roles.ADMIN}">
                                <select id="category" name="category" class="form-control">
                                    <c:forEach var="categ" items="${listCategories}">
                                        <option value="${categ.categoryName}" ${categ.categoryName == item.category.categoryName ? 'selected="selected"' : ''} >${categ.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label class="form-control">${item.category.categoryName}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Price($)</label>
                            <c:if test="${role==Roles.ADMIN}">
                                <input name="price" id="price" type="number" data-validation="number"
                                       required="required" min="0" class="form-control" value="${item.price}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label class="form-control">${item.price}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Availible count</label>
                            <c:if test="${role==Roles.ADMIN}">
                                <input type="number" name="availableCount" id="availableCount" data-validation="number"
                                       required="required" min="0" class="form-control"
                                       value="${item.availableCount}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label class="form-control" id="available">${item.availableCount}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Country</label>
                            <c:if test="${role==Roles.ADMIN}">
                                <input id="country" name="country" class="form-control" data-validation="text"
                                       required="required" value="${item.params.country}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label class="form-control">${item.params.country}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Author</label>
                            <c:if test="${role==Roles.ADMIN}">
                                <input id="author" name="author" class="form-control" data-validation="text"
                                       required="required" value="${item.params.author}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label class="form-control">${item.params.author}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Height</label>
                            <c:if test="${role==Roles.ADMIN}">
                                <input id="height" type="number" name="height" class="form-control"
                                       data-validation="number"
                                       required="required" min="0" value="${item.params.height}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label class="form-control">${item.params.height}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Width</label>
                            <c:if test="${role==Roles.ADMIN}">
                                <input type="number" id="width" name="width" class="form-control"
                                       data-validation="number"
                                       required="required" min="0" value="${item.params.width}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label class="form-control">${item.params.width}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                    </div>


                    <div class="form-group">
                        <c:if test="${role==Roles.ADMIN}">
                            <button id="editItem" name="editItem" type="submit" class="btn btn-outline-dark  btn-block">
                                Save
                            </button>
                        </c:if>


                        <c:if test="${role!=Roles.ADMIN}">
                            <button id="buyItem" type="button" onclick="addItem(${item.itemId})"
                                    class="btn btn-outline-dark btn-block" ${item.availableCount==0 ? 'disabled = "disabled"':''}>
                                Buy
                            </button>
                        </c:if>

                    </div>
                </form>
            </article>
        </div>
    </div>
</div>
</body>
</html>