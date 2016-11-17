<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!--TODO
< %@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/html/header.html" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>CityCon Login</title>
    <meta name="generator" content="Bootply" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link href="/cityCon/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <link href="/cityCon/css/style_loginForm.css" rel="stylesheet" type="text/css">
</head>
<body>


<!--TODO
< c:if test="$ {param.errorType=dublicate}">
  <b></b>
  	< c:if test="$ {param.errorType=invalidData}">
  		<b>Incorrect information</b>
	< /c:if>
< /c:if>
-->

<form action="SignUpServlet" method="get" id="signUp">

    <div id="loginModal2" >
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <h1 class="text-center">Create your personal account</h1>
                </div>

                <div class="modal-body">
                    <form class="form col-md-12 center-block " method="post" action="/cityCon/signup">
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
                            <input type="submit" class="btn btn-primary btn-lg btn-block" value="Create an account">
                        </div>
                    </form>
                </div>

                <!--TODO: надо убрать, просто так удалить - вся форма ломается-->
                <div class="modal-footer">
                    <!--<div class="col-md-12">
                        <button class="btn" data-dismiss="modal" aria-hidden="true" onclick="location.href='/cityCon/'">Cancel</button>
                    </div> -->
                </div>


            </div>
        </div>
    </div>

</form>


    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>


</body>
</html>
<%@ include file="/html/footer.html" %>

