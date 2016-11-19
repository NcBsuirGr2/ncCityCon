<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/style_loginForm.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="icon" href="favicon.ico" />
</head>
<body>
<%@ include file="/html/header.jsp" %>
<div class="content-wrapper">
    <div class="before-footer">

            <div id="loginModal2" >
                <div class="modal-dialog">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h1 class="text-center">Create your personal account</h1>
                        </div>

                        <div class="modal-body">
                            <form class="form col-md-12 center-block " method="POST" action="/signup">
                                <div class="form-group">
                                    <input type="text" class="form-control input-lg" placeholder="Login" name="login">
                                </div>
                                <div class="form-group">
                                    <input type="password" class="form-control input-lg" placeholder="Password" name="password">
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control input-lg" placeholder="e-mail" name="e-mail">
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control input-lg" placeholder="name" name="name">
                                </div>
                                <div class="form-group">
                                    <input type="submit" class="btn btn-primary btn-lg btn-block" value="Create an account">
                                </div>
                            </form>
                        </div>

                        <!--TODO: надо убрать, просто так удалить - вся форма ломается-->
                        <div class="modal-footer">
                            <!--<div class="col-md-12">
                                <button class="btn" data-dismiss="modal" aria-hidden="true" onclick="location.href='/'">Cancel</button>
                            </div> -->
                        </div>


                    </div>
                </div>
            </div>
    </div>
    <%@ include file="/html/footer.html" %>
</div>

</body>
</html>


