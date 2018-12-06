<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.online_market.entity.enums.Roles" %>
<html>
<head>
    <title>Catalog</title>
    <jsp:include page="layout.jsp"/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

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

    <style>
        img:active {
            -webkit-transform: scale(2);
            transform: scale(2);
            z-index: 3;
        }
    </style>
</head>
<body>
<script src=/resources/js/catalog.js type="text/javascript"></script>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-lg-3">
            <h1 class="display-4">Art Shop</h1>
            <div class="list-group">
                <a href="/items" class="list-group-item">ALL</a>
                <c:forEach items="${categoryList}" var="category">
                    <a href="/filterItems/${category.categoryName}" class="list-group-item">${category.categoryName}</a>
                </c:forEach>
            </div>
        </div>

        <div class="col-lg-9" style="margin-top: 5%">
            <div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel" data-interval="2000">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner" role="listbox">
                    <div class="carousel-item active">
                        <img class="d-block img-fluid" src="/resources/images/slides/1.jpg" alt="First slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block img-fluid" src="/resources/images/slides/2.jpg" alt="Second slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block img-fluid" src="/resources/images/slides/3.jpg" alt="Third slide">
                    </div>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>

            <div class="row">
                <table>
                    <c:forEach items="${itemList}" var="item" varStatus="rowCounter">

                        <c:if test="${rowCounter.count % 3 == 1}">
                            <tr>
                        </c:if>
                        <div class="col-lg-4 col-md-6 mb-4">
                            <div class="card" style="height: 530px;">
                                <img class="card-img-top img-fluid img-thumbnail" width="300" height="200"
                                     src="/image/${item.itemId}" alt="${item.itemName}" style="cursor:zoom-in;">

                                <div class="card-body ">
                                    <a href="/item/${item.itemId}" style="color: black;">
                                        <h4 class="card-title"><strong>${item.itemName}</strong></h4>
                                        <h5><strong><i>$${item.price}</i></strong></h5>
                                        <p class="card-text-bottom">
                                            Category: ${item.category.categoryName}<br>Author: ${item.params.author}<br>Size: ${item.params.height}x${item.params.width}<br>
                                            <label id="availible${item.itemId}">Availible
                                                count: ${item.availableCount}</label>
                                        </p>
                                    </a>
                                </div>

                                <div class="card-footer">
                                    <c:if test="${role==Roles.ADMIN}">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <button onclick="addItem(${item.itemId})" id="buyButton1"
                                                        class="btn btn-outline-dark btn-block" ${item.availableCount==0 || !item.shown ? 'disabled = "disabled"':''}>
                                                    Buy
                                                </button>
                                            </div>
                                            <div class="col-md-6">
                                                <a href="/item/${item.itemId}" class="btn btn-outline-dark btn-block">Edit</a>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${role!=Roles.ADMIN}">
                                        <button onclick="addItem(${item.itemId})" id="buyButton"
                                                class="btn btn-outline-dark btn-block" ${item.availableCount==0 ? 'disabled = "disabled"':''}>
                                            Buy
                                        </button>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <c:if test="${rowCounter.count % 3 == 0||rowCounter.count == fn:length(itemList)}">
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </div>
            <!-- /.row -->
        </div>
    </div>
    <!-- /.row -->
</div>
<!-- /.container -->

<ul class="pagination justify-content-center">
    <li ${pageId==1 ? 'class="page-item disabled"' : 'class="page-item"'}><a class="page-link"
                                                                             href="/catalog/${pageId-1}">Previous</a>
    </li>
    <c:forEach var="i" begin="1" end="${pageSize}">
        <li ${i==pageId ? 'class="page-item active"' : 'class="page-item"'}><a class="page-link"
                                                                               href="/catalog/${i}">${i}</a>
        </li>
    </c:forEach>
    <li ${pageId==pageSize ? 'class="page-item disabled"' : 'class="page-item"'}><a class="page-link"
                                                                                    href="/catalog/${pageId+1}">Next</a>
    </li>
</ul>


</body>
</html>
