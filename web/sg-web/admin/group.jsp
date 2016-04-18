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

    <!-- Data Tables -->
    <link href="${ctx}/sg-web/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">

    <link href="${ctx}/sg-web/css/animate.css" rel="stylesheet">
    <link href="${ctx}/sg-web/css/style.css?v=2.2.0" rel="stylesheet">

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
            <div class="row">
                <div class="col-lg-10" style="margin-left: 10px;margin-right: 10px;">
                    <h2>批量选课</h2>
                <%--<ol class="breadcrumb">--%>
                    <%--<li>--%>
                        <%--<a href="${ctx}/user/index.do?uid=${user.id}">主页</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a href="${ctx}/group/info.do?uid=${user.id}">批量选课</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<strong>数据列表</strong>--%>
                    <%--</li>--%>
                <%--</ol>--%>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-10" style="margin-left: 10px;margin-right: 10px;">
                    <h2><button class="btn btn-primary" data-toggle="modal" data-target="#myGroup1" type="button" id="btn_create" name="btn_create">新增组</button></h2>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="ibox-content">
                <form>
                    <table class="table table-striped table-bordered table-hover dataTables-example">
                        <thead>
                        <tr class="gradeX">
                            <th>编号</th>
                            <th>组名</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${entitys}" var="entity">
                            <tr>
                                <td style="width: 10%;"><c:out value="${entity.id}"></c:out></td>
                                <td style="width: 30%">
                                    <c:out value="${entity.name}"></c:out>&numsp;&numsp;<c:out value="${entity.courseName}"></c:out>
                                </td>
                                <td style="width: 15%;"><c:out value="${fn:substring(entity.addTime,0,19)}"></c:out></td>
                                <td class="center">
                                    <a href="${ctx}/group/oper.do?uid=${user.id}&id=${entity.id}&mark=2" class="btn btn-white btn-sm"><i class="fa fa-user"></i> 成员 </a>
                                    <c:if test="${entity.xkFlag == 0}">
                                        <a href="${ctx}/group/oper.do?uid=${user.id}&id=${entity.id}&mark=1" class="btn btn-white btn-sm"><i class="fa fa-check-circle"></i> 选课 </a>
                                    </c:if>
                                    <button class="btn btn-white btn-sm" data-toggle="modal" data-target="#myGroup2" type="button" id="btn_edit" name="btn_edit" onclick="editGroup('${entity.id}','${entity.name}')" ><i class="fa fa-pencil"></i>编辑</button>
                                    <c:if test="${entity.xkFlag == 0}">
                                        <a href="javascript:void(0)" onclick="delGroup(${entity.id})" class="btn btn-white btn-sm"><i class="fa fa-times-circle"></i> 删除 </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
            </div>
            <!--创建分组弹出层-->
            <div class="modal inmodal" id="myGroup1" tabindex="-1" role="dialog"  aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content animated fadeIn">
                        <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <%--<i class="fa fa-picture-o modal-icon"></i>--%>
                            <h4 class="modal-title">创建分组</h4>
                        <%--<small>这里可以显示副标题。</small>--%>
                        </div>
                    <div class="modal-body">
                        <form class="form-horizontal" id="create_form" action="" method="post">
                        <fieldset>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">组名 </label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">成员 </label>
                                <div class="col-sm-8">
                                    <textarea id="mobiles" name="mobiles" class="form-control" rows="6" placeholder="可以输入多个手机号,用逗号隔开" style="resize: none"></textarea>
                                </div>
                            </div>
                        </fieldset>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <div class="col-sm-8 col-sm-offset-1">
                            <button type="button" class="btn btn-primary" id="btn_create_save" name="btn_create_save" style="width: 30%">保存</button>&numsp;&numsp;&numsp;&numsp;
                            <button type="button" class="btn btn-warning" data-dismiss="modal" id="btn_create_cancel" name="btn_create_cancel" style="width: 30%">取消</button>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
            <!--修改分组弹出层-->
            <div class="modal inmodal" id="myGroup2" tabindex="-1" role="dialog"  aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content animated fadeIn">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                            <%--<i class="fa fa-picture-o modal-icon"></i>--%>
                            <h4 class="modal-title">编辑组名</h4>
                            <%--<small>这里可以显示副标题。</small>--%>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" id="update_form" action="" method="post">
                                <fieldset>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">组名 </label>
                                        <div class="col-sm-8">
                                            <input id="g_id" name="g_id" type="hidden" class="form-control" >
                                            <input id="upName" name="upName" type="text" class="form-control" >
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <div class="col-sm-8 col-sm-offset-1">
                                <button type="button" class="btn btn-primary" id="btn_upodate_save" name="btn_upodate_save" style="width: 30%">保存</button>&numsp;&numsp;&numsp;&numsp;
                                <button type="button" class="btn btn-warning" data-dismiss="modal" id="btn_upodate_cancel" name="btn_upodate_cancel" style="width: 30%">取消</button>
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
<!-- Page-Level Scripts -->
<script>
    $(document).ready(function () {
//        $('.dataTables-example').dataTable({
//            "aLengthMenu":[25,50,100],
//            "bSort": false //排序功能
//        });

        $('#btn_create_save').click(function (){
            var name = $('#name').val();
            var mobiles = $('#mobiles').val();
            if(name == null || name == ""){
                layer.alert("组名不能为空,请输入",3,'提示信息');
                return false;
            }else if(mobiles == null || mobiles == ""){
                layer.alert("成员不能为空,请输入",3,'提示信息');
                return false;
            }else{
                $.post("/group/add.do", $("#create_form").serialize(),
                        function(data){
                            if(data.success == 0) {
//                                console.log(data);
                                $('#myGroup1').modal('hide');
                                $.layer({
                                    shade : ['',false],
                                    area : ['auto','auto'],
                                    dialog : {msg:data.msg, btns : 1, type : 4, btn : ['确定'],
                                        yes : function(){
                                            window.location.href="${ctx}/group/info.do?uid=${user.id}";
                                        }
                                    }
                                });
                            }else{
                                layer.alert(data.msg,10,'提示信息');
                            }
                        }, "json");
            }
        });

        $('#btn_upodate_save').click(function (){
            $.post("/group/edit.do", $("#update_form").serialize(),
                    function(data){
                        if(data.success == 0) {
                            $('#myGroup2').modal('hide');
                            window.location.href="${ctx}/group/info.do?uid=${user.id}";
                        }else{
                            layer.alert(data.msg,10,'提示信息');
                        }
                    }, "json");
        });
    });

    function editGroup(id,gname){
        $('#g_id').val(id);
        $('#upName').val(gname);
    }

    function delGroup(id){
        layer.confirm('您确定要删除此分组吗？', function(index){
            window.location.href="${ctx}/group/del.do?uid=${user.id}&id="+id;
            layer.close(index);
        });

    }
</script>
</body>
</html>