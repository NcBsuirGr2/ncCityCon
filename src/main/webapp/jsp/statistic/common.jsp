<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Statistic</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1, maximum-scale=1">

    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/statistic/statisticList.css">
    <link rel="icon" href="favicon.ico" />
</head>
<body>
<div class="content-wrapper">

    <%@ include file="/include/header.jsp" %>

    <div class="before-footer">

        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h4>Common statistic</h4>
            </div>
            <div class="panel-body" style="width: 100%">
                <form class="form-horizontal" action="/user" method="POST" role="form" id="form">
                    <div class="form-group">
                        <div for="name" class="col-xs-4 test-right"><strong>Total users count:</strong></div>
                        <div class="col-xs-7">
                            ${count_users}
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-4 test-right"><strong>Total cities count:</strong></div>
                        <div class="col-xs-7">
                            ${count_cities}
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-4 test-right"><strong>Total routers count:</strong></div>
                        <div class="col-xs-7">
                            ${count_routers}
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-4 test-right"><strong>Total connections count:</strong></div>
                        <div class="col-xs-7">
                            ${count_connections}
                        </div>
                    </div>
                </form>
            </div>

            <%@ include file="/include/statisticFooter.html" %>
        </div>
        <%@ include file="/html/footer.html" %>
    </div>
</body>
</html>
