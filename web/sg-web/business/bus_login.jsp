<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../jstl/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"><meta name="renderer" content="webkit">
    <title>松果亲子</title>
    <LINK REL="icon" HREF="${ctx}/sg-web/img/sg_logo.png">
    <link href="${ctx}/sg-web/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${ctx}/sg-web/font-awesome/css/font-awesome.css?v=4.3.0" rel="stylesheet">
    <link href="${ctx}/sg-web/css/animate.css" rel="stylesheet">
    <link href="${ctx}/sg-web/css/style.css?v=2.2.0" rel="stylesheet">

    <!-- Mainly scripts -->
    <script src="${ctx}/sg-web/js/jquery-2.1.1.min.js"></script>
    <script src="${ctx}/sg-web/js/bootstrap.min.js?v=3.4.0"></script>
</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div><h1 class="logo-name">SG</h1></div>
            <h3>松果亲子互动平台--商家</h3>
            <form class="m-t" id="login_form" role="form" action="${ctx}/bus/loginpage.do" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" id="name" name = "name" placeholder="客户名称" required="">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" id="password" name = "password" placeholder="客户密码" required="">
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>
                <p class="text-muted text-center">${msg}</p>

                <!--<p class="text-muted text-center"> <a href="#"><small>忘记密码了？</small></a> | <a href="#">注册一个新账号</a></p>-->
            </form>
        </div>
    </div>
</body>
</html>
