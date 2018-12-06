<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%@page import="com.online_market.entity.enums.OrderStatus" %>
<%@page import="com.online_market.entity.enums.PaymentStatus" %>
<%@page import="com.online_market.utils.MD5Util" %>

<%@ taglib prefix="gravatar" uri="http://www.paalgyula.hu/schemas/tld/gravatar" %>


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
    <title>Statistics</title>
    <jsp:include page="layout.jsp"/>
    <link href="<c:url value='../../resources/css/orderHistory.css' />" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="panel panel-default panel-order ">
    <div class="panel-body">
        <div class="row" style="margin-left: 10%; margin-top: 2%; margin-right: 10%">
            <div class="col-md-4">
                <h3 class="display-5">Day: <span style="color:darkred;">$${incomeMap.get("day")}</span></h3>
            </div>
            <div class="col-md-4">
                <h3 class="display-5">Week: <span style="color:darkgreen;">$${incomeMap.get("week")}</span></h3>
            </div>
            <div class="col-md-4">
                <h3 class="display-5 pull-right">Month: <span
                        style="color:darkslateblue;">$${incomeMap.get("month")}</span></h3>
            </div>
        </div>
        <div class="row" style="margin-left: 10%; margin-top: 2%;">
            <div class="col-md-6">
                <div class="row">
                    <h2 class="display-4">Top Users</h2>
                </div>
                <c:forEach var="user" items="${topUsers}">
                    <div class="row" style="margin-top: 10px">
                        <div class="col-md-2">
                            <img src="http://www.gravatar.com/avatar/${MD5Util.md5Hex(user.key.email)}"
                                 class="media-object img-thumbnail">
                        </div>
                        <div class="col-md-4">
                            <i>${user.key.firstName} ${user.key.lastName}</i><br>
                            <strong>Login: </strong><i>${user.key.login}</i><br>
                            <strong>Total: </strong><i>$${user.value}</i>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="col-md-6 pull-right">
                <div class="row">
                    <h2 class="display-4">Top Paintings</h2>
                </div>
                <c:forEach var="item" items="${topItems}">
                    <div class="row" style="margin-top: 10px">
                        <div class="col-md-2">
                            <a href="/item/${item.key.itemId}" target="_blank"> <img src="/smallImage/${item.key.itemId}"
                                                                                     alt="${item.key.itemName}"
                                                                                     class="media-object img-thumbnail"></a>
                        </div>
                        <div class="col-md-6">
                            <strong><i>${item.key.itemName}</i></strong><br>
                            <strong>Author: </strong><i>${item.key.params.author}</i><br>
                            <strong>Total number of orders: </strong><i>${item.value}</i>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>