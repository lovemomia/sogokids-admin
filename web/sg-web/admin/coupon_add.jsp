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

    <script>
        //外部js调用
        laydate({
            elem: '#hello', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });

        //日期范围限制
        var start = {
            elem: '#startTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            min: laydate.now(), //设定最小日期为当前日期
            max: '2099-06-16 23:59:59', //最大日期
            istime: true,
            istoday: false,
            choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
            }
        };
        var end = {
            elem: '#endTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            min: laydate.now(),
            max: '2099-06-16 23:59:59',
            istime: true,
            istoday: false,
            choose: function (datas) {
                start.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };
        laydate(start);
        laydate(end);
    </script>

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
                                 </span>  </span>
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
                <li>
                    <a href="index.jsp#"><i class="fa fa-bar-chart"></i> <span class="nav-label">查询统计</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li><a href="${ctx}/query/info.do?uid=${user.id}"><i class="fa fa-pie-chart"></i> <span class="nav-label">选课查询</span> </a></li>
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
                        <li><a href="${ctx}/place/info.do?uid=${user.id}"><i class="fa fa-rebel"></i> <span class="nav-label">地址信息</span> </a></li>
                        <li><a href="${ctx}/inst/info.do?uid=${user.id}"><i class="fa fa-bank"></i> <span class="nav-label">机构信息</span> </a></li>
                        <li><a href="${ctx}/teacher/info.do?uid=${user.id}"><i class="fa fa-user-secret"></i> <span class="nav-label">师资力量</span></a></li>
                    </ul>
                </li>
                <li class="active"><a href="${ctx}/coupon/info.do?uid=${user.id}"><i class="fa fa-cc-paypal"></i> <span class="nav-label">优惠设置</span></a></li>
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
                <h2>红包列表</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="${ctx}/user/index.do?uid=${user.id}">主页</a>
                    </li>
                    <li>
                        <a href="${ctx}/coupon/info.do?uid=${user.id}">红包设置</a>
                    </li>
                    <li>
                        <strong>创建红包</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">
                <h2><a href="${ctx}/coupon/info.do?uid=${user.id}" class="btn btn-primary btn-x">返回</a></h2>
            </div>
        </div>
        <div class="row">
            <div class="ibox-content">
                <form class="form-horizontal" id="vform" action="${ctx}/coupon/add.do?uid=${user.id}" method="post">
                    <fieldset>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">红包标题</label>
                            <div class="col-sm-3">
                                <textarea id="title" name="title" type="text" class="form-control"></textarea>
                            </div>
                            <label class="col-sm-2 control-label">选择分类 </label>
                            <div class="col-sm-3">
                                <select id="type" name="type" class="form-control m-b" >
                                    <option value="1">消费满送</option>
                                </select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">红包用途 </label>
                            <div class="col-sm-3">
                                <select id="src" name="src" class="form-control m-b" >
                                    <option value="1">注册赠送</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">红包数量</label>
                            <div class="col-sm-3">
                                <input id="count" name="count" type="text" class="form-control" >
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">时间类型 </label>
                            <div class="col-sm-2">
                                <select id="timeType" name="timeType" class="form-control m-b" >
                                    <c:forEach items="${timeTypes}" var="node">
                                        <option value="${node.id}">${node.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label">时间</label>
                            <div class="col-sm-2">
                                <input id="time" name="time" type="text" class="form-control" >
                            </div>
                            <label class="col-sm-1 control-label">单位</label>
                            <div class="col-sm-2">
                                <select id="timeUnit" name="timeUnit" class="form-control m-b" >
                                    <c:forEach items="${timeUnits}" var="node">
                                        <option value="${node.id}">${node.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">消费额度 </label>
                            <div class="col-sm-3">
                                <input id="consumption" name="consumption" type="text" class="form-control" >
                            </div>
                            <label class="col-sm-2 control-label">折扣率</label>
                            <div class="col-sm-3">
                                <input id="discount" name="discount" type="text" class="form-control" >
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">有效期 </label>
                            <div class="col-sm-3">
                                <input id="startTime" name="startTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly>
                            </div>
                            <div class="col-sm-1">至</div>
                            <div class="col-sm-3">
                                <input id="endTime" name="endTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">上下线时间 </label>
                            <div class="col-sm-3">
                                <input id="onlineTime" name="onlineTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly>
                            </div>
                            <div class="col-sm-1">至</div>
                            <div class="col-sm-3">
                                <input id="offlineTime" name="offlineTime" type="text" class="form-control layer-date" placeholder="YYYY-MM-DD hh:mm:ss" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">红包描述 </label>
                            <div class="col-sm-8">
                                <textarea id="desc" name="desc" type="text" class="form-control">${model.desc}</textarea>
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
</body>
</html>