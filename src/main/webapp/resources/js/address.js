$.validate({
    borderColorOnError: '#c80e0e',
    addValidClassOnAll: true,
    lang: 'en',
    validateOnBlur: false, // disable validation when input looses focus
    errorMessagePosition: 'top', // Instead of 'inline' which is default
    scrollToTopOnError: false // Set this property to true on longer forms
});
$(function () {
    /*  Submit form using Ajax */
    $('button[type=submit]').click(function (e) {

        //Prevent default submission of form
        e.preventDefault();

        //Remove all errors
        $('input').next().remove();

        var form = $("#addressForm").valid;
        $.post({
            url: 'addressProcess',
            data: $('form[name=addressForm]').serialize(),
            success: function (res) {
                alert("Your info was updated");
            }
        })
    });
});