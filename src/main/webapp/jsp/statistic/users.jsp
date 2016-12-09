<%--
  Created by IntelliJ IDEA.
  User: Vojts
  Date: 08.12.2016
  Time: 3:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <h4>Users statistic</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" action="${pageContext.request.contextPath}/user" method="POST" role="form" id="form">
                    <div class="form-group">
                        <div for="name" class="col-xs-4 test-right"><strong>Count users:</strong></div>
                        <div class="col-xs-7">
                            ${count_users}
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-4 test-right"><strong>Count cities:</strong></div>
                        <div class="col-xs-7">
                            ${count_admins} (<fmt:formatNumber type="percent" minFractionDigits="2"
                                                               value="${count_admins/count_users}"/>)
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-4 test-right"><strong>Count admins:</strong></div>
                        <div class="col-xs-7">
                            ${count_operators} (<fmt:formatNumber type="percent" minFractionDigits="2"
                                                                  value="${count_operators/count_users}"/>)
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-4 test-right"><strong>Count guests:</strong></div>
                        <div class="col-xs-7">
                            ${count_guests} (<fmt:formatNumber type="percent" minFractionDigits="2"
                                                               value="${count_guests/count_users}"/>)
                        </div>
                    </div>
                    <c:if test="${not empty first_users}">
                        <div class="form-group">
                            <div class="col-xs-4 test-right"><strong>First users (${first_users.get(0).createDate}):</strong></div>
                        </div>
                        <c:forEach items="${first_users}" var="user">
                            <div class="form-group">
                                <div class="col-xs-4 test-right">
                                    ${user.name} ${user.login} ${user.group}
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty last_users}">
                        <div class="form-group">
                            <div class="col-xs-4 test-right"><strong>Last users (${last_users.get(0).createDate}):</strong></div>
                        </div>
                        <c:forEach items="${last_users}" var="user">
                            <div class="form-group">
                                <div class="col-xs-4 test-right">
                                        ${user.name}    ${user.login}   ${user.group}
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                </form>
            </div>

                <%@ include file="/include/statistic_footer.html" %>
            </div>
            <%@ include file="/html/footer.html" %>
        </div>
    </body>
</html>