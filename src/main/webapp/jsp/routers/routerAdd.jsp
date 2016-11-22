<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">

        <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link rel="icon" href="favicon.ico" />
        <title>Edit router</title>
    </head>
<body>
    <div class="content-wrapper">

    <%@ include file="/include/header.jsp" %>

        <div class="before-footer">
            <c:if test="${not empty param.errorType}">
                <div class="alert alert-warning alert-dismissible" style="margin-top: 20px">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Error!</strong> 
                    <c:choose>
                        <c:when test="${param.errorType == 'dublicate'}">
                            Router with such SN already exists.
                        </c:when>
                    </c:choose>
                    <c:choose>
                        <c:when test="${param.errorType == 'invalidData'}">
                            There is no country or city with such name.
                        </c:when>
                    </c:choose>
                </div>
            </c:if> 

            <div class="panel panel-default">
                <div class="panel-heading text-center">
                    <h4>Setup router</h4>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" action="/router" method="POST" role="form" id="form">
                        <div class="form-group">
                            <label for="name" class="col-xs-3 control-label">Country:</label>
                            <div class="col-xs-7">
                            <c:if test="${not empty addRouter}">
                                <input class="form-control" required placeholder="Country" name="countryName" type="text" value="${addRouter.countryName}">
                            </c:if>
                            <c:if test="${empty addRouter}">
                                <input class="form-control" required placeholder="Country" name="countryName" type="text" value="${param.editCountryName}">
                            </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="name" class="col-xs-3 control-label">City:</label>
                            <div class="col-xs-7">
                            <c:if test="${not empty addRouter}">
                                <input class="form-control" required placeholder="City" name="cityName" type="text" value="${addRouter.cityName}">
                            </c:if>
                            <c:if test="${empty addRouter}">
                                <input class="form-control" required placeholder="City" name="cityName" type="text" value="${param.editCityName}">
                            </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="name" class="col-xs-3 control-label">Name:</label>
                            <div class="col-xs-7">
                            <c:if test="${not empty addRouter}">
                                <input class="form-control" required placeholder="Name" name="name" type="text" value="${addRouter.name}">
                            </c:if>
                            <c:if test="${empty addRouter}">
                                <input class="form-control" required placeholder="Name" name="name" type="text" value="${param.editName}">
                            </c:if>
                            </div>
                        </div>   
                        <div class="form-group">
                            <label for="login" class="col-xs-3 control-label">SN:</label>
                             <div class="col-xs-7">                                 
                                <c:if test="${not empty addRouter}">
                                    <input class="form-control" required placeholder="SN" name="SN" type="text" value="${addRouter.SN}">
                                </c:if>
                                <c:if test="${empty addRouter}">
                                    <input class="form-control" required placeholder="SN" name="SN" type="text" value="${param.editSN}">
                                </c:if>
                             </div>
                        </div>
                        <div class="form-group">
                            <label for="group" class="col-xs-3 control-label">Ports:</label>
                            <div class="col-xs-2">
                                <select class="form-control" id="group" name="portsNum" form="form">
                                    <option value="1" <c:if test="${param.editPortsNum == 1}">selected</c:if>>1</option>
                                    <option value="2" <c:if test="${param.editPortsNum == 2}">selected</c:if>>2</option>
                                    <option value="3" <c:if test="${param.editPortsNum == 3}">selected</c:if>>3</option>
                                    <option value="4" <c:if test="${param.editPortsNum == 4}">selected</c:if>>4</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="group" class="col-xs-3 control-label">Active:</label>
                            <div class="col-xs-2">
                                <select class="form-control" name="active" form="form">
                                    <option value="true" <c:if test="${param.editActive == 'true'}">selected</c:if> >true</option>
                                    <option value="false" <c:if test="${param.editActive == 'false'}">selected</c:if>>false</option>
                                </select>
                            </div>
                        </div>
                        <input type="hidden" name="type" value="add"/>
                        <input type="hidden" name="id" value="${addRouter.id}"/>
                     </form>
                </div>

                <div class="panel-footer">
                    <div class="row">
                        <div class="col-sm-6">
                            <button type="button" class="btn btn-primary btn-block center-block" data-toggle="modal" data-target=".changesDialog">Apply</button>
                        </div>
                        <div class="col-sm-6">
                            <a href="/routers" class="btn btn-primary btn-block center-block">Back</a>
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
                                <p>Are you sure you want to create new router?</p>
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