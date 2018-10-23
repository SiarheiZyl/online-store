<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Items</title>
</head>
<body>
<h3>Items</h3>
<c:forEach var="item" items="${itemList}">
    <tr>
        <td>${item.itemId}</td>
        <td>${item.itemName}</td>
        <td>${item.price}</td>
        <td>${item.weight}</td>
        <td>${item.availableCount}</td>
        <td>${item.picture}</td>
        <td>${item.category.categoryName}</td>
    </tr>
</c:forEach>
</body>
</html>
