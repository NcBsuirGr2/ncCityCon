<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>

        <meta http-equiv="content-type" content="text/html; charset=UTF-8">        
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

        <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="js/formValidation.js"></script>

        <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="/css/style_loginForm.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <link href="/css/securityPages/common.css" rel="stylesheet" type="text/css">

        <link rel="icon" href="favicon.ico" />
    </head>

    <body>
        <div class="content-wrapper">
            <%@ include file="/include/header.jsp" %>

            <div class="before-footer">
                <div class="alert alert-warning formAlert hide">
                    Invalid form data.
                </div>
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
                            <form class="form-horizontal" method="POST" id="form" action="/signup">
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">Name:</label>
                                    <div class="col-xs-7">
                                        <div class="form-group">
                                            <input type="text" maxlength="15" class="form-control simpleText" autofocus required placeholder="Name" name="name" value="${param.name}">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">Login:</label>
                                    <div class="col-xs-7">
                                        <div class="form-group">
                                            <input type="text" maxlength="15" class="form-control simpleText" required placeholder="Login" name="login" value="${param.login}">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">Password:</label>
                                    <div class="col-xs-7">
                                        <div class="form-group">
                                            <input type="password" maxlength="15" class="form-control asciiInput" placeholder="Password" name="password" value="${param.password}">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">E-mail:</label>
                                    <div class="col-xs-7">
                                        <div class="form-group">
                                            <input type="text" maxlength="20" class="form-control emailInput" required placeholder="E-mail" name="email" value="${param.email}">
                                        </div>
                                    </div>
                                </div>
                                 <div class="form-group" style="margin-bottom: 5px; text-align: center;">
                                    <input type="submit" class="btn btn-primary" value="Create an account">
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
            <%@ include file="/include/footer.html" %>
        </div>

    </body>
</html>


