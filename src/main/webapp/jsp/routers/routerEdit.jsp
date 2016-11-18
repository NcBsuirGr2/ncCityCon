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

<body>
<div class="content-wrapper">
<%@ include file="/html/header.jsp" %>
    <div class="before-footer">
<div class="panel panel-default">
	<div class="panel-heading">Edit</div>
            	<form class="form-horizontal" role="form" method="post" action="RouterEditServlet" >
                	<div class="form-group">
                    	<label for="name" class="col-xs-3 control-label">Name</label>
                        	<div class="col-xs-9">
                            	<input class="form-control" placeholder="Name" id="name" type="text" autofocus value="<c:out value="${router.name}"/> ">
                            </div>
                    </div>   
                    <div class="form-group">
                        <label for="SN" class="col-xs-3 control-label">SN</label>
                             <div class="col-xs-9">
                                 <input class="form-control" placeholder="SN" id="SN" type="text" autofocus value="<c:out value="${router.SN}"/> ">
                             </div>
                    </div>
                    <div class="form-group">
                        <label for="portsNum" class="col-xs-3 control-label">Port number</label>
                             <div class="col-xs-9">
                                 <input class="form-control" placeholder="Port number" id="portsNum" type="text" autofocus value="<c:out value="${router.portsNum}"/> ">
                             </div>
                    </div>
                    <div class="form-group">
                        <label for="cityId" class="col-xs-3 control-label">City Id</label>
                             <div class="col-xs-9">
                                 <input class="form-control" placeholder="cityId" id="cityId" type="text" autofocus value="<c:out value="${router.cityId}"/> ">
                             </div>
                    </div>
                 </form>
	<a><button type="button" class="btn btn-primary" value="edit">Edit</button></a>
</div>
 </div>
 <%@ include file="/html/footer.html" %>
 </div>
	
 </body>

</html>