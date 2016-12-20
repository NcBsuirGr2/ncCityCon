<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header id="header">
	<div class="row">
		<div class="col-sm-4">
			<a href="/"><img alt="logo" id="menu" src="/img/logo.png"/></a>
		</div>

		<div class="col-sm-8" >
		<c:if test="${showLogoutBtn}">
			<a href='/logout' class='btn btn-header btn-default pull-right'>Logout</a>
		</c:if>
		<c:if test="${showConnectionsBtn}">
			<a href="/connections" class="btn btn-header btn-default pull-right">Connections</a>
		</c:if>
		<c:if test="${showRoutersBtn}">
			<a href="/routers" class="btn btn-header btn-default pull-right">Routers</a>
		</c:if>
		<c:if test="${showCitiesBtn}">
			<a href="/cities" class="btn btn-header btn-default pull-right">Cities</a>
		</c:if>
		<c:if test="${showUsersBtn}">
			<a href="/users" class="btn btn-header btn-default pull-right">Users</a>
		</c:if>
		<c:if test="${not empty sessionScope.user}">
			<a href="/user?action=edit&login=${sessionScope.user.login}" class="btn btn-header btn-default pull-right">Account</a>
		</c:if>
		<c:if test="${not empty sessionScope.user}">
			<a href="/statistic/" class="btn btn-header btn-default pull-right">Statistic</a>
		</c:if>
		</div>
	</div>
</header>