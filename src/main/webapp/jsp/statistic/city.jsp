<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>City statistic</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1, maximum-scale=1">

    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/statistic/statisticList.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="icon" href="/favicon.ico" />
</head>
<body>
<div class="content-wrapper">

    <%@ include file="/include/header.jsp" %>

    <div class="before-footer">

        <div class="panel panel-default">
            <div class="panel-heading text-center">
                City statistic
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-xs-6 text-right"><strong>Full city name:</strong></div>
                    <div class="col-xs-6">
                        ${country}, ${city}
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6 text-right"><strong>Total routers num:</strong></div>
                    <div class="col-xs-6">
                        <a href="/routers?country=${country}&city=${city}">${routersNum}</a>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6 text-right"><strong>Total connections num:</strong></div>
                    <div class="col-xs-6">
                        <a href="/connections?country=${country}&city=${city}">${connectionsNum}</a>
                    </div>
                </div>
                <c:if test="${not empty connectedActiveCities}">
                    <br>
                    <div class="row">
                        <div class="col-xs-6 text-right"><strong>Connected cities (active connection):</strong></div>
                        <div class="col-xs-6">
                            <c:forEach var="city" items="${connectedActiveCities}" varStatus="loopStatus">
                                <a href="/statistic/cities/${city.country}/${city.city}">
                                    ${city.country}, ${city.city}
                                </a> (${city.connectionsCount} connections);
                                <c:if test="${!loopStatus.last}"><br></c:if>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty connectedInactiveCities}">
                    <br>
                    <div class="row">
                        <div class="col-xs-6 text-right"><strong>Connected cities (inactive connection):</strong></div>
                        <div class="col-xs-6">
                            <c:forEach var="city" items="${connectedInactiveCities}" varStatus="loopStatus">
                                <a href="/statistic/cities/${city.country}/${city.city}">
                                        ${city.country}, ${city.city}
                                </a> (${city.connectionsCount} connections);
                                <c:if test="${!loopStatus.last}"><br></c:if>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="panel-footer">
                <div class="row">
                    <div class="col-sm-4">
                    </div>
                    <div class="col-sm-4">
                        <a href="/cities" class="btn btn-primary btn-block center-block">Back</a>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/html/footer.html" %>
    </div>
</body>
</html>