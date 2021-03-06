<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../jstl/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"><meta name="renderer" content="webkit">
    <title>松果亲子</title>

    <style type="text/css">
        *{margin:0;vertical-align: middle;}
        .selectList{margin: 0;  font-size: 100%; vertical-align: middle;}
    </style>

    <LINK REL="icon" HREF="${ctx}/sg-web/img/sg_logo.png">

    <link href="${ctx}/sg-web/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${ctx}/sg-web/font-awesome/css/font-awesome.css?v=4.3.0" rel="stylesheet">

    <link href="${ctx}/sg-web/css/animate.css" rel="stylesheet">
    <link href="${ctx}/sg-web/css/style.css?v=2.2.0" rel="stylesheet">
    <link href="${ctx}/sg-web/js/plugins/layer/skin/layer.css" rel="stylesheet">

    <link href="${ctx}/sg-web/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">

    <link href="${ctx}/sg-web/ueditor/themes/default/css/ueditor.min.css" rel="stylesheet">

    <!-- Mainly scripts -->
    <script src="${ctx}/sg-web/js/jquery-2.1.1.min.js"></script>
    <script src="${ctx}/sg-web/js/bootstrap.min.js?v=3.4.0"></script>
    <script src="${ctx}/sg-web/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/sg-web/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/sg-web/js/hplus.js?v=2.2.0"></script>
    <script src="${ctx}/sg-web/js/plugins/pace/pace.min.js"></script>
    <script src="${ctx}/sg-web/js/ajaxfileupload.js"></script>
    <!-- layerDate plugin javascript -->
    <script src="${ctx}/sg-web/js/plugins/layer/laydate/laydate.js"></script>
    <!-- layer javascript -->
    <script src="${ctx}/sg-web/js/plugins/layer/layer.min.js"></script>

    <!-- Fancy box -->
    <script src="${ctx}/sg-web/js/plugins/fancybox/jquery.fancybox.js"></script>

</head>

