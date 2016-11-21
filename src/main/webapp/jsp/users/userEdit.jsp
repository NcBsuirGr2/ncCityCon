<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">

        <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="/js/userPages/userEdit.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link rel="icon" href="favicon.ico" />
        <title>Edit user</title>
    </head>
<body>
    <div class="content-wrapper">

    <%@ include file="/include/header.jsp" %>

        <div class="before-footer">
            <div class="panel panel-default">
            	<div class="panel-heading text-center">
                    <h4>Edit user</h4>
                </div>
                <div class="panel-body">
                	<form class="form-horizontal" action="/user" method="POST" role="form" id="form">
                    	<div class="form-group">
                        	<label for="name" class="col-xs-3 control-label">Name:</label>
                        	<div class="col-xs-9">
                            	<input class="form-control" required placeholder="Name" id="name" name="name" type="text" value="${editUser.name}">
                            </div>
                        </div>   
                        <div class="form-group">
                            <label for="login" class="col-xs-3 control-label">Login:</label>
                             <div class="col-xs-9">
                                 <input class="form-control" required placeholder="Login" id="login" name="login" type="text" value="${editUser.login}">
                             </div>
                        </div>
                        <div class="form-group">
                            <label for="login" class="col-xs-3 control-label">Password:</label>
                             <div class="col-xs-9">
                                 <input class="form-control" required placeholder="Password" id="password" name="password" type="text" value="${editUser.password}">
                             </div>
                        </div>
                        <div class="form-group">
                            <label for="email" class="col-xs-3 control-label">E-mail:</label>
                             <div class="col-xs-9">
                                 <input class="form-control" pattern=".+@.+\..+" required placeholder="E-mail" id="email" name="email" type="email" value="${editUser.email}">
                             </div>
                        </div>
                        <div class="form-group">
                            <label for="group" class="col-xs-3 control-label">Group:</label>
                            <div class="col-xs-9">
                                <select class="form-control" id="group" name="group" form="form">
                                    <option value="admin" <c:if test="${editUser.group == 'admin'}">selected</c:if>>Admin</option>
                                    <option value="operator" <c:if test="${editUser.group == 'operator'}">selected</c:if>>Operator</option>
                                    <option value="guest" <c:if test="${editUser.group == 'guest'}">selected</c:if>>Guest</option>
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