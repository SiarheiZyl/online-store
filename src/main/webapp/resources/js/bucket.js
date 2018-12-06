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
            //$("#amountT").value(amount);

            itemsTotal = Number( $("#lblCartCount").text())-quantity;
            if(itemsTotal == 0)
                $("#lblCartCount").html(0).hide();
            else
                $("#lblCartCount").html(Number( $("#lblCartCount").text())-quantity).show()
        }//возвращаемый результат от сервера
    });
}

