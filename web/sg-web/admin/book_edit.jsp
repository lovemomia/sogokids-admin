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

    <!-- Data Tables -->
    <link href="${ctx}/sg-web/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">

    <style>
        .main{margin:0 auto;}
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

        .teacher_w_h{
            width: 50px;
            height: 50px;
            border: solid 1px #b3b3b3;
        }
    </style>

    <!-- Mainly scripts -->
    <script src="${ctx}/sg-web/js/jquery-2.1.1.min.js"></script>
    <script src="${ctx}/sg-web/js/bootstrap.min.js?v=3.4.0"></script>
    <script src="${ctx}/sg-web/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/sg-web/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Data Tables -->
    <script src="${ctx}/sg-web/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="${ctx}/sg-web/js/plugins/dataTables/dataTables.bootstrap.js"></script>

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

    <!-- ueditor javascript -->
    <script type="text/javascript" charset="utf-8" src="${ctx}/sg-web/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/sg-web/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/sg-web/ueditor/lang/zh-cn/zh-cn.js"> </script>

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
                <h2>试听课程</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="${ctx}/user/index.do?uid=${user.id}">主页</a>
                    </li>
                    <li>
                        <a href="${ctx}/book/info.do?uid=${user.id}">试听课程</a>
                    </li>
                    <li>
                        <strong>编辑课程</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">
                <h2><a href="${ctx}/book/info.do?uid=${user.id}" class="btn btn-primary btn-x">返回</a></h2>
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
                                <li id="tab_li_1" class="active"><a id="tab1" data-toggle="tab" href="tabs_panels.html#tab-1"><i class="fa fa-laptop"></i>基本信息</a></li>
                                <li id="tab_li_2" class=""><a id="tab2" data-toggle="tab" href="tabs_panels.html#tab-2"><i class="fa fa-picture-o"></i>轮播图片</a></li>
                                <li id="tab_li_3" class=""><a id="tab3" data-toggle="tab" href="tabs_panels.html#tab-3"><i class="fa fa-book"></i>课前绘本</a></li>
                                <li id="tab_li_4" class=""><a id="tab4" data-toggle="tab" href="tabs_panels.html#tab-4"><i class="fa fa-gears"></i>课程表</a></li>
                                <li id="tab_li_5" class=""><a id="tab5" data-toggle="tab" href="tabs_panels.html#tab-5"><i class="fa fa-reddit"></i>讲师团</a></li>
                                <li id="tab_li_6" class=""><a id="tab6" data-toggle="tab" href="tabs_panels.html#tab-6"><i class="fa fa-newspaper-o"></i>图文详情</a></li>
                            </ul>
                        </div>
                    </div>

                    <div class="panel-body">

                        <div class="tab-content">
                            <div id="tab-1" class="tab-pane active">
                                <div class="ibox-content">
                                    <div class="list-group">
                                        <form class="form-horizontal" id="course_form" action="" method="post">
                                            <fieldset>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">标题</label>
                                                    <div class="col-sm-3">
                                                        <textarea id="title" name="title" class="form-control" rows="8">${model.title}</textarea>
                                                        <input id="course_id" name="course_id" type="hidden" value="${model.id}">
                                                        <input id="subjectId" name="subjectId" type="hidden" value="${model.subjectId}">
                                                    </div>
                                                    <label class="col-sm-2 control-label"></label>
                                                    <div class="col-sm-3">
                                                        <input id="img_path" name="img_path" type="file" class="form-control" style="width: 200px;">
                                                        <img id="img_a" src="${filepath}${model.cover}" style="width: 200px;height: 100px"/>
                                                        <input id="cover" name="cover" value="${model.cover}" type="hidden">
                                                        <input id="filepath" name="filepath" type="hidden" value="${filepath}">
                                                    </div>
                                                    <div class="col-sm-3 col-sm-offset-3">
                                                        <button class="btn btn-primary" type="button" id="btn_img_save" name="btn_img_save">上传</button>
                                                    </div>
                                                </div>
                                                <div class="hr-line-dashed"></div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">所属机构</label>
                                                    <div class="col-sm-3">
                                                        <select id="institutionId" name="institutionId" class="form-control m-b" >
                                                            <c:forEach items="${insts}" var="node">
                                                                <c:choose>
                                                                    <c:when test="${node.id == model.institutionId}">
                                                                        <option value="${node.id}" selected>${node.name}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${node.id}"  >${node.name}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <label class="col-sm-2 control-label">售卖价格</label>
                                                    <div class="col-sm-3">
                                                        <input id="price" name="price" type="text" class="form-control" value="${model.price}">
                                                    </div>
                                                </div>
                                                <%--<div class="hr-line-dashed"></div>--%>
                                                <%--<div class="form-group">--%>
                                                    <%--<label class="col-sm-3 control-label">课程体系</label>--%>
                                                    <%--<div class="col-sm-7">--%>
                                                        <%--<select class="form-control m-b" id="subjectId" name="subjectId">--%>
                                                            <%--<c:forEach items="${subs}" var="node">--%>
                                                                <%--<c:choose>--%>
                                                                    <%--<c:when test="${node.id == model.subjectId}">--%>
                                                                        <%--<option value="${node.id}" selected>${node.title}</option>--%>
                                                                    <%--</c:when>--%>
                                                                    <%--<c:otherwise>--%>
                                                                        <%--<option value="${node.id}"  >${node.title}</option>--%>
                                                                    <%--</c:otherwise>--%>
                                                                <%--</c:choose>--%>
                                                            <%--</c:forEach>--%>
                                                        <%--</select>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                                <div class="hr-line-dashed"></div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">年龄范围</label>
                                                    <div class="col-sm-3">
                                                        <input id="minAge" name="minAge" type="text" class="form-control" value="${model.minAge}">
                                                    </div>
                                                    <div class="col-sm-1">至</div>
                                                    <div class="col-sm-3">
                                                        <input id="maxAge" name="maxAge" type="text" class="form-control" value="${model.maxAge}">
                                                    </div>
                                                    <div class="col-sm-1">岁</div>
                                                </div>
                                                <div class="hr-line-dashed"></div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">课程目标</label>
                                                    <div class="col-sm-8">
                                                        <textarea id="goal" name="goal" class="form-control" rows="5">${model.goal}</textarea>
                                                    </div>
                                                </div>
                                                <div class="hr-line-dashed"></div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">上课流程</label>
                                                    <div class="col-sm-8">
                                                        <textarea id="flow" name="flow" class="form-control" rows="5">${model.flow}</textarea>
                                                    </div>
                                                </div>
                                                <div class="hr-line-dashed"></div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">特别提示</label>
                                                    <div class="col-sm-8">
                                                        <textarea id="tips" name="tips" class="form-control" rows="5">${model.tips}</textarea>
                                                    </div>
                                                </div>

                                                <div class="hr-line-dashed"></div>
                                                <div class="form-group">
                                                    <div class="col-sm-4 col-sm-offset-4">
                                                        <button class="btn btn-primary" type="button" id="btn_cour_save" name="btn_cour_save">&numsp;&numsp;保存内容&numsp;&numsp;</button>
                                                        &numsp;&numsp;&numsp;&numsp;&numsp;&numsp;
                                                        <button class="btn btn-warning" type="button" id="btn_cour_next_save" name="btn_cour_next_save">&numsp;&numsp;下一步&numsp;&numsp;</button>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div id="tab-2" class="tab-pane">
                                <div class="ibox-content">
                                    <div class="list-group">
                                        <form class="form-horizontal" id="img_form" action="" method="post">
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">轮播图片</label>
                                                <div class="col-sm-6">
                                                    <input type="file" id="file_img" name="file_img" class="form-control">
                                                </div>
                                                <div class="col-sm-3">
                                                    <button class="btn btn-primary" type="button" id="btn_lb_save" name="btn_lb_save">上传</button>
                                                </div>
                                            </div>
                                        </form>
                                        <form>
                                            <div class="hr-line-dashed"></div>
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="ibox float-e-margins">
                                                        <div class="ibox-title">
                                                            <h5>轮播图片</h5>
                                                        </div>
                                                        <div class="ibox-content">
                                                            <div id="img_div" class="main">
                                                                ${imageJson}
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                    <div class="form-group">
                                        <div class="col-sm-5 col-sm-offset-5">
                                            <button class="btn btn-warning" type="button" id="btn_pic_next_save" name="btn_pic_next_save">下一步</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tab-3" class="tab-pane">
                                <div class="ibox-content">
                                    <div class="list-group">
                                        <form class="form-horizontal" id="book_form" action="" method="post">
                                            <fieldset>
                                                <div class="form-group">
                                                    <label class="col-sm-3 control-label">绘本图片</label>
                                                    <div class="col-sm-6">
                                                        <input type="file" id="hb_img" name="hb_img" class="form-control">
                                                    </div>
                                                    <div class="col-sm-3">
                                                        <button class="btn btn-primary" type="button" id="btn_book_save" name="btn_book_save">上传</button>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </form>
                                        <form>
                                            <div class="hr-line-dashed"></div>
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="ibox float-e-margins">
                                                        <div class="ibox-title">
                                                            <h5>绘本图片</h5>
                                                        </div>
                                                        <div class="ibox-content">
                                                            <div id="book_div" class="main">
                                                                ${bookJson}
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </form>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                    <div class="form-group">
                                        <div class="col-sm-5 col-sm-offset-5">
                                            <button class="btn btn-warning" type="button" id="btn_book_next_save" name="btn_book_next_save">下一步</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div id="tab-4" class="tab-pane">
                                <div class="col-lg-12">
                                    <div class="ibox">
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
                                                                <form class="form-horizontal" id="sku_form" action="" method="post">
                                                                    <fieldset>
                                                                        <div class="form-group">
                                                                            <label class="col-sm-3 control-label">上课地址</label>
                                                                            <div class="col-sm-6">
                                                                                <div id="course_placeId" class="col-sm-7">
                                                                                    <select id="placeId" name="placeId" class="form-control m-b" style="width: 200px" >
                                                                                        <c:forEach items="${places}" var="node">
                                                                                            <option value="${node.id}">${node.name} ${node.address}</option>
                                                                                        </c:forEach>
                                                                                    </select>
                                                                                </div>
                                                                            </div>
                                                                            <div class="col-sm-1">
                                                                                <button class="btn btn-primary" data-toggle="modal" data-target="#myModal4" type="button" id="btn_place_add" name="btn_place_add">+</button>
                                                                            </div>
                                                                        </div>
                                                                        <div class="hr-line-dashed"></div>
                                                                        <div class="form-group">
                                                                            <label class="col-sm-3 control-label">库存数量</label>
                                                                            <div class="col-sm-7">
                                                                                <input id="sku_id" name="sku_id" type="hidden" value="0">
                                                                                <input id="stock" name="stock" type="text" value="20" class="form-control">
                                                                            </div>
                                                                        </div>
                                                                        <div class="hr-line-dashed"></div>
                                                                        <div class="form-group">
                                                                            <label class="col-sm-3 control-label">大人数量</label>
                                                                            <div class="col-sm-3">
                                                                                <input id="adult" name="adult" type="text" class="form-control">
                                                                            </div>
                                                                            <label class="col-sm-3 control-label">儿童数量</label>
                                                                            <div class="col-sm-3">
                                                                                <input id="child" name="child" type="text" class="form-control">
                                                                            </div>
                                                                        </div>
                                                                        <div class="hr-line-dashed"></div>
                                                                        <div class="form-group">
                                                                            <label class="col-sm-3 control-label">开始时间</label>
                                                                            <div class="col-sm-7">
                                                                                <input id="startTime" name="startTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly>
                                                                            </div>
                                                                        </div>
                                                                        <div class="hr-line-dashed"></div>
                                                                        <div class="form-group">
                                                                            <label class="col-sm-3 control-label">结束时间</label>
                                                                            <div class="col-sm-7">
                                                                                <input id="endTime" name="endTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly>
                                                                            </div>
                                                                        </div>
                                                                        <div class="hr-line-dashed"></div>
                                                                        <div class="form-group">
                                                                            <label class="col-sm-3 control-label">下线时间</label>
                                                                            <div class="col-sm-7">
                                                                                <input id="deadline" name="deadline" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly>
                                                                            </div>
                                                                        </div>
                                                                        <div class="hr-line-dashed"></div>
                                                                        <div class="form-group">
                                                                            <div class="col-sm-4 col-sm-offset-4">
                                                                                <button class="btn btn-primary" type="button" id="btn_sku_save" name="btn_sku_save">保存内容</button>
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
                                                            <div class="list-group">
                                                                <form>
                                                                    <div id="sku_div" class="form-group" style="height:500px;overflow:auto;">
                                                                        ${skuHtml}
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>
                                                        <div class="modal inmodal" id="myModal4" tabindex="-1" role="dialog"  aria-hidden="true">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content animated fadeIn">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                                                        <%--<i class="fa fa-picture-o modal-icon"></i>--%>
                                                                        <h4 class="modal-title">添加地址</h4>
                                                                        <%--<small>这里可以显示副标题。</small>--%>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <form class="form-horizontal" id="place_form" action="" method="post">
                                                                            <fieldset>
                                                                                <div class="selectList">
                                                                                    <div class="form-group">
                                                                                        <label class="col-sm-2 control-label">选择城市 </label>
                                                                                        <div class="col-sm-8">
                                                                                            <select id="placeCityId" name="placeCityId" class="province form-control m-b">
                                                                                                <option>请选择</option>
                                                                                            </select>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="form-group">
                                                                                        <label class="col-sm-2 control-label">选择区域 </label>
                                                                                        <div class="col-sm-8">
                                                                                            <select id="quid" name="quid" class="city form-control m-b">
                                                                                                <option>请选择</option>
                                                                                            </select>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="form-group">
                                                                                        <label class="col-sm-2 control-label">选择街道 </label>
                                                                                        <div class="col-sm-8">
                                                                                            <select id="regionId" name="regionId" class="district form-control m-b">
                                                                                                <option>请选择</option>
                                                                                            </select>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="form-group">
                                                                                        <label class="col-sm-2 control-label">地址名称 </label>
                                                                                        <div class="col-sm-8">
                                                                                            <input id="name" name="name" type="text" class="form-control" >
                                                                                        </div>
                                                                                    </div>

                                                                                    <div class="form-group">
                                                                                        <label class="col-sm-2 control-label">地址详情 </label>
                                                                                        <div class="col-sm-8">
                                                                                            <input id="ads" name="ads" type="text" class="form-control" >
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="form-group">
                                                                                        <label class="col-sm-2 control-label">经纬度 </label>
                                                                                        <div class="col-sm-6">
                                                                                            <input id="lng_lat" name="lng_lat" type="text" class="form-control" >(举例格式:116.511203,39.926666)
                                                                                        </div>
                                                                                        <div class="col-sm-3">
                                                                                            <button class="btn btn-primary" type="button" id="btn_query_l" name="btn_query_l">查看经纬度</button>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="form-group">
                                                                                        <label class="col-sm-2 control-label">地址描述 </label>
                                                                                        <div class="col-sm-8">
                                                                                            <textarea id="desc_place" name="desc_place" type="text" class="form-control"></textarea>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </fieldset>
                                                                        </form>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-primary" id="btn_place_save" name="btn_place_save">保存</button>
                                                                        <button type="button" class="btn btn-white" data-dismiss="modal" id="btn_place_close" name="btn_place_close">关闭</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="hr-line-dashed"></div>
                                            <div class="form-group">
                                                <div class="col-sm-5 col-sm-offset-5">
                                                    <button class="btn btn-warning" type="button" id="btn_sku_next_save" name="btn_sku_next_save">&numsp;&numsp;下一步&numsp;&numsp;</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tab-5" class="tab-pane">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="ibox">
                                            <div class="ibox-title">
                                                <h5>讲师团</h5>
                                            </div>
                                            <div class="ibox-content">
                                                <div class="row">
                                                    <div class="col-lg-5">
                                                        <div class="panel panel-default">
                                                            <div class="panel-heading">
                                                                <h5>未选讲师</h5>
                                                            </div>
                                                            <div class="panel-body">
                                                                <div class="list-group" style="height:700px;overflow:auto;">
                                                                    <form id="w_form" action="" method="">
                                                                        <%--<table class="table table-striped table-bordered table-hover dataTables-example">--%>
                                                                        <div id="w-teacher">
                                                                            ${w_teacher}
                                                                        </div>
                                                                        <%--</table>--%>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-lg-2">
                                                        <div class="panel panel-default">
                                                            <div class="panel-heading"><h5>操作</h5></div>
                                                            <div class="panel-body">
                                                                <div class="list-group" style="height:700px;overflow:auto;">
                                                                    <p>&numsp;</p><p>&numsp;</p><p>&numsp;</p><p>&numsp;</p><p>&numsp;</p><p>&numsp;</p><p>&numsp;</p>
                                                                    <p>&numsp;</p><p>&numsp;</p><p>&numsp;</p><p>&numsp;</p>
                                                                    <p align="center"><button id="add_teacher" name="add_teacher" type="button" class="btn btn-primary btn-sm"> 添加 <i class="fa fa-mail-forward"></i></button></p>
                                                                    <p>&numsp;</p><p>&numsp;</p>
                                                                    <p align="center"><button id="del_teacher" name="del_teacher" type="button" class="btn btn-danger btn-sm"><i class="fa fa-mail-reply"></i> 取消 </button></p>
                                                                    <p>&numsp;</p><p>&numsp;</p>
                                                                    <p align="center"><button id="btn_teacher_next_save" name="btn_teacher_next_save" type="button" class="btn btn-warning btn-sm"> 下一步 </button></p>
                                                                    <p>&numsp;</p><p>&numsp;</p><p>&numsp;</p><p>&numsp;</p>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </div>

                                                    <div class="col-lg-5">
                                                        <div class="panel panel-default">
                                                            <div class="panel-heading">
                                                                <h5>已选讲师</h5>
                                                            </div>
                                                            <div class="panel-body">
                                                                <div class="list-group" style="height:700px;overflow:auto;">
                                                                    <form id="y_form" action="" method="">
                                                                        <%--<table class="table table-striped table-bordered table-hover dataTables-example">--%>
                                                                        <div id="y-teacher">
                                                                            ${y_teacher}
                                                                        </div>
                                                                        <%--</table>--%>
                                                                    </form>
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
                            <div id="tab-6" class="tab-pane">
                                <div class="col-lg-12">
                                    <div class="ibox"></div>
                                    <div class="ibox-content">
                                        <form id="t_w_xq" action="" method="">
                                            <fieldset>
                                                <div class="row">
                                                    <div class="ibox-content">
                                                        <div class="form-group">
                                                            <div class="col-sm-12">
                                                                <label class="col-sm-12 control-label">课程介绍 </label>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="col-sm-12">
                                                                <textarea id="abstracts" name="abstracts" class="form-control" style="width: 1000px;" rows="3">${detail.abstracts}</textarea>
                                                                <input id="d_id" name="d_id" type="hidden" value="${detail.id}">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row">
                                                    <div class="ibox-content">
                                                        <div class="col-lg-8">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading">
                                                                    录入课程图文
                                                                </div>
                                                                <div class="panel-body">
                                                                    <div class="list-group" style="height:600px;overflow:auto;">
                                                                        <div class="form-group">
                                                                            <label class="control-label">标题 </label>
                                                                            <input id="d_title" name="d_title" type="text" class="form-control">
                                                                            <input id="inti" name="inti" type="hidden" value="-1">
                                                                        </div>
                                                                        <div class="form-group">
                                                                            <%--<div class="col-sm-12">--%>
                                                                                <label class="control-label">正文 </label>
                                                                                <script id="my_editor" name = "detail" type="text/plain"></script>
                                                                            <%--</div>--%>
                                                                        </div>
                                                                        <p>&numsp;</p>
                                                                        <div class="form-group">
                                                                            <div class="col-sm-4 col-sm-offset-4">
                                                                                <button class="btn btn-primary" type="button" id="btn_detail_save" name="btn_detail_save">添加</button>
                                                                                &numsp;&numsp;
                                                                                <button class="btn btn-danger" type="button" id="btn_clear" name="btn_clear">清空</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                            </div>
                                                        </div>

                                                        <div class="col-lg-4 ">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading">
                                                                    显示图文列表
                                                                </div>
                                                                <div class="panel-body">
                                                                    <div class="list-group" style="height:600px;overflow:auto;">
                                                                        <form>
                                                                            <%--<div class="hr-line-dashed"></div>--%>
                                                                            <div id="detail_div" class="form-group">
                                                                                <%--${skuHtml}--%>
                                                                                <p align="center">暂时没有图文详情信息!</p>
                                                                            </div>
                                                                        </form>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="ibox-content">
                                                        <%--<div class="hr-line-dashed"></div>--%>
                                                        <div class="form-group">
                                                            <div class="col-sm-5 col-sm-offset-4">
                                                                <button class="btn btn-primary" type="button" id="btn_all_save" name="btn_all_save">保存内容</button>
                                                                <%--&numsp;&numsp;&numsp;&numsp;--%>
                                                                <%--<a href="${ctx}/book/info.do?uid=${user.id}" class="btn btn-primary btn-x">完成</a>--%>
                                                            </div>
                                                        </div>
                                                        <p>&numsp;</p>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div>
                                </div>

                            </div> <!-- tabs end-->

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

