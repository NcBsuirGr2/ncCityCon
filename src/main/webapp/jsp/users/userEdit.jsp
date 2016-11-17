<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/cityCon/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="/cityCon/css/style.css">
        <title>Edit user</title>
    </head>

    <%@ include file="/html/header.html" %>

    <body>
        <div class="panel panel-default">
        	<div class="panel-heading">Edit user</div>
            	<form class="form-horizontal" role="form" method="post" action="/cityCon/user" >
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
                        <label for="email" class="col-xs-3 control-label">E-mail</label>
                             <div class="col-xs-9">
                                 <input class="form-control" placeholder="E-mail" id="email" name="email" type="text" autofocus value="<c:out value="${users.email}"/> ">
                             </div>
                    </div>
                 </form>
        	<a><button type="button" class="btn btn-primary" value="edit" name="edit">Edit</button></a>
        </div>
     </body>

    <%@ include file="/html/footer.html" %>
</html>