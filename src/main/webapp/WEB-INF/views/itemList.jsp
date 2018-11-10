<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Items</title>
    <jsp:include page="layout.jsp"/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style>
        img:active {
            -webkit-transform: scale(2);
            transform: scale(2);
            z-index: 3;
        }
    </style>
    <script>
function addItem(itemId){
                $.ajax({
                    type:'GET',
                    data:{itId: itemId},
                    url:"/addItemToOrderProcess" ,
                    success: function (res) {
                        $("#availible"+itemId).html("Availible count: "+res);
                    }
                });
}
    </script>
</head>
<body>

<jsp:include page="navbar.jsp"/>
<div class="container">

    <div class="row">
        <div class="col-lg-2">
            <h1 class="my-4"><i>Filtering</i></h1>
            <form:form modelAttribute="params" action="/filterItems/${category}" method="get" >
            <div class="list-group">
                <div class="form-group">
                <form:select  path="author" class="form-control">
                    <form:option value=""> --AUTHOR--</form:option>
                    <form:options items="${authors}"/>
                </form:select>
                </div>
                <div class="form-group">
                <form:select  path="country" class="form-control">
                    <form:option value=""> --COUNTRY--</form:option>
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

        <div class="col-lg-10" style="margin-top: 5%">

            <div class="row">

                <table>


                    <c:forEach items="${itemList}" var="item" varStatus="rowCounter">

                        <c:if test="${rowCounter.count % 3 == 1}">
                            <tr>
                        </c:if>
                        <div class="col-lg-4 col-md-6 mb-4">
                            <div class="card" style="height: 530px;">
                                <img class="card-img-top img-fluid img-thumbnail" width="300" height="200" src=/resources/images/${item.itemId}.jpg style="cursor:zoom-in;" >
                                <div class="card-body ">
                                    <h4 class="card-title"><strong>${item.itemName}</strong></h4>
                                    <h5><strong><i>$${item.price}</i></strong></h5>
                                    <p class="card-text-bottom">Category: ${item.category.categoryName}<br>Author: ${item.params.author}<br>Size: ${item.params.height}x${item.params.width}<br>
                                    <label id="availible${item.itemId}">Availible count: ${item.availableCount}</label>
                                    </p>
                                </div>
                                <div class="card-footer">
                                    <button onclick="addItem(${item.itemId})" class="btn btn-outline-dark btn-block">Buy</button>
                                </div>
                            </div>
                        </div>

                        <c:if test="${rowCounter.count % 3 == 0||rowCounter.count == fn:length(itemList)}">
                            </tr>
                        </c:if>
                    </c:forEach >
                </table>
            </div>
            <!-- /.row -->
        </div>
    </div>
    <!-- /.row -->
</div>
<!-- /.container -->
</body>
</html>
