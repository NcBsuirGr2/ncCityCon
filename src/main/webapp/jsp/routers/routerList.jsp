<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1, maximum-scale=1">

	<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>	
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/selectEntity.js"></script>
	<script type="text/javascript" src="/js/routerPages/routerList.js"></script>

	<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
	<title>Router List</title>
</head>

<body>
<div class="content-wrapper">
<%@ include file="/include/header.jsp" %>
	<div class="before-footer">

		<div class="panel panel-default">
			<div class="panel-heading">Router List</div>
			<table class="selectable table table-striped table-bordered table-hover" style="table-layout: auto">
				<thead>
				<tr>
					<th>Name</th>
					<th>SN</th>
					<th>Port number</th>
					<th>Status</th>
				</tr>
				</thead>

				<tbody>
					<c:forEach items="${entityArray}" var="router">
						<td class="hidden idField">${router.id}</td>
						<td>${router.name}</td>
						<td class="unique">${router.SN}</td>
						<td>${router.portsNum}</td>
						<td>${router.active}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="panel-footer">
						<div class="row">

							<div class="col-sm-4">
								<c:if test="${showSystemUnitsOperationBtns}">
									<a href="/connection?action=add">
										<button class="btn btn-primary center-block">Add</button>
									</a>
								</c:if>
							</div>

							<div class="col-sm-4"> 
								<c:if test="${showSystemUnitsOperationBtns}">
									<a class="editHref" href="#"> 
										<button class="btn btn-primary editBtn center-block">Edit</button>
									</a>
								</c:if>
							</div>

							<div class="col-sm-4">
								<c:if test="${showSystemUnitsOperationBtns}">
									<form action="/connection" id="deleteForm" method="POST">
										<input type="hidden" id="deleteId" name="id" value="-1">
										<input type="hidden" name="type" value="delete">
										<button type="button" class="btn btn-primary center-block deleteDialogBtn" data-toggle="modal" data-target=".deleteDialog">Delete</button>
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
											<p>Are you sure you want to delete selected router? All it's connections will be also deleted</p>
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


	</div>


	<%@ include file="/html/footer.html" %>
</div>
</body>
</html>

