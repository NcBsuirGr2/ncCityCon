<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<title>Edit</title>
</head>
<body>
<div class="content-wrapper">
<%@ include file="/include/header.jsp" %>
	<div class="before-footer">
<div class="panel panel-default">
	<div class="panel-heading">Edit</div>
	<form class="form-horizontal" role="form" method="post" action="CityEditServlet" >

		<div class="form-group">
			<label for="NameCity" class="col-xs-3 control-label">NameCity</label>
			<div class="col-xs-9">
				<input class="form-control" placeholder="NameCity" id="NameCity" name="NameCity" type="text" autofocus value="<c:out value="${city.Name}"/> ">
			</div>
		</div>

		<div class="form-group">
			<label for="Country" class="col-xs-3 control-label">Country</label>
			<div class="col-xs-9">
				<input class="form-control" placeholder="Country" id="Country" name="Country" type="text" autofocus value="<c:out value="${city.Country}"/> ">
			</div>
		</div>

	</form>
	<a><button type="button" class="btn btn-primary" value="edit" name="edit">Save</button></a>
</div>
</div>
<%@ include file="/include/footer.html" %>
</div>
</body>
</html>
