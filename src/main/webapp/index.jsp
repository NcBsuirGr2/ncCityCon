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
	<%@ include file="html/header.jsp" %>
		<div class="before-footer">
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
									<form action="/signin">
										<button class="btn btn-primary btn-lg btn-block">Sign In</button>
									</form>
									</div>
									<div class="form-group">
									<form action="/signup">
										<button class="btn btn-primary btn-lg btn-block">Sign Up</button>
									</form>
									</div>
								</fieldset>
								</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Footer -->
		<%@ include file="html/footer.html" %>
	</div>

</body>
</html>

