<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<title>Routers</title>
</head>
<body>
<div class="content-wrapper">
<%@ include file="/html/header.jsp" %>
    <div class="before-footer">
<div class="panel panel-default">
    <table class="table table-striped" style="table-layout: auto">
        <thead>
            <tr>
                <th>Port</th>
                <th>City</th>
                <th>Router</th>
                <th>Port</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${entityArray}" var="connection">
                <tr>
                    <!--TODO: указать точные поля -->
                    <td><c:out value="${connection}" /></td>
                    <td><c:out value="${connection}" /></td>
                    <td><c:out value="${connection}" /></td>
                    <td><c:out value="${connection}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<div class="panel panel-default">

    <!-- TODO: указать точно какой идентификатор будет передаваться -->
    <a href="ConnectionEditServlet?action=edit&name=<c:out value="${connection}"/>">
        <button type="button" class="btn btn-primary" value="Edit" name="edit">Edit</button>
    </a>

    <!-- TODO: указать точно какой идентификатор будет передаваться -->
    <a href="ConnectionEditServlet?action=reset&name=<c:out value="${connection}"/>">
        <button type="button" class="btn btn-primary" value="Reset" name="delete">Delete</button>
    </a>
</div>
</div>
<%@ include file="/html/footer.html" %>
</div>

</body>
</html>

