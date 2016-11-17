<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/cityCon/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/cityCon/css/style.css">
		<title>Router List</title>
    	<meta name="generator" content="Bootply" />
    	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	</head>
<body>
<%@ include file="/html/header.html" %>
	<div class="dropdown">
		<button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">City
			<span class="caret"></span></button>
				<ul class="dropdown-menu">
					<li><a href="#">City1</a></li>
					<li><a href="#">City2</a></li>
					<li><a href="#">City3</a></li>
				</ul>
	</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">Router List</div>
    				<table class="table table-striped" style="table-layout: auto">
     					<thead>
							<tr>
								<th>Name</th>
			   					<th>SN</th>
			        			<th>Port number</th>
								<th>Status</th>
								<th>City Id</th>
        					</tr>
        				</thead>
        				<tbody>
							<c:forEach items="${entityArray}" var="router">
								<tr>
									<td><a href="/cityCon/connections"><c:out value="${router.name}" /></a></td>
									<td><c:out value="${router.SN}" /></td>
									<td><c:out value="${router.portsNum}" /></td>
									<td><c:out value="${router.active}" /></td>
									<td><c:out value="${router.cityId}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
			</div>
			<center> 
			<p href="RouterEditServlet?action=newRouter&name=<c:out value="${router.SN}"/>">
				<button type="button" class="btn btn-primary" value="Add" name="add" >Add</button>
			</p>
			<p href="RouterEditServlet?action=newRouter&name=<c:out value="${router.SN}"/>">
				<button type="button" class="btn btn-primary" value="Edit" name="edit" >Edit</button>
			</p>
			<p href="UserEditServlet?action=newRouter&name=<c:out value="${router.SN}"/>">
				<button type="button" class="btn btn-primary" value="Delete" name="delete" >Delete</button>
			</p>
			</center>
			
	<form action="router" method="GET">
        <input type=hidden name="action" value="add">
        <input type=submit value="add">
    </form>

    <form action="router" method="GET">
        <input type=hidden name="action" value="edit">
        <input type=hidden name="RouterName" value="Mike">   <!-- value="$(router.name}"> -->
        <input type=submit value="edit">
    </form>
    <form action="router" method="DELETE">
        <input type=hidden name="action" value="delete">
        <input type=hidden name="RouterName" value="Mike">   <!-- value="$(router.name}"> -->
        <input type=submit value="delete">
    </form>
			
			
<%@ include file="/html/footer.html" %>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="/cityCon/js/bootstrap.min.js"></script>
</body>
</html>


