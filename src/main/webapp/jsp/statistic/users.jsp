<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>Statistic</title>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1, maximum-scale=1">

  <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>


  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/statistic/statisticList.css">
  <link rel="icon" href="/favicon.ico" />

  <script type="text/javascript">
      google.charts.load("current", {packages:["corechart"]});
      google.charts.setOnLoadCallback(drawChart);
      function drawChart() {
          var data = google.visualization.arrayToDataTable([
              ['Group', 'Users'],
              ['Admins', ${count_admins}],
              ['Operators', ${count_operators}],
              ['Guests',  ${count_guests}]
          ]);
         var options = {
              title: 'My Daily Activities',
              is3D: true,
          };
         var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
          chart.draw(data, options);
      }
  </script>
</head>
<body>
<div class="content-wrapper">

  <%@ include file="/include/header.jsp" %>

  <div class="before-footer">

    <div class="panel panel-default">
      <div class="panel-heading text-center">
        Users statistic
      </div>
      <div class="panel-body">
          <div class="row">
            <div class="col-xs-6 text-right"><strong>Total users count:</strong></div>
            <div class="col-xs-6">
              ${count_users}
            </div>
          </div>

          <br>
          <div class="row">
              <div class="col-xs-2"></div>
              <div class="col-xs-8">
                  <div id="piechart_3d" style="width: 500px; height: 315px; align-content: center"></div>
              </div>
              <div class="col-xs-2"></div>
          </div>

          <br>

          <c:if test="${not empty first_users}">
              <div class="row">
                  <div class="col-xs-6 text-right"><strong>First users (${first_users[0].createDate}):</strong></div>

                    <div class="col-xs-6">
                      <c:forEach items="${first_users}" var="user" varStatus="loopStatus">
                        ${user.login} <c:if test="${!loopStatus.last}">,</c:if>
                      </c:forEach>
                    </div>
              </div>
          </c:if>

            <br>

          <c:if test="${not empty last_users}">
              <div class="row">
                  <div class="col-xs-6 text-right"><strong>Last users (${last_users[0].createDate}):</strong></div>

                  <div class="col-xs-6">
                    <c:forEach items="${last_users}" var="user" varStatus="loopStatus">
                        ${user.login} <c:if test="${!loopStatus.last}">,</c:if>
                    </c:forEach>
                  </div>
              </div>
          </c:if>

          <br>

          <c:if test="${not empty users_online_logins}">
              <div class="row">
                  <div class="col-xs-6 text-right"><strong>Users online:</strong></div>

                  <div class="col-xs-6">
                      <c:forEach items="${users_online_logins}" var="user" varStatus="loopStatus">
                          ${user} <c:if test="${!loopStatus.last}">,</c:if>
                      </c:forEach>
                  </div>
              </div>
          </c:if>
      </div>

        <%@ include file="/include/statisticFooter.html" %>
      </div>
      <%@ include file="/html/footer.html" %>
    </div>
  </div>
</body>
</html>