<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
            <link rel="stylesheet" type="text/css" href="/css/style.css">
            <link rel="stylesheet" href="/css/easy-autocomplete.min.css"> 
            <link rel="stylesheet" type="text/css" href="/css/connectionPages/connectionEdit.css">

            <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
            <script src="/js/jquery.easy-autocomplete.min.js"></script>

            <script src="/js/formValidation.js"></script>
            <script src="/js/connectionPages/autocomplete.js"></script>

            <link rel="icon" href="favicon.ico" />

            <title>Edit connection</title>
        </head>

    <body>
        <div class="content-wrapper">
            <%@ include file="/include/header.jsp" %>
            <div class="alert alert-warning formAlert hide">
                Invalid form data.
            </div>
            <c:if test="${not empty param.errorType}">
                <div class="alert alert-warning alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Error!</strong> 
                    <c:choose>
                        <c:when test="${param.errorType == 'invalidSN'}">
                            Router with such SN doesn't exist.
                        </c:when>
                        <c:when test="${param.errorType == 'noFreePorts'}">
                            Routers have no ports to connect to.
                        </c:when>
                    </c:choose>
                </div>
            </c:if> 

            <div class="before-footer">
                <div class="panel panel-default">
                	<div class="panel-heading text-center">
                        <h4>Edit connection</h4>
                    </div>
                    <div class="panel-body">
                    	<form class="form-horizontal" id="form" method="POST" action="/connection" >

                            <div class="form-group row">
                                <label for="port" class="col-xs-3 control-label">City-1:</label>
                                    <div class="col-xs-7">
                                        <input class="form-control" type="text" id="city1" value="${connection.firstRouterCityName}">  
                                    </div>
                            </div> 
                        	<div class="form-group row">
                            	<label for="port" class="col-xs-3 control-label">SN-1:</label>
                                	<div class="col-xs-7">
                                        <c:if test="${not empty connection.firstRouterSN}">
                                    	   <input class="form-control simpleText" name="SN1" placeholder="SN1" type="text" value="${connection.firstRouterSN}">
                                        </c:if>
                                        <c:if test="${empty connection.firstRouterSN}">
                                           <input class="form-control simpleText" id="SN1" name="SN1" placeholder="SN1" type="text" value="${param.SN1}">
                                        </c:if>
                                    </div>
                            </div>   
  
                            <div class="form-group row">
                                <label for="port" class="col-xs-3 control-label">City-2:</label>
                                    <div class="col-xs-7">
                                        <input class="form-control" type="text" id="city2" value="${connection.secondRouterCityName}">  
                                    </div>
                            </div> 
                            <div class="form-group row">
                                <label for="city" class="col-xs-3 control-label">SN-2:</label>
                                     <div class="col-xs-7">                                         
                                        <c:if test="${not empty connection.firstRouterSN}">
                                            <input class="form-control simpleText" name="SN2" placeholder="SN2" type="text" value="${connection.secondRouterSN}">
                                        </c:if>
                                        <c:if test="${empty connection.firstRouterSN}">
                                           <input class="form-control simpleText" id="SN2" name="SN2" placeholder="SN2" type="text" value="${param.SN2}">
                                        </c:if>
                                     </div>
                            </div>
                            <input type="hidden" name="type" value="${param.action}"/>
                            <input type="hidden" name="id" value="${connection.id}"/>
                        </form>
                    </div>

                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-sm-6">
                                <button type="button" class="btn btn-primary btn-block center-block" data-toggle="modal" data-target=".changesDialog">Apply</button>
                            </div>
                            <div class="col-sm-6">
                            <!-- Implement Back btn -->
                                <a href="/connections" class="btn btn-primary btn-block center-block">Back</a>
                            </div>
                        </div>
                    </div>    

                    <!-- Apply dialog modal -->
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
                                    <input type="submit" class="btn btn-primary" form="form" value="Yes"/> 
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
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