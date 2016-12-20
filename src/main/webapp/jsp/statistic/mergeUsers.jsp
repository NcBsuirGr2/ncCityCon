<%--
  Created by IntelliJ IDEA.
  User: Vojts
  Date: 15.12.2016
  Time: 2:58
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
                Merge users statistic
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-xs-4 text-right"><strong>Count users before merge:</strong></div>
                    <div class="col-xs-7">
                        ${mergeStatistic.count_users_from_database}
                    </div>
                </div>

                <br>

                <div class="row">
                    <div class="col-xs-4 text-right"><strong>Count users after merge:</strong></div>
                    <div class="col-xs-7">
                        ${mergeStatistic.count_users_from_database + mergeStatistic.count_merged_users_from_service}
                            (+ <fmt:formatNumber type="percent" minFractionDigits="2"
                                               value="${mergeStatistic.count_merged_users_from_service/
                                               mergeStatistic.count_users_from_database}"/>)
                    </div>
                </div>

                <br>

                <div class="row">
                    <div class="col-xs-4 text-right"><strong>Count users from service:</strong></div>
                    <div class="col-xs-6">
                        ${mergeStatistic.count_users_from_service}
                    </div>
                </div>

                <br>

                <div class="row">
                    <div class="col-xs-4 text-right"><strong>Count successful added users from service:</strong></div>
                    <div class="col-xs-6">
                        ${mergeStatistic.count_merged_users_from_service}
                            (<fmt:formatNumber type="percent" minFractionDigits="2"
                                               value="${mergeStatistic.count_merged_users_from_service/
                                               mergeStatistic.count_users_from_service}"/>)
                    </div>
                </div>

                <br>

                <div class="row">
                    <div class="col-xs-4 text-right"><strong>Count already exist users  from service:</strong></div>
                    <div class="col-xs-6">
                        ${mergeStatistic.count_valid_users_from_service-mergeStatistic.count_merged_users_from_service}
                            (<fmt:formatNumber type="percent" minFractionDigits="2"
                                               value="${1 - mergeStatistic.count_merged_users_from_service/
                                               mergeStatistic.count_valid_users_from_service}"/>)
                    </div>
                </div>

                <br>

                <div class="row">
                    <div class="col-xs-4 text-right"><strong>Count users  from service with invalid data:</strong></div>
                    <div class="col-xs-6">
                        ${mergeStatistic.count_users_from_service-mergeStatistic.count_valid_users_from_service}
                            (<fmt:formatNumber type="percent" minFractionDigits="2"
                                               value="${1 - mergeStatistic.count_valid_users_from_service/
                                               mergeStatistic.count_users_from_service}"/>)
                    </div>
                </div>

            </div>
        </div>
        <%@ include file="/html/footer.html" %>
    </div>
</div>
</body>
</html>