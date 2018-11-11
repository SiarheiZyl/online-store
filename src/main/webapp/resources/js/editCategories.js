function addCategory(){
    $.ajax({
        type:'POST',//тип запроса
        data:{categName: $("#categName").val()},//параметры запроса
        url:"/addNewCategoryProcess" ,//url адрес обработчика
        success: function (res) {
            $('#itemCateg').append("<option value="+res +">"+res+"</option>");
            $('#categName').val('');
            $('#categName').attr("placeholder", "New category");
        }//возвращаемый результат от сервера
    });
}

function addItem(){
    $.ajax({
        type:'POST',//тип запроса
        data:{itemName: $("#item").val(),
            itemCateg: $("#itemCateg").val(),
            author: $("#author").val(),
            country: $("#country").val(),
            height: $("#height").val(),
            width: $("#width").val(),
            avalCount: $("#avalCount").val(),
            price: $("#price").val()},//параметры запроса
        url:"/addNewItemProcess" ,//url адрес обработчика
        success: function (res) {
            $('#item').val('');
            $('#author').val('');
            $('#country').val('');
            $('#height').val('');
            $('#width').val('');
            $('#avalCount').val('');
            $('#price').val('');
        }
    });
}