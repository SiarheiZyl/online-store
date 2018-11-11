function removeItem(itemId, quantity, price){
    $.ajax({
        type:'POST',//тип запроса
        data:{itemId: itemId,
            quantity: quantity},//параметры запроса
        url:"/deleteProcess" ,//url адрес обработчика
        success: function (res) {

            $("#totalPrice").html("Total price:"+"$<b id=\"sum\">"+(Number($("#sum").text())-Number(price))+"<b>");
            $("#row"+itemId).remove();
        }//возвращаемый результат от сервера
    });
}
