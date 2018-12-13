<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%@page import="com.online_market.entity.enums.OrderStatus" %>
<%@page import="com.online_market.entity.enums.PaymentStatus" %>
<%@page import="com.online_market.utils.MD5Util" %>

<%@ taglib prefix="gravatar" uri="http://www.paalgyula.hu/schemas/tld/gravatar" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit orders</title>
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
    <style>
        img:active {
            -webkit-transform: scale(2);
            transform: scale(2);
            z-index: 3;
        }
    </style>

</head>
<body>
<script src=/resources/js/editOrders.js type="text/javascript"></script>
<jsp:include page="navbar.jsp"/>
<div class="panel panel-default panel-order " style="margin-left: 5%; width: 90%">
    <h1 class="display-4">Orders</h1>
    <div class="panel-body  ">
        <form action="/editOrders/${pageId}" method="get">
            <div class="form-row">


                <div class="col-md-2">
                    <label>From</label>
                    <input id="fromDate" name="fromDate" type="date" class="form-control"/>
                </div>
                <div class="col-md-2">
                    <label>To</label>
                    <input id="toDate" name="toDate" type="date" class="form-control"/>
                </div>
                <div class="col-md-4">
                    <label></label>
                    <button id="period" class="btn btn-dark btn-sm pull-bottom" type="submit"
                            style="width: 100px;height: 40px; margin-top: 31px"> Find
                    </button>
                </div>


            </div>

        </form>
        <div id="orders">
            <c:forEach var="order" items="${orders}">
                <div class="row">
                    <div class="col-md-1"><img src="http://www.gravatar.com/avatar/${MD5Util.md5Hex(order.user.email)}"
                                               class="media-object img-thumbnail"></div>
                    <div class="col-md-11">
                        <div class="row border-bottom" style="margin-top: 10px">
                            <div class="col-md-12">
                                <c:if test="${order.orderStatus==OrderStatus.DELIVERED}">
                                    <div class="float-right"><h4><label class="badge badge-success"
                                                                        id="orderStatusLabel${order.orderId}"
                                                                        style="margin-left: 10px">${order.orderStatus}</label>
                                    </h4></div>
                                </c:if>
                                <c:if test="${order.orderStatus==OrderStatus.AWAITING_PAYMENT}">
                                    <div class="float-right"><h4><label class="badge badge-warning"
                                                                        id="orderStatusLabel${order.orderId}"
                                                                        style="margin-left: 10px"> ${order.orderStatus}</label>
                                    </h4></div>
                                </c:if>
                                <c:if test="${order.orderStatus==OrderStatus.AWAITING_SHIPMENT}">
                                    <div class="float-right"><h4><label class="badge badge-info"
                                                                        id="orderStatusLabel${order.orderId}"
                                                                        style="margin-left: 10px"> ${order.orderStatus}</label>
                                    </h4></div>
                                </c:if>
                                <c:if test="${order.orderStatus==OrderStatus.SHIPPED}">
                                    <div class="float-right"><h4><label class="badge badge-primary"
                                                                        id="orderStatusLabel${order.orderId}"
                                                                        style="margin-left: 10px"> ${order.orderStatus}</label>
                                    </h4></div>
                                </c:if>

                                <div class="float-right"><h4>
                                    <label ${order.paymentStatus==PaymentStatus.WAITING ? 'class="badge badge-info"': 'class="badge badge-success"' }
                                            id="paymentStatusLabel${order.orderId}"> ${order.paymentStatus}</label></h4>
                                </div>

                                <span><strong>Payment method:</strong></span>
                                <span class="label label-info">${order.paymentMethod} </span><br>
                                <span><strong>Delivery method:</strong></span>
                                <span class="label label-info">${order.deliveryMethod} </span><br>
                                <span><strong>Total:</strong></span>
                                <span class="label label-info">$${order.amount} </span><br>
                                <div style="margin-bottom: 5px">
                                    <div style="margin-top: 10px; width: 220px; margin-bottom: 10px">
                                        <div style="margin-bottom: 10px">
                                            <select id="orderStatus${order.orderId}" class="form-control">
                                                <c:forEach var="OS" items="${orderStatusList}">
                                                    <option value="${OS}" ${OS == order.orderStatus ? 'selected="selected"' : ''}>${OS}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <select id="paymentStatus${order.orderId}" class="form-control">
                                            <c:forEach var="PS" items="${paymentStatusList}">
                                                <option value="${PS}" ${PS == order.paymentStatus ? 'selected="selected"' : ''}>${PS}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <button id="repeatOrder" class="btn btn-outline-dark btn-sm pull-bottom"
                                            onclick="changeStatus(${order.orderId})">Change
                                    </button>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <small><i>Order made by ${order.user.login} on ${order.date}</i></small>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <nav aria-label="Page navigation" style="margin-top: 10px">
        <ul class="pagination justify-content-center">
            <li ${pageId==1 ? 'class="page-item disabled"' : 'class="page-item"'}><a class="page-link"
                                                                                     href="/editOrders/${pageId-1}?fromDate=${fromDate}&toDate=${toDate}">Previous</a>
            </li>
            <c:forEach var="i" begin="1" end="${pageSize}">
                <li ${i==pageId ? 'class="page-item active"' : 'class="page-item"'}><a class="page-link"
                                                                                       href="/editOrders/${i}?fromDate=${fromDate}&toDate=${toDate}">${i}</a>
                </li>
            </c:forEach>
            <li ${pageId==pageSize ? 'class="page-item disabled"' : 'class="page-item"'}><a class="page-link"
                                                                                            href="/editOrders/${pageId+1}?fromDate=${fromDate}&toDate=${toDate}">Next</a>
            </li>
        </ul>
    </nav>
</div>
</body>
</html>
