<%--
  Created by IntelliJ IDEA.
  User: Vojts
  Date: 08.12.2016
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Statistic</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1, maximum-scale=1">

    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/userPages/userList.js"></script>

    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/statistic/statisticList.css">
    <link rel="icon" href="favicon.ico" />
</head>
<body>
    <table>
        <tr>
            <th>Count users:</th>
            <td>${count_users}</td>
        </tr>
        <tr>
            <th>Count cities:</th>
            <td>${count_cities}</td>
        </tr>
        <tr>
            <th>Count routers:</th>
            <td>${count_routers}</td>
        </tr>
        <tr>
            <th>Count connections:</th>
            <td>${count_connections}</td>
        </tr>
    </table>
</body>
</html>
