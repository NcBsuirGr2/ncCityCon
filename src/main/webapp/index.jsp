<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
	<link rel="icon" href="favicon.ico" />
	<title>CityCon</title>
</head>
<body>
	<div class="content-wrapper">
		<!-- Header -->
		<%@ include file="include/header.jsp" %>

		<div class="container before-footer">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="login-panel panel panel-default">
						<center class="panel-heading" >
							<h2 >CityCon</h2>
						</center>
						<div class="panel-body">
							<a href="/signin" class="btn btn-primary btn-block btn-lg center-block">Sign in</a>
							<br>
							<a href="/signup" class="btn btn-primary btn-block btn-lg center-block">Sign up</a>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Footer -->
		<%@ include file="include/footer.html" %>
	</div>

</body>
</html>

