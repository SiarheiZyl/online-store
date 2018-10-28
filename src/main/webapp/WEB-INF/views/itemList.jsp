<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Items</title>
</head>
<body>
<h3>Items</h3>
<%--<c:forEach var="item" items="${itemList}">
    <tr>
        <td>${item.itemId}</td>
        <td>${item.itemName}</td>
        <td>${item.price}</td>
        <td>${item.weight}</td>
        <td>${item.availableCount}</td>
        <td>${item.picture}</td>
        <td>${item.category.categoryName}</td>
        <td>
            <form:form id="addItemForm" modelAttribute="order" action="/items/${item.itemId}/addItemToOrderProcess" method="post"  >
            <a href="items/${item.itemId}/add">+</a>
            </form:form>
        </td>


    </tr>
</c:forEach>--%>


 <c:forEach var="item" items="${itemList}">
     <form:form id="addItemForm" modelAttribute="item" action="items/${item.itemId}/addItemToOrderProcess" method="post"  >
        <table align="center">
            <tr>
                <td>
                    <form:label path="itemName">${item.itemName}</form:label>
                </td>

                <td>
                    <form:label path="price">${item.price}</form:label>
                </td>

                <td>
                    <form:label path="weight">${item.weight}</form:label>
                </td>

                <td>
                    <form:label path="availableCount">${item.availableCount}</form:label>
                </td>

                <td>
                    <form:label path="picture">${item.picture}</form:label>
                </td>

                <td>
                    <form:label path="category">${item.category.categoryName}</form:label>
                </td>

                <td>
                    <form:button id="order" name="order">Order</form:button>
                </td>
            </tr>

        </table>
     </form:form>
</c:forEach>

</body>
</html>
