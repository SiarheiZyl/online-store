$(function() {
    /*  Submit form using Ajax */
    $('button[type=submit]').click(function(e) {

        //Prevent default submission of form
        e.preventDefault();

        $.post({
            url : 'addressProcess',
            data : $('form[name=addressForm]').serialize()

        })
    });
});