<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3>Users</h3>
<c:forEach var="user" items="${userList}">
    <tr>
        <td>${user.id}</td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.birthdate}</td>
        <td>${user.email}</td>
    </tr>
</c:forEach>
</body>
</html>
