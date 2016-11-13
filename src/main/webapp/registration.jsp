<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>List</title>
    </head>
    <body>
        <%@ include file="html/header.html" %>
        <h1 align="center">Registration form</h1>
        <form align="center" method="post" action="registration" >
            <p><input name="login" type="text" placeholder="Enter your login"></p>
            <p><input name="password" type="text" placeholder="Enter your password"></p>
            <p><input name="E-mail" type="email" placeholder="Enter your email"></p>
            <p><input type="submit" value="Enter"></p>
        </form>

        <%@ include file="html/footer.html" %>
    </body>
</html>

