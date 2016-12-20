<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Statistic</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1, maximum-scale=1">

    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/statistic/statisticList.css">
    <link rel="icon" href="/favicon.ico" />
</head>
<body>
<div class="content-wrapper">

    <%@ include file="/include/header.jsp" %>

    <div class="before-footer">

        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h4>Routers statistic</h4>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-xs-4 text-right"><strong>Routers count:</strong></div>
                    <div class="col-xs-6">
                        ${count_routers}
                    </div>
                </div>

                <br>
                <div class="row">
                    <div class="col-xs-4 text-right"><strong>Inactive routers count:</strong></div>
                    <div class="col-xs-6">
                        ${count_inactive_routers} (<fmt:formatNumber type="percent" minFractionDigits="2"
                                                         value="${inactive_routers_percent}"/>)
                    </div>
                </div>

                <br>
                <div class="row">
                    <div class="col-xs-4 text-right"><strong>Average connections per router:</strong></div>
                    <div class="col-xs-6">
                        <fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${connections_per_router}"/>
                    </div>
                </div>

                <br>
                <div class="row">
                    <div class="col-xs-4 text-right"><strong>Total ports count:</strong></div>
                    <div class="col-xs-6">
                        ${count_routers_ports}
                    </div>
                </div>

                <br>
                <div class="row">
                    <div class="col-xs-4 text-right"><strong>Using ports of routers:</strong></div>
                    <div class="col-xs-6">
                        <fmt:formatNumber type="percent" minFractionDigits="2" value="${used_ports_percent}"/>
                    </div>
                </div>
            </div>

            <%@ include file="/include/statisticFooter.html" %>
        </div>
        <%@ include file="/html/footer.html" %>
    </div>
</body>
</html>
