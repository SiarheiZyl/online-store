<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Basket</title>
    <jsp:include page="layout.jsp"/>
    <link href="<c:url value='../../resources/css/bucket.css' />" rel="stylesheet">
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
<script src=/resources/js/bucket.js type="text/javascript"></script>
<jsp:include page="navbar.jsp"/>
<div class="card-body">
    <c:set var="sum" value="${0}"/>
    <c:forEach var="item" items="${itemMap}">
        <c:set var="sum" value="${sum + item.key.price*item.value}"/>
        <div class="row" id="row${item.key.itemId}">
            <div class="col-12 col-sm-12 col-md-2 text-center">
                <img class="img-responsive img-thumbnail" src="/smallImage/${item.key.itemId}" alt="${item.key.itemName}"
                     width="120" height="80">
            </div>
            <div class="col-12 text-sm-center col-sm-12 text-md-left col-md-6">
                <h4 class="product-name"><strong>${item.key.itemName}</strong></h4>
                <h4>
                    <small style="font-size: 10pt">
                        Category: ${item.key.category.categoryName}<br>Availible:${item.key.availableCount} </small>
                </h4>
            </div>

            <div class="col-12 col-sm-12 text-sm-center col-md-4 text-md-right row">
                <div class="col-3 col-sm-3 col-md-6 text-md-right" style="padding-top: 5px">
                    <h6>${item.key.price}<strong><span class="text-muted">$</span></strong></h6>
                </div>
                <div class="col-4 col-sm-4 .col-md-4">
                    <div class="quantity">
                        <input type="number" step="1" max="99" min="1" value=${item.value} title="quantity" class="qty"
                               id="#qty_input">
                    </div>
                </div>
                <div class="col-2 col-sm-2 col-md-2 text-right">
                    <button id="delete" name="delete" class="btn btn-outline-danger btn-xs"
                            onclick="removeItem(${item.key.itemId},${item.value},${item.key.price*item.value})"><i
                            class="fa fa-trash" aria-hidden="true"></i></button>
                </div>
            </div>
        </div>

    </c:forEach>

    <c:if test="${itemMap.size()!=0}">
        <c:if test="${id>0}">
            <form:form id="addOrderForm" modelAttribute="order" action="/orderProcess" method="post">
                <div class="card-footer float-bottom">
                    <div class="coupon col-md-5 col-sm-5 no-padding-left float-left">
                        <div class="row " style="margin-top: 10px">
                            <div class="col-6">
                                <form:label path="paymentMethod">PaymentMethod</form:label>
                                <form:select path="paymentMethod" class="form-control b-select">
                                    <form:options items="${paymentList}"/>
                                </form:select>
                            </div>

                            <div class="col-6">
                                <form:label path="deliveryMethod">DeliveryMethod</form:label>
                                <form:select path="deliveryMethod" class="form-control b-select">
                                    <form:options items="${deliveryList}"/>
                                </form:select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="float-right float-bottom" style="margin-top: 20px">

                    <form:button id="orderbut" name="order"
                                 class="btn btn-primary pull-right vbottom">Order</form:button>
                </div>
            </form:form>
        </c:if>
        <div class="float-right" id="totalPrice" style="margin: 10px">
            Total price: $<b id="sum">${sum}</b>
        </div>
        <c:if test="${id>0==false}">

            <div class="float-left" style="margin-top: 5px; margin-left: 38px">
                <i>To make an order you need to</i><a href="/login"> sign in </a>or <a href="register">register</a>
            </div>
        </c:if>
    </c:if>
</div>
</body>
</html>