<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<title>Connection</title>
</head>
<body>
<div class="content-wrapper">
<%@ include file="/include/header.jsp" %>
    <div class="before-footer">
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
                <td><a href="/routers"><c:out value="${city.name}" /></a></td>
                <td><c:out value="${city.countryName}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<center>
    <form action="city" method="GET">
        <input type=hidden name="action" value="add">
        <button type=submit class="btn btn-primary" value="add">Add</button>
    </form>

    <form action="city" method="GET">
        <input type=hidden name="action" value="edit">
        <input type=hidden name="CityName" value="Brest">   <!-- value="$(city.name}"> -->
        <button type=submit class="btn btn-primary" value="edit">Edit</button>
    </form>

    <form action="city" method="DELETE">
        <input type=hidden name="action" value="delete">
        <input type=hidden name="CityName" value="Brest">   <!-- value="$(city.name}"> -->
        <button type=submit class="btn btn-primary" value="delete">Delete</button>
    </form>

</center>
</div>
<%@ include file="/include/footer.html" %>
</div>
</body>
</html>


