<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
          <div class="row">
            <div class="col-xs-4 text-right"><strong>Countries count:</strong></div>
            <div class="col-xs-6">
              ${count_countries}
            </div>
          </div>

          <br>

          <div class="row">
            <div class="col-xs-4 text-right"><strong>Cities count:</strong></div>
            <div class="col-xs-6">
              ${count_cities}
            </div>
          </div>

          <br>

          <div class="row">
            <div class="col-xs-4 text-right"><strong>Country with max cities count:</strong></div>
            <div class="col-xs-6">
              <c:forEach items="${max_city_countries}" var="countryMap" varStatus="loopStatus">
                ${countryMap["country"]} (${countryMap["citiesCount"]} cities)<c:if test="${!loopStatus.last}">, </c:if>
              </c:forEach>
            </div>
          </div>

          <br>

          <div class="row">
            <div class="col-xs-4 text-right"><strong>Country with min cities count:</strong></div>
            <div class="col-xs-6">
              <c:forEach items="${min_city_countries}" var="countryMap" varStatus="loopStatus">
                ${countryMap["country"]} (${countryMap["citiesCount"]} cities)<c:if test="${!loopStatus.last}">, </c:if>
              </c:forEach>
            </div>
          </div>

          <br>

          <div class="row">
              <div class="col-xs-4 text-right"><strong>City with max routers count:</strong></div>
              <div class="col-xs-6">
                <c:forEach items="${max_router_cities}" var="cityMap" varStatus="loopStatus">
                    ${cityMap["country"]}, ${cityMap["city"]} (${cityMap["routersCount"]} routers)
                    <c:if test="${!loopStatus.last}">, </c:if>
              </c:forEach>
              </div>
          </div>

      </div>

      <%@ include file="/include/statisticFooter.html" %>
    </div>
    <%@ include file="/html/footer.html" %>
  </div>
</body>
</html>