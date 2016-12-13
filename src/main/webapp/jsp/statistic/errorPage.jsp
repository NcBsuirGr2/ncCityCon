<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
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
