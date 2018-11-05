<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Basket</title>
    <jsp:include page="layout.jsp"/>
    <link href="<c:url value='../../resources/css/bucket.css' />" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="card-body">
    <c:set var="sum" value="${0}"/>
    <c:forEach var="item" items="${itemMap}">
            <form:form id="deleteItemForm" modelAttribute="order" action="/bucket/deleteProcess/${item.key.itemId}/${item.value}" method="post"  >
            <div class="row">
                <div class="col-12 col-sm-12 col-md-2 text-center">
                    <img class="img-responsive img-thumbnail" src=/resources/images/${item.key.itemId}.jpg  width="120" height="80">
                </div>
                <div class="col-12 text-sm-center col-sm-12 text-md-left col-md-6">
                    <h4 class="product-name"><strong>${item.key.itemName}</strong></h4>
                    <h4>
                        <small style="font-size: 10pt"> Category: ${item.key.category.categoryName}<br>Availible:${item.key.availableCount} </small>
                    </h4>
                </div>

                <div class="col-12 col-sm-12 text-sm-center col-md-4 text-md-right row">
                    <div class="col-3 col-sm-3 col-md-6 text-md-right" style="padding-top: 5px">
                        <h6>${item.key.price}<strong><span class="text-muted">$</span></strong></h6>
                    </div>
                    <div class="col-4 col-sm-4 .col-md-4">
                        <div class="quantity">
                            <input type="number" step="1" max="99" min="1" value= ${item.value} title="quantity" class="qty" id="#qty_input">
                        </div>
                    </div>
                    <div class="col-2 col-sm-2 col-md-2 text-right">
                        <form:button id="order" name="order" class="btn btn-outline-danger btn-xs" ><i class="fa fa-trash" aria-hidden="true"></i></form:button>
                    </div>
                </div>
            </div>
                <c:set var="sum" value="${sum + item.key.price*item.value}"/>
            </form:form>
        </c:forEach>

<c:if test="${itemMap.size()!=0}">
 <c:if test="${id>0}">
 <form:form id="addOrderForm" modelAttribute="order" action="/orderProcess" method="post"  >
    <div class="card-footer pull-bottom">
        <div class="coupon col-md-5 col-sm-5 no-padding-left pull-left">
            <div class="row " style="margin-top: 10px">
                <div class="col-6">
                    <form:label path="paymentMethod">PaymentMethod</form:label>
                    <form:select path="paymentMethod" class="form-control b-select">
                        <form:option value="NONE" label="         --- Select ---" />
                        <form:options items="${paymentList}"/>
                    </form:select>
                </div>

                <div class="col-6">
                    <form:label path="deliveryMethod">DeliveryMethod</form:label>
                    <form:select path="deliveryMethod" class="form-control b-select">
                        <form:option value="NONE" label="         --- Select ---" />
                        <form:options items="${deliveryList}" />
                    </form:select>
                </div>
            </div>
            </div>
        </div>
        <div class="pull-right pull-bottom" style="margin: 10px">

            <form:button id="order" name="order" class="btn btn-primary pull-right vbottom" >Order</form:button>
            <div class="pull-right" style="margin: 5px">
                <form:hidden path="amount" value="${sum}"/>
            </div>
        </div>
 </form:form>
 </c:if>
</c:if>
    <div class="pull-right" style="margin: 5px">
        Total price: <b>${sum}$</b>
    </div>
</div>
</body>
</html>
