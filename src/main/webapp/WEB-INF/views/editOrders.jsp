<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.online_market.entity.enums.PaymentMethod"%>
<%@page import="com.online_market.entity.enums.DeliveryMethod"%>
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
    <title>Orders</title>
</head>
<body>
<c:forEach var="order" items="${orders}">
<form:form id="editOrdersForm" modelAttribute="order" action="/user/${id}/editOrdersProcess/${order.orderId}" method="post"  >
    <table align="center">

        <tr>
<%--            <td>
                <form:hidden path="orderId" name="orderId" id="orderId"  />
            </td>--%>

            <td>
                <form:label path="orderId">${order.orderId}</form:label>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="paymentMethod">Payment Method</form:label>
            </td>
            <td>
                <form:label path="paymentMethod">${order.paymentMethod}</form:label>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="deliveryMethod">DeliveryMethod</form:label>
            </td>
            <td>
                <form:label path="deliveryMethod">${order.deliveryMethod}</form:label>
            </td>

        </tr>

        <tr>
            <td>
                <form:label path="orderStatus">OrderStatus</form:label>
            </td>

            <td>
                <form:label path="orderStatus">${order.orderStatus}</form:label>
            </td>
            <td>
                <form:select path="orderStatus">
                   <%-- <form:option value="NONE" label="--- Change ---" />--%>
                    <form:options items="${orderStatusList}" />
                </form:select>
            </td>
        </tr>

        <tr>
            <td>
                <form:label path="paymentStatus">PaymentStatus</form:label>
            </td>
            <td>
                <form:label path="paymentStatus">${order.paymentStatus}</form:label>
            </td>
            <td>
                <form:select path="paymentStatus">
                   <%-- <form:option value="NONE" label="--- Change ---" />--%>
                    <form:options items="${paymentStatusList}" />
                </form:select>
            </td>
        </tr>

        <tr>
            <td></td>
            <td>
                <form:button id="change" name="change">Change</form:button>
            </td>
        </tr>

        <tr>
            <td></td>
            <td><a href="/user/${id}">UserInfo</a>
            </td>
        </tr>
        <tr></tr>
    </table>
</form:form>
</c:forEach>
</body>
</html>
