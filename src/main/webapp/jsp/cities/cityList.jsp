<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="citycon"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1, maximum-scale=1">

    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
   <script type="text/javascript" src="/js/cityPages/selectEntityCity.js"></script>
    <script type="text/javascript" src="/js/cityPages/cityList.js"></script>

    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/cityPages/cityList.css">
    <link rel="icon" href="favicon.ico" />

    <title>Cities</title>
</head>

<body>
<div class="content-wrapper">
    <%@ include file="/include/header.jsp" %>
    <div class="alert alert-info selectAlert hide">
        Please, choose one element from the list below.
    </div>

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
                <div class="panel-heading" style="text-align: center;">
                    <div>Cities</div>
                    <div>
                        <form action="/cities" method="get" name="form" onsubmit="return true;">
                            <img src="/img/search.png">
                            <input class="panel-search" name="search" type="text" id="search" size="10" maxlength="15">
                        </form>
                    </div>
                </div>

                <table class="selectable table table-striped table-bordered table-hover" style="table-layout: auto">
                    <thead>
                    <tr>
                        <th>
                            <citycon:sortBy asc="${paginationParameters['cities']['asc']}"
                                            sortByIs="${paginationParameters['cities']['sortBy']}"
                                            sortByNeed="name"
                                            value="Name"/>
                        </th>

                        <th>
                            <citycon:sortBy asc="${paginationParameters['cities']['asc']}"
                                            sortByIs="${paginationParameters['cities']['sortBy']}"
                                            sortByNeed="countryName"
                                            value="Country"/>
                        </th>

                        <th>
                            <citycon:sortBy asc="${paginationParameters['cities']['asc']}"
                                            sortByIs="${paginationParameters['cities']['sortBy']}"
                                            sortByNeed="routersNum"
                                            value="Routers number"/>
                        </th>

                        <th class="hidden">id</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${entityArray}" var="city">
                        <tr>
                            <td class="uniqueCity"><a href="/statistic/cities/${city.countryName}/${city.name}">${city.name}</a></td>
                            <td class="uniqueCountry">${city.countryName}</td>
                            <td>${city.routersNum}</td>
                            <td class="hidden idField">${city.id}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="panel-footer">
                    <div class="row">
                        <c:if test="${showSystemUnitsOperationBtns}">
                            <div class="col-md-1-5">
                                <a href="/city?action=add" class="btn btn-primary btn-block btn-group-md center-block routersHref">Add</a>
                            </div>

                            <div class="col-md-1-5">
                                <a href="#" class="btn btn-primary btn-block btn-group-md center-block editHref">Edit</a>
                            </div>

                            <div class="col-md-1-5">
                                    <form action="/city" id="deleteForm" method="POST">
                                        <input type="hidden" id="deleteId" name="id" value="-1">
                                        <input type="hidden" name="action" value="delete">
                                        <button type="button" class="btn btn-primary btn-block btn-group-md center-block deleteDialogBtn" data-toggle="modal" data-target=".deleteDialog">Delete</button>
                                    </form>
                                </div>
                            <div class="col-md-1-5">
                                <a href="#" class="btn btn-primary btn-block btn-group-md center-block routersHref">Routers</a>
                            </div>

                            <div class="col-md-1-5">
                                <a href="#" class="btn btn-primary btn-block btn-group-md center-block connectionsHref">Connections</a>
                            </div>
                        </c:if>
                        <c:if test="${not showSystemUnitsOperationBtns}">
                            <div class="col-md-2">
                            </div>
                            <div class="col-md-4">
                                <a href="#" class="btn btn-primary btn-block btn-group-md center-block routersHref">Routers</a>
                            </div>
                            <div class="col-md-4">
                                <a href="#" class="btn btn-primary btn-block btn-group-md center-block connectionsHref">Connections</a>
                            </div>
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
                                    <p>Are you sure you want to delete selected city?</p>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                    <input type=submit class="btn btn-primary" form="deleteForm" value="Delete"></input>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Select city modal -->
                    <div class="modal fade selectUserModal" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <h4 class="modal-title">No city selected</h4>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Ok</button>
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
        <c:if test="${endPage > 1}">
            <ul class="pagination">
                <c:if test="${beginPage > previousPage}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${previousPage}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                            <span class="sr-only">Previous</span>
                        </a>
                    </li>
                </c:if>

                
                
                <c:forEach begin="${beginPage}" end="${endPage}" varStatus="i">
                    <c:if test="${i.index == currentPage}">
                            <c:set var="isActive" value="active"/>
                    </c:if>
                    <c:if test="${i.index != currentPage}">
                            <c:set var="isActive" value=""/>
                    </c:if>
                    <li class="page-item ${isActive}">
                        <a class="page-link" href="?page=${i.index}">
                            ${i.index}
                        </a>
                    </li>
                </c:forEach>

                <c:if test="${endPage < nextPage}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${nextPage}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                            <span class="sr-only">Next</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </c:if> 

        <div class="row">
            <div class="col-sm-3">
            </div>

            <div class="col-sm-3">
                <label class="pull-right control-label">Items per page:</label>
            </div>

            <div class="col-sm-2">
                <select class="pull-left" id="itemsPerPageSelect" onChange="window.location.href=this.value">
                    <option <c:if test="${paginationParameters['cities']['itemsPerPage'] == 5}">selected</c:if>  value="?itemsPerPage=5">5</option>
                    <option <c:if test="${paginationParameters['cities']['itemsPerPage'] == 10 || empty paginationParameters['cities']['itemsPerPage']}">selected</c:if> value="?itemsPerPage=10">10</option>
                    <option <c:if test="${paginationParameters['cities']['itemsPerPage'] == 15}">selected</c:if> value="?itemsPerPage=15">15</option>
                </select>
            </div>

            <div class="col-sm-4">
            </div>
        </div>
    </center>

    <%@ include file="/include/footer.html" %>
</div>
</body>
</html>