<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="gravatar" uri="http://www.paalgyula.hu/schemas/tld/gravatar" %>
<%@page import="com.online_market.entity.enums.OrderStatus" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Order History</title>
    <link rel="stylesheet" type="text/css" href="/netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css">
    <jsp:include page="layout.jsp"/>
    <link href="<c:url value='../../resources/css/orderHistory.css' />" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="panel panel-default panel-order " style="margin-top: 5%; margin-left: 5%; width: 90%">
    <h1 class="my-4"><i>Order history</i></h1>
    <div class="panel-body">
        <c:forEach var="order" items="${orders}">
            <form:form id="repeatOrderrderForm" modelAttribute="ord" action="/repeatOrderProcess/${order.key.orderId}"
                       method="post">
                <div class="col-md-12">
                    <div class="row border-bottom" style="margin-top: 10px">
                        <div class="col-md-12">
                            <c:if test="${order.key.orderStatus==OrderStatus.DELIVERED}">
                                <div class="float-right"><label class="badge badge-success">Delivered</label></div>
                            </c:if>
                            <c:if test="${order.key.orderStatus==OrderStatus.AWAITING_PAYMENT}">
                                <div class="float-right"><label class="badge badge-warning">Awaiting payment</label>
                                </div>
                            </c:if>
                            <c:if test="${order.key.orderStatus==OrderStatus.AWAITING_SHIPMENT}">
                                <div class="float-right"><label class="badge badge-info">Awaiting shipment</label></div>
                            </c:if>
                            <c:if test="${order.key.orderStatus==OrderStatus.SHIPPED}">
                                <div class="float-right"><label class="badge badge-primary">Shipped</label></div>
                            </c:if>
                            <span><strong>Items:</strong></span> <span class="label label-info">
                        <c:set var="sum" value="${0}"/>
                        <c:forEach var="item" items="${order.value}">
                            ${item.key.itemName}(${item.value})
                            <c:set var="sum" value="${sum + item.key.price*item.value}"/>
                        </c:forEach>
                            </span><br>
                            Cost: $${sum}<br>
                            Made on: ${order.key.date}
                            <div style="margin-bottom: 5px">
                                <form:button id="repeatOrder"
                                             class="btn btn-outline-dark btn-sm pull-bottom">Repeat Order</form:button>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
        </c:forEach>
    </div>
</div>
</body>
</html>
