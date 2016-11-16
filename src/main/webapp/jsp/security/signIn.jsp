<%--
  User: dima
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/html/header.html" %>
<!--%@ include file="html/header.html" %>-->
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>CityCon Login</title>
    <meta name="generator" content="Bootply" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link href="/cityCon/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="/cityCon/css/style_loginForm.css" rel="stylesheet" type="text/css">
</head>
<body>

<!--
<c:if test="${param.errorType}">
  <b>Incorrect information</b>
</c:if>
<form action="SignInServlet" method="get" id="signIn">
-->

    <!--login modal-->
    <div id="loginModal" > <!--class="modalshow"  tabindex="-1" role="dialog" aria-hidden="true"-->
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>-->
                    <h1 class="text-center">Sign in to CityCon</h1>
                </div>

                <div class="modal-body">
                    <form class="form col-md-12 center-block " method="post" action="/cityCon/signin">
                        <div class="form-group">
                            <input type="text" class="form-control input-lg" placeholder="Login" name="login">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control input-lg" placeholder="Password" name="password">
                        </div>
                        <div class="form-group">
                            <input type="submit" class="btn btn-primary btn-lg btn-block" value="Sign In">
                            <span class="pull-right"><a href="/cityCon/jsp/security/signUp.jsp">Sign Up</a></span>
                        </div>
                    </form>
                </div>


                <div class="modal-footer">
                    <div class="col-md-12">
                        <button class="btn" data-dismiss="modal" aria-hidden="true" onclick="location.href='/cityCon/'">Cancel</button>
                    </div>
                </div>


            </div>
        </div>
    </div>

    <div>
        <p> Чтобы попасть на admin.jsp введите логин admin</p>
        <p> Чтобы попасть на city.jsp введите логин operator</p>
        <p> Чтобы попасть на страницу регистрации нажать кнопку registration</p>
        <p> Чтобы вернутся на главную страницу нажмите на иконку сверху</p>
    </div>







    <!-- script references -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>


</body>
</html>
<!--%@ include file="/html/footer.html" %>-->
<%@ include file="/html/footer.html" %>

