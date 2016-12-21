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
		<script type="text/javascript" src="/js/connectionPages/connectionList.js"></script>

		<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<link rel="stylesheet" type="text/css" href="/css/connectionPages/connectionList.css">
		<link rel="icon" href="favicon.ico" />
		
		<title>Connections</title>
	</head>
	
	<c:choose>
		<c:when test="${not empty param.SN}">
			<c:set var="sameSelect" value="SN=${param.SN}"/>
		</c:when>
		<c:when test="${not empty param.city and not empty param.country}">
			<c:set var="sameSelect" value="country=${param.country}&city=${param.city}"/>
		</c:when>
	</c:choose>

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
					       	New connection has been created.
					    </c:when>
					    <c:when test="${param.success == 'delete'}">
					        Connection has been deleted.
					    </c:when>
					    <c:when test="${param.success == 'edit'}">
					        Connection has been modified.
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
						<div>Connections</div>
						<div>
							<form action="/connections" method="get" name="form" onsubmit="return true;">
								<img src="/img/search.png">
								<input class="panel-search" name="search" type="text" id="search" size="10" maxlength="15">
							</form>
						</div>
					</div>
						<c:if test="${not empty entityArray}">
							<table class="selectable table table-striped table-bordered table-hover" style="table-layout: auto">
								<thead>
									<tr>
										<th>
											<citycon:sortBy asc="${paginationParameters['connections']['asc']}"
															sortByIs="${paginationParameters['connections']['sortBy']}"
															sortByNeed="City1"
															value="Left city"
															prefix="${sameSelect}"/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['connections']['asc']}"
															sortByIs="${paginationParameters['connections']['sortBy']}"
															sortByNeed="City2"
															value="Right city"
															prefix="${sameSelect}"/>

										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['connections']['asc']}"
															sortByIs="${paginationParameters['connections']['sortBy']}"
															sortByNeed="SN1"
															value="Left SN"
															prefix="${sameSelect}"/>
										</th>
										<th>
											<citycon:sortBy asc="${paginationParameters['connections']['asc']}"
															sortByIs="${paginationParameters['connections']['sortBy']}"
															sortByNeed="SN2"
															value="Right SN"
															prefix="${sameSelect}"/>
										</th>

										<th class="hidden">id</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${entityArray}" var="connection">
										<tr>
											<td <c:if test="${not connection.firstRouter.active}">class="danger"</c:if> >
												<a href="/statistic/cities/${connection.firstRouter.city.countryName}/${connection.firstRouter.city.name}">
													${connection.firstRouter.city.name}
												</a>
											</td>
											<td <c:if test="${not connection.secondRouter.active}">class="danger"</c:if>>
												<a href="/statistic/cities/${connection.secondRouter.city.countryName}/${connection.secondRouter.city.name}">
													${connection.secondRouter.city.name}
												</a>
											</td>
											<td <c:if test="${not connection.firstRouter.active}">class="danger"</c:if> >
												<a href="/statistic/routers/${connection.firstRouter.SN}">
													${connection.firstRouter.SN}
												</a>
											</td>
											<td <c:if test="${not connection.secondRouter.active}">class="danger"</c:if>>
												<a href="/statistic/routers/${connection.secondRouter.SN}">
														${connection.secondRouter.SN}
												</a>
											</td>
											<td class="hidden unique idField">${connection.id}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						<c:if test="${empty entityArray}">
							<h4 class="text-center">There is no connections in this router</h4>
						</c:if>

						<div class="panel-footer">
							<c:if test="${not empty entityArray}">
								<div class="row">

									<div class="col-sm-4">
										<c:if test="${showSystemUnitsOperationBtns}">
											<a href="/connection?${sameSelect}&action=add" class="btn btn-primary center-block">Add</a>
										</c:if>
									</div>

									<div class="col-sm-4">
										<c:if test="${showSystemUnitsOperationBtns}">
											<a href="#" class="btn btn-primary center-block editHref">Edit</a>
										</c:if>
									</div>

									<div class="col-sm-4">
										<c:if test="${showSystemUnitsOperationBtns}">
											<form action="/connection" id="deleteForm" method="POST">
												<input type="hidden" id="deleteId" name="id" value="-1">
												<input type="hidden" name="action" value="delete">
												<input type="hidden" id="sameSelect" name="sameSelect" value="${sameSelect}">
												<button type="button" class="btn btn-primary center-block btn-block deleteDialogBtn" data-toggle="modal" data-target=".deleteDialog">Delete</button>
											</form>
										</c:if>
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
													<p>Are you sure you want to delete selected connection?</p>
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
											<a href="/connection?${sameSelect}&action=add" class="btn btn-primary center-block">Add</a>
										</div>
										<div class="col-sm-2"></div>
										<div class="col-sm-3">
											<a href="/connectionss" class="btn btn-primary center-block">Back</a>
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
						</div>
					</div>	<!-- Panel end -->
				</div>

				<div class="col-sm-1">
				</div>
			 
			</div> <!-- End of panel row -->
			
			<center class="before-footer">
				<c:if test="${not empty entityArray}">
					<!-- Pagination block -->
					<c:choose>
						<c:when test="${not empty param.SN}">
							<c:set var="paginationPath" value="?SN=${param.SN}&sortBy=${param.sortBy}&asc=${param.asc}"/>
						</c:when>
						<c:otherwise>
							<c:set var="paginationPath" value="?country=${param.country}&city=${param.city}&sortBy=${param.sortBy}&asc=${param.asc}"/>
						</c:otherwise>
					</c:choose>

					<c:if test="${endPage > 1}">
						<ul class="pagination">
							<c:if test="${beginPage > previousPage}">
								<li class="page-item">
									<a class="page-link" href="?${sameSelect}&page=${previousPage}" aria-label="Previous">
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
									<a class="page-link" href="?${sameSelect}&page=${i.index}">
										${i.index}
									</a>
								</li>
							</c:forEach>

							<c:if test="${endPage < nextPage}">
								<li class="page-item">
									<a class="page-link" href="?${sameSelect}&page=${nextPage}" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
										<span class="sr-only">Next</span>
									</a>
								</li>
							</c:if>
						</ul>
					</c:if>

					<div class="row">
						<div class="col-sm-3">
						</div>

						<div class="col-sm-3">
							<label class="pull-right control-label">Items per page:</label>
						</div>

						<div class="col-sm-2">
							<select class="pull-left" id="itemsPerPageSelect" onChange="window.location.href=this.value">
								<option <c:if test="${paginationParameters['connections']['itemsPerPage'] == 5}">selected</c:if>  value="?${sameSelect}&itemsPerPage=5">5</option>
								<option <c:if test="${param.itemsPerPage == 10 || empty param.itemsPerPage}">selected</c:if> value="?${sameSelect}&itemsPerPage=10">10</option>
								<option <c:if test="${param.itemsPerPage == 15}">selected</c:if> value="?${sameSelect}&itemsPerPage=15">15</option>
							</select>
						</div>

						<div class="col-sm-4">
						</div>
					</div>
					<!-- Pagination -->
				</c:if>

			</center>

			<%@ include file="/include/footer.html" %>
		</div>
	</body>
</html>

