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

    <style>
        .clickShow {
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.8);
            filter:alpha(opacity=10);
            opacity:.1;
            left:0px;
            top:0px;
            position:fixed;
            overflow:hidden;
            z-index:10000;
            display: none;
        }

        .clickShow >img {
            width: 200px;
            height: 200px;
            position: absolute;
            border: 1px solid #999;
            top: 50%;
            left: 50%;
            margin-left: -50px;
            margin-top: -50px;
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
    <!-- layerDate plugin javascript -->
    <script src="${ctx}/sg-web/js/plugins/layer/laydate/laydate.js"></script>

    <!-- layer javascript -->
    <script src="${ctx}/sg-web/js/plugins/layer/layer.min.js"></script>
    <script src="${ctx}/sg-web/js/sg-admin/sogokids-onkeydown.js"></script>

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
                <h2>微课列表</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="${ctx}/user/index.do?uid=${user.id}">主页</a>
                    </li>
                    <li>
                        <a href="${ctx}/expert/course_info.do?uid=${user.id}">微课信息</a>
                    </li>
                    <li>
                        <strong>创建微课</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">
                <h2><a href="${ctx}/expert/course_info.do?uid=${user.id}" class="btn btn-primary btn-x">返回</a></h2>
            </div>
        </div>
        <div class="row">
            <div class="ibox-content">
                <form class="form-horizontal" id="vform" action="${ctx}/expert/course_add.do?uid=${user.id}" method="post">
                    <fieldset>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">上传图片</label>
                            <div class="col-sm-2" style="cursor: pointer;">
                                <img id="img_a" src="${ctx}/sg-web/img/770jiahao.jpg" style="width: 200px;height: 100px;border: 1px solid #999;"/>
                                <input id="img_path" type="file" name="img_path" style="opacity: 0;filter:alpha(opacity=0);">
                                <input id="cover" name="cover" type="hidden">
                                <input id="filepath" name="filepath" type="hidden" value="${filepath}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">标题</label>
                            <div class="col-sm-8">
                                <textarea id="title" name="title" class="form-control" rows="3" style="resize:none;"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">标题</label>
                            <div class="col-sm-8">
                                <textarea id="subhead" name="subhead" class="form-control" rows="3" style="resize:none;"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">专家信息 </label>
                            <div class="col-sm-3">
                                <select id="expertId" name="expertId" class="form-control" >
                                    <c:forEach items="${experts}" var="node">
                                        <option value="${node.id}">${node.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">微课音频</label>
                            <div class="col-sm-1">
                                <button class="btn btn-primary" type="button" id="file_g" name="file_g">上传文件</button>
                                <input id="file_path" type="file" name="file_path" style="opacity: 0;filter:alpha(opacity=0);">
                                <input id="content" name="content" type="text" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">音频时长</label>
                            <div class="col-sm-3">
                                <input id="mins" name="mins" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">提问价格</label>
                            <div class="col-sm-3">
                                <input id="price" name="price" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">开始时间</label>
                            <div class="col-sm-4">
                                <input id="startTime" name="startTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">结束时间</label>
                            <div class="col-sm-4">
                                <input id="endTime" name="endTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-8">
                                <textarea id="desc" name="desc" class="form-control" rows="3" style="resize:none;"></textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-5">
                                <button class="btn btn-primary" type="submit">保存内容</button>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
        <div id="zzdiv" class="clickShow">
            <img src="${ctx}/sg-web/img/upload.gif"/>
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

        $('#file_g').click(function (){
            document.getElementById("file_path").click();
        });

        $('#file_path').change(function (){
            var pathUrl = $('#file_path').val();
            if(pathUrl != null || pathUrl != ""){
                //创建FormData对象
                var data = new FormData();
                //为FormData对象添加数据
                $.each($('#file_path')[0].files, function(i, file) {
                    data.append('upload_file', file);
                });

                $("#zzdiv").show();

                $.ajax({
                    url:'/upload/jsonFile.do',
                    type:'POST',
                    data:data,
                    cache: false,
                    contentType: false,    //不可缺
                    processData: false,    //不可缺
                    success:function(data){
                        var obj = $.parseJSON(data);
                        if(obj.success == 0){
                            $("#content").val(obj.path);
                            $("#zzdiv").hide();
                            layer.alert(obj.msg,10,'提示信息');
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