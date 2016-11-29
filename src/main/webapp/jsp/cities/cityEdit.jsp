<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">

	<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="/js/cityPages/suggestions.js"></script>
	<script src="/js/formValidation.js"></script>
	<link rel="icon" href="favicon.ico" />
	<title>Edit city</title>
</head>
<body>
<div class="content-wrapper">

	<%@ include file="/include/header.jsp" %>

	<div class="before-footer">
		<div class="alert alert-warning formAlert hide">
            Invalid form data.
        </div>
		<c:if test="${not empty param.errorType}">
			<div class="alert alert-warning alert-dismissible" style="margin-top: 20px">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<strong>Error!</strong>
				<c:choose>
					<c:when test="${param.errorType == 'dublicate'}">
						City with such name already exists.
					</c:when>
				</c:choose>
			</div>
		</c:if>

		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h4>Edit city</h4>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" action="/city" method="POST" role="form" id="form">
					<div class="form-group">
						<label for="country" class="col-xs-3 control-label">Country:</label>
						<div class="col-xs-7">
							<select class="form-control simpleText" id="country" name="countryName" form="form">
                                <option label=" "></option>
                                <c:if test="${not empty city or not empty param.countryName}">
                                    <c:choose>
                                        <c:when test="${not empty city}">
                                            <option value="${city.countryName}" selected>${city.countryName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${param.countryName}" selected>${param.countryName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </select>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-xs-3 control-label">City:</label>
						<div class="col-xs-7">
							<c:if test="${not empty editCity}">
								<input class="form-control simpleText" maxlength="15" required placeholder="Name" id="name" name="name" type="text" value="${editCity.name}">
							</c:if>
							<c:if test="${empty editCity}">
								<input class="form-control simpleText" maxlength="15" required placeholder="Name" id="name" name="name" type="text" value="${param.editName}">
							</c:if>
						</div>
					</div>

					<input type="hidden" name="action" value="${param.action}"/>
					<input type="hidden" name="id" value="${editCity.id}"/>
				</form>
			</div>

			<div class="panel-footer">
				<div class="row">
					<div class="col-sm-6">
						<button type="button" class="btn btn-primary btn-block center-block" data-toggle="modal" data-target=".changesDialog">Apply</button>
					</div>
					<div class="col-sm-6">
						<a href="/cities" class="btn btn-primary btn-block center-block">Back</a>
					</div>
				</div>
			</div>
			<!-- Save dialog modal -->
			<div class="modal fade changesDialog">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Confirm changes</h4>
						</div>

						<div class="modal-body">
							<p>Are you sure you want to apply changes?</p>
						</div>

						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
							<input type="submit" class="btn btn-primary" form="form" value="Yes"/>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>

	<%@ include file="/include/footer.html" %>

</div>
</body>


</html>