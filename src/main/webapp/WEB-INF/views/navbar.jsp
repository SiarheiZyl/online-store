<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-md navbar-dark bg-dark sidebarNavigation" data-sidebarClass="navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand fa fa-shopping-cart" href="/items">Store</a>
        <button class="navbar-toggler leftNavbarToggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
                aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExampleDefault"style="align-items: right">
            <ul class="nav navbar-nav nav-flex-icons ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/bucket" >Bucket
                        <span class="glyphicon glyphicon-shopping-cart  " ></span>
                    </a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/login" >Log out
                        <span class="glyphicon glyphicon-log-in" ></span>
                    </a>
                </li>
            </ul>

        </div>
    </div>
</nav>