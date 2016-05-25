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

    <link href="${ctx}/sg-web/css/plugins/switchery/switchery.css" rel="stylesheet">

    <!-- Mainly scripts -->
    <script src="${ctx}/sg-web/js/jquery-2.1.1.min.js"></script>
    <script src="${ctx}/sg-web/js/bootstrap.min.js?v=3.4.0"></script>
    <script src="${ctx}/sg-web/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/sg-web/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <!-- layerDate plugin javascript -->
    <script src="${ctx}/sg-web/js/plugins/layer/laydate/laydate.js"></script>

    <!-- layer javascript -->
    <script src="${ctx}/sg-web/js/plugins/layer/layer.min.js"></script>

    <!-- Switchery -->
    <script src="${ctx}/sg-web/js/plugins/switchery/switchery.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/sg-web/js/hplus.js?v=2.2.0"></script>
    <script src="${ctx}/sg-web/js/plugins/pace/pace.min.js"></script>
    <script src="${ctx}/sg-web/js/sg-admin/sogokids-onkeydown.js"></script>

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
                <h2>活动列表</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="${ctx}/user/index.do?uid=${user.id}">主页</a>
                    </li>
                    <li>
                        <a href="${ctx}/coop_activity/info.do?uid=${user.id}">活动信息</a>
                    </li>
                    <li>
                        <strong>编辑活动</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">
                <h2><a href="${ctx}/coop_activity/info.do?uid=${user.id}" class="btn btn-primary btn-x">返回</a></h2>
            </div>
        </div>
        <div class="row">
            <div class="ibox-content">
                <form class="form-horizontal" id="activity_form" name="activity_form" action="${ctx}/coop_activity/edit.do?uid=${user.id}&id=${model.id}" method="post">
                    <fieldset>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">活动图</label>
                            <div class="col-sm-3" style="cursor: pointer;">
                                <c:choose>
                                    <c:when test="${fn:contains(model.cover,'http')}">
                                        <img id="img_a" src="${model.cover}" style="width: 200px;height: 100px;border: 1px solid #999;"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img id="img_a" src="${filepath}${model.cover}" style="width: 200px;height: 100px;border: 1px solid #999;"/>
                                    </c:otherwise>
                                </c:choose>
                                <input id="img_path" name="img_path" type="file" style="opacity: 0;filter:alpha(opacity=0);">
                                <input id="cover" name="cover" value="${model.cover}" type="hidden">
                                <input id="filepath" name="filepath" type="hidden" value="${filepath}">
                            </div>
                            <label class="col-sm-2 control-label">转发logo</label>
                            <div class="col-sm-3" style="cursor: pointer;">
                                <c:choose>
                                    <c:when test="${fn:contains(model.icon,'http')}">
                                        <img id="img_b" src="${model.icon}" style="width: 200px;height: 100px;border: 1px solid #999;"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img id="img_b" src="${filepath}${model.icon}" style="width: 200px;height: 100px;border: 1px solid #999;"/>
                                    </c:otherwise>
                                </c:choose>
                                <input id="icon_path" type="file" name="icon_path" style="opacity: 0;filter:alpha(opacity=0);">
                                <input id="icon" name="icon" value="${model.icon}" type="hidden">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">选择单位 </label>
                            <div class="col-sm-3">
                                <select id="cooperatorId" name="cooperatorId" class="form-control" >
                                    <c:forEach items="${coops}" var="node">
                                        <c:choose>
                                            <c:when test="${node.id == model.cooperatorId}">
                                                <option value="${node.id}" selected>${node.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${node.id}">${node.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">活动标题 </label>
                            <div class="col-sm-3">
                                <textarea id="title" name="title" class="form-control" rows="3" style="resize:none;">${model.title}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">活动描述 </label>
                            <div class="col-sm-3">
                                <textarea id="desc" name="desc" class="form-control" rows="3" style="resize:none;">${model.desc}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">短信消息 </label>
                            <div class="col-sm-3">
                                <textarea id="message" name="message" class="form-control" rows="3" style="resize:none;">${model.message}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">是否付费 </label>
                            <div class="col-sm-3">
                                <select id="needPay" name="needPay" class="form-control" >
                                    <c:forEach items="${needs}" var="node">
                                        <c:choose>
                                            <c:when test="${node.id == model.needPay}">
                                                <option value="${node.id}" selected>${node.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${node.id}">${node.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">活动价格 </label>
                            <div class="col-sm-3">
                                <input id="price" name="price" type="text" class="form-control" value="${model.price}" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">售卖数量 </label>
                            <div class="col-sm-3">
                                <input id="stock" name="stock" type="text" value="${model.stock}" class="form-control" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">是否新用户专享 </label>
                            <div class="col-sm-3">
                                <%--<input id="forNewUser" name="forNewUser" type="text" class="form-control" value="0" >--%>
                                <%--(值为1是新用户专享,0则不是)--%>
                                    <c:choose>
                                        <c:when test="${model.forNewUser == 1}">
                                            <input id="forNewUser" name="forNewUser" type="checkbox" class="js-switch" checked/>
                                        </c:when>
                                        <c:otherwise>
                                            <input id="forNewUser" name="forNewUser" type="checkbox" class="js-switch"  />
                                        </c:otherwise>
                                    </c:choose>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">开始时间</label>
                            <div class="col-sm-3">
                                <input id="startTime" name="startTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" value="${model.startTime}" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">结束时间</label>
                            <div class="col-sm-3">
                                <input id="endTime" name="endTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" value="${model.endTime}" readonly>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">上线时间</label>
                            <div class="col-sm-3">
                                <input id="onlineTime" name="onlineTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" value="${model.onlineTime}" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">下线时间</label>
                            <div class="col-sm-3">
                                <input id="offlineTime" name="offlineTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" value="${model.offlineTime}" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">活动详情</label>
                            <div class="col-sm-8">
                                <script id="my_editor" name = "content" type="text/plain">${model.detail}</script>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-5">
                                <button class="btn btn-primary" type="button" id="btn_save_activity" name="btn_save_activity">保存内容</button>
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

        var editor = new baidu.editor.ui.Editor({
            textarea : 'detail',
            initialFrameHeight:390,
            initialFrameWidth:null,
            toolbars: [['bold', 'italic','underline','|','forecolor','backcolor','|',"simpleupload",'|','justifyleft','justifycenter','justifyright','justifyjustify']],
            autoHeightEnabled:false
        });
        editor.render("my_editor");

        var elem = document.querySelector('.js-switch');
        var switchery = new Switchery(elem, {
            color: '#1AB394'
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

        $('#img_b').click(function (){
            document.getElementById("icon_path").click();
        });

        $('#icon_path').change(function (){
            var pathUrl = $('#icon_path').val();
            if(pathUrl != null || pathUrl != ""){
                //创建FormData对象
                var data1 = new FormData();
                //为FormData对象添加数据
                $.each($('#icon_path')[0].files, function(i, file) {
                    data1.append('upload_file', file);
                });

                $.ajax({
                    url:'/upload/jsonImg.do',
                    type:'POST',
                    data:data1,
                    cache: false,
                    contentType: false,    //不可缺
                    processData: false,    //不可缺
                    success:function(data){
                        var obj = $.parseJSON(data);
                        if(obj.success == 0){
                            $("#icon").val(obj.path);
                            $("#img_b").attr("src", $("#filepath").val() + obj.path);
                        }else{
                            layer.alert(obj.msg,10,'提示信息');
                        }
                    }
                });
            }
        });

        $('#btn_save_activity').click(function (){
            var strP=/^\d+(\.\d+)?$/;
            var icon = $('#icon').val();
            var cover = $('#cover').val();
            var title = $('#title').val();
            var message = $('#message').val();
            var needPay = $('#needPay').val();
            var price = $('#price').val();
            var startTime = $('#startTime').val();
            var endTime = $('#endTime').val();
            var onlineTime = $('#onlineTime').val();
            var offlineTime = $('#offlineTime').val();

            if(title == null || title == ""){
                layer.alert('请填写标题信息！',3,'提示信息');
                return false;
            }else if(cover == null || cover == ""){
                layer.alert('请上传活动图！',3,'提示信息');
                return false;
            }else if(icon == null || icon == ""){
                layer.alert('请上传转发logo！',3,'提示信息');
                return false;
            }else if(message == null || message == ""){
                layer.alert('请填写短信信息！',3,'提示信息');
                return false;
            }else if(needPay == 1 && (price == null || price == "" || !strP.test(price))){
                layer.alert('您已选择需要付款活动,未填写价格信息或填写格式出错(只能填写数字类型,举例格式"100或10.99")！',3,'提示信息');
                return false;
            }else if(startTime == null || startTime == ""){
                layer.alert('请填写开始时间信息！',3,'提示信息');
                return false;
            }else if(endTime == null || endTime == ""){
                layer.alert('请填写结束时间信息！',3,'提示信息');
                return false;
            }else if(onlineTime == null || onlineTime == ""){
                layer.alert('请填写上线时间信息！',3,'提示信息');
                return false;
            }else if(offlineTime == null || offlineTime == ""){
                layer.alert('请填写下线时间信息！',3,'提示信息');
                return false;
            }else {
                $('#activity_form').submit();
            }
        });

    });
</script>
</body>
</html>