<!-- sg-admin javascript -->
<script src="${ctx}/sg-web/js/sg-admin/sogokids-book.js"></script>
<script src="${ctx}/sg-web/js/sg-admin/sogokids-img.js"></script>
<script src="${ctx}/sg-web/js/sg-admin/sogokids-cour-tabs.js"></script>

<script language="JavaScript">

    function createObject(title,content){
        var obj = new Object();
        obj.title= title;
        obj.content = content;
//        obj.get = function(){
//            alert(this.title + ", " + this.content);
//        }
        return obj;
    }

    var content_array = new Array();

    var editor = new baidu.editor.ui.Editor({
        textarea : 'detail',
        initialFrameHeight:390,
        initialFrameWidth:null,
        toolbars: [["simpleupload"]],
        autoHeightEnabled:false
    });
    editor.render("my_editor");


    $(function() {

        $.post("/course/loadDetail.do?courseId="+${model.id},
                function(data){
//                    console.log(data.detailJson);
                    var detailobj = $.parseJSON(data.detailJson);
                    for(var n = 0;n < detailobj.length; n++){
                        var detail = new createObject(detailobj[n].title,detailobj[n].content);
                        content_array.push(detail);
                    }
                    arrayData();
                }, "json");

        $('.dataTables-example').dataTable({
            "aLengthMenu":[8],
            "bSort": false //排序功能
        });

        $(".selectList").each(function(){
            var url = "/place/datajson.do?id=0";
            var areaJson;
            var temp_html;
            var oProvince = $(this).find(".province");
            var oCity = $(this).find(".city");
            var oDistrict = $(this).find(".district");
            //初始化省
            var province = function(){
                $.each(areaJson,function(i,province){
                    temp_html+="<option value='"+province.id+"'>"+province.name+"</option>";
                });
                oProvince.html(temp_html);
                city();
            };
            //赋值市
            var city = function(){
                temp_html = "";

                var n = oProvince.get(0).selectedIndex;
                var regions = areaJson[n].regions;
                if(regions == undefined){
                }else{
                    $.each(areaJson[n].regions,function(i,city){
                        temp_html+="<option value='"+city.id+"'>"+city.name+"</option>";
                    });
                }

                oCity.html(temp_html);
                district();
            };
            //赋值县
            var district = function(){
                temp_html = "";
                var m = oProvince.get(0).selectedIndex;
                var n = oCity.get(0).selectedIndex;

                if(n == -1){
                }else if(typeof(areaJson[m].regions[n].regchild) == "undefined"){
                    oDistrict.css("display","none");
                }else{
                    oDistrict.css("display","inline");
                    $.each(areaJson[m].regions[n].regchild,function(i,district){
                        temp_html+="<option value='"+district.id+"'>"+district.name+"</option>";
                    });
                };
                oDistrict.html(temp_html);
            };
            //选择省改变市
            oProvince.change(function(){
                city();
            });
            //选择市改变县
            oCity.change(function(){
                district();
            });
            //获取json数据
            $.getJSON(url,function(data){
                areaJson = data;
                province();
            });
        });

        $("#btn_query_l").click(function(){
            window.open("http://api.map.baidu.com/lbsapi/getpoint/index.html");
        });

        $("#btn_detail_save").click(function(){
            var title = $('#d_title').val();
            var content = editor.getContent();
//            alert(editor.getPlainTxt());
            if(title == "" || title == null){
                layer.alert('请填写标题信息！',5,'提示信息');
            }else if (content == "" || content == null) {
                layer.alert('请填写图文信息！',5,'提示信息');
            } else {
                var obj = new createObject(title,content);
                var inti = $('#inti').val();
                if(inti < 0){
                    content_array.push(obj);
                }else{
                    content_array[inti] = obj;
                }

                arrayData();

                $('#d_title').val("");
                editor.setContent("");
                $('#inti').val(-1);
            }

        });



        $("#btn_all_save").click(function(){
            var courseId = $('#course_id').val();
            var d_id = $('#d_id').val();
            var abstracts = $('#abstracts').val();
            var details = JSON.stringify(content_array);
            $.post("/course/addDetail.do?courseId="+courseId, {d_id:d_id,abstracts:abstracts,details:details},
                    function(data){
                        if(data.success == 0){
                            $("#d_id").attr("value",data.d_id);
                        }
                        layer.alert(data.msg,10,'提示信息');
                    }, "json");

//            var obj = new createObject(title,content);
//            alert(obj.title + ", " + obj.content);
//            var inti = $('#inti').val();
//            if(inti < 0){
//                content_array.push(obj);
//            }else{
//                content_array[inti] = obj;
//            }
//
//            arrayData();
//
//            $('#d_title').val("");
//            editor.setContent("");
//            $('#inti').val(-1);

        });

        $("#btn_clear").click(function(){
            $('#d_title').val("");
            editor.setContent("");
            $('#inti').val(-1);

        });
    });

    function editDetail(i){
        var obj = content_array[i];
        $('#inti').val(i);
        $('#d_title').val(obj.title);
        editor.setContent(obj.content);
    }

    Array.prototype.remove=function(dx)
    {
        if(isNaN(dx)||dx>this.length){return false;}
        for(var i=0,n=0;i<this.length;i++)
        {
            if(this[i]!=this[dx])
            {
                this[n++]=this[i]
            }
        }
        this.length-=1
    }

    function delDetail(i){
        layer.confirm('您确定要删除此图文详情内容吗？', function(index){
            content_array.remove(i);
            arrayData();
            layer.close(index);
        });
    }

    function arrayData(){
        var html = "<div align='left'>";
        if(content_array.length < 0){
            html = html + "<p>没有图文信息!</p>";
        }else{
            for(var j=0;j<content_array.length; j++){
                var obj = content_array[j];
                html = html +"<h3>"+ obj.title+"</h3>";
                html = html + "<br>";
                html = html + obj.content;
                html = html + "<br>";
                var btn_edit_id = "btn_detail_edit"+j;
                var btn_del_id = "btn_detail_del"+j;
                html = html + "<p align='center'>" + "<button class='btn btn-primary btn-sm' type='button' id='"+btn_edit_id+"' name='"+btn_edit_id+"' onclick='editDetail("+j+")'><i class='fa fa-pencil'></i>编辑</button>";
                html = html + "&numsp;&numsp;";
                html = html + "<button class='btn btn-danger btn-sm' type='button' id='"+btn_del_id+"' name='"+btn_del_id+"' onclick='delDetail("+j+")'><i class='fa fa-trash'></i>删除</button> "+ "</p>";
                html = html + "<div class='hr-line-dashed'></div>";
            }
        }
        html = html + "</div>";

        var divshow = $("#detail_div");
        divshow.text("");
        divshow.append(html);
    }
</script>

</body>
</html>