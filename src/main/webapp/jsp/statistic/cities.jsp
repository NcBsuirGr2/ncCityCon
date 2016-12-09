<%--
  Created by IntelliJ IDEA.
  User: Vojts
  Date: 08.12.2016
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
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
                <h4>Cities statistic</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" action="${pageContext.request.contextPath}/user" method="POST" role="form" id="form">
                    <div class="form-group">
                        <div for="name" class="col-xs-4 test-right"><strong>Count countries:</strong></div>
                        <div class="col-xs-7">
                            ${count_countries}
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-4 test-right"><strong>Count cities:</strong></div>
                        <div class="col-xs-7">
                            ${count_cities}
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-4 test-right"><strong>Country with max count cities:</strong></div>
                        <div class="col-xs-7">
                            ${max_city_country.name} (${max_city_country.countCities} cities)
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-4 test-right"><strong>Country with min count cities:</strong></div>
                        <div class="col-xs-7">
                            ${min_city_country.name} (${min_city_country.countCities} cities)
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-4 test-right"><strong>City with max count routers:</strong></div>
                        <div class="col-xs-7">
                            ${max_routers_city.name}(${max_routers_city.countryName}) - ${max_routers_city.routersNum} routers
                        </div>
                    </div>
                </form>
            </div>

            <%@ include file="/include/statistic_footer.html" %>
        </div>
        <%@ include file="/html/footer.html" %>
    </div>
</body>
</html>
