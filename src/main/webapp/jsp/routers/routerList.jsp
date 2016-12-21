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
		<script type="text/javascript" src="/js/routerPages/routerList.js"></script>

		<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<link rel="stylesheet" type="text/css" href="/css/routerPages/routerList.css">
		<title>Router List</title>
	</head>

	<c:if test="${not empty param.country and not empty param.city}">
		<c:set var="samePath" value="country=${param.country}&city=${param.city}"/>
	</c:if>

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
					       	New router has been created.
					    </c:when>
					    <c:when test="${param.success == 'delete'}">
					        Router has been deleted.
					    </c:when>
					    <c:when test="${param.success == 'edit'}">
					        Router has been modified.
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
								<div class="col-md-5">
								</div>
								<div class="col-md-2">
									Routers
								</div>
								<div class="col-md-5">
									<div class="pull-right">
										<form action="/routers" method="get" name="form" onsubmit="return true;">
											<img src="/img/search.png" height="25px">
											<input class="panel-search simpleText" name="search" type="text" id="search" size="18"
												   maxlength="15" value="${param.search}" placeholder="search..." title="search by City, SN or Name" required>
										</form>
									</div>
								</div>
							</div>
					</div>
						<c:if test="${not empty entityArray}">
							<table class="selectable table table-striped table-bordered table-hover" style="table-layout: auto">
								<thead>
								<thead>

									<tr>
										<th>
											<citycon:sortBy asc="${paginationParameters['routers']['asc']}"
															sortByIs="${paginationParameters['routers']['sortBy']}"
															sortByNeed="country"
															value="Country"
															prefix="${samePath}"
															search="${param.search}"
											/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['routers']['asc']}"
															sortByIs="${paginationParameters['routers']['sortBy']}"
															sortByNeed="city"
															value="City"
															prefix="${samePath}"
															search="${param.search}"
											/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['routers']['asc']}"
															sortByIs="${paginationParameters['routers']['sortBy']}"
															sortByNeed="SN"
															value="SN"
															prefix="${samePath}"
															search="${param.search}"
											/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['routers']['asc']}"
															sortByIs="${paginationParameters['routers']['sortBy']}"
															sortByNeed="name"
															value="Name"
															prefix="${samePath}"
															search="${param.search}"
											/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['routers']['asc']}"
															sortByIs="${paginationParameters['routers']['sortBy']}"
															sortByNeed="portsNum"
															value="Ports num"
															prefix="${samePath}"
															search="${param.search}"
											/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['routers']['asc']}"
															sortByIs="${paginationParameters['routers']['sortBy']}"
															sortByNeed="usedPortsNum"
															value="Used ports"
															prefix="${samePath}"
															search="${param.search}"
											/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['routers']['asc']}"
															sortByIs="${paginationParameters['routers']['sortBy']}"
															sortByNeed="isActive"
															value="Active"
															prefix="${samePath}"
															search="${param.search}"
											/>
										</th>
										<th class="hidden">id</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${entityArray}" var="router">
										<td>${router.city.countryName}</td>
										<td><a href="/statistic/cities/${router.city.countryName}/${router.city.name}">${router.city.name}</a></td>
										<td class="unique"><a href="/statistic/routers/${router.SN}">${router.SN}</a></td>
										<td>${router.name}</td>
										<td>${router.portsNum}</td>
										<td>${router.usedPortsNum}</td>
										<td  <c:if test="${not router.active}">class="danger"</c:if> >${router.active}</td>
										<td class="hidden idField">${router.id}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						<c:if test="${empty entityArray}">
							<h4 class="text-center">Empty routers set</h4>
						</c:if>

						<div class="panel-footer">
							<c:if test="${not empty entityArray}">
								<div class="row">
									<c:if test="${showSystemUnitsOperationBtns}">
										<div class="col-sm-3">
											<a href="/router?${samePath}&action=add" class="btn btn-primary center-block">Add</a>
										</div>

										<div class="col-sm-3">
											<a href=# class="btn btn-primary center-block editHref">Edit</a>
										</div>

										<div class="col-sm-3">
											<a href="#" class="btn btn-primary center-block connectionsHref">Connections</a>
										</div>

										<div class="col-sm-3">
											<form action="/router" id="deleteForm" method="POST">
												<input type="hidden" id="deleteId" name="id" value="-1">
												<input type="hidden" name="action" value="delete">
												<input type="hidden" id="city" name="city" value="${param.city}"/>
												<input type="hidden" id="country" name="country" value="${param.country}"/>
												<button type="button" class="btn btn-primary center-block btn-block deleteDialogBtn" data-toggle="modal" data-target=".deleteDialog">Delete</button>
											</form>
										</div>
									</c:if>
									<c:if test="${not showSystemUnitsOperationBtns}">
										<div class="col-sm-4">
										</div>

										<div class="col-sm-4">
											<a href="#" class="btn btn-primary center-block connectionsHref">Connections</a>
										</div>
									</c:if>
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
													<p>Are you sure you want to delete selected router? All it's connections will be also deleted</p>
												</div>

												<div class="modal-footer">
													<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
													<input type=submit class="btn btn-primary" form="deleteForm" value="Delete"></input>
												</div>
											</div>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${empty entityArray}">
								<c:if test="${showSystemUnitsOperationBtns}">
									<div class="row">
										<div class="col-sm-2"></div>
										<div class="col-sm-3">
											<a href="/router?${samePath}&action=add" class="btn btn-primary center-block">Add</a>
										</div>
										<div class="col-sm-2"></div>
										<div class="col-sm-3">
											<a href="/routers" class="btn btn-primary center-block">Back</a>
										</div>
									</div>
								</c:if>
								<c:if test="${not showSystemUnitsOperationBtns}">
									<div class="row">
										<div class="col-sm-4"></div>
										<div class="col-sm-4">
											<a href="/routers" class="btn btn-primary center-block">Back</a>
										</div>
										<div class="col-sm-4"></div>
									</div>
								</c:if>
							</c:if>
						</div> <!-- Panel footer end -->

					</div> <!-- Panel end -->
				</div>

				<div class="col-sm-1">
				</div>
			</div> 

			<center class="before-footer">
				<c:if test="${not empty entityArray}">
					<!-- Pagination block -->
					<c:if test="${endPage > 1}">
						<ul class="pagination">
							<c:if test="${beginPage > previousPage}">
								<li class="page-item">
									<a class="page-link" href="?${samePath}&page=${previousPage}&search=${param.search}" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
										<span class="sr-only">Previous</span>
									</a>
								</li>
							</c:if>

							<c:forEach begin="${beginPage}" end="${endPage}" varStatus="i">
								<c:if test="${i.index == currentPage}">
										<c:set var="isActive" value="active"/>
								</c:if>
								<c:if test="${i.index != currentPage}">
										<c:set var="isActive" value=""/>
								</c:if>
								<li class="page-item ${isActive}">
									<a class="page-link" href="?${samePath}&page=${i.index}&search=${param.search}">
										${i.index}
									</a>
								</li>
							</c:forEach>

							<c:if test="${endPage < nextPage}">
								<li class="page-item">
									<a class="page-link" href="?${samePath}&page=${nextPage}&search=${param.search}" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
										<span class="sr-only">Next</span>
									</a>
								</li>
							</c:if>
						</ul>
					</c:if>

					<div class="row itemsPerPage">
						<div class="col-sm-3">
						</div>

						<div class="col-sm-3">
							<label class="pull-right control-label">Items per page:</label>
						</div>

						<div class="col-sm-2">
							<select class="pull-left" id="itemsPerPageSelect" onChange="window.location.href=this.value">
								<option
										<c:if test="${paginationParameters['routers']['itemsPerPage'] == 5}">
											selected
										</c:if>
										<c:choose>
											<c:when test="${not empty param.search}">
												value="?${samePath}&itemsPerPage=5&search=${param.search}">
											</c:when>
											<c:otherwise>
												value="?${samePath}&itemsPerPage=5">
											</c:otherwise>
										</c:choose>
										5
								</option>
								<option
										<c:if test="${paginationParameters['routers']['itemsPerPage'] == 10 || empty paginationParameters['routers']['itemsPerPage']}">
											selected
										</c:if>
										<c:choose>
											<c:when test="${not empty param.search}">
												value="?${samePath}&itemsPerPage=10&search=${param.search}">
											</c:when>
											<c:otherwise>
												value="?${samePath}&itemsPerPage=10">
											</c:otherwise>
										</c:choose>
										10
								</option>
								<option
										<c:if test="${paginationParameters['routers']['itemsPerPage'] == 15}">
											selected
										</c:if>
										<c:choose>
											<c:when test="${not empty param.search}">
												value="?${samePath}&itemsPerPage=15&search=${param.search}">
											</c:when>
											<c:otherwise>
												value="?${samePath}&itemsPerPage=15">
											</c:otherwise>
										</c:choose>
										15
								</option>
							</select>
						</div>

						<div class="col-sm-4">
						</div>
					</div>
					<!-- Pagination -->
				</c:if>
			</center>


			<%@ include file="/html/footer.html" %>
		</div>
	</body>
</html>

