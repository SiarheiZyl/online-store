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