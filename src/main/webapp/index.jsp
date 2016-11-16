<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/cityCon/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/cityCon/css/style.css">
        <title>CityCon</title>
    </head>
<%@ include file="html/header.html" %>
    <body>
        <div>
            <p> Здесь должна быть ознакомительная информация:</p>
            <p> О чем этот сайт? Зачем он нужен? и т.д...</p>
            <p> также кнопка Sign In (Войти)</p>
            <p> и кнопка Sign Up (Зарегистрироваться)</p>
        </div>
        
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">        
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Login</h3>
				</div>
					<div class="panel-body">
					<fieldset>
						<div class="form-group">
						<form action="/cityCon/signin">
            				<button class="btn btn-primary btn-lg btn-block">Sign In</button>
        				</form>
						</div>
						<div class="form-group">
        				<form action="/cityCon/signup">
            				<button class="btn btn-primary btn-lg btn-block">Sign Up</button>
        				</form>
        				</div>
        			</fieldset>
        			</div>
    			
			</div>
		</div>
	</div>
</div>
        


        <div>
            <br/>
            <p> В дальнейшем, будет просматриваться Сессия?Куки?</p>
            <p> и если мы уже вводили логин и пароль,</p>
            <p> то сразу будем перенаправлятся на admin.jsp или city.jsp</p>
        </div>



        <!--
        <form align="center" method="post" action="autorization">
            <p><input name="login" type="text" placeholder="Enter your login"></p>
            <p><input name="password" type="text" placeholder="Enter your password"></p>
            <p><input type="submit" value="Enter"></p>
        </form>
        <p align="right"><a href="signUp.jsp">registration</a></p>
        -->
<%@ include file="html/footer.html" %>
    </body>
</html>
