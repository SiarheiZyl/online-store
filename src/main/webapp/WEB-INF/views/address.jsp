<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Siarhei
  Date: 29.10.2018
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Address</title>
</head>
<body>
<form:form id="addressForm" modelAttribute="address" action="addressProcess" method="post">
    <table align="center">

        <tr>
            <td>
                <form:hidden path="addressId" name="addressId" id="addressId"  />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="country">Country</form:label>
            </td>
            <td>
                <form:input path="country" name="country" id="country" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="town">Town</form:label>
            </td>
            <td>
                <form:input path="town" name="town" id="town" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="zipCode">ZipCode</form:label>
            </td>
            <td>
                <form:input path="zipCode" name="zipCode" id="zipCode" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="street">Street</form:label>
            </td>
            <td>
                <form:input path="street" name="street" id="street" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="building">Building</form:label>
            </td>
            <td>
                <form:input path="building" name="building" id="building" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="apartment">Apartment</form:label>
            </td>
            <td>
                <form:input path="apartment" type="apartment" name="apartment" id="apartment" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <form:button id="address" name="adress">Save</form:button>
            </td>
        </tr>
        <tr></tr>
        <tr>
            <td></td>
            <td><a href="user/${id}">UserInfo</a>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
