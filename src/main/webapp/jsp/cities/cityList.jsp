<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="/html/header.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<title>Connection</title>
</head>
<body>
<div class="panel-heading">List Users</div>
<table class="table table-striped" style="table-layout: auto">
    <thead>
    <tr>
        <th>NameCity</th>
        <th>Country</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${entityArray}" var="city">
        <tr>
            <td><c:out value="${city.name}" /></td>
            <td><c:out value="${city.countryName}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
<center>
    <p href="CityEditServlet?action=add">
        <button type="button" class="btn btn-primary" value="Add" name="add">Add</button>
    </p>
    <p href="CityEditServlet?action=edit&name=<c:out value="${city.name}"/>">
        <button type="button" class="btn btn-primary" value="Edit" name="edit">Edit</button>
    </p>
    <p href="CityEditServlet?action=delete&name=<c:out value="${city.name}"/>">
        <button type="button" class="btn btn-primary" value="Delete" name="delete">Delete</button>
    </p>
</center>


</body>
</html>
<%@ include file="/html/footer.html" %>

