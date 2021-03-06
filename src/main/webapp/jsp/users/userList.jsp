<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="citycon"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1, maximum-scale=1">

		<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>		
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript" src="/js/userPages/userList.js"></script>

		<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<link rel="stylesheet" type="text/css" href="/css/userPages/userList.css">
		<link rel="icon" href="favicon.ico" />
		
		<title>Users</title>
	</head>

	<body>
		<div class="content-wrapper">
			<%@ include file="/include/header.jsp" %>
			<div class="alert alert-info selectAlert hide">
				Please, choose one element from the list below.
			</div>
			<c:if test="${not empty param.success}">
				<div class="alert alert-success alert-dismissible">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>Success!</strong> 
					<c:choose>
					    <c:when test="${param.success == 'add'}">
					       	New user has been created.
					    </c:when>
					    <c:when test="${param.success == 'delete'}">
					        User has been deleted.
					    </c:when>
					    <c:when test="${param.success == 'edit'}">
					        User has been modified.
					    </c:when>
					</c:choose>
				</div>
			</c:if>		
			<div class="row">
				<div class="col-sm-1">
				</div>
				<div class="col-sm-10">
					<div class="panel panel-default">
						<div class="panel-heading" style="text-align: center;">
							<div class="row">
								<div class="col-sm-3">
								</div>
								<div class="col-sm-6">
									Users
								</div>
								<div class="col-sm-3">
										<div class="pull-right">
											<form action="/users" method="get" name="form" onsubmit="return true;">
												<img src="/img/search.png" height="25px">
												<input class="panel-search simpleText" name="search" type="text" id="search" size="18"
												   maxlength="15" value="${param.search}" placeholder="search..." title="search by Name or Login">
											</form>
										</div>
								</div>
							</div>
						</div>
						<c:if test="${not empty entityArray}">
					    	<table class="selectable table table-striped table-bordered table-hover" style="table-layout: auto">
				     			<thead>
									<tr>
										<th>
											<citycon:sortBy asc="${paginationParameters['users']['asc']}"
														sortByIs="${paginationParameters['users']['sortBy']}"
														sortByNeed="name"
														value="Name"
														search="${param.search}"
											/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['users']['asc']}"
														sortByIs="${paginationParameters['users']['sortBy']}"
														sortByNeed="login"
														value="Login"
														search="${param.search}"
											/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['users']['asc']}"
														sortByIs="${paginationParameters['users']['sortBy']}"
														sortByNeed="group"
														value="Group"
														search="${param.search}"
											/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['users']['asc']}"
														sortByIs="${paginationParameters['users']['sortBy']}"
														sortByNeed="email"
														value="Email"
														search="${param.search}"
											/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['users']['asc']}"
														sortByIs="${paginationParameters['users']['sortBy']}"
														sortByNeed="createDate"
														value="Joined on"
														search="${param.search}"
											/>
										</th>
							        	<th class="hidden">id</th>
				        			</tr>
				        		</thead>
				        		<tbody>
									<c:forEach items="${entityArray}" var="user">
										<tr>
											<td>${user.name}</td>
											<td class="unique">${user.login}</td>
											<td>${user.group}</td>
											<td>${user.email}</td>
											<td>${user.createDate}</td>
											<td class="hidden idField">${user.id}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						<c:if test="${empty entityArray}">
							<h4 class="text-center">Empty user set</h4>
						</c:if>

						<div class="panel-footer">
							<c:if test="${showUsersOperationBtns and not empty entityArray}">
								<div class="row">
									<div class="col-sm-3">
										<a href="/user?action=add" class="btn btn-primary center-block">Add</a>
									</div>

									<div class="col-sm-3">
										<a href="#" class="btn btn-primary center-block editHref">Edit</a>
									</div>

									<div class="col-sm-3">
										<form action="/user" id="deleteForm" method="POST">
											<input type="hidden" id="deleteId" name="id" value="-1">
											<input type="hidden" name="type" value="delete">
											<button type="button" class="btn btn-primary center-block btn-block deleteDialogBtn" data-toggle="modal" data-target=".deleteDialog">Delete</button>
										</form>
									</div>

									<div class="col-sm-3">
										<a href="/statistic/merge" class="btn center-block btn-primary">Merge Users</a>
									</div>

									<!-- Delete dialog modal -->
									<div class="modal fade deleteDialog">
										<div class="modal-dialog" role="document">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal" aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
													<h4 class="modal-title">Confirm deletion</h4>
												</div>

												<div class="modal-body">
													<p>Are you sure you want to delete selected user?</p>
												</div>

												<div class="modal-footer">
													<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
													<input type=submit class="btn btn-primary" form="deleteForm" value="Delete">
												</div>
											</div>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${empty entityArray}">
								<c:if test="${showUsersOperationBtns}">
									<div class="row">
										<div class="col-sm-2"></div>
										<div class="col-sm-3">
											<a href="/user?action=add" class="btn btn-primary center-block">Add</a>
										</div>
										<div class="col-sm-2"></div>
										<div class="col-sm-3">
											<a href="/users" class="btn btn-primary center-block">Back</a>
										</div>
									</div>
								</c:if>
							</c:if>
						</div>
					</div>
				</div>
				<div class="col-sm-1">
				</div>
			</div> <!-- End of panel row -->

			<div class="before-footer" style="text-align: center;">
				<c:if test="${not empty entityArray}">
					<%@ include file="/include/pagination.jsp" %>
				</c:if>
			</div>

			<%@ include file="/include/footer.html" %>
		</div>
	</body>
</html>

