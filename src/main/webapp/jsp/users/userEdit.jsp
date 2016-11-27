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
                    <h4>Edit user</h4>
                </div>
                <div class="panel-body">
                	<form class="form-horizontal" action="/user" method="POST" role="form" id="form">
                    	<div class="form-group">
                        	<label for="name" class="col-xs-3 control-label">Name:</label>
                        	<div class="col-xs-7">
                                <c:if test="${not empty editUser}">
                                	<input class="form-control simpleText" maxlength="15" autofocus required placeholder="Name" name="name" type="text" value="${editUser.name}">
                                </c:if>
                                <c:if test="${empty editUser}">
                                    <input class="form-control simpleText" maxlength="15" required placeholder="Name" name="name" type="text" value="${param.editName}">
                                </c:if>
                            </div>
                        </div>   
                        <div class="form-group">
                            <label for="login" class="col-xs-3 control-label">Login:</label>
                             <div class="col-xs-7">                                 
                                <c:if test="${not empty editUser}">
                                    <input class="form-control simpleText" maxlength="15" required placeholder="Login" name="login" type="text" value="${editUser.login}">
                                </c:if>
                                <c:if test="${empty editUser}">
                                    <input class="form-control simpleText" maxlength="15" required placeholder="Login" name="login" type="text" value="${param.editLogin}">
                                </c:if>
                             </div>
                        </div>
                        <div class="form-group">
                            <label for="login" class="col-xs-3 control-label">Password:</label>
                             <div class="col-xs-7">
                                 <input class="form-control asciiInput" maxlength="15" required placeholder="Password" name="password" type="text" value="${editUser.password}">
                             </div>
                        </div>
                        <div class="form-group">
                            <label for="email" class="col-xs-3 control-label">E-mail:</label>
                             <div class="col-xs-7">                                 
                                <c:if test="${not empty editUser}">
                                   <input class="form-control emailInput" maxlength="20" required placeholder="E-mail" name="email" type="text" value="${editUser.email}">
                                </c:if>
                                <c:if test="${empty editUser}">
                                    <input class="form-control emailInput" maxlength="20" required placeholder="E-mail" name="email" type="text" value="${param.editEmail}">
                                </c:if>
                             </div>
                        </div>
                        <div class="form-group">
                            <label for="group" class="col-xs-3 control-label">Group:</label>
                            <div class="col-xs-4">
                                <select class="form-control" id="group" name="group" form="form">
                                    <option value="admin" <c:if test="${editUser.group == 'admin' || param.editGroup == 'admin'}">selected</c:if>>Admin</option>
                                    <option value="operator" <c:if test="${editUser.group == 'operator' || param.editGroup == 'operator'}">selected</c:if>>Operator</option>
                                    <option value="guest" <c:if test="${editUser.group == 'guest' || param.editGroup == 'guest'}">selected</c:if>>Guest</option>
                                </select>
                            </div>
                        </div>
                        <input type="hidden" name="type" value="${param.action}"/>
                        <input type="hidden" name="id" value="${editUser.id}"/>
                     </form>
                </div>

                <div class="panel-footer">
                    <div class="row">
                        <div class="col-sm-6">
                            <button type="button" class="btn btn-primary btn-block center-block" data-toggle="modal" data-target=".changesDialog">Apply</button>
                        </div>
                        <div class="col-sm-6">
                            <a href="/users" class="btn btn-primary btn-block center-block">Back</a>
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