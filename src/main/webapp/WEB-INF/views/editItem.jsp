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
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>
</head>
<body>
<script type="text/javascript">

// wait for the DOM to be loaded
$(document).ready(function() {
    // bind 'myForm' and provide a simple callback function
    $('#editItemForm').ajaxForm(function() {
        alert("Item was updated successfully!");
    });
});

function addItem(itemId){
        $.ajax({
            type:'GET',
            data:{itId: itemId},
            url:"/addItemToOrderProcess" ,
            success: function (res) {
                $("#available").html(res);
                $("#lblCartCount").html(Number( $("#lblCartCount").text())+1).show();
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
                <h4 class="card-title mt-1">Edit</h4>
                </c:if>
                <c:if test="${role!=Roles.ADMIN}">
                    <h3 class="display-4">${item.itemName}</h3>
                </c:if>
            </header>
            <article class="card-body">
                <form action="/editItemProcess" method="post" id="editItemForm" name="editItemForm" enctype="multipart/form-data">
                    <div>
                        <input type="hidden" name="itemId" id="itemId" value="${item.itemId}"/>
                    </div>


                    <div class="form-group">
                        <img id="upload-image" class="card-img-top img-fluid img-thumbnail" src="/image/${item.itemId}" alt="${item.itemName}">
                    </div>

                    <div class="custom-file" id="customFile" lang="es">
                        <input type="file"  class="custom-file-input"  id="imageFile" onchange="readURL(this);" accept="image/jpeg, image/png, image/gif" name="image">
                        <label class="custom-file-label" for="imageFile">
                            Select file...
                        </label>
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Name</label>
                            <c:if test="${role==Roles.ADMIN}">
                            <input name="itemName" id="itemName" class="form-control" value="${item.itemName}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label  class="form-control" >${item.itemName}</label>
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
                                <label  class="form-control" >${item.category.categoryName}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Price($)</label>
                            <c:if test="${role==Roles.ADMIN}">
                            <input name="price" id="price" type="number" class="form-control" value="${item.price}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label  class="form-control" >${item.price}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Availible count</label>
                            <c:if test="${role==Roles.ADMIN}">
                            <input type="number" name="availableCount" id="availableCount" class="form-control"
                                   value="${item.availableCount}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label  class="form-control" id="available" >${item.availableCount}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Country</label>
                            <c:if test="${role==Roles.ADMIN}">
                            <input id="country" name="country" class="form-control" value="${item.params.country}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label  class="form-control" >${item.params.country}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Author</label>
                            <c:if test="${role==Roles.ADMIN}">
                            <input id="author" name="author" class="form-control" value="${item.params.author}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label  class="form-control" >${item.params.author}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Height</label>
                            <c:if test="${role==Roles.ADMIN}">
                            <input id="height" type="number" name="height" class="form-control" value="${item.params.height}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label  class="form-control" >${item.params.height}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Width</label>
                            <c:if test="${role==Roles.ADMIN}">
                            <input type="number" id="width" name="width" class="form-control" value="${item.params.width}"/>
                            </c:if>
                            <c:if test="${role!=Roles.ADMIN}">
                                <label  class="form-control" >${item.params.width}</label>
                            </c:if>
                        </div> <!-- form-group end.// -->
                    </div>


                    <div class="form-group">
                        <c:if test="${role==Roles.ADMIN}">
                        <button id="editItem" name="editItem" type="submit" class="btn btn-primary btn-block">Save
                        </button>
                        </c:if>

                        <c:if test="${role!=Roles.ADMIN}">
                            <button id="buyItem" type="button" onclick="addItem(${item.itemId})" class="btn btn-primary btn-block">Buy
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