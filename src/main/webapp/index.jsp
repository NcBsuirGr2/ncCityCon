<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" cяontent="text/html; charset=ISO-8859-1">
        <title>logging</title>
    </head>

    <body>
    <%@ include file="html/header.html" %>
        <form align="center" method="post" action="autorization">
            <p><input name="login" type="text" placeholder="Enter your login"></p>
            <p><input name="password" type="text" placeholder="Enter your password"></p>
            <p><input type="submit" value="Enter"></p>
        </form>
        <p align="right"><a href="registration.jsp">registration</a></p>
    <%@ include file="html/footer.html" %>
    </body>
</html>