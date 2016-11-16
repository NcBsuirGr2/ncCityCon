<%--
 * @author  Karina
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/cityCon/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/cityCon/css/style.css">
<title>Edit</title>
</head>
<%@ include file="/html/header.html" %>
<body>
<div class="panel panel-default">
	<div class="panel-heading">Edit</div>
            	<form class="form-horizontal" role="form" method="post" action="#" >
                	<div class="form-group">
                    	<label for="name" class="col-xs-3 control-label">Name</label>
                        	<div class="col-xs-9">
                            	<input class="form-control" placeholder="Name" id="name" name="name" type="text" autofocus>
                            </div>
                    </div>   
                    <div class="form-group">
                        <label for="login" class="col-xs-3 control-label">Login</label>
                             <div class="col-xs-9">
                                 <input class="form-control" placeholder="Login" id="login" name="login" type="text" autofocus>
                             </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-xs-3 control-label">E-mail</label>
                             <div class="col-xs-9">
                                 <input class="form-control" placeholder="E-mail" id="email" name="email" type="text" autofocus>
                             </div>
                    </div>
                 </form>
	<a><input class="btn btn-lg btn-primary btn-default" type="submit" value="Save" name="edit"></a>
</div>
             
	
 </body>
<%@ include file="/html/footer.html" %>
</html>