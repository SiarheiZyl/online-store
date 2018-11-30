<%--
  Created by IntelliJ IDEA.
  User: Siarhei
  Date: 30.11.2018
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action="/test/upload" enctype="multipart/form-data">
    File to upload: <input type="file" name="file"><br />
    Name: <input type="text" name="name"><br /> <br />
    <input type="submit" value="Upload"> Press here to upload the file!
    <img src="/image/3">
</form>
</body>
</html>
