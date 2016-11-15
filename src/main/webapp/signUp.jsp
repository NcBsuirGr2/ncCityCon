<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="html/header.html" %>
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


<div id="loginModal" >
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h1 class="text-center">input your data</h1>
            </div>

            <div class="modal-body">
                <form class="form col-md-12 center-block " method="post" action="SignUp">
                    <div class="form-group">
                        <input type="text" class="form-control input-lg" placeholder="Login" name="login">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control input-lg" placeholder="Password" name="password">
                    </div>
                    <div class="form-group">
                        <input type="email" class="form-control input-lg" placeholder="Email" name="E-mail">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control input-lg" placeholder="name" name="name">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary btn-lg btn-block">Register</button>
                    </div>
                </form>
            </div>


            <div class="modal-footer">
                <div class="col-md-12">
                    <button class="btn" data-dismiss="modal" aria-hidden="true" onclick="location.href='/cityCon/index.jsp'">Cancel</button>
                </div>
            </div>


        </div>
    </div>
</div>


<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>


</body>
</html>
<%@ include file="html/footer.html" %>

