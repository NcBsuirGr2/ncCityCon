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
            <script src="/js/connectionPages/connectionEdit.js"></script>

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
                                <label for="port" class="col-xs-3 control-label">Country-1:</label>
                                <div class="col-xs-7">
                                    <select class="form-control" id="country1" name="country1" form="form">
                                        <option label=" "></option>
                                        <option value="Belarus" 
                                            <c:if test="${connection.firstRouterCountry == 'Belarus' or param.firstCountry == 'Belarus'}">    selected
                                            </c:if>
                                            >Belarus</option>
                                        <option value="Moldova"
                                            <c:if test="${connection.firstRouterCountry == 'Moldova' or param.firstCountry == 'Moldova'}">    selected
                                            </c:if>
                                            >Moldova</option>
                                        </option>
                                        <option value="Poland"
                                            <c:if test="${connection.firstRouterCountry == 'Poland' or param.firstCountry == 'Poland'}">    selected
                                            </c:if>
                                            >Poland
                                        </option>
                                        <option value="Ukraine"
                                            <c:if test="${connection.firstRouterCountry == 'Ukraine' or param.firstCountry == 'Ukraine'}">    selected
                                            </c:if>
                                            >Ukraine
                                        </option>
                                        <option value="Russia"
                                            <c:if test="${connection.firstRouterCountry == 'Russia' or param.firstCountry == 'Russia'}">    selected
                                            </c:if>
                                            >Russia
                                        </option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="port" class="col-xs-3 control-label">City-1:</label>
                                <div class="col-xs-7">
                                    <select class="form-control" id="city1" name="city1" form="form">
                                        <option label=" "></option>
                                        <c:if test="${not empty connection or not empty param.firstCity}">
                                            <c:choose>
                                                <c:when test="${not empty connection}">
                                                    <option value="${connection.firstRouterCityName}" selected>${connection.firstRouterCityName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${param.firstCity}" selected>${param.firstCity}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </select>
                                </div>
                            </div> 
                        	<div class="form-group row">
                            	<label for="port" class="col-xs-3 control-label">SN-1:</label>
                            	<div class="col-xs-7">
                                    <select class="form-control" id="SN1" name="SN1" form="form">
                                        <option label=" "></option>
                                        <c:if test="${not empty connection or not empty param.SN1}">
                                            <c:choose>
                                                <c:when test="${not empty connection}">
                                                    <option value="${connection.firstRouterSN}" selected>${connection.firstRouterSN}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${param.SN1}" selected>${param.SN1}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </select>
                                </div>
                            </div>   
                            <br>
                            <br>
                            <div class="form-group row">
                                <label for="port" class="col-xs-3 control-label">Country-2:</label>
                                <div class="col-xs-7">
                                    <select class="form-control" id="country1" name="country1" form="form">
                                        <option label=" "></option>
                                        <option value="Belarus" 
                                            <c:if test="${connection.secondRouterCountry == 'Belarus' or param.secondCountry == 'Belarus'}">    selected
                                            </c:if>
                                            >Belarus</option>
                                        <option value="Moldova"
                                            <c:if test="${connection.secondRouterCountry == 'Moldova' or param.secondCountry == 'Moldova'}">    selected
                                            </c:if>
                                            >Moldova</option>
                                        </option>
                                        <option value="Poland"
                                            <c:if test="${connection.secondRouterCountry == 'Poland' or param.secondCountry == 'Poland'}">    selected
                                            </c:if>
                                            >Poland
                                        </option>
                                        <option value="Ukraine"
                                            <c:if test="${connection.secondRouterCountry == 'Ukraine' or param.secondCountry == 'Ukraine'}">    selected
                                            </c:if>
                                            >Ukraine
                                        </option>
                                        <option value="Russia"
                                            <c:if test="${connection.secondRouterCountry == 'Russia' or param.secondCountry == 'Russia'}">    selected
                                            </c:if>
                                            >Russia
                                        </option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="port" class="col-xs-3 control-label">City-2:</label>
                                <div class="col-xs-7">
                                    <select class="form-control" id="city2" name="city2" form="form">
                                        <option label=" "></option>
                                        <c:if test="${not empty connection or not empty param.secondCity}">
                                            <c:choose>
                                                <c:when test="${not empty connection}">
                                                    <option value="${connection.secondRouterCityName}" selected>${connection.secondRouterCityName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${param.secondCity}" selected>${param.secondCity}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="port" class="col-xs-3 control-label">SN-2:</label>
                                <div class="col-xs-7">
                                    <select class="form-control" id="SN2" name="SN2" form="form">
                                        <option label=" "></option>
                                        <c:if test="${not empty connection or not empty param.SN2}">
                                            <c:choose>
                                                <c:when test="${not empty connection}">
                                                    <option value="${connection.secondRouterSN}" selected>${connection.secondRouterSN}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${param.SN2}" selected>${param.SN2}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                            <input type="hidden" name="action" value="${param.action}"/>
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