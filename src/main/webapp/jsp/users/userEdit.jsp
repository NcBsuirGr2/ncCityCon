<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="/js/formValidation.js"></script>

        <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <link rel="icon" href="favicon.ico" />
        <title>Edit user</title>
    </head>
<body>
    <div class="content-wrapper">

    <%@ include file="/include/header.jsp" %>
        <div class="alert alert-warning formAlert hide">
            Invalid form data.
        </div>
        <div class="before-footer">
            <c:if test="${not empty param.errorType}">
                <div class="alert alert-warning alert-dismissible" style="margin-top: 20px">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Error!</strong> 
                    <c:choose>
                        <c:when test="${param.errorType == 'dublicate'}">
                            User with such login or e-mail already exists.
                        </c:when>
                    </c:choose>
                </div>
            </c:if> 

            <div class="panel panel-default">
            	<div class="panel-heading text-center">
                    Edit user
                </div>
                <div class="panel-body">
                	<form class="form-horizontal" action="/user" method="POST" role="form" id="form">
                    	<div class="form-group">
                        	<label for="name" class="col-xs-3 control-label">Name:</label>
                        	<div class="col-xs-7">
                                <c:choose>
                                    <c:when test="${sessionScope.user.login eq param.login}">
                                        <input class="form-control simpleText" maxlength="15" required placeholder="Name" name="name" type="text" value="${editUser.name}">
                                    </c:when>
                                    <c:when test="${empty editUser}">
                                        <input class="form-control simpleText" maxlength="15" required placeholder="Name" name="name" type="text" value="${param.editName}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="form-control-static">${editUser.name}</div>
                                        <input type="hidden" name="name" value="${editUser.name}">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>   
                        <div class="form-group">
                             <label for="login" class="col-xs-3 control-label">Login:</label>
                             <div class="col-xs-7">
                                 <c:choose>
                                     <c:when test="${sessionScope.user.login eq param.login}">
                                         <input class="form-control simpleText" maxlength="15" required placeholder="Login" name="login" type="text" value="${editUser.login}">
                                     </c:when>
                                     <c:when test="${empty editUser}">
                                         <input class="form-control simpleText" maxlength="15" required placeholder="Login" name="login" type="text" value="${param.editLogin}">
                                     </c:when>
                                     <c:otherwise>
                                         <div class="form-control-static">${editUser.login}</div>
                                         <input type="hidden" name="login" value="${editUser.login}">
                                     </c:otherwise>
                                 </c:choose>
                             </div>
                        </div>
                        <c:choose>
                            <c:when test="${sessionScope.user.login eq param.login || param.action == 'add'}">
                                <div class="form-group">
                                    <label for="login" class="col-xs-3 control-label">Password:</label>
                                    <div class="col-xs-7">
                                        <input class="form-control asciiInput" maxlength="15" required placeholder="Password" name="password" type="password" value="${editUser.password}">
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" name="password" value="${editUser.password}">
                            </c:otherwise>
                        </c:choose>
                        <div class="form-group">
                             <label for="email" class="col-xs-3 control-label">E-mail:</label>
                             <div class="col-xs-7">
                                 <c:choose>
                                     <c:when test="${sessionScope.user.login eq param.login}">
                                         <input class="form-control emailInput" maxlength="20" required placeholder="E-mail" name="email" type="text" value="${editUser.email}">
                                     </c:when>
                                     <c:when test="${empty editUser}">
                                         <input class="form-control emailInput" maxlength="20" required placeholder="E-mail" name="email" type="text" value="${param.editEmail}">
                                     </c:when>
                                     <c:otherwise>
                                         <div class="form-control-static">${editUser.email}</div>
                                         <input type="hidden" name="email" value="${editUser.email}">
                                     </c:otherwise>
                                 </c:choose>
                             </div>
                        </div>
                        <div class="form-group">
                            <label for="group" class="col-xs-3 control-label">Group:</label>
                            <div class="col-xs-3">
                                <c:choose>
                                    <c:when test="${ sessionScope.user.group == 'admin' && sessionScope.user.login ne param.login}">
                                        <select class="form-control" id="group" name="group" form="form">
                                            <option value="admin" <c:if test="${editUser.group == 'admin' || param.editGroup == 'admin'}">selected</c:if>>Admin</option>
                                            <option value="operator" <c:if test="${editUser.group == 'operator' || param.editGroup == 'operator'}">selected</c:if>>Operator</option>
                                            <option value="guest" <c:if test="${editUser.group == 'guest' || param.editGroup == 'guest'}">selected</c:if>>Guest</option>
                                        </select>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="form-control-static">${editUser.group}</div>
                                        <input type="hidden" name="group" value="${editUser.group}">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <input type="hidden" name="type" value="${param.action}"/>
                        <input type="hidden" name="id" value="${editUser.id}"/>
                     </form>
                </div>

                <div class="panel-footer">
                    <div class="row">
                        <c:choose>
                            <c:when test="${sessionScope.user.login eq param.login}">
                                <div class="col-sm-4">
                                    <button type="button" class="btn btn-primary btn-block center-block" data-toggle="modal" data-target=".changesDialog">Apply</button>
                                </div>
                                <div class="col-sm-4">
                                    <a href="/users" class="btn btn-primary btn-block center-block">Back</a>
                                </div>
                                <div class="col-sm-4">
                                    <form action="/user" id="deleteForm" method="POST">
                                        <input type="hidden" id="deleteId" name="id" value="${editUser.id}">
                                        <input type="hidden" name="type" value="delete">
                                        <button type="button" class="btn btn-primary center-block btn-block deleteDialogBtn" data-toggle="modal" data-target=".deleteDialog">Delete account</button>
                                    </form>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="col-sm-6">
                                    <button type="button" class="btn btn-primary btn-block center-block" data-toggle="modal" data-target=".changesDialog">Apply</button>
                                </div>
                                <div class="col-sm-6">
                                    <a href="/users" class="btn btn-primary btn-block center-block">Back</a>
                                </div>
                            </c:otherwise>
                        </c:choose>

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
                                <p>Are you sure you want to delete your account?</p>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                <input type=submit class="btn btn-primary" form="deleteForm" value="Delete">
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