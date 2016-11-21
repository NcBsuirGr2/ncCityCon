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
		<script type="text/javascript" src="/js/connectionPages/connectionList.js"></script>

		<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<link rel="stylesheet" type="text/css" href="/css/connectionPages/connectionList.css">
		<link rel="icon" href="favicon.ico" />
		
		<title>Connections</title>
	</head>

	<body>
		<div class="content-wrapper">
			<%@ include file="/include/header.jsp" %>
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
					<center class="panel-heading">
						Connections
					</center>
					    <table class="selectable table table-striped table-bordered table-hover" style="table-layout: auto">
				     		<thead>
								<tr>
									<th>City-1</th>
									<th>SN-1</th>
									<th>id-1[temp]></th>
									<th>City-2</th>
									<th>SN-2</th>
									<th>id-2[temp]></th>									
							        <th class="hidden">id</th>								
				        		</tr>
				        	</thead>
				        	<tbody>
								<c:forEach items="${entityArray}" var="connection">
									<tr>
										<td>${connection.firstRouterSN}</td>
										<td>${connection.firstRouterCityName}</td>
										<td>${connection.firstRouterId}</td>
										<td>${connection.secondRouterSN}</td>
										<td>${connection.secondRouterCityName}</td>
										<td>${connection.secondRouterId}</td>
										<td class="hidden unique idField">${connection.id}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					<div class="panel-footer">
						<div class="row">

							<div class="col-sm-4">
								<a href="/connection?action=add">
									<button class="btn btn-primary center-block">Add</button>
								</a>
							</div>

							<div class="col-sm-4"> 
								<a class="editHref" href="#"> 
									<button class="btn btn-primary editBtn center-block">Edit</button>
								</a>
							</div>

							<div class="col-sm-4">
								<form action="/connection" id="deleteForm" method="POST">
									<input type="hidden" id="deleteId" name="id" value="-1">
									<input type="hidden" name="type" value="delete">
									<button type="button" class="btn btn-primary center-block deleteDialogBtn" data-toggle="modal" data-target=".deleteDialog">Delete</button>
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
											<p>Are you sure you want to delete selected connection?</p>
										</div>

										<div class="modal-footer">
											<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
											<input type=submit class="btn btn-primary" form="deleteForm" value="Delete"></input>
										</div>
									</div>
								</div>
							</div>

							<!-- Select connection modal -->
							<div class="modal fade selectConnectionModal" tabindex="-1" role="dialog" aria-hidden="true">
								<div class="modal-dialog modal-sm">
									<div class="modal-content">
										<div class="modal-body">
											<h4 class="modal-title">No connection selected</h4>
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
		    	<%@ include file="/include/pagination.jsp" %>
			</center>

			<%@ include file="/include/footer.html" %>
		</div>
	</body>
</html>

