<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="/js/selectEntity.js"></script>
	<script type="text/javascript" src="/js/userList.js"></script>

	<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">

	<title>List</title>
</head>
<body>
<%@ include file="/html/header.html" %>
<div class="panel panel-default">
<div class="panel-heading">List Users</div>
    <table class="selectable table table-striped" style="table-layout: auto">
     		<thead>
				<tr>
					<th>Name</th>
			   		<th>Login</th>
			        <th>E-mail</th>
					
        		</tr>
        	</thead>
        	<tbody>
				<c:forEach items="${entityArray}" var="users">
					<tr>
						<td><c:out value="${users.name}" /></td>
						<td class="unique"><c:out value="${users.login}" /></td>
						<td><c:out value="${users.email}" /></td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	<div class="panel-footer">
		<div class="row">

			<div class="col-sm-4">
				<a href="/user?action=add">
					<button class="btn btn-primary">Add</button>
				</a>
			</div>

			<div class="col-sm-4"> 
				<a class="editHref" href="#"> 
					<button class="btn btn-primary">Edit</button>
				</a>
			</div>

			<div class="col-sm-4">
				<form action="/user" method="DELETE">
					<input type="hidden" class="deleteButton" name="login" value="">
					<input type=submit class="btn btn-primary" value="Delete"></input>
		    	</form>		
			</div>

		</div>
	</div>
</div>
	
		
		
<%@ include file="/html/footer.html" %>
</body>
</html>

