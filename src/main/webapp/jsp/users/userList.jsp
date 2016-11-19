<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1, maximum-scale=1">

		<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
		<script type="text/javascript" src="/js/selectEntity.js"></script>
		<script type="text/javascript" src="/js/userPages/userList.js"></script>

		<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<link rel="stylesheet" type="text/css" href="/css/users/userList.css">

		<title>Users</title>
	</head>

	<body>
		<div class="content-wrapper">
			<%@ include file="/html/header.jsp" %>
			<div class="before-footer">
				<div class="panel panel-default">
					<center class="panel-heading">
						List Users
					</center>
					    <table class="selectable table table-striped table-bordered table-hover" style="table-layout: auto">
				     		<thead>
								<tr>
									<th>Name</th>
							   		<th>Login</th>
							        <th>Group</th>
							        <th>E-mail</th>
							        <th class="hidden">id</th>								
				        		</tr>
				        	</thead>
				        	<tbody>
								<c:forEach items="${entityArray}" var="users">
									<tr>
										<td>${users.name}</td>
										<td class="unique">${users.login}</td>
										<td>${users.group}</td>
										<td>${users.email}</td>
										<td class="hidden idField">${users.id}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					<div class="panel-footer">
						<div class="row">

							<div class="col-sm-4">
								<a href="/user?action=add">
									<button class="btn btn-primary center-block">Add</button>
								</a>
							</div>

							<div class="col-sm-4"> 
								<a class="editHref" href="#"> 
									<button class="btn btn-primary center-block">Edit</button>
								</a>
							</div>

							<div class="col-sm-4">
								<form action="/user" id="deleteForm" method="POST">
									<input type="hidden" id="deleteId" name="id" value="-1">
									<input type="hidden" name="type" value="delete">
									<input type=submit class="btn btn-primary center-block" value="Delete"></input>
						    	</form>		
							</div>

						</div>
					</div>
				</div>
			</div>

			<%@ include file="/html/footer.html" %>
		</div>
	</body>
</html>

