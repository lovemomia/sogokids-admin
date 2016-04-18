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

    <!-- layerDate plugin javascript -->
    <script src="${ctx}/sg-web/js/plugins/layer/laydate/laydate.js"></script>


    <!-- layer javascript -->
    <script src="${ctx}/sg-web/js/plugins/layer/layer.min.js"></script>

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
                <h2>查询条件</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="${ctx}/user/index.do?uid=${user.id}">主页</a>
                    </li>
                    <li>
                        <a href="${ctx}/query/info.do?uid=${user.id}">订单查询</a>
                    </li>
                    <li>
                        <strong>查询信息</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="row">
            <div class="ibox-content">
                <form class="form-horizontal" id="vform" action="${ctx}/query/queryOrder.do?uid=${user.id}" method="post">
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
                            <label class="col-sm-1 control-label">订单状态</label>
                            <div class="col-sm-2">
                                <select id="status" name="status" class="form-control m-b" >
                                    <c:forEach items="${status}" var="node">
                                        <c:choose>
                                            <c:when test="${node.id == new_status}">
                                                <option value="${node.id}" selected>${node.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${node.id}"  >${node.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-1 col-sm-offset-1">
                                <button class="btn btn-primary" type="submit">查询</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">手机号</label>
                            <div class="col-sm-2">
                                <input id="mobile" name="mobile" type="text" value="${mobile}" class="form-control">
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
                            <%--<th width="5%">订单号</th>--%>
                            <th width="10%">用户昵称</th>
                            <th width="10%">用户电话</th>
                            <th width="10%">订单金额</th>
                            <th width="15%">订单状态</th>
                            <th width="10%">交易方式</th>
                            <th width="20%">交易时间</th>
                            <th width="20%">状态</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orders}" var="entity">
                                    <%--<td><c:out value="${entity.id}"></c:out></td>--%>
                                    <td><c:out value="${entity.customer.nickName}"></c:out></td>
                                    <td><c:out value="${entity.customer.mobile}"></c:out></td>
                                    <td><c:out value="${entity.priceSum}"></c:out></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${entity.status == 1 || entity.status == 2}">
                                                未付款
                                            </c:when>
                                            <c:when test="${entity.status == 3 || entity.status == 4}">
                                                已付款
                                            </c:when>
                                            <c:when test="${entity.status == 5}">
                                                退款中
                                            </c:when>
                                            <c:otherwise>
                                                已退款
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${entity.payment.payType == 21}">
                                                微信APP支付
                                            </c:when>
                                            <c:when test="${entity.payment.payType == 22}">
                                                微信公众号支付
                                            </c:when>
                                            <c:when test="${entity.payment.payType == 1}">
                                                支付宝支付
                                            </c:when>
                                            <c:otherwise>
                                                未支付
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><c:out value="${entity.payment.finishTime}"></c:out></td>
                                    <td>
                                        <a href="${ctx}/query/orderDetail.do?uid=${user.id}&id=${entity.id}&startTime=${startTime}&endTime=${endTime}&mobile=${mobile}&new_status=${new_status}" class="btn btn-white btn-sm"><i class="fa fa-newspaper-o"></i> 订单详情 </a>
                                        <c:if test="${entity.status == 3 && entity.tk_mark == 0}">
                                            <%--<a href="${ctx}/query/refund.do?uid=${user.id}&id=${entity.id}&startTime=${startTime}&endTime=${endTime}&mobile=${mobile}&new_status=${new_status}" class="btn btn-primary btn-sm"><i class="fa fa-history"></i> 退款 </a>--%>
                                            <a href="javascript:void(0)" onclick="refund(${entity.id})" class="btn btn-primary btn-sm"><i class="fa fa-history"></i> 退款 </a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
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
<!-- Page-Level Scripts -->
<script>
    $(document).ready(function () {
        $('.dataTables-example').dataTable({
            "bSort": false //排序功能
        });
    });

    function refund(id){
        layer.confirm('您确定要进行退款操作吗？', function(index){
            window.location.href="${ctx}/query/refund.do?uid=${user.id}&id="+id+"&startTime=${startTime}&endTime=${endTime}&mobile=${mobile}&new_status=${new_status}";
            layer.close(index);
        });

    }

//function queryCourseInfo(order_id,order_package_id){
//    var url = "/query/order_course.do";
//    $.post(url,{order_id:order_id,order_package_id:order_package_id}, function(data){
//        var divshow = $("#data_div");
//        divshow.text("");
//        divshow.append(data.orderCourses);
//
//    }, "json");
//}

//function refund(order_id){
//    layer.confirm('您确定要申请退款吗？', function(index){
//        var url = "/query/refund.do";
//        $.post(url,{order_id:order_id}, function(data){
//            layer.alert(data.msg,10,'提示信息');
//        }, "json");
//        layer.close(index);
//    });
//}
</script>
</body>
</html>