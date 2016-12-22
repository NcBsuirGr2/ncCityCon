<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
            <link rel="stylesheet" type="text/css" href="/css/style.css">
            <link rel="stylesheet" type="text/css" href="/css/connectionPages/connectionEdit.css">

            <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

            <script src="/js/formValidation.js"></script>
            <script src="/js/connectionPages/connectionEdit.js"></script>

            <link rel="icon" href="favicon.ico" />

            <title>
                <c:choose>
                <c:when test="${param.action == 'edit'}">
                    Edit connection
                </c:when>
                <c:when test="${param.action == 'add' || empty param.action}">
                    Add connection
                </c:when>
            </c:choose>
            </title>
        </head> 
    <body>
        <c:choose>
            <c:when test="${not empty param.SN}">
                <c:set var="sameSelect" value="SN=${param.SN}"/>
            </c:when>
            <c:when test="${not empty param.city and not empty param.country}">
                <c:set var="sameSelect" value="country=${param.country}&city=${param.city}"/>
            </c:when>
        </c:choose>

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
                        <c:choose>
                            <c:when test="${param.action == 'edit'}">
                                Edit connection
                            </c:when>
                            <c:when test="${param.action == 'add' || empty param.action}">
                                Add connection
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="panel-body">
                    	<form class="form-horizontal" id="form" method="POST" action="/connection" >
                            <div class="form-group row">
                                <label class="col-xs-3 control-label">Country-1:</label>
                                <div class="col-xs-7">
                                    <select class="form-control notEmptyInput" id="country1" name="country1" form="form">
                                        <option label=" "></option>
                                        <c:if test="${not empty connection or not empty param.firstCountry}">
                                            <c:choose>
                                                <c:when test="${not empty param.firstCountry}">
                                                    <option value="${param.firstCountry}" selected>${param.firstCountry}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${connection.firstRouter.city.countryName}" selected>${connection.firstRouter.city.countryName}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-xs-3 control-label">City-1:</label>
                                <div class="col-xs-7">
                                    <select class="form-control notEmptyInput" id="city1" name="city1" form="form">
                                        <option label=" "></option>
                                        <c:if test="${not empty connection or not empty param.firstCity}">
                                            <c:choose>
                                                <c:when test="${not empty param.firstCity}">
                                                    <option value="${param.firstCity}" selected>${param.firstCity}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${connection.firstRouter.city.name}" selected>${connection.firstRouter.city.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </select>
                                </div>
                            </div> 
                        	<div class="form-group row">
                            	<label class="col-xs-3 control-label">SN-1:</label>
                            	<div class="col-xs-7">
                                    <select class="form-control notEmptyInput" id="SN1" name="SN1" form="form">
                                        <option label=" "></option>
                                        <c:if test="${not empty connection or not empty param.SN1}">
                                            <c:choose>
                                                <c:when test="${not empty param.SN1}">
                                                    <option value="${param.SN1}" selected>${param.SN1}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${connection.firstRouter.SN}" selected>${connection.firstRouter.SN}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </select>
                                </div>
                            </div>   
                            <br>
                            <br>
                            <div class="form-group row">
                                <label class="col-xs-3 control-label">Country-2:</label>
                                <div class="col-xs-7">
                                    <select class="form-control notEmptyInput" id="country2" name="country2" form="form">
                                        <option label=" "></option>
                                        <c:if test="${not empty connection or not empty param.secondCountry}">
                                            <c:choose>
                                                <c:when test="${not empty param.secondCountry}">
                                                    <option value="${param.secondCountry}" selected>${param.secondCountry}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${connection.secondRouter.city.countryName}" selected>${connection.secondRouter.city.countryName}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-xs-3 control-label">City-2:</label>
                                <div class="col-xs-7">
                                    <select class="form-control notEmptyInput" id="city2" name="city2" form="form">
                                        <option label=" "></option>
                                        <c:if test="${not empty connection or not empty param.secondCity}">
                                            <c:choose>
                                                <c:when test="${not empty param.secondCity}">
                                                    <option value="${param.secondCity}" selected>${param.secondCity}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${connection.secondRouter.city.name}" selected>${connection.secondRouter.city.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-xs-3 control-label">SN-2:</label>
                                <div class="col-xs-7">
                                    <select class="form-control notEmptyInput" id="SN2" name="SN2" form="form">
                                        <option label=" "></option>
                                        <c:if test="${not empty connection or not empty param.SN2}">
                                            <c:choose>
                                                <c:when test="${not empty param.SN2}">
                                                    <option value="${param.SN2}" selected>${param.SN2}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${connection.secondRouter.SN}" selected>${connection.secondRouter.SN}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                            <input type="hidden" name="action" value="${param.action}"/>
                            <input type="hidden" name="id" value="${connection.id}"/>
                            <input type="hidden" name="sameSelect" value="${sameSelect}"/>
                        </form>
                    </div>

                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-sm-6">
                                <button type="button" class="btn btn-primary btn-block center-block" data-toggle="modal" data-target=".changesDialog">Apply</button>
                            </div>
                            <div class="col-sm-6">
                                <a href="/connections?${sameSelect}" class="btn btn-primary btn-block center-block">Back</a>
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