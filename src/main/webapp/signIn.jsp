<%--
  User: dima
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--%@ include file="html/header.html" %>-->
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>CityCon Login</title>
    <meta name="generator" content="Bootply" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="css/style_loginForm.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="header header-logged-out width-full pt-5 pb-4" role="banner">
        <div class="container clearfix width-full">
            <a class="header-logo" href="index.jsp" aria-label="Homepage"
               data-ga-click="(Logged out) Header, go to homepage, icon:logo-wordmark">
                <svg aria-hidden="true" class="octicon octicon-mark-github" height="48" version="1.1" viewBox="0 0 16 16" width="48">
                    <a href="#"><img alt="logo" id="menu" src="img/logo.png"/></a>
                </svg>
            </a>
        </div>
    </div>


    <!--login modal-->
    <div id="loginModal" > <!--class="modalshow"  tabindex="-1" role="dialog" aria-hidden="true"-->
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>-->
                    <h1 class="text-center">Sign in to CityCon</h1>
                </div>

                <div class="modal-body">
                    <form class="form col-md-12 center-block " method="post" action="SignIn">
                        <div class="form-group">
                            <input type="text" class="form-control input-lg" placeholder="Login" name="login">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control input-lg" placeholder="Password" name="password">
                        </div>
                        <div class="form-group">
                            <button class="btn btn-primary btn-lg btn-block">Sign In</button>
                            <span class="pull-right"><a href="signUp.jsp">Register</a></span><span><a href="#">Need help?</a></span>
                        </div>
                    </form>
                </div>


                <div class="modal-footer">
                    <div class="col-md-12">
                        <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
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
<!--%@ include file="html/footer.html" %>-->

