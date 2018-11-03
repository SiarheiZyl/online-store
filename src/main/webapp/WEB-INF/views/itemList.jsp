<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Items</title>
    <jsp:include page="layout.jsp"/>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">

    <div class="row">
        <div class="col-lg-3">
            <h1 class="my-4"><i>Filtering</i></h1>
            <form:form modelAttribute="params" action="/user/${id}/filterItems" method="get" >
            <div class="list-group">
                <div class="form-group">
                <form:select  path="author" class="form-control">
                    <form:option value=""> --SELECT AUTHOR--</form:option>
                    <form:options items="${authors}"/>
                </form:select>
                </div>
                <div class="form-group">
                <form:select  path="country" class="form-control">
                    <form:option value=""> --SELECT COUNTRY--</form:option>
                    <form:options items="${countries}"/>
                </form:select>
                </div>
                <label>MaxWidth</label>
                <form:input path="width" name="width" type="number" id="width" class="form-control" />
                <div class="form-group">
                <label>MaxHeight</label>
                <form:input path="height" name="height" type="number" id="height"  class="form-control"/>
                </div>
                <div class="form-group">
                    <form:button id="filter" class="btn btn-primary btn-block">Filter</form:button>
                </div>
            </div>
            </form:form>
        </div>

        <div class="col-lg-9" style="margin-top: 5%">

            <div class="row">

                <table>
                    <c:forEach items="${itemList}" var="item" varStatus="rowCounter">
                        <form:form id="addItemForm" modelAttribute="item" action="items/${item.itemId}/addItemToOrderProcess" method="post"  >
                        <c:if test="${rowCounter.count % 3 == 1}">
                            <tr>
                        </c:if>
                        <form:form id="addItemForm" modelAttribute="item" action="items/${item.itemId}/addItemToOrderProcess" method="post"  >
                        <div class="col-lg-4 col-md-6 mb-4">
                            <div class="card" style="height: 430px;">
                                <a href="#"><img class="card-img-top" src="http://placehold.it/700x400" alt=""></a>
                                <div class="card-body ">
                                    <h4 class="card-title">${item.itemName}</h4>
                                    <h5>$${item.price}</h5>
                                    <p class="card-text">Category: ${item.category.categoryName}<br>Availible count: ${item.availableCount}</p>
                                </div>
                                <div class="card-footer">
                                    <form:button id="order" class="btn btn-primary btn-block">Order</form:button>
                                </div>
                            </div>
                        </div>
                        </form:form>
                        <c:if test="${rowCounter.count % 3 == 0||rowCounter.count == fn:length(itemList)}">
                            </tr>
                        </c:if>
                        </form:form>
                    </c:forEach >
                </table>


            </div>
            <!-- /.row -->

        </div>



    </div>
    <!-- /.row -->

</div>
<!-- /.container -->


<%--<table align="center">
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
</table>--%>
</body>
</html>
