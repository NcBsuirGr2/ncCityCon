<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/cityCon/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/cityCon/css/style.css">
<title>List</title>
</head>
<body>
<%@ include file="/html/header.html" %>
<div class="panel panel-default">
<div class="panel-heading">List Users</div>
    <table class="table table-striped" style="table-layout: auto">
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
						<td><c:out value="${users.login}" /></td>
						<td><c:out value="${users.email}" /></td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
</div>
	
	<center> 
		<form action="users" method="GET">
			<p href="UserEditServlet?action=add">
				<button type=submit class="btn btn-primary" value="add" name="add">Add</button>
			</p>
		</form>
		 
		<form action="city" method="GET"> 
			<p href="UserEditServlet?action=edit&name=<c:out value="${users.name}"/>">
				<button type=submit class="btn btn-primary" value="edit" name="edit">Edit</button>
			</p>
    	</form>		
		
		<form action="city" method="DELETE">
			<p href="UserEditServlet?action=delete&name=<c:out value="${users.name}"/>">
				<button type=submit class="btn btn-primary" value="delete" name="delete">Delete</button>
			</p>
    	</form>		
		
	</center>
		
<%@ include file="/html/footer.html" %>
</body>
</html>

