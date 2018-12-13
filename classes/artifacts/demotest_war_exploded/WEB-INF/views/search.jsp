<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.online_market.entity.enums.Roles" %>
<%--
  Created by IntelliJ IDEA.
  User: Siarhei
  Date: 01.12.2018
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
    <jsp:include page="layout.jsp"/>
    <title>Search</title>
    <script type="text/javascript">
        function addItem(itemId) {
            $.ajax({
                type: 'GET',//тип запроса
                data: {itId: itemId},//параметры запроса
                url: "/addItemToOrderProcess",//url адрес обработчика
                success: function (res) {
                    if (res !== "-1") {
                        $("#lblCartCount").html(Number($("#lblCartCount").text()) + 1).show();
                        $("#availible" + itemId).html("Availible count: " + res);
                    }
                }//возвращаемый результат от сервера
            });
        }
    </script>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="panel panel-default panel-order " style="margin-left: 5%; width: 90%">
    <h1 class="display-4">Search</h1>
    <div class="panel-body ">
        <form action="/search" method="get">
            <div class="form-row">
                <div class="col-md-4">
                    <input id="fromDate" name="searchData" class="form-control"/>
                </div>
                <div class="col-md-4">
                    <label></label>
                    <button id="period" class="btn btn-dark btn-sm pull-bottom" type="submit"
                            style="width: 100px;height: 40px;"> Find
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="row" id="items" style="margin-left: 5%; margin-right: 5%">
    <c:if test="${items!=null}">
        <c:forEach items="${items}" var="item" varStatus="rowCounter">
            <div class="col-md-3  mb-3">
                <div class="card" style="height: 550px;">
                    <img class="card-img-top img-fluid img-thumbnail" width="300" height="200"
                         src="/image/${item.itemId}" alt="${item.itemName}" style="cursor:zoom-in;">

                    <div class="card-body ">
                        <a href="/item/${item.itemId}" style="color: black;">
                            <h4 class="card-title"><strong>${item.itemName}</strong></h4>
                            <h5><strong><i>$${item.price}</i></strong></h5>
                            <p class="card-text-bottom">
                                Category: ${item.category.categoryName}<br>Author: ${item.params.author}<br>Size: ${item.params.height}x${item.params.width}<br>
                                <label id="availible${item.itemId}">Availible count: ${item.availableCount}</label>
                            </p>
                        </a>
                    </div>

                    <div class="card-footer">
                        <c:if test="${role==Roles.ADMIN}">
                            <div class="row">
                                <div class="col-md-6">
                                    <button onclick="addItem(${item.itemId})"
                                            class="btn btn-outline-dark btn-block" ${item.availableCount==0 ? 'disabled = "disabled"':''}>
                                        Buy
                                    </button>
                                </div>
                                <div class="col-md-6">
                                    <a href="/item/${item.itemId}" class="btn btn-outline-dark btn-block">Edit</a>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${role!=Roles.ADMIN}">
                            <button onclick="addItem(${item.itemId})"
                                    class="btn btn-outline-dark btn-block" ${item.availableCount==0 ? 'disabled = "disabled"':''}>
                                Buy
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>
</body>
</html>
