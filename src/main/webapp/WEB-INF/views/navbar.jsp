<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.online_market.entity.enums.Roles" %>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
      integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
<nav class="navbar navbar-expand-md navbar-dark bg-dark sidebarNavigation" data-sidebarClass="navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand fa fa-shopping-cart fa-lg" href="/catalog/1"><span class="display-5"> Store</span></a>
        <button class="navbar-toggler leftNavbarToggler" type="button" data-toggle="collapse"
                data-target="#navbarsExampleDefault"
                aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExampleDefault" style="align-items: right">
            <ul class="nav navbar-nav nav-flex-icons ml-auto">
                <c:if test="${role==Roles.USER}">
                    <li class="nav-item active">
                        <a class="nav-link" href="/orderHistory/1">Order history</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="/user/${id}">
                            <span class="fa fa-user " title="Profile" style="font-size:24px"></span>
                        </a>
                    </li>

                </c:if>
                <c:if test="${role==Roles.ADMIN}">

                    <li class="nav-item active">
                        <a class="nav-link" href="/editOrders/1"><span class="display-5">Edit orders</span></a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="/editCategories">
                            <span class="fas fa-edit" title="Edit categories" style="font-size:24px"></span>
                        </a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="/statistics">
                            <span class="fas fa-chart-bar" title="Statistics" style="font-size:24px"></span>
                        </a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="/user/${id}">
                            <span class="fas fa-user" title="Profile" style="font-size:24px"></span>
                        </a>
                    </li>
                </c:if>
                <li class="nav-item active">
                    <a class="nav-link" href="/search">
                        <span class="fas fa-search" title="Search" style="font-size:24px"></span>
                    </a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/bucket">
                        <span class="fa fa-shopping-basket" title="Basket" style="font-size:24px"></span>

                        <span class='badge badge-warning ' id='lblCartCount' style="font-size: 12px;background: #ff0000;color: #fff;padding: 0 5px;vertical-align: top;margin-left: -10px;border-radius:15px;${numberOfItemsInBucket==0? 'display: none': 'display:'}">${numberOfItemsInBucket}</span>

                    </a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/login">
                        <span ${ id>0 ? 'class="fas fa-sign-out-alt" title = "Sign out"' : 'class="fas fa-sign-in-alt" title = "Sign in"'}style="font-size:24px"></span>
                    </a>
                </li>
            </ul>

        </div>
    </div>
</nav>