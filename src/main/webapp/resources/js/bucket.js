function removeItem(itemId, quantity, price){
    $.ajax({
        type:'POST',//тип запроса
        data:{itemId: itemId,
            quantity: quantity},//параметры запроса
        url:"/deleteProcess" ,//url адрес обработчика
        success: function (res) {

            amount = (Number($("#sum").text())-Number(price));

            $("#totalPrice").html("Total price:"+"$<b id=\"sum\">"+amount+"<b>");
            $("#row"+itemId).remove();
            $("#amountT").value(amount);
        }//возвращаемый результат от сервера
    });
}
