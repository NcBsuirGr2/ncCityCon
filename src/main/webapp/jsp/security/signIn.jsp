<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>

        <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

        <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="/css/style.css" rel="stylesheet" type="text/css">
        <link href="/css/securityPages/common.css" rel="stylesheet" type="text/css">

        <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="js/common.js"></script>

        <link href="favicon.ico" rel="icon"/>
    </head>

    <body>
   
    
        <div class="content-wrapper">

         <%@ include file="/include/header.jsp" %>
            <div class="before-footer">
                
            <c:if test="${not empty param.errorType}">
                <div class="alert alert-warning alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Error</strong> 
                    <c:choose>
                        <c:when test="${param.errorType == 'invalidData'}">
                            Wrong login or password.
                        </c:when>
                    </c:choose>
                </div>
            </c:if> 

                <div class="modal-dialog">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h2 class="text-center">Sign in to CityCon</h2>
                        </div>

                        <div class="modal-body">
                            <form class="form-horizontal" method="POST" id="form" action="/signin">                               
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">Login:</label>
                                    <div class="col-xs-7">
                                        <div class="form-group">
                                            <input type="text" maxlength="15" class="form-control" autofocus placeholder="Login" name="login">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">Password:</label>
                                    <div class="col-xs-7">
                                        <div class="form-group">
                                            <input type="password" maxlength="15" class="form-control asciiInput" placeholder="Password" name="password">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group" style="text-align: center;">
                                    <input type="submit" class="btn btn-primary" value="Sign In">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>


                <div class="modal-dialog" style="margin-bottom: 10px">
                    <div class="modal-content">
                        <div class="modal-footer">
                            <h4 class="pull-left" align="left">New to CityCon?</h4>
                            <h4><a class="pull-right" href="/signup">Create an account</a></h4>
                        </div>
                    </div>
                </div>
            </div>

            <%@ include file="/include/footer.html" %>
        </div>
    </body>
</html>


