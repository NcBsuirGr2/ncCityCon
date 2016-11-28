<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Error</title>
	<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <div class="content-wrapper">
<%@ include file="/include/header.jsp" %>
	<div class="before-footer">
    <h1 align="center">Error</h1>
    <h3 align="center">
        Sorry, error has occurred.
        Error message was: ${errorMessage}
    </h3>
    </div>
    <%@ include file="/include/footer.html" %>
    </div>
</body>
</html>
