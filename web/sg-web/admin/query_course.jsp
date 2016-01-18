<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="utf-8"%>
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
    <link href="${ctx}/sg-web/js/plugins/layer/skin/layer.css" rel="stylesheet">

    <!-- Data Tables -->
    <link href="${ctx}/sg-web/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">

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

    <!-- Data Tables -->
    <script src="${ctx}/sg-web/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="${ctx}/sg-web/js/plugins/dataTables/dataTables.bootstrap.js"></script>

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
                            <li><a href="#">个人资料</a></li>
                            <li><a href="#">修改密码</a></li>
                            <li class="divider"></li>
                            <li><a href="${ctx}/user/login.do">安全退出</a></li>
                        </ul>
                    </div>
                    <div class="logo-element">
                        SG
                    </div>

                </li>
                <li><a href="${ctx}/user/index.do?uid=${user.id}"><i class="fa fa-th-large"></i> <span class="nav-label">主页</span> </a></li>
                <li><a href="${ctx}/sub/info.do?uid=${user.id}"><i class="fa fa-connectdevelop"></i> <span class="nav-label">课程体系</span> </a></li>
                <li><a href="${ctx}/book/info.do?uid=${user.id}"><i class="fa fa-leanpub"></i> <span class="nav-label">试听课程</span> </a></li>
                <li><a href="${ctx}/one/info.do?uid=${user.id}"><i class="fa fa-drupal"></i> <span class="nav-label">推荐课程</span> </a></li>
                <li><a href="${ctx}/group/info.do?uid=${user.id}"><i class="fa fa-building"></i> <span class="nav-label">批量选课</span> </a></li>
                <li>
                    <a href="index.jsp#"><i class="fa fa-user-secret"></i> <span class="nav-label">老师管理</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li><a href="${ctx}/teacher/check.do?uid=${user.id}"><i class="fa fa-edit"></i> <span class="nav-label">资质审核</span> </a></li>
                        <li><a href="${ctx}/teacher/info.do?uid=${user.id}"><i class="fa fa-user-secret"></i> <span class="nav-label">师资力量</span></a></li>
                        <li><a href="${ctx}/teacher/assign.do?uid=${user.id}"><i class="fa fa-code-fork"></i> <span class="nav-label">课程分配</span> </a></li>
                        <li><a href="${ctx}/teacher/material.do?uid=${user.id}"><i class="fa fa-delicious"></i> <span class="nav-label">教案更新</span></a></li>
                    </ul>
                </li>
                <li class="active">
                    <a href="index.jsp#"><i class="fa fa-bar-chart"></i> <span class="nav-label">查询统计</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li class="active"><a href="${ctx}/query/info.do?uid=${user.id}"><i class="fa fa-pie-chart"></i> <span class="nav-label">选课查询</span> </a></li>
                        <li><a href="${ctx}/query/order.do?uid=${user.id}"><i class="fa fa-rub"></i> <span class="nav-label">订单查询</span> </a></li>
                    </ul>
                </li>
                <li>
                    <a href="index.jsp#"><i class="fa fa-home"></i> <span class="nav-label">首页配置</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li><a href="${ctx}/banner/info.do?uid=${user.id}"><i class="fa fa-star"></i> <span class="nav-label">banner设置</span> </a></li>
                        <li><a href="${ctx}/event/info.do?uid=${user.id}"><i class="fa fa-globe"></i> <span class="nav-label">event设置</span> </a></li>
                        <li><a href="${ctx}/icon/info.do?uid=${user.id}"><i class="fa fa-picture-o"></i> <span class="nav-label">icon设置</span> </a></li>
                    </ul>
                </li>
                <li>
                    <a href="index.jsp#"><i class="fa fa-gears"></i> <span class="nav-label">系统设置</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li><a href="${ctx}/city/info.do?uid=${user.id}"><i class="fa fa-hacker-news"></i> <span class="nav-label">城市信息</span> </a></li>
                        <li><a href="${ctx}/region/info.do?uid=${user.id}"><i class="fa fa-map-marker"></i> <span class="nav-label">区域信息</span> </a></li>
                        <li><a href="${ctx}/place/info.do?uid=${user.id}"><i class="fa fa-rebel"></i> <span class="nav-label">商户信息</span> </a></li>
                        <li><a href="${ctx}/inst/info.do?uid=${user.id}"><i class="fa fa-bank"></i> <span class="nav-label">机构信息</span> </a></li>
                        <li><a href="${ctx}/app/info.do?uid=${user.id}"><i class="fa fa-mobile-phone"></i> <span class="nav-label">APP版本</span></a></li>
                    </ul>
                </li>
                <li><a href="${ctx}/coupon/info.do?uid=${user.id}"><i class="fa fa-cc-paypal"></i> <span class="nav-label">优惠设置</span></a></li>
                <li>
                    <a href="index.jsp#"><i class="fa fa-user"></i> <span class="nav-label">用户信息</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li><a href="${ctx}/user/info.do?uid=${user.id}"><i class="fa fa-users"></i> <span class="nav-label">用户信息</span> </a></li>
                        <li><a href="${ctx}/role/info.do?uid=${user.id}"><i class="fa fa-graduation-cap"></i> <span class="nav-label">角色设置</span> </a></li>
                        <li><a href="${ctx}/func/info.do?uid=${user.id}"><i class="fa fa-joomla"></i> <span class="nav-label">权限设置</span> </a></li>
                    </ul>
                </li>
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
                <h2>查询条件</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="${ctx}/user/index.do?uid=${user.id}">主页</a>
                    </li>
                    <li>
                        <a href="${ctx}/query/info.do?uid=${user.id}">选课查询</a>
                    </li>
                    <li>
                        <strong>查询信息</strong>
                    </li>
                </ol>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel blank-panel">

                    <div class="panel-heading">
                        <%--<div class="panel-title m-b-md">--%>
                            <%--<h4>文字选项卡</h4>--%>
                        <%--</div>--%>
                        <div class="panel-options">

                            <ul class="nav nav-tabs">
                                <li id="tab_li_1" class="active"><a id="tab1" data-toggle="tab" href="tabs_panels.html#tab-1"><i class="fa fa-calendar"></i>时间查找</a></li>
                                <li id="tab_li_2" class=""><a id="tab2" data-toggle="tab" href="tabs_panels.html#tab-2"><i class="fa fa-mobile-phone"></i>手机号查询</a></li>
                            </ul>
                        </div>
                    </div>

                    <div class="panel-body">

                        <div class="tab-content">
                            <div id="tab-1" class="tab-pane active">
                                <div class="row">
                                    <div class="ibox-content">
                                        <form class="form-horizontal" id="time_form" action="${ctx}/query/query.do?uid=${user.id}" method="post">
                                            <fieldset>
                                                <div class="form-group">
                                                    <label class="col-sm-1 control-label">开始时间 </label>
                                                    <div class="col-sm-2">
                                                        <input id="startTime" name="startTime" type="text" value="${startTime}" class="form-control layer-date" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" readonly>
                                                    </div>
                                                    <label class="col-sm-1 control-label">结束时间</label>
                                                    <div class="col-sm-2">
                                                        <input id="endTime" name="endTime" type="text" value="${endTime}" class="form-control layer-date"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" readonly>
                                                    </div>
                                                    <label class="col-sm-1 control-label">课程标题</label>
                                                    <div class="col-sm-2">
                                                        <input id="title" name="title" type="text" value="${title}" class="form-control">
                                                    </div>
                                                    <div class="col-sm-1 col-sm-offset-1">
                                                        <button class="btn btn-primary" type="button" id="btn_time_query" name ="btn_time_query">查询</button>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="ibox-content">
                                        <form>
                                            <table class="table table-striped table-bordered table-hover dataTables-example">
                                                <thead>
                                                <tr class="gradeX">
                                                    <th style="width: 30%">项目</th>
                                                    <th style="width: 20%">开课时间</th>
                                                    <th style="width: 20%">上课地点</th>
                                                    <th>操作</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${courseTimeQuery}" var="entity">
                                                    <tr>
                                                        <td>
                                                            <div class="chat">
                                                                <img id="img_a" src="${filepath}${entity.courseCover}" class="chat-avatar" style="width: 100px;height: 50px"/>
                                                                <div>
                                                                    <c:out value="${entity.courseTitle}"></c:out>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td><c:out value="${entity.skuStartTime}"></c:out></td>
                                                        <td><c:out value="${entity.placeName}"></c:out></td>
                                                        <td><a href="${ctx}/query/queryDetail.do?uid=${user.id}&id=${entity.courseId}&skuId=${entity.skuId}&startTime=${startTime}&endTime=${endTime}&title=${title}&name=${entity.courseTitle}" class="btn btn-white btn-sm"><i class="fa fa-newspaper-o"></i> <c:out value="${entity.userCount}"></c:out>人已选 </a></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div id="tab-2" class="tab-pane">
                                <div class="row">
                                    <div class="ibox-content">
                                        <form class="form-horizontal" id="mobile_form" action="${ctx}/query/queryMobile.do?uid=${user.id}" method="post">
                                            <fieldset>
                                                <div class="form-group">
                                                    <label class="col-sm-1 control-label">手机号</label>
                                                    <div class="col-sm-2">
                                                        <input id="mobile" name="mobile" type="text" value="${mobile}" class="form-control">
                                                    </div>
                                                    <div class="col-sm-1 col-sm-offset-1">
                                                        <button class="btn btn-primary" type="button" id="btn_mobile_query" name="btn_mobile_query">查询</button>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="ibox-content">
                                        <form>
                                            <div id="mobileDiv">
                                                <table class="table table-striped table-bordered table-hover dataTables-abc">
                                                    <thead>
                                                    <tr class="gradeX">
                                                        <th>用户昵称</th>
                                                        <th>联系方式</th>
                                                        <th>所选课程</th>
                                                        <th>开课时间</th>
                                                        <th>上课地点</th>
                                                        <th>购买来源</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>

                                                    <%--<c:forEach items="${queryMobiles}" var="entity">--%>
                                                        <tr>
                                                            <td colspan="6">没有数据</td>
                                                        </tr>
                                                    <%--</c:forEach>--%>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>
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
</div>
<!-- Page-Level Scripts -->
<script>
    $(document).ready(function () {
        $('.dataTables-example').dataTable({
            "bSort": false //排序功能
        });

        $('#btn_time_query').click(function(){
            $("#time_form").submit();
//            $('#tab-1').show();
//            $('#tab-2').hide();
        });

        $('#btn_mobile_query').click(function(){
//            $("#mobile_form").submit();
//            $('#tab-1').hide();
//            $('#tab_li_1').attr('class','');
//            $('#tab-2').show();
//            $('#tab_li_2').attr('class','active');
            $.post("/query/queryMobile.do", $("#mobile_form").serialize(),
                    function(data){
                        if(data.success == 0) {
                            console.log(data);
                            var divshow = $("#mobileDiv");
                            divshow.text("");// 清空数据
                            divshow.append(data.queryMobiles);
                        }else{
                            layer.alert(data.msg,10,'提示信息');
                        }
                    }, "json");
        });
    });
</script>
</body>
</html>