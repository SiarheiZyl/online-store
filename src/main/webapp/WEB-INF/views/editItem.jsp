<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="com.online_market.entity.enums.Roles" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Info</title>
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

</head>
<body>
<script type="text/javascript">
    $(function () {
        /*  Submit form using Ajax */
        $('button[type=submit]').click(function (e) {

            //Prevent default submission of form
            e.preventDefault();

            //Remove all errors
            $('input').next().remove();

            $.post({
                url: '/editItemProcess',
                data: {
                    itemId: $("#itemId").val(),
                    itemName: $("#itemName").val(),
                    category: $("#category").val(),
                    author: $("#author").val(),
                    country: $("#country").val(),
                    height: $("#height").val(),
                    width: $("#width").val(),
                    availableCount: $("#availableCount").val(),
                    price: Number($("#price").val())
                },
                success: function (res) {
                    alert("Item was updated");
                }
            })
        });
    });
</script>
<jsp:include page="navbar.jsp"/>
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <header class="card-header">
                <h4 class="card-title mt-1">Edit</h4>
            </header>
            <article class="card-body">
                <form action="/editItemProcess" method="post" name="editItemForm">
                    <div>
                        <input type="hidden" name="itemId" id="itemId" value="${item.itemId}"/>
                    </div>


                    <div class="form-group">
                        <img class="card-img-top img-fluid img-thumbnail" src=/resources/images/${item.itemId}.jpg>
                    </div>


                    <div class="form-row">
                        <div class="col form-group">
                            <label>Name</label>
                            <input name="itemName" id="itemName" class="form-control" value="${item.itemName}"/>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Category</label>
                            <select id="category" class="form-control">
                                <c:forEach var="categ" items="${listCategories}">
                                    <option value="${categ.categoryName}" ${categ.categoryName == item.category.categoryName ? 'selected="selected"' : ''} >${categ.categoryName}</option>
                                </c:forEach>
                            </select>
                        </div> <!-- form-group end.// -->
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Price($)</label>
                            <input name="price" id="price" type="number" class="form-control" value="${item.price}"/>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Availible count</label>
                            <input type="number" name="availableCount" id="availableCount" class="form-control"
                                   value="${item.availableCount}"/>
                        </div> <!-- form-group end.// -->
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Country</label>
                            <input id="country" class="form-control" value="${item.params.country}"/>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Author</label>
                            <input id="author" class="form-control" value="${item.params.author}"/>
                        </div> <!-- form-group end.// -->
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>Height</label>
                            <input id="height" type="number" class="form-control" value="${item.params.height}"/>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Width</label>
                            <input type="number" id="width" class="form-control" value="${item.params.width}"/>
                        </div> <!-- form-group end.// -->
                    </div>


                    <div class="form-group">
                        <button id="editItem" name="editItem" type="submit" class="btn btn-primary btn-block">Save
                        </button>
                    </div>
                </form>
            </article>
        </div>
    </div>
</div>
</body>
</html>