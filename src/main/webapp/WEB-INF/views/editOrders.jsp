<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.online_market.entity.enums.OrderStatus"%>
<%@page import="com.online_market.entity.enums.PaymentStatus"%>
<%--
  Created by IntelliJ IDEA.
  User: Siarhei
  Date: 30.10.2018
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit orders</title>
    <jsp:include page="layout.jsp"/>
    <link href="<c:url value='../../resources/css/orderHistory.css' />" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="panel panel-default panel-order " style="margin-top: 5%; margin-left: 5%; width: 90%">
    <h1 class="my-4"><i>Orders</i></h1>
    <div class="panel-body   " >
<c:forEach var="order" items="${orders}">
<form:form id="editOrdersForm" modelAttribute="order" action="/user/${id}/editOrdersProcess/${order.orderId}" method="post"  >
    <div class="col-md-12">
        <div class="row border-bottom" style="margin-top: 10px">
            <div class="col-md-12">
                <c:if test="${order.orderStatus==OrderStatus.DELIVERED}">
                    <div class="pull-right"><h3><label class="badge badge-success"> Delivered</label> </h3></div>
                </c:if>
                <c:if test="${order.orderStatus==OrderStatus.AWAITING_PAYMENT}">
                    <div class="pull-right"><h3></h3><label class="badge badge-warning"> Awaiting payment</label></h3> </div>
                </c:if>
                <c:if test="${order.orderStatus==OrderStatus.AWAITING_SHIPMENT}">
                    <div class="pull-right"><h3><label class="badge badge-info"> Awaiting shipment</label></h3> </div>
                </c:if>
                <c:if test="${order.orderStatus==OrderStatus.SHIPPED}">
                    <div class="pull-right"><h3><label class="badge badge-primary"> Shipped </label> </h3></div>
                </c:if>

                <c:if test="${order.paymentStatus==PaymentStatus.WAITING}">
                    <div class="pull-right"><h3><label class="badge badge-info"> Waiting </label> </h3></div>
                </c:if>
                <c:if test="${order.paymentStatus==PaymentStatus.PAID}">
                    <div class="pull-right"><h3><label class="badge badge-success"> Paid </label> </h3></div>
                </c:if>
                <span><strong>Payment method:</strong></span>
                <span class="label label-info">${order.paymentMethod}</span><br>
                <span><strong>Delivery method:</strong></span>
                <span class="label label-info">${order.deliveryMethod}</span><br>
                <div style="margin-bottom: 5px">
                    <div style="margin-top: 10px; width: 220px; margin-bottom: 10px">
                    <form:select path="orderStatus" class="form-control">
                        <form:options items="${orderStatusList}" />
                    </form:select>
                    <form:select path="paymentStatus" class="form-control">
                        <form:options items="${paymentStatusList}" />
                    </form:select>
                    </div>
                    <form:button id="repeatOrder" class="btn btn-outline-dark btn-sm pull-bottom" >Change</form:button>
                </div>
                <div class="col-md-12">
                  <small><i>Order made by ${order.user.login}</i></small>
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
