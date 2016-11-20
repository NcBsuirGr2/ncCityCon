<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1, maximum-scale=1">

		<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/js/selectEntity.js"></script>
		<script type="text/javascript" src="/js/userPages/userList.js"></script>

		<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<link rel="stylesheet" type="text/css" href="/css/users/userList.css">
		<link rel="icon" href="favicon.ico" />
		
		<title>Users</title>
	</head>

	<body>
		<div class="content-wrapper">
			<%@ include file="/html/header.jsp" %>
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
					<center class="panel-heading">
						List Users
					</center>
					    <table class="selectable table table-striped table-bordered table-hover" style="table-layout: auto">
				     		<thead>
								<tr>
									<th>
										<c:set var="newAsc" value="true"/>
										<c:if test="${empty param.asc or (param.asc == true and param.sortBy == 'name')}">
											<c:set var="newAsc" value="false"/>
										</c:if>

										<a href="?itemsPerPage=${param.itemsPerPage}&page=${param.page}&sortBy=name&asc=${newAsc}">
											Name 
											<c:if test="${empty param.sortBy || param.sortBy == 'name'}">
												<c:if test="${newAsc == true}">&#9660;</c:if>
												<c:if test="${newAsc == false}">&#9650;</c:if>
											</c:if>											
										</a>
									</th>
									<th>
										<c:set var="newAsc" value="true"/>
										<c:if test="${not empty param.asc && param.asc == true && param.sortBy == 'login'}">
											<c:set var="newAsc" value="false"/>
										</c:if>
										<a href="?itemsPerPage=${param.itemsPerPage}&page=${param.page}&sortBy=login&asc=${newAsc}">
											Login 
											<c:if test="${param.sortBy == 'login'}">
												<c:if test="${newAsc == true}">&#9660;</c:if>
												<c:if test="${newAsc == false}">&#9650;</c:if>
											</c:if>
										</a>
									</th>
									<th>
										<c:set var="newAsc" value="true"/>
										<c:if test="${not empty param.asc && param.asc == true && param.sortBy == 'group'}">
											<c:set var="newAsc" value="false"/>
										</c:if>

										<a href="?itemsPerPage=${param.itemsPerPage}&page=${param.page}&sortBy=group&asc=${newAsc}">
											Group 
											<c:if test="${param.sortBy == 'group'}">
												<c:if test="${newAsc == true}">&#9660;</c:if>
												<c:if test="${newAsc == false}">&#9650;</c:if>
											</c:if>
										</a>
									</th>
									<th>
										<c:set var="newAsc" value="true"/>
										<c:if test="${not empty param.asc && param.asc == true && param.sortBy == 'email'}">
											<c:set var="newAsc" value="false"/>
										</c:if>

										<a href="?itemsPerPage=${param.itemsPerPage}&page=${param.page}&sortBy=email&asc=${newAsc}">
											E-main 
											<c:if test="${param.sortBy == 'email'}">
												<c:if test="${newAsc == true}">&#9660;</c:if>
												<c:if test="${newAsc == false}">&#9650;</c:if>
											</c:if>
										</a>
									</th>
									<th>
										<c:set var="newAsc" value="true"/>
										<c:if test="${not empty param.asc && param.asc == true && param.sortBy == 'createDate'}">
											<c:set var="newAsc" value="false"/>
										</c:if>

										<a href="?itemsPerPage=${param.itemsPerPage}&page=${param.page}&sortBy=createDate&asc=${newAsc}">
											Join date 
											<c:if test="${param.sortBy == 'createDate'}">
												<c:if test="${newAsc == true}">&#9660;</c:if>
												<c:if test="${newAsc == false}">&#9650;</c:if>
											</c:if>
										</a>
									</th>
							        <th class="hidden">id</th>								
				        		</tr>
				        	</thead>
				        	<tbody>
								<c:forEach items="${entityArray}" var="users">
									<tr>
										<td>${users.name}</td>
										<td class="unique">${users.login}</td>
										<td>${users.group}</td>
										<td>${users.email}</td>
										<td>${users.createDate}</td>
										<td class="hidden idField">${users.id}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					<div class="panel-footer">
						<div class="row">

							<div class="col-sm-4">
								<a href="/user?action=add">
									<button class="btn btn-primary center-block">Add</button>
								</a>
							</div>

							<div class="col-sm-4"> 
								<a class="editHref" href="#"> 
									<button class="btn btn-primary center-block">Edit</button>
								</a>
							</div>

							<div class="col-sm-4">
								<form action="/user" id="deleteForm" method="POST">
									<input type="hidden" id="deleteId" name="id" value="-1">
									<input type="hidden" name="type" value="delete">
									<button type="button" class="btn btn-primary deleteDialogBtn" data-toggle="modal" data-target=".deleteDialog">Delete</button>
						    	</form>		
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
											<input type=submit class="btn btn-primary" form="deleteForm" value="Delete"></input>
										</div>
									</div>
								</div>
							</div>

							<!-- Select user modal -->
							<div class="modal fade selectUserModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
								<div class="modal-dialog modal-sm">
									<div class="modal-content">
										<div class="modal-body">
											<h4 class="modal-title">No user selected</h4>
										</div>

										<div class="modal-footer">
											<button type="button" class="btn btn-secondary" data-dismiss="modal">Ok</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					</div>
					<div class="col-sm-1">
					</div>
				</div>
			</div>

			<center class="before-footer">				
		    	<c:if test="${endPage > 1}">
					<ul class="pagination">
						<c:if test="${beginPage > previousPage}">
							<li class="page-item">
								<a class="page-link" href="?itemsPerPage=${param.itemsPerPage}&page=${previousPage}&sortBy=${param.sortBy}&asc=${param.asc}" aria-label="Previous">
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
								<a class="page-link" href="?itemsPerPage=${param.itemsPerPage}&page=${i.index}&sortBy=${param.sortBy}&asc=${param.asc}">
									${i.index}
								</a>
							</li>
						</c:forEach>

						<c:if test="${endPage < nextPage}">
							<li class="page-item">
								<a class="page-link" href="?itemsPerPage=${param.itemsPerPage}&page=${nextPage}&sortBy=${param.sortBy}&asc=${param.asc}" aria-label="Next">
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
						<label class="pull-right control-label">Users per page:</label>
					</div>

					<div class="col-sm-2">
						<select class="pull-left" id="itemsPerPageSelect" onChange="window.location.href=this.value">
				            <option <c:if test="${param.itemsPerPage == 5}">selected</c:if>  value="?itemsPerPage=5&page=${param.page}&sortBy=${param.sortBy}&asc=${param.asc}">5</option>
				            <option <c:if test="${param.itemsPerPage == 10 || empty param.itemsPerPage}">selected</c:if> value="?itemsPerPage=10&page=${param.page}&sortBy=${param.sortBy}&asc=${param.asc}">10</option>
				            <option <c:if test="${param.itemsPerPage == 15}">selected</c:if> value="?itemsPerPage=15&page=${param.page}&sortBy=${param.sortBy}&asc=${param.asc}">15</option>
				        </select>
			        </div>

			        <div class="col-sm-4">
					</div>
		        </div>
			</center>

			<%@ include file="/html/footer.html" %>
		</div>
	</body>
</html>

