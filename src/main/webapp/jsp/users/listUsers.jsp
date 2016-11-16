<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/hltable.js"></script>
<title>Admin</title>
</head>
<%@ include file="/html/header.html" %>
<body>
<div class="container">
	<div class="login-panel panel panel-success">
		<table class="table table-bordered table-hover table-striped" style="table-layout: fixed">
     		<thead>
				<tr>
					<th>Name</th>
			   		<th>Login</th>
			        <th>E-mail</th>

        		</tr>
        	</thead>
        	<tbody>
				<c:forEach items="${entityArray}" var="user">
					<tr>
						<td><c:out value="${user.name}" /></td>
						<td><c:out value="${user.login}" /></td>
						<td><c:out value="${user.email}" /></td>

					</tr>
				</c:forEach>
			</tbody>
	</table>
	</div>
	</div>
	<center>
    	<a href="userEdit"><button class="btn btn-info btn-lg">Add</button></a>
    	<a href="userEdit"><button class="btn btn-info btn-lg">Edit</button></a>
    	<a href="#"><button class="btn btn-info btn-lg">Delete</button></a>
    </center>
    <center>
		<ul class="pagination">
			<li><a href="#">&laquo;</a></li>
  			<li><a href="#">1</a></li>
  			<li><a href="#">2</a></li>
  			<li><a href="#">3</a></li>
  			<li><a href="#">4</a></li>
  			<li><a href="#">5</a></li>
  			<li><a href="#">&raquo;</a></li>
		</ul>
	</center>

</body>
<%@ include file="/html/footer.html" %>
</html>

