function addItem(itemId){
    $.ajax({
        type:'GET',//тип запроса
        data:{itId: itemId},//параметры запроса
        url:"/addItemToOrderProcess" ,//url адрес обработчика
        success: function (res) {
            if(res !== "-1") {
                $("#lblCartCount").html(Number( $("#lblCartCount").text())+1).show();
                $("#availible"+itemId).html("Availible count: "+res);
            }


        }//возвращаемый результат от сервера
    });
}