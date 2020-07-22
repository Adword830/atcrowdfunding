<%--Created by IntelliJ IDEA.
User: hopu Date: 2020/6/8 Time: 11:30
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/font-awesome.min.css">
    <link rel="stylesheet" href="static/css/login.css">
    <script src="static/jquery/jquery-3.1.0.min.js"></script>
    <script src="static/bootstrap/js/bootstrap.min.js"></script>
    <script>
        $(function(){
            $("button").click(function () {
                window.history.back(-1);
            })
        })
    </script>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>
<div style="text-align: center">
    <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in" style="text-align: center"></i> 尚筹网消息通知</h2>
    <h3>${requestScope.exception.message}</h3>
    <button class="btn btn-lg btn-success btn-block" style="margin: 20px auto 20px auto;width: 150px" > 返回上一级</button>
</div>
</body>
</html>