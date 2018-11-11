$(function() {
    /*  Submit form using Ajax */
    $('button[type=submit]').click(function(e) {

        //Prevent default submission of form
        e.preventDefault();

        //Remove all errors
        $('input').next().remove();

        $.post({
            url : '/updateUser',
            data : $('form[name=infoForm]').serialize(),
            success: function (res) {
                $("#password").attr("placeholder", "New password").val("").focus().blur();
            }
        })
    });
});