<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Items</title>
    <jsp:include page="layout.jsp"/>
</head>
<body>
<h3 align="center">Items</h3>

<table align="center">
 <c:forEach var="item" items="${itemList}">
     <form:form id="addItemForm" modelAttribute="item" action="items/${item.itemId}/addItemToOrderProcess" method="post"  >
            <tr>
                <td>
                    <form:label path="itemName">${item.itemName}</form:label>
                </td>

                <td>
                    <form:label path="price">${item.price}</form:label>
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
     </form:form>
</c:forEach>
    <tr>
        <td></td>
        <td><a href="/user/${id}/bucket">Bucket</a>
        </td>
    </tr>
    <tr>
        <td></td>
        <td><a href="/user/${id}">User Info</a>
        </td>
    </tr>
</table>
</body>
</html>
