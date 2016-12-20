<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        

        <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="/js/formValidation.js"></script>
        <script src="/js/routerPages/suggestions.js"></script>

        <link rel="icon" href="favicon.ico" />

        <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">

        <title>Edit router</title>
    </head>
<body>

    <c:if test="${not empty param.city and not empty param.country}">
        <c:set var="sameSelect" value="country=${param.country}&city=${param.city}"/>
    </c:if>

    <div class="content-wrapper">

    <%@ include file="/include/header.jsp" %>
        <div class="alert alert-warning formAlert hide">
            Invalid form data.
        </div>
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
                <center class="panel-heading text-center">
                    Edit router
                </center>
                <div class="panel-body">
                <c:choose>
                    <c:when test="${param.action eq 'edit'}">
                        <%@ include file="/include/routerEditForm.jsp"%>
                    </c:when>
                    <c:otherwise>
                        <%@ include file="/include/routerAddForm.jsp"%>
                    </c:otherwise>
                </c:choose>

                </div>

                <div class="panel-footer">
                    <div class="row">
                        <div class="col-sm-6">
                            <button type="button" class="btn btn-primary btn-block center-block " data-toggle="modal" data-target=".changesDialog">Apply</button>
                        </div>
                        <div class="col-sm-6">
                            <a href="/routers?${sameSelect}" class="btn btn-primary btn-block center-block">Back</a>
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
                                <input type="hidden" name="sameSelect" form="form" value="${sameSelect}"/>
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