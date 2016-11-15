<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="html/header.html" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>List</title>
</head>
<body>

    <h1 align="center">Create your personal account</h1>
    <form align="center" method="post" action="SignUp" >
        <p><input name="login" type="text" placeholder="Enter your login"></p>
        <p><input name="password" type="text" placeholder="Enter your password"></p>
        <p><input name="E-mail" type="email" placeholder="Enter your email"></p>
        <p><input name="name" type="text" placeholder="Enter your user name"></p>
        <p><input type="submit" value="Enter"></p>
    </form>

    <div>
        <p>Заполняем данные и попадаем на страницу city.jsp</p>
        <p>т.к. аккаунт админа мы все равно создать не сможем</p>
    </div>

</body>
</html>
<%@ include file="html/footer.html" %>

