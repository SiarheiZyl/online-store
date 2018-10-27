<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.online_market.entity.enums.PaymentMethod"%>
<%@page import="com.online_market.entity.enums.DeliveryMethod"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>
</head>
<body>
<form:form id="addItemForm" modelAttribute="order" action="/user/${id}/items/${itemId}/addItemProcess" method="post"  >
    <table align="center">
        <tr>
            <td>
                <form:label path="paymentMethod">PaymentMethod</form:label>
            </td>
            <td>
                <form:select path="paymentMethod">
                    <form:option value="NONE" label="--- Select ---" />
                    <form:options items="${paymentList}"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="deliveryMethod">DeliveryMethod</form:label>
            </td>
            <td>
                <form:select path="deliveryMethod">
                    <form:option value="NONE" label="--- Select ---" />
                    <form:options items="${deliveryList}" />
                </form:select>
            </td>
        </tr>

        <tr>
            <td></td>
            <td>
                <form:button id="order" name="order">Order</form:button>
            </td>
        </tr>
        <tr></tr>
        <tr>
            <td></td>
            <td><a href="../">Log in</a>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
