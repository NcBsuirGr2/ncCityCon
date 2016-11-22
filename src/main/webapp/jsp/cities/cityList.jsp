<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1, maximum-scale=1">

    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/selectEntity.js"></script>
    <script type="text/javascript" src="/js/userPages/userList.js"></script>

    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/userPages/userList.css">
    <link rel="icon" href="favicon.ico" />

    <title>Cities</title>
</head>

<body>
<div class="content-wrapper">
    <%@ include file="/include/header.jsp" %>

    <c:if test="${not empty param.success}">
        <div class="alert alert-success alert-dismissible">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Success!</strong>
            <c:choose>
                <c:when test="${param.success == 'add'}">
                    New city has been created.
                </c:when>
                <c:when test="${param.success == 'delete'}">
                    City has been deleted.
                </c:when>
                <c:when test="${param.success == 'edit'}">
                    City has been modified.
                </c:when>
            </c:choose>
        </div>
    </c:if>



    <div class="row">
        <div class="col-sm-1">
        </div>
        <div class="col-sm-10">
            <div class="panel panel-default">
                <center class="panel-heading">
                    CityCon Cities
                </center>


                <table class="selectable table table-striped table-bordered table-hover" style="table-layout: auto">
                    <thead>
                    <tr>

                        <th>
                            <c:set var="newAsc" value="true"/>
                            <c:if test="${empty param.asc or (param.asc == true and (param.sortBy == 'name' or empty param.sortBy))}">
                            <c:set var="newAsc" value="false"/>
                            </c:if>

                            <a href="?itemsPerPage=${param.itemsPerPage}&page=${param.page}&sortBy=name&asc=${newAsc}">
                                City
                                <c:if test="${empty param.sortBy || param.sortBy == 'name'}">
                                <c:if test="${newAsc == true}">&#9660;</c:if>
                                <c:if test="${newAsc == false}">&#9650;</c:if>
                                </c:if>
                            </a>
                        </th>


                        <th>
                            <c:set var="newAsc" value="true"/>
                            <c:if test="${not empty param.asc && param.asc == true && param.sortBy == 'countryName'}">
                            <c:set var="newAsc" value="false"/>
                            </c:if>
                            <a href="?itemsPerPage=${param.itemsPerPage}&page=${param.page}&sortBy=login&asc=${newAsc}">
                                Country
                                <c:if test="${param.sortBy == 'countryName'}">
                                <c:if test="${newAsc == true}">&#9660;</c:if>
                                <c:if test="${newAsc == false}">&#9650;</c:if>
                                </c:if>
                            </a>
                        </th>

                        <th>
                            <c:set var="newAsc" value="true"/>
                            <c:if test="${not empty param.asc && param.asc == true && param.sortBy == 'routersNum'}">
                            <c:set var="newAsc" value="false"/>
                            </c:if>
                            <a href="?itemsPerPage=${param.itemsPerPage}&page=${param.page}&sortBy=login&asc=${newAsc}">
                                Routers Number
                                <c:if test="${param.sortBy == 'routersNum'}">
                                <c:if test="${newAsc == true}">&#9660;</c:if>
                                <c:if test="${newAsc == false}">&#9650;</c:if>
                                </c:if>
                            </a>
                        </th>

                        <th class="hidden">id</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${entityArray}" var="city">
                        <tr>
                            <td><a href="/routers">${city.name}</a></td>
                            <td>${city.countryName}</td>
                            <td>${city.routersNum}</td>
                            <td class="hidden idField">${city.id}</td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>



                <div class="panel-footer">
                    <div class="row">

                        <div class="col-sm-4">
                            <c:if test="${showUsersOperationBtns}">
                                <a href="/user?action=add">
                                    <button class="btn btn-primary center-block">Add</button>
                                </a>
                            </c:if>
                        </div>

                        <div class="col-sm-4">
                            <c:if test="${showUsersOperationBtns}">
                                <a class="editHref" href="#">
                                    <button class="btn btn-primary center-block">Edit</button>
                                </a>
                            </c:if>
                        </div>

                        <div class="col-sm-4">
                            <c:if test="${showUsersOperationBtns}">
                                <form action="/user" id="deleteForm" method="POST">
                                    <input type="hidden" id="deleteId" name="id" value="-1">
                                    <input type="hidden" name="type" value="delete">
                                    <button type="button" class="btn btn-primary center-block deleteDialogBtn" data-toggle="modal" data-target=".deleteDialog">Delete</button>
                                </form>
                            </c:if>
                        </div>
                        <!-- Delete dialog modal -->
                        <div class="modal fade deleteDialog">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                        <h4 class="modal-title">Confirm deletion</h4>
                                    </div>

                                    <div class="modal-body">
                                        <p>Are you sure you want to delete selected user?</p>
                                    </div>

                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                        <input type=submit class="btn btn-primary" form="deleteForm" value="Delete"></input>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Select user modal -->
                        <div class="modal fade selectUserModal" tabindex="-1" role="dialog" aria-hidden="true">
                            <div class="modal-dialog modal-sm">
                                <div class="modal-content">
                                    <div class="modal-body">
                                        <h4 class="modal-title">No user selected</h4>
                                    </div>

                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Ok</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>





























            </div>
            <div class="col-sm-1">
            </div>
        </div>
    </div>















    <center class="before-footer">
        <%@ include file="/include/pagination.jsp" %>
    </center>


    <%@ include file="/include/footer.html" %>
</div>




</body>
</html>


