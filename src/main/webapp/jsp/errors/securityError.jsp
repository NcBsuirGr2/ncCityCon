<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 16.11.16
  Time: 13.27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
   	<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="content-wrapper">
<%@ include file="/html/header.jsp" %>
	<div class="before-footer">
<h1 align="center">Security error</h1>
<h3 align="center">
    <p>You can't watch this page.</p>
    <p><a href="/">go to main page</a></p>
</h3>
</div>
<%@ include file="/html/footer.html" %>
</div>

</body>
</html>
