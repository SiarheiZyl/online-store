<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>
    <jsp:include page="layout.jsp"/>
    <link href="<c:url value='../../resources/css/registration.css' />" rel="stylesheet">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <header class="card-header">
                <a href="login" class="float-right btn btn-outline-dark mt-1">Log in</a>
                <h4 class="card-title mt-2">Sign up</h4>
            </header>
            <article class="card-body">
                <form:form id="regForm" modelAttribute="user" action="registerProcess" method="post" var="Placeholder">
                    <div class="form-row">
                        <div class="col form-group">
                            <label>First name </label>
                            <form:input path="firstName" name="firstName" id="firstName" class="form-control"
                                        data-validation="text" required="required"
                                        data-validation-error-msg="You did not enter first name"/>
                        </div> <!-- form-group end.// -->
                        <div class="col form-group">
                            <label>Last name</label>
                            <form:input path="lastName" name="lastName" id="lastName" class="form-control"
                                        data-validation="text" required="required"
                                        data-validation-error-msg="You did not enter last name"/>
                        </div> <!-- form-group end.// -->
                    </div>

                    <div class="form-group">
                        <label>Username</label>
                        <h6 style="color: red">${LOGIN_MESSAGE}</h6>
                        <form:input path="login" name="login" id="username" class="form-control" data-validation="text"
                                    required="required" data-validation-error-msg="You did not enter username"
                                    minlength="5"/>
                    </div>

                    <div class="form-group">
                        <label>Email address</label>
                        <form:input path="email" name="email" id="email" type="email"
                                    pattern=" /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/"
                                    data-validation="email" required="required"
                                    data-validation-error-msg="You did not enter a valid e-mail" class="form-control"/>
                        <small class="form-text text-muted">We'll never share your email with anyone else.</small>
                    </div>

                    <div class="form-group">
                        <label>Birthdate</label>
                        <form:input path="birthdate" type="date" name="birthdate" id="birthdate" class="form-control"
                                    data-validation="date" required="required"/>
                    </div>

                    <div class="form-group">
                        <label>Create password</label>
                        <form:password path="password" name="password" id="password" class="form-control"
                                       required="required" minlength="4"/>
                    </div>

                    <div class="form-group">
                        <form:button id="register" name="register"
                                     class="btn btn-dark btn-block">Register</form:button>
                    </div>
                    <small class="text-muted">By clicking the 'Sign Up' button, you confirm that you accept our <br>
                        Terms of use and Privacy Policy.
                    </small>
                </form:form>
                <script>
                    $.validate({
                        borderColorOnError: '#c80e0e',
                        addValidClassOnAll: true,
                        lang: 'en',
                        validateOnBlur: false, // disable validation when input looses focus
                        errorMessagePosition: 'top', // Instead of 'inline' which is default
                        scrollToTopOnError: false // Set this property to true on longer forms
                    });
                </script>
            </article>
            <div class="border-top card-body text-center">Have an account? <a href="login">Log In</a></div>
        </div>
    </div>
</div>
</body>
</html>