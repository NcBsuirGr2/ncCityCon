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
				<div class="col-md-2">
				</div>
				<div class="col-md-8">
					<div class="login-panel panel panel-default" style="width: 560px">
						<center class="panel-heading" >
							<h2>CityCon</h2>
						</center>
						<div class="panel-body">
							<iframe class="btn btn-block btn-lg center-block" width="560px" height="315px" src="https://www.youtube.com/embed/k-T4A3Wc48M" frameborder="0" allowfullscreen></iframe>
							<br>
							<a href="/signin" class="btn btn-primary btn-block btn-lg center-block">Sign in</a>
							<br>
							<a href="/signup" class="btn btn-primary btn-block btn-lg center-block">Sign up</a>
						</div>
					</div>
				</div>
				<div class="col-md-2">
				</div>
			</div>
		</div>

		<!-- Footer -->
		<%@ include file="include/footer.html" %>
	</div>

</body>
</html>