<body>
<div id="wrapper">
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">

                    <div class="dropdown profile-element" align="center"> <span>
                                <img alt="image" class="img-circle" src="${ctx}/sg-web/img/face.png" />
                                 </span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="${ctx}/user/index.do?uid=${user.id}">
                                    <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">${user.username}</strong>
                                 </span> </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="${ctx}/user/oper.do?uid=${user.id}&id=${user.id}&mark=2">个人资料</a></li>
                            <li><a href="${ctx}/user/oper.do?uid=${user.id}&id=${user.id}&mark=2">修改密码</a></li>
                            <li class="divider"></li>
                            <li><a href="${ctx}/user/login.do">安全退出</a></li>
                        </ul>
                    </div>
                    <div class="logo-element">
                        SG
                    </div>

                </li>
                ${menus}
            </ul>

        </div>
    </nav>

    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <span class="m-r-sm text-muted welcome-message"><a href="${ctx}/user/index.do?uid=${user.id}" title="返回首页"><i class="fa fa-home"></i></a>欢迎登录松果亲子后台</span>
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="index.jsp#">
                            <i class="fa fa-envelope"></i> <span class="label label-warning">16</span>
                        </a>
                        <ul class="dropdown-menu dropdown-messages">
                            <li>
                                <div class="dropdown-messages-box">
                                    <a href="#" class="pull-left">
                                        <img alt="image" class="img-circle" src="${ctx}/sg-web/img/a7.jpg">
                                    </a>
                                    <div class="media-body">
                                        <small class="pull-right">46小时前</small>
                                        <strong>小四</strong> 项目已处理完结
                                        <br>
                                        <small class="text-muted">3天前 2014.11.8</small>
                                    </div>
                                </div>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="dropdown-messages-box">
                                    <a href="#" class="pull-left">
                                        <img alt="image" class="img-circle" src="${ctx}/sg-web/img/a4.jpg">
                                    </a>
                                    <div class="media-body ">
                                        <small class="pull-right text-navy">25小时前</small>
                                        <strong>国民岳父</strong> 这是一条测试信息
                                        <br>
                                        <small class="text-muted">昨天</small>
                                    </div>
                                </div>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                    <a href="#">
                                        <i class="fa fa-envelope"></i> <strong> 查看所有消息</strong>
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="index.jsp#">
                            <i class="fa fa-bell"></i> <span class="label label-primary">8</span>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a href="#">
                                    <div>
                                        <i class="fa fa-envelope fa-fw"></i> 您有16条未读消息
                                        <span class="pull-right text-muted small">4分钟前</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <i class="fa fa-qq fa-fw"></i> 3条新回复
                                        <span class="pull-right text-muted small">12分钟钱</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                    <a href="#">
                                        <strong>查看所有 </strong>
                                        <i class="fa fa-angle-right"></i>
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="${ctx}/user/login.do">
                            <i class="fa fa-sign-out"></i> 退出
                        </a>
                    </li>
                </ul>

            </nav>
        </div>
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>课程列表</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="${ctx}/user/index.do?uid=${user.id}">主页</a>
                    </li>
                    <li>
                        <a href="${ctx}/sub/info.do?uid=${user.id}&subid=${subid}">课程体系</a>
                    </li>
                    <li>
                        <a href="${ctx}/course/info.do?uid=${user.id}&subid=${subid}">课程信息</a>
                    </li>
                    <li>
                        <strong>填写课程推荐信息</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">
                <h2><a href="${ctx}/course/info.do?uid=${user.id}&subid=${subid}" class="btn btn-primary btn-x">返回</a></h2>
            </div>
        </div>
        <div class="row">
            <div class="ibox-content">
                <form class="form-horizontal" id="set_form" action="${ctx}/course/setOneCourse.do?uid=${user.id}&course_id=${model.id}" method="post">
                    <fieldset>
                        <div class="form-group">
                            <div class="col-sm-10">
                                <p><h3 align="center">${model.title}</h3></p>
                                <input id="set_id" name="set_id" type="hidden" value="0">
                                <input id="course_id" name="course_id" type="hidden" value="${model.id}">
                                <input id="sub_id" name="sub_id" type="hidden" value="${subid}">
                            </div>
                        </div>
                        <%--<div class="hr-line-dashed"></div>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">推荐顺序 </label>--%>
                            <%--<div class="col-sm-3">--%>
                                <%--<input id="weight" name="weight" type="text" class="form-control" value="0" >--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="form-group">
                            <%--<label class="col-sm-2 control-label">原始价格</label>--%>
                            <%--<div class="col-sm-3">--%>
                                <%--<input id="originalPrice" name="originalPrice" type="text" class="form-control">--%>
                            <%--</div>--%>
                            <label class="col-sm-2 control-label">出售价格</label>
                            <div class="col-sm-2">
                                <input id="price" name="price" type="text" class="form-control" style="width: 80%;">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">大人数量</label>
                            <div class="col-sm-1">
                                <input id="adult" name="adult" type="text" class="form-control" value="1">
                            </div>
                            <label class="col-sm-1 control-label">儿童数量</label>
                            <div class="col-sm-1">
                                <input id="child" name="child" type="text" class="form-control" value="1">
                                <%--<input id="courseCount" name="courseCount" type="hidden" value="1">--%>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">规则描述</label>
                            <div class="col-sm-3">
                                <textarea id="desc" name="desc" class="form-control" rows="3" style="resize:none;">${gzms}</textarea>
                                <input id="sku_id" name="sku_id" type="hidden" value="0"></td>
                            </div>
                        </div>

                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">时间数量</label>--%>
                            <%--<div class="col-sm-3">--%>
                                <%--<input id="time" name="time" type="text" class="form-control">--%>
                            <%--</div>--%>
                            <%--<label class="col-sm-2 control-label">时间单位</label>--%>
                            <%--<div class="col-sm-3">--%>
                                <%--<div id="div_time">--%>
                                    <%--<select id="timeUnit" name="timeUnit" class="form-control m-b">--%>
                                        <%--<c:forEach items="${time_unit}" var="node">--%>
                                            <%--<option value="${node.id}">${node.name}</option>--%>
                                        <%--</c:forEach>--%>
                                    <%--</select>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">购买须知 </label>
                            <div class="col-sm-8">
                                <textarea id="notice" name="notice" class="form-control" rows="7" style="resize:none;">${gmxz}</textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-5">
                                <button class="btn btn-primary" type="button" id="btn_set_save" name="btn_set_save">保存内容</button>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
        <div class="footer">
            <div class="pull-right">
                By：<a href="http://www.duolaqinzi.com" target="_blank">sg home</a>
            </div>
            <div>
                <strong>Copyright</strong> SG &copy; 2015
            </div>
        </div>
    </div>
</div>
<script language="JavaScript">
    $(document).ready(function () {
        $('.fancybox').fancybox({
            openEffect: 'none',
            closeEffect: 'none'
        });

        $('#btn_set_save').click(function(){
            $.post("/course/setOneCourse.do", $("#set_form").serialize(),
                    function(data){
                        if(data.success == 0){
//                            $("#set_id").attr("value",data.set_id);
//                            $("#sku_id").attr("value",data.sku_id);
//                            layer.alert(data.msg,10,'提示信息');
                            window.location.href="${ctx}/one/info.do?uid=${user.id}";
                        }else{
                            layer.alert(data.msg,10,'提示信息');
                        }
                    }, "json");

        });
    });
</script>
</body>
</html>