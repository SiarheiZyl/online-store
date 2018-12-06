<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Siarhei
  Date: 18.11.2018
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link href="<c:url value='../../resources/css/pageNotFound.css' />" rel="stylesheet">
</head>
<body>
<div class="container"><span class="numer">4</span>
    <div class="circle">
        <div class="drops"></div>
        <div class="drops"></div>
        <div class="hand"></div>
        <div class="hand rgt"></div>
        <div class="holder">
            <div class="bob">
                <div class="nose"></div>
                <div class="face">
                    <div class="mouth">
                        <div class="tongue"></div>
                    </div>
                </div>
                <div class="ear"></div>
                <div class="ear rgt"></div>
                <div class="neck"></div>
            </div>
        </div>
    </div>
    <span class="numer">4</span>
    <a href="/catalog/1"
       style="position: center;height: 80px;width: 300px;font-size: 24pt; color: whitesmoke; text-decoration: none;"
       class="btn btn-primary btn-block">Back to shop</a>
</div>


</body>
</html>
