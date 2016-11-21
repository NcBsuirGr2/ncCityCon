<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="/js/selectEntity.js"></script>
	<script type="text/javascript" src="/js/routerPages/routerList.js"></script>

	<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
	<title>Router List</title>
	<meta name="generator" content="Bootply" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>

<body>
<div class="content-wrapper">
	<%@ include file="/html/header.jsp" %>
	<div class="before-footer">
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
			<table class="selectable table table-striped" style="table-layout: auto">
				<thead>
				<tr>
					<th>Name</th>
					<th>SN</th>
					<th>Port number</th>
					<th>Status</th>
				</tr>
				</thead>
				<tbody>

				<c:forEach items="${entityArray}" var="router">
					<td class="hidden idField">${router.id}</td>
					<td><c:out value="${router.name}" /></td>
					<td class="unique"><c:out value="${router.SN}" /></td>
					<td><c:out value="${router.portsNum}" /></td>
					<td><c:out value="${router.active}" /></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="panel-footer">
				<div class="row">

					<div class="col-sm-4">
						<a href="/router?action=add">
							<button class="btn btn-primary">Add</button>
						</a>
					</div>

					<div class="col-sm-4">
						<a class="editHref" href="#">
							<button class="btn btn-primary">Edit</button>
						</a>
					</div>

					<div class="col-sm-4">
						<form action="router" id="deleteForm" method="POST">
							<input type="hidden" id="deleteId" name="id" value="-1">
							<input type="hidden" name="type" value="delete">
							<input type=submit class="btn btn-primary" value="Delete"></input>
						</form>
					</div>

				</div>
			</div>
		</div>


	</div>


	<%@ include file="/html/footer.html" %>
</div
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>

