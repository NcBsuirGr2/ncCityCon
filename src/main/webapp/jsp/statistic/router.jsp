<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Router statistic</title>

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
                Router statistic
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-xs-6 text-right"><strong>Router SN:</strong></div>
                    <div class="col-xs-6">
                        ${SN}
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6 text-right"><strong>Router name:</strong></div>
                    <div class="col-xs-6">
                        ${name}
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6 text-right"><strong>Router status:</strong></div>
                    <div class="col-xs-6">
                        ${active}
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6 text-right"><strong>Total connections num:</strong></div>
                    <div class="col-xs-6">
                        <a href="/connections?SN=${SN}">${connectionsNum}</a>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6 text-right"><strong>Inactive connections num:</strong></div>
                    <div class="col-xs-6">
                        ${inactiveConnectionsNum}
                    </div>
                </div>
                <c:if test="${not empty connectedRoutersActive}">
                    <br>
                    <div class="row">
                        <div class="col-xs-6 text-right"><strong>Connected routers (active connection):</strong></div>
                        <div class="col-xs-6">
                            <c:forEach var="router" items="${connectedRoutersActive}" varStatus="loopStatus">
                                <a href="/statistic/routers/${router.SN}">
                                        ${router.SN}
                                </a> (${router.connectionsCount} connections);
                                <c:if test="${!loopStatus.last}"><br></c:if>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty connectedRoutersInactive}">
                    <br>
                    <div class="row">
                        <div class="col-xs-6 text-right"><strong>Connected routers (inactive connection):</strong></div>
                        <div class="col-xs-6">
                            <c:forEach var="router" items="${connectedRoutersInactive}" varStatus="loopStatus">
                                <a href="/statistic/routers/${router.SN}">
                                        ${router.SN}
                                </a> (${router.connectionsCount} connections);
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
                        <a href="/routers" class="btn btn-primary btn-block center-block">Back</a>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/html/footer.html" %>
    </div>
</body>
</html>