<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

	<title>Connections</title>
</head>
<body>

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
					<div class="panel-heading">List Users</div>
			    		<table class="selectable table table-striped" style="table-layout: auto">
			     		<thead>
							<tr>
								<th>Port</th>
						   		<th>City</th>
						        <th>Router</th>
						        <th class="hidden">id</th>								
			        		</tr>
			        	</thead>
        				<tbody>
            				<c:forEach items="${entityArray}" var="connection">
                				<tr>
                    			<!--TODO: указать точные поля -->
                    				<td>${connection}</td>
                    				<td>${connection}</td>
                    				<td>${connection}</td>
                				</tr>
            				</c:forEach>
        				</tbody>
    			</table>
				<div class="panel-footer">
					<div class="row">

						<div class="col-sm-4">
							<a href="/connection?action=add">
								<button class="btn btn-primary">Add</button>
							</a>
						</div>

						<div class="col-sm-4"> 
							<a class="editHref" href="#"> 
								<button class="btn btn-primary">Edit</button>
							</a>
						</div>

						<div class="col-sm-4">
							<form action="/connection" id="deleteForm" method="POST">
								<input type="hidden" id="deleteId" name="id" value="-1">
								<input type="hidden" name="type" value="delete">
								<input type=submit class="btn btn-primary" value="Delete"></input>
					    	</form>		
						</div>

					</div>
				</div>
			</div>
		</div>
		<%@ include file="/html/footer.html" %>
</div>
		
		

</body>
</html>

