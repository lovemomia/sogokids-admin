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
    <link href="${ctx}/sg-web/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">

    <style>
        .main{margin:0 auto;margin-left: 50px;}
        .div_img{float: left;}
        .div_img ul{
            background: #F8F8FF;
            margin: 0 auto;
            text-align: center;
            margin-left: 30px;
            margin-right: 30px;
            margin-top: 10px;
            margin-bottom: 10px;
            padding: 0;
            border: solid 1px #b3b3b3;
        }
        .div_img ul li{
            list-style-type:none;
            margin-bottom: 5px;
        }

        .img_w_h{
            width: 200px;
            height: 160px;
            border: solid 1px #b3b3b3;
        }
    </style>

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

    <!-- sg-admin javascript -->
    <script src="${ctx}/sg-web/js/sg-admin/sogokids-sub.js"></script>
    <script src="${ctx}/sg-web/js/sg-admin/sogokids-img.js"></script>
    <script src="${ctx}/sg-web/js/sg-admin/sogokids-sub-tabs.js"></script>

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
                <li class="active"><a href="${ctx}/sub/info.do?uid=${user.id}"><i class="fa fa-connectdevelop"></i> <span class="nav-label">课程体系</span> </a></li>
                <li><a href="${ctx}/book/info.do?uid=${user.id}"><i class="fa fa-leanpub"></i> <span class="nav-label">试听课程</span> </a></li>
                <li><a href="${ctx}/one/info.do?uid=${user.id}"><i class="fa fa-drupal"></i> <span class="nav-label">推荐课程</span> </a></li>
                <li>
                    <a href="index.jsp#"><i class="fa fa-bar-chart"></i> <span class="nav-label">查询统计</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li><a href="${ctx}/query/info.do?uid=${user.id}"><i class="fa fa-pie-chart"></i> <span class="nav-label">选课查询</span> </a></li>
                        <li><a href="${ctx}/query/order.do?uid=${user.id}"><i class="fa fa-rub"></i> <span class="nav-label">订单查询</span> </a></li>
                    </ul>
                </li>
                <li><a href="${ctx}/discuss/info.do?uid=${user.id}"><i class="fa fa-comments-o"></i> <span class="nav-label">话题管理</span></a></li>
                <li>
                    <a href="index.jsp#"><i class="fa fa-user-secret"></i> <span class="nav-label">老师管理</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li><a href="${ctx}/teacher/check.do?uid=${user.id}"><i class="fa fa-edit"></i> <span class="nav-label">资质审核</span> </a></li>
                        <li><a href="${ctx}/teacher/info.do?uid=${user.id}"><i class="fa fa-user-secret"></i> <span class="nav-label">师资力量</span></a></li>
                        <li><a href="${ctx}/teacher/assign.do?uid=${user.id}"><i class="fa fa-code-fork"></i> <span class="nav-label">课程分配</span> </a></li>
                        <li><a href="${ctx}/teacher/material.do?uid=${user.id}"><i class="fa fa-delicious"></i> <span class="nav-label">教案更新</span></a></li>
                    </ul>
                </li>
                <li><a href="${ctx}/group/info.do?uid=${user.id}"><i class="fa fa-building"></i> <span class="nav-label">批量选课</span> </a></li>
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
                <h2>课程包列表</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="${ctx}/user/index.do?uid=${user.id}">主页</a>
                    </li>
                    <li>
                        <a href="${ctx}/sub/info.do?uid=${user.id}">课程包信息</a>
                    </li>
                    <li>
                        <strong>编辑课程包</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">
                <h2><a href="${ctx}/sub/info.do?uid=${user.id}" class="btn btn-primary btn-x">返回</a></h2>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel blank-panel">

                    <div class="panel-heading">
                        <div class="panel-title m-b-md">
                            <h4></h4>
                        </div>
                        <div class="panel-options">

                            <ul class="nav nav-tabs">
                                <li id="tab_li_1" class="active"><a id="tab1" data-toggle="tab" href="tabs_panels.html#tab-4"><i class="fa fa-laptop"></i>基本信息</a></li>
                                <li id="tab_li_2" class=""><a id="tab2" data-toggle="tab" href="tabs_panels.html#tab-5"><i class="fa fa-picture-o"></i>活动图片</a></li>
                                <li id="tab_li_3" class=""><a id="tab3" data-toggle="tab" href="tabs_panels.html#tab-6"><i class="fa fa-cart-plus"></i>购买须知</a></li>
                                <li id="tab_li_4" class=""><a id="tab4" data-toggle="tab" href="tabs_panels.html#tab-7"><i class="fa fa-gears"></i>购买规则</a></li>
                                <li id="tab_li_5" class=""><a id="tab5" data-toggle="tab" href="tabs_panels.html#tab-8"><i class="fa fa-angellist"></i>用户评价</a></li>
                            </ul>
                        </div>
                    </div>

                    <div class="panel-body">
                        <div class="tab-content">
                            <div id="tab-1" class="tab-pane active">
                                <div class="ibox-content">
                                    <div class="list-group">
                                        <form class="form-horizontal" id="sub_form" action="" method="post">
                                            <fieldset>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">主标题</label>
                                                    <div class="col-sm-3">
                                                        <textarea id="title" name="title" class="form-control" rows="4" style="resize:none;">${model.title}</textarea>
                                                        <input id="sub_id" name="sub_id" type="hidden" value="${model.id}">
                                                        <input id="type" name="type" type="hidden" value="${model.type}"></td>
                                                        <input id="subTitle" name="subTitle" type="hidden" value="${model.subTitle}">
                                                    </div>
                                                    <label class="col-sm-2 control-label">首页图</label>
                                                    <div class="col-sm-3" style="cursor: pointer;">
                                                        <img id="img_a" src="${filepath}${model.cover}" style="width: 200px;height: 100px;border: 1px solid #999;"/>
                                                        <input id="img_path" type="file" name="img_path" style="opacity: 0;filter:alpha(opacity=0);">
                                                        <input id="cover" name="cover" type="hidden" value="${model.cover}">
                                                        <input id="filepath" name="filepath" type="hidden" value="${filepath}">
                                                        <%--<div class="col-sm-3 col-sm-offset-3">--%>
                                                            <%--<button class="btn btn-primary" type="button" id="btn_img_save" name="btn_img_save">上传</button>--%>
                                                        <%--</div>--%>
                                                    </div>
                                                </div>
                                                <%--<div class="form-group">--%>
                                                    <%--<label class="col-sm-2 control-label">副标题</label>--%>
                                                    <%--<div class="col-sm-3">--%>
                                                        <%--<textarea id="subTitle" name="subTitle" class="form-control" rows="4" style="resize:none;">${model.subTitle}</textarea>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                                <%--<div class="hr-line-dashed"></div>--%>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">所属城市 </label>
                                                    <div class="col-sm-3">
                                                        <select class="form-control m-b" id="cityId" name="cityId">
                                                            <c:forEach items="${citys}" var="node">
                                                                <c:choose>
                                                                    <c:when test="${node.id == model.cityId}">
                                                                        <option value="${node.id}" selected>${node.name}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${node.id}"  >${node.name}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <%--<label class="col-sm-2 control-label">所属标签</label>--%>
                                                    <%--<div class="col-sm-3">--%>
                                                        <%--<select id="tags" name="tags" class="form-control m-b" >--%>
                                                            <%--<c:forEach items="${tags}" var="node">--%>
                                                                <%--<c:choose>--%>
                                                                    <%--<c:when test="${node.title == model.tags}">--%>
                                                                        <%--<option value="${node.id}" selected>${node.title}</option>--%>
                                                                    <%--</c:when>--%>
                                                                    <%--<c:otherwise>--%>
                                                                        <%--<option value="${node.id}"  >${node.title}</option>--%>
                                                                    <%--</c:otherwise>--%>
                                                                <%--</c:choose>--%>
                                                            <%--</c:forEach>--%>
                                                        <%--</select>--%>
                                                    <%--</div>--%>
                                                </div>
                                                <%--<div class="hr-line-dashed"></div>--%>
                                                <%--<div class="form-group">--%>
                                                <%--<label class="col-sm-3 control-label">所属类型 </label>--%>
                                                <%--<div class="col-sm-7">--%>
                                                <%--<select id="type" name="type" class="form-control m-b" >--%>
                                                <%--<c:forEach items="${sub_type}" var="node">--%>
                                                <%--<c:choose>--%>
                                                <%--<c:when test="${node.id == model.type}">--%>
                                                <%--<option value="${node.id}" selected>${node.name}</option>--%>
                                                <%--</c:when>--%>
                                                <%--<c:otherwise>--%>
                                                <%--<option value="${node.id}"  >${node.name}</option>--%>
                                                <%--</c:otherwise>--%>
                                                <%--</c:choose>--%>
                                                <%--</c:forEach>--%>
                                                <%--</select>--%>
                                                <%--</div>--%>
                                                <%--</div>--%>
                                                <%--<div class="hr-line-dashed"></div>--%>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">推荐理由</label>
                                                    <div class="col-sm-8">
                                                        <textarea id="intro" name="intro" class="form-control" rows="5" style="resize:none;">${model.intro}</textarea>
                                                    </div>
                                                </div>

                                                <div class="hr-line-dashed"></div>
                                                <div class="form-group">
                                                    <div class="col-sm-4 col-sm-offset-4">
                                                        <button class="btn btn-primary" type="button" id="btn_sub_save" name="btn_sub_save">&numsp;&numsp;保存内容&numsp;&numsp;</button>
                                                        &numsp;&numsp;&numsp;&numsp;&numsp;&numsp;
                                                        <button class="btn btn-warning" type="button" id="btn_sub_next_save" name="btn_sub_next_save">&numsp;&numsp;下一步&numsp;&numsp;</button>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div id="tab-2" class="tab-pane">
                                <div class="ibox-content">
                                    <div class="row">
                                        <form class="form-horizontal" id="img_form" action="" method="post">
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">选择图片</label>
                                                <div class="col-sm-6">
                                                    <input type="file" id="file_img" name="file_img" class="form-control">
                                                </div>
                                                <div class="col-sm-3">
                                                    <button class="btn btn-primary" type="button" id="btn_lb_save" name="btn_lb_save">上传</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                    <div class="row">
                                        <div id="img_div" class="main">
                                            ${imageJson}
                                        </div>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                    <div class="row">
                                        <div class="col-sm-5 col-sm-offset-5">
                                            <button class="btn btn-warning" type="button" id="btn_pic_next_save" name="btn_sub_next_save">下一步</button>
                                            <p>&numsp;</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tab-3" class="tab-pane">
                                <%--<div class="col-lg-12">--%>
                                    <%--<div class="ibox">--%>
                                        <div class="ibox-content">
                                            <div class="row">
                                                <div class="col-lg-1"></div>
                                                <div class="col-lg-5">
                                                    <div class="panel panel-default">
                                                        <div class="panel-heading">
                                                            录入信息
                                                        </div>
                                                        <div class="panel-body">
                                                            <div class="list-group" style="height:500px;">
                                                                <form class="form-horizontal" id="notice_form" action="" method="post">
                                                                    <fieldset>
                                                                        <div class="form-group">
                                                                            <label class="col-sm-3 control-label">须知标题</label>
                                                                            <div class="col-sm-7">
                                                                                <input id="n_title" name="n_title" type="text" class="form-control">
                                                                                <input id="notice_id" name="notice_id" type="hidden" value="0"></td>
                                                                            </div>
                                                                        </div>
                                                                        <%--<div class="hr-line-dashed"></div>--%>
                                                                        <div class="form-group">
                                                                            <label class="col-sm-3 control-label">须知内容</label>
                                                                            <div class="col-sm-7">
                                                                                <textarea id="n_content" name="n_content" class="form-control" rows="16" style="resize:none;"></textarea>
                                                                            </div>
                                                                        </div>
                                                                        <%--<div class="hr-line-dashed"></div>--%>
                                                                        <div class="form-group">
                                                                            <div class="col-sm-4 col-sm-offset-4" >
                                                                                <div style="margin-bottom: 10px;margin-top: 40px;">
                                                                                    <button class="btn btn-primary" type="button" id="btn_notice_save" name="btn_notice_save">保存内容</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </fieldset>
                                                                </form>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                                <div class="col-lg-5">
                                                    <div class="panel panel-default">
                                                        <div class="panel-heading">
                                                            显示列表
                                                        </div>
                                                        <div class="panel-body">
                                                            <div class="list-group" style="height:500px;overflow:auto;">
                                                                <form>
                                                                    <div class="hr-line-dashed"></div>
                                                                    <div id="ncontent_div" class="form-group">
                                                                        ${notices}
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-sm-4 col-sm-offset-5">
                                                    <button class="btn btn-warning" type="button" id="btn_notice_next_save" name="btn_notice_save">下一步</button>
                                                </div>
                                            </div>
                                        </div>
                                    <%--</div>--%>
                                <%--</div>--%>
                            </div>
                            <div id="tab-4" class="tab-pane">
                                <%--<div class="col-lg-12">--%>
                                    <%--<div class="ibox">--%>
                                        <div class="ibox-content">
                                            <div class="row">
                                                <div class="col-lg-1"></div>
                                                <div class="col-lg-5">
                                                    <div class="panel panel-default">
                                                        <div class="panel-heading">
                                                            录入信息
                                                        </div>
                                                        <div class="panel-body">
                                                            <div class="list-group" style="height:420px;">
                                                                <form class="form-horizontal m-t" id="sku_form" action="" method="post">
                                                                            <input id="sku_id" name="sku_id" type="hidden" value="0">
                                                                    <%--<fieldset>--%>
                                                                        <%--<div class="hr-line-dashed"></div>--%>
                                                                            <div class="form-group">
                                                                                <label class="col-sm-3 control-label">大人数量</label>
                                                                                <div class="col-sm-3">
                                                                                    <input id="adult" name="adult" type="text" class="form-control" value="1">
                                                                                </div>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label class="col-sm-3 control-label">儿童数量</label>
                                                                                <div class="col-sm-3">
                                                                                    <input id="child" name="child" type="text" class="form-control" value="1">
                                                                                </div>
                                                                            </div>
                                                                        <%--<div class="hr-line-dashed"></div>--%>
                                                                        <div class="form-group">
                                                                            <%--<label class="col-sm-3 control-label">原始价格</label>--%>
                                                                            <%--<div class="col-sm-3">--%>
                                                                            <%--<input id="originalPrice" name="originalPrice" type="text" class="form-control">--%>
                                                                            <%--</div>--%>
                                                                            <label class="col-sm-3 control-label">售卖价格</label>
                                                                            <div class="col-sm-3">
                                                                                <input id="price" name="price" type="text" class="form-control">
                                                                            </div>
                                                                        </div>
                                                                        <%--<div class="hr-line-dashed"></div>--%>
                                                                        <div class="form-group">
                                                                            <label class="col-sm-3 control-label">选课次数</label>
                                                                            <div class="col-sm-3">
                                                                                <input id="courseCount" name="courseCount" type="text" class="form-control">
                                                                            </div>
                                                                        </div>
                                                                        <%--<div class="hr-line-dashed"></div>--%>
                                                                        <div class="form-group" >
                                                                            <label class="col-sm-3 control-label">选课期限</label>
                                                                            <div class="col-sm-3">
                                                                                <input id="time" name="time" type="text" class="form-control">
                                                                            </div>
                                                                            <div class="col-sm-3" style="width: 33%">
                                                                                <div id="div_time">
                                                                                    <select id="timeUnit" name="timeUnit" class="form-control m-b">
                                                                                        <c:forEach items="${time_unit}" var="node">
                                                                                            <option value="${node.id}">${node.name}</option>
                                                                                        </c:forEach>
                                                                                    </select>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                            <div class="form-group">
                                                                                <label class="col-sm-3 control-label">规则描述</label>
                                                                                <div class="col-sm-7">
                                                                                    <textarea id="desc" name="desc" class="form-control" style="resize:none;" rows="3"></textarea>
                                                                                </div>
                                                                            </div>
                                                                        <%--<div class="form-group">--%>
                                                                            <%--<label class="col-sm-3 control-label">时间单位</label>--%>
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
                                                                        <%--<div class="hr-line-dashed"></div>--%>
                                                                        <div class="form-group">
                                                                            <div class="col-sm-4 col-sm-offset-4">
                                                                                <div style="margin-bottom: 10px;margin-top: 30px;">
                                                                                    <button class="btn btn-primary" type="button" id="btn_sku_save" name="btn_sku_save">保存内容</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    <%--</fieldset>--%>
                                                                </form>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                                <div class="col-lg-5">
                                                    <div class="panel panel-default">
                                                        <div class="panel-heading">
                                                            显示列表
                                                        </div>
                                                        <div class="panel-body">
                                                            <div class="list-group" style="height:420px;overflow:auto;">
                                                                <form>
                                                                    <%--<div class="hr-line-dashed"></div>--%>
                                                                    <div id="sku_div" class="form-group">
                                                                        ${skuHtml}
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-sm-12 col-sm-offset-5">
                                                    <div style="margin-bottom: 20px;margin-top: 20px;">
                                                        <button class="btn btn-warning" type="button" id="btn_sku_next_save" name="btn_sku_next_save">下一步</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    <%--</div>--%>
                                <%--</div>--%>
                            </div>

                            <div id="tab-5" class="tab-pane">
                                <div class="ibox-content">
                                    <div class="row">
                                        <div class="col-lg-5 col-sm-offset-1">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    课程评价
                                                </div>
                                                <div class="panel-body">
                                                    <div class="list-group" >
                                                        <form id="comment_form" action="" method="post">
                                                            <div class="row m-b-sm m-t-sm">
                                                                <div class="col-md-12">
                                                                    <div class="input-group" style="margin-bottom: 10px;">
                                                                        <input type="text" placeholder="请输入项目名称" class="input-sm form-control">
                                                                        <span class="input-group-btn"><button type="button" class="btn btn-sm btn-primary" id="btn_query" name="btn_query"> 搜索</button> </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </form>
                                                        <div class="feed-activity-list">
                                                            <div id="content_div" style="height:420px;overflow:auto;">
                                                                ${ntComments}
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="col-lg-5">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    推荐评价
                                                </div>
                                                <div class="panel-body">
                                                    <div class="list-group" style="height:482px;overflow:auto;">
                                                        <form>
                                                            <div id="t_div" class="form-group feed-activity-list">
                                                                ${tComments}
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12 col-sm-offset-5">
                                            <div style="margin-bottom: 20px;margin-top: 20px;">
                                                <a href="${ctx}/sub/info.do?uid=${user.id}" class="btn btn-primary btn-x">&numsp;&numsp;完&numsp;&numsp;成&numsp;&numsp;</a>
                                            </div>
                                        </div>
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

<script language="JavaScript">
    $(document).ready(function () {
        $('.fancybox').fancybox({
            openEffect: 'none',
            closeEffect: 'none'
        });

        $('#img_a').click(function (){
            document.getElementById("img_path").click();
        });

        $('#img_path').change(function (){
            var pathUrl = $('#img_path').val();
            if(pathUrl != null || pathUrl != ""){
                //创建FormData对象
                var data = new FormData();
                //为FormData对象添加数据
                $.each($('#img_path')[0].files, function(i, file) {
                    data.append('upload_file', file);
                });

                $.ajax({
                    url:'/upload/jsonImg.do',
                    type:'POST',
                    data:data,
                    cache: false,
                    contentType: false,    //不可缺
                    processData: false,    //不可缺
                    success:function(data){
                        var obj = $.parseJSON(data);
                        if(obj.success == 0){
                            $("#cover").val(obj.path);
                            $("#img_a").attr("src", $("#filepath").val() + obj.path);
                        }else{
                            layer.alert(obj.msg,10,'提示信息');
                        }
                    }
                });
            }
        });
    });
</script>

</body>
</html>