<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
            <link rel="stylesheet" type="text/css" href="/css/style.css">
            <title>Edit</title>
        </head>

    <body>
        <div class="content-wrapper">

            <%@ include file="/include/header.jsp" %>

            <div class="before-footer">
                <div class="panel panel-default">

                	<div class="panel-heading">
                        <h4>Edit connection</h4>
                    </div>

                    <div class="panel-body">
                    	<form class="form-horizontal" id="form" method="post" action="/connection" >
                        	<div class="form-group">
                            	<label for="port" class="col-xs-3 control-label">SN1</label>
                                	<div class="col-xs-9">
                                    	<input class="form-control" placeholder="Port" id="port" type="text" autofocus value="<c:out value="${connection.port}"/> ">
                                    </div>
                            </div>   
                            <div class="form-group">
                                <label for="city" class="col-xs-3 control-label">SN2</label>
                                     <div class="col-xs-9">
                                         <input class="form-control" placeholder="City" id="city" type="text" autofocus value="<c:out value="${connection.city}"/> ">
                                     </div>
                            </div>
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