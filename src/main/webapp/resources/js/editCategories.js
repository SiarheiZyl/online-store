function addCategory() {
    $.ajax({
        type: 'POST',//тип запроса
        data: {categName: $("#categName").val()},//параметры запроса
        url: "/addNewCategoryProcess",//url адрес обработчика
        success: function (res) {
            alert("New category was successfully added!");
            $('#itemCateg').append("<option value=" + res + ">" + res + "</option>");
            $('#categName').val('');
            $('#categName').attr("placeholder", "New category");

        }//возвращаемый результат от сервера
    });
}

