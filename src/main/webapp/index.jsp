<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="html/header.html" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="/cityCon/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/cityCon/css/style.css">
	<title>CityCon</title>
</head>
<body>
	<div>
		<p> Здесь должна быть ознакомительная информация:</p>
		<p> О чем этот сайт? Зачем он нужен? и т.д...</p>
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


</body>
</html>
<%@ include file="html/footer.html" %>
