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
<div class="panel panel-default">
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
    <a href="CityEditServlet?action=add">
        <button type="button" class="btn btn-primary" value="Add" name="add">Add</button>
    </a>
    <a href="CityEditServlet?action=edit&name=<c:out value="${city.name}"/>">
        <button type="button" class="btn btn-primary" value="Edit" name="edit">Edit</button>
    </a>
    <a href="CityEditServlet?action=delete&name=<c:out value="${city.name}"/>">
        <button  type="button" class="btn btn-primary" value="Delete" name="delete">Delete</button>
    </a>
</center>


<form action="city" method="GET">
    <input type=hidden name="action" value="add">
    <input type=submit value="add">
</form>

<form action="city" method="GET">
    <input type=hidden name="action" value="edit">
    <input type=hidden name="CityName" value="Mike">   <!-- value="$(city.name}"> -->
    <input type=submit value="edit">
</form>

<form action="city" method="DELETE">
    <input type=hidden name="CityName" value="Mike">   <!-- value="$(city.name}"> -->
    <input type=submit value="delete">
</form>

<button  type="button" class="btn btn-primary"  name="delete2" value="Delete2" onclick="www.vk.com" method="GET">testButton</button>


</body>
</html>
<%@ include file="/html/footer.html" %>

