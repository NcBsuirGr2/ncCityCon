<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%@ include file="/html/header.html" %>
    <h1 align="center">Error</h1>
    <h3 align="center">
        <%=((String)request.getSession().getAttribute("errorMessage"))%>
    </h3>>
    <%@ include file="/html/footer.html" %>
</body>
</html>
