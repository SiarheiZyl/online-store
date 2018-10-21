<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login Page</title>
</head>
<body>

<div style="margin:10px">
  <h2>${STATUS_MESSAGE}</h2>
    <form action="loginProcess" method="post">

        User Name : <input type="text" id="userName" name="username"/><br/>

        Password : <input type="password" id="password" name="password"/><br/>

        <input type="submit" value="Login" />

    </form>
    <a href="register">Not registred yet?</a>
</div>

</body>
</html>