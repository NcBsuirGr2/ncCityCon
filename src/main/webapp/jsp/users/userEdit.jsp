<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <title>Edit user</title>
    </head>

    <%@ include file="/html/header.html" %>

    <body>
        <div class="panel panel-default">
        	<div class="panel-heading">
                Edit user
            </div>
            <div class="panel-body">
            	<form class="form-horizontal" role="form" method="put" action="/user" >
                	<div class="form-group">
                    	<label for="name" class="col-xs-3 control-label">Name</label>
                        	<div class="col-xs-9">
                            	<input class="form-control" placeholder="Name" id="name" name="name" type="text" autofocus value="<c:out value="${users.name}"/> ">
                            </div>
                    </div>   
                    <div class="form-group">
                        <label for="login" class="col-xs-3 control-label">Login</label>
                             <div class="col-xs-9">
                                 <input class="form-control" placeholder="Login" id="login" name="login" type="text" autofocus value="<c:out value="${users.login}"/> ">
                             </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-xs-3 control-label">e-mail</label>
                             <div class="col-xs-9">
                                 <input class="form-control" placeholder="e-mail" id="email" name="email" type="text" autofocus value="<c:out value="${users.email}"/> ">
                             </div>
                    </div>
                 </form>
            </div>

            <div class="panel-footer">
                <div class="row">
                    <div class="col-sm-6">
                        <button class="btn btn-primary btn-block center-block">Save</button>
                    </div>
                    <div class="col-sm-6">
                        <a href="/users" class="btn btn-primary btn-block center-block">Back</a>
                    </div>
                </div>
            </div>    

        </div>
     </body>

    <%@ include file="/html/footer.html" %>
</html>