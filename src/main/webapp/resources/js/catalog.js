function addItem(itemId){
    $.ajax({
        type:'GET',//тип запроса
        data:{itId: itemId},//параметры запроса
        url:"/addItemToOrderProcess" ,//url адрес обработчика
        success: function (res) {
            $("#availible"+itemId).html("Availible count: "+res);
        }//возвращаемый результат от сервера
    });
}