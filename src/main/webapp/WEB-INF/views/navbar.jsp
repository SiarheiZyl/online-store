<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.online_market.entity.enums.Roles"%>
<nav class="navbar navbar-expand-md navbar-dark bg-dark sidebarNavigation" data-sidebarClass="navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand fa fa-shopping-cart fa-lg" href="/catalog"> Store</a>
        <button class="navbar-toggler leftNavbarToggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
                aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExampleDefault"style="align-items: right">
            <ul class="nav navbar-nav nav-flex-icons ml-auto">
                <c:if test="${user.role==Roles.USER}">
                    <li class="nav-item active">
                        <a class="nav-link" href="/orderHistory" >Order history
                            <span class="glyphicon glyphicon-log-in" ></span>
                        </a>
                    </li>
                </c:if>
                <c:if test="${user.role==Roles.ADMIN}">
                    <li class="nav-item active">
                        <a class="nav-link" href="/user/${id}/editOrders" >Edit orders</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="/user/${id}/statistics" >Statistics</a>
                    </li>
                </c:if>
                <li class="nav-item active">
                    <a class="nav-link" href="/bucket" >
                        <span class="fa fa-shopping-basket "style="font-size:24px" ></span>
                    </a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/login" >
                        <span class="fa fa-sign-out "style="font-size:24px" ></span>
                    </a>
                </li>
            </ul>

        </div>
    </div>
</nav>