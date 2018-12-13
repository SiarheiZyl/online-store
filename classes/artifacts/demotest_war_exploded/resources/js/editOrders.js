function changeStatus(orderId){
    $.ajax({
        type:'POST',
        data:{orderId: orderId,
            orderStatus: $("#orderStatus"+orderId).val(),
            paymentStatus: $("#paymentStatus"+orderId).val() },
        url:"/editOrdersProcess" ,
        success: function (res) {
            $("#orderStatusLabel"+orderId).removeClass();
            if(res == 0)
                $("#orderStatusLabel"+orderId).addClass("badge badge-warning");
            if(res == 1)
                $("#orderStatusLabel"+orderId).addClass("badge badge-info");
            if(res == 2)
                $("#orderStatusLabel"+orderId).addClass("badge badge-primary");
            if(res == 3)
                $("#orderStatusLabel"+orderId).addClass("badge badge-success");
            $("#orderStatusLabel"+orderId).html($("#orderStatus"+orderId).val());

            $("#paymentStatusLabel"+orderId).removeClass();
            if($("#paymentStatus"+orderId).text()=="WAITING")
                $("#paymentStatusLabel"+orderId).addClass("badge badge-info")
            else
                $("#paymentStatusLabel"+orderId).addClass("badge badge-success");
            $("#paymentStatusLabel"+orderId).html($("#paymentStatus"+orderId).val());
        }
    });
}

function  filterDate() {

    $.ajax({
        type:'GET',
        data:{fromDate: $("#fromDate").val(),
            toDate: $("#toDate").val()},
        url:"/editOrders/1"
    });
}