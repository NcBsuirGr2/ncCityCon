<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Edit</title>
</head>
<%@ include file="/html/header.html" %>
<body>
<div class="panel panel-default">
	<div class="panel-heading">Edit</div>
            	<form class="form-horizontal" role="form" method="post" action="ConnectionEditServlet" >
                	<div class="form-group">
                    	<label for="port" class="col-xs-3 control-label">Port</label>
                        	<div class="col-xs-9">
                            	<input class="form-control" placeholder="Port" id="port" type="text" autofocus value="<c:out value="${connection.port}"/> ">
                            </div>
                    </div>   
                    <div class="form-group">
                        <label for="city" class="col-xs-3 control-label">City</label>
                             <div class="col-xs-9">
                                 <input class="form-control" placeholder="City" id="city" type="text" autofocus value="<c:out value="${connection.city}"/> ">
                             </div>
                    </div>
                    <div class="form-group">
                        <label for="router" class="col-xs-3 control-label">Router</label>
                             <div class="col-xs-9">
                                 <input class="form-control" placeholder="Router" id="router" type="text" autofocus value="<c:out value="${connection.router}"/> ">
                             </div>
                    </div>
                 </form>
	<a><button type="button" class="btn btn-primary" value="edit">Edit</button></a>
</div>
             
	
 </body>
<%@ include file="/html/footer.html" %>
</html>