<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Items</title>
    <jsp:include page="layout.jsp"/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

    <style>
        img:active {
            -webkit-transform: scale(2);
            transform: scale(2);
            z-index: 3;
        }
    </style>
</head>
<body>

<jsp:include page="navbar.jsp"/>
<div class="container">

    <div class="row">
        <div class="col-lg-3">
            <h1 class="my-4"><i>Art Shop</i></h1>
                <div class="list-group">
                        <a href="/items" class="list-group-item">ALL</a>
                    <c:forEach items="${categoryList}" var="category">
                        <a href="/filterItems/${category.categoryName}" class="list-group-item">${category.categoryName}</a>
                    </c:forEach >
                </div>

        </div>

        <div class="col-lg-9" style="margin-top: 5%">

            <div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner" role="listbox">
                    <div class="carousel-item active">
                        <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="First slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Second slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Third slide">
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
                        <form:form id="addItemForm" modelAttribute="item" action="items/${item.itemId}/addItemToOrderProcess" method="post"  >
                            <div class="col-lg-4 col-md-6 mb-4">
                                <div class="card" style="height: 600px;">
                                    <img class="card-img-top img-fluid img-thumbnail" width="300" height="200" src=/resources/images/${item.itemId}.jpg style="cursor:zoom-in;" >
                                    <div class="card-body ">
                                        <h4 class="card-title"><strong>${item.itemName}</strong></h4>
                                        <h5><strong><i>$${item.price}</i></strong></h5>
                                        <p class="card-text-bottom">Category: ${item.category.categoryName}<br>Availible count: ${item.availableCount}</p>
                                    </div>
                                    <div class="card-footer">
                                        <form:button id="order" class="btn btn-primary btn-block">Buy</form:button>
                                    </div>
                                </div>
                            </div>

                        </form:form>
                        <c:if test="${rowCounter.count % 3 == 0||rowCounter.count == fn:length(itemList)}">
                            </tr>
                        </c:if>
                    </c:forEach >
                </table>


            </div>
            <!-- /.row -->
        </div>
    </div>
    <!-- /.row -->
</div>
<!-- /.container -->
</body>
</html>
