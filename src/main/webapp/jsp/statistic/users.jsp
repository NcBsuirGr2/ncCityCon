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
            <div class="col-xs-6 text-right"><strong>Count admins:</strong></div>
            <div class="col-xs-6">
              ${count_admins} (<fmt:formatNumber type="percent" minFractionDigits="2" value="${count_admins/count_users}"/>)
            </div>
          </div>

            <br>

          <div class="row">
            <div class="col-xs-6 text-right"><strong>Count operators:</strong></div>
            <div class="col-xs-6">
              ${count_operators} (<fmt:formatNumber type="percent" minFractionDigits="2" value="${count_operators/count_users}"/>)
            </div>
          </div>

          <br>

          <div class="row">
            <div class="col-xs-6 text-right"><strong>Guests count:</strong></div>
            <div class="col-xs-6">
              ${count_guests} (<fmt:formatNumber type="percent" minFractionDigits="2" value="${count_guests/count_users}"/>)
            </div>
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