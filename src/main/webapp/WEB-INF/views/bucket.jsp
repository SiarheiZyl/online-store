<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.online_market.entity.enums.PaymentMethod"%>
<%@page import="com.online_market.entity.enums.DeliveryMethod"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>
    <jsp:include page="layout.jsp"/>
</head>
<body>
<form:form id="addOrderForm" modelAttribute="order" action="/user/${id}/orderProcess" method="post"  >
    <table align="center">

        <c:forEach var="item" items="${itemMap}">
            <tr>
                <td>${item.key.itemName}</td>
                <td>${item.key.price}</td>
                <td>${item.key.weight}</td>
                <td>${item.key.availableCount}</td>
                <td>${item.key.picture}</td>
                <td>${item.key.category.categoryName}</td>
                <td>quantity: ${item.value}</td>
            </tr>
        </c:forEach>
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

        <tr>
            <td></td>
            <td><a href="/user/${id}/items">ItemList</a>
            </td>
        </tr>
        <tr></tr>
    </table>
</form:form>
</body>
</html>
