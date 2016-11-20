<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>

        <meta http-equiv="content-type" content="text/html; charset=UTF-8">        
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>

        <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="/css/style_loginForm.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <link href="/css/securityPages/common.css" rel="stylesheet" type="text/css">

        <link rel="icon" href="favicon.ico" />
    </head>

    <body>
        <div class="content-wrapper">
            <%@ include file="/html/header.jsp" %>

            <div class="before-footer">

                <c:if test="${not empty param.errorType}">
                    <div class="alert alert-warning alert-dismissible">
                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        <strong>Error</strong> 
                        <c:choose>
                            <c:when test="${param.errorType == 'invalidData'}">
                                Wrong login or password.
                            </c:when>
                            <c:when test="${param.errorType == 'dublicate'}">
                                User with such login or e-mail already exists.
                            </c:when>
                        </c:choose>
                    </div>
                </c:if> 

                <div class="modal-dialog" style="margin-bottom: 10px">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h2 class="text-center">Create your personal account</h2>
                        </div>

                        <div class="modal-body">
                            <form class="form" method="POST" action="/signup">
                                <div class="form-group">
                                    <input type="text" class="form-control input-lg" required placeholder="Login" name="login">
                                </div>
                                <div class="form-group">
                                    <input type="password" class="form-control input-lg" placeholder="Password" name="password">
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control input-lg" required placeholder="e-mail" name="e-mail">
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control input-lg" placeholder="name" name="name">
                                </div>
                                <div class="form-group" style="margin-bottom: 5px">
                                    <input type="submit" class="btn btn-primary btn-lg btn-block" value="Create an account">
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
                <div class="modal-dialog" style="margin-bottom: 10px">
                    <div class="modal-content">
                        <div class="modal-footer">
                            <h4 class="pull-left" align="left">Already have account?</h4>
                            <h4><a class="pull-right" href="/signin">Sign in</a></h4>
                        </div>
                    </div>
                </div>
            </div>
            <%@ include file="/html/footer.html" %>
        </div>

    </body>
</html>


