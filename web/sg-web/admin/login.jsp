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
    <script src="${ctx}/sg-web/js/jquery.cookie.js"></script>
    <script src="${ctx}/sg-web/js/bootstrap.min.js?v=3.4.0"></script>
</head>

<%--<body class="gray-bg">--%>
<body  style="overflow:hidden;">
<div class="wrapper">
    <div class="row">
        <div class="col-lg-7" style="margin-left: 21%;margin-right: 21%;margin-top: 12%;margin-bottom: 20%;">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>松果亲子－后台管理</h5>
                    <div class="ibox-tools"></div>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-6 b-r">
                            <p class="text-center">
                                <img src="${ctx}/sg-web/img/qr_code.png" style="width: 50%;height: 50%;margin-top: 10%;margin-bottom: 10%;" >
                            </p>
                        </div>
                        <div class="col-sm-6">
                            <h3 class="m-t-none m-b">登录</h3>
                            <form class="m-t" id="login_form" role="form" action="${ctx}/user/loginpage.do" method="post">
                                <div class="form-group">
                                    <label>用户名</label>
                                    <input type="text" id="username" name="username" placeholder="请输入您的用户名" class="form-control" required="">
                                </div>
                                <div class="form-group">
                                    <label>密码</label>
                                    <input type="password" id="password" name="password" placeholder="请输入您的密码" class="form-control" required="">
                                </div>
                                <div>
                                    <button class="btn btn-primary pull-right" type="submit"><strong>登 录</strong></button>
                                    <label>
                                        注册用户－请联系管理员
                                    </label>
                                    <p>&numsp;</p>
                                    <p class="text-muted text-left">
                                        <c:if test="${msg!='' && msg != null}" ><font color="red">用户名/密码错误,${msg}!</font></c:if>
                                    </p>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    <%--<div class="middle-box text-center loginscreen  animated fadeInDown">--%>
        <%--<div>--%>
            <%--<div><h1 class="logo-name"><img src="${ctx}/sg-web/img/sg_logo.png" style="width: 100px;height: 100px"></h1></div>--%>
            <%--<h3>松果亲子互动平台--管理</h3>--%>
            <%--<form class="m-t" id="login_form" role="form" action="${ctx}/user/loginpage.do" method="post">--%>
                <%--<div class="form-group">--%>
                    <%--<input type="text" class="form-control" id="username" name = "username" placeholder="用户名" required="">--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<input type="password" class="form-control" id="password" name = "password" placeholder="密码" required="">--%>
                <%--</div>--%>
                <%--<button type="submit" class="btn btn-primary block full-width m-b">登 录</button>--%>
                <%--<p class="text-muted text-center">${msg}</p>--%>

                <%--<!--<p class="text-muted text-center"> <a href="#"><small>忘记密码了？</small></a> | <a href="#">注册一个新账号</a></p>-->--%>
            <%--</form>--%>
        <%--</div>--%>
    <%--</div>--%>

</body>
</html>
