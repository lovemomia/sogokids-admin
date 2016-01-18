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

    <!-- Morris -->
    <link href="${ctx}/sg-web/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">

    <!-- Gritter -->
    <link href="${ctx}/sg-web/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">

    <link href="${ctx}/sg-web/css/animate.css" rel="stylesheet">
    <link href="${ctx}/sg-web/css/style.css?v=2.2.0" rel="stylesheet">

    <!-- Mainly scripts -->
    <script src="${ctx}/sg-web/js/jquery-2.1.1.min.js"></script>
    <script src="${ctx}/sg-web/js/jquery.cookie.js"></script>
    <script src="${ctx}/sg-web/js/bootstrap.min.js?v=3.4.0"></script>
    <script src="${ctx}/sg-web/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/sg-web/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/sg-web/js/hplus.js?v=2.2.0"></script>
    <script src="${ctx}/sg-web/js/plugins/pace/pace.min.js"></script>

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
                                <%--<span class="text-muted text-xs">超级管理员 <b class="caret"></b></span>--%>
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
                    <li class="active"><a href="${ctx}/user/index.do?uid=${user.id}"><i class="fa fa-th-large"></i> <span class="nav-label">主页</span> </a></li>
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
                            <li><a href="${ctx}/place/info.do?uid=${user.id}"><i class="fa fa-rebel"></i> <span class="nav-label">商户信息</span> </a></li>
                            <li><a href="${ctx}/inst/info.do?uid=${user.id}"><i class="fa fa-bank"></i> <span class="nav-label">机构信息</span> </a></li>
                            <%--<li><a href="${ctx}/teacher/info.do?uid=${user.id}"><i class="fa fa-user-secret"></i> <span class="nav-label">师资力量</span></a></li>--%>
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
                        <!--<form role="search" class="navbar-form-custom" method="post" action="search_results.html">-->
                        <!--<div class="form-group">-->
                        <!--<input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">-->
                        <!--</div>-->
                        <!--</form>-->

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
            <div class="row  border-bottom white-bg dashboard-header">
                <div class="col-sm-12">
                    <blockquote class="text-warning" style="font-size:14px">
                        你好！我们是松果团队
                        <br>我们分享最新鲜的亲子活动
                        <br>最权威的亲子教育咨询
                        <br>陪伴是最好的成长......
                        <br>......
                        <h4 class="text-danger">让孩子们在玩乐中探索世界吧...</h4>
                    </blockquote>

                    <hr>
                </div>
                <div class="col-sm-3">
                    <h2>松果亲子</h2>
                    <small>移动设备请扫描以下二维码进行关注：</small>
                    <br>
                    <br>
                    <img src="${ctx}/sg-web/img/qr_code.png" width="100%" style="max-width:264px;">
                    <br>
                </div>
                <div class="col-sm-5">
                    <h2>
                        在玩乐中探索世界
                    </h2>
                    <p>让您在工作之余的周末能够愉快的陪伴孩子</p>
                    <p>让您在工作之余的周末也能够进行有效的社交</p>
                    <p>让您孩子在玩了同时也能够找到玩伴</p>
                    <p>
                        <b>当前版本：</b>v1.0
                    </p>
                    <p>
                        <b>技术支持：</b><span class="label label-warning">松果技术团队</span>
                    </p>
                    <br>
                    <p>
                        <a class="btn btn-success btn-outline" href="http://www.duolaqinzi.com" target="_blank">
                            <i class="fa fa-qq"> </i> 联系我们
                        </a>
                        <a class="btn btn-white btn-bitbucket" href="http://www.duolaqinzi.com" target="_blank">
                            <i class="fa fa-home"></i> 访问公司首页
                        </a>
                    </p>
                </div>
                <div class="col-sm-4">
                    <h4>松果互动平台功能特点：</h4>
                    <ol>
                        <li>系统管理</li>
                        <li>权限管理</li>
                        <li>录单系统</li>
                        <li>商户系统</li>
                        <li>营销系统</li>
                        <li>其它诸多的功能组件</li>
                        <li>更多……</li>
                    </ol>
                </div>

            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="wrapper wrapper-content">
                        <div class="row">
                            <div class="col-lg-4">
                                <div class="ibox float-e-margins">
                                    <div class="ibox-title">
                                        <h5>技术开发文档</h5>
                                    </div>
                                    <div class="ibox-content">
                                        <p>开发文档，如下图所示：</p>
                                        <p>
                                            <img src="${ctx}/sg-web/img/hplus-docs.png" width="100%">
                                        </p>
                                        <p>开发文档位于<a href="https://tower.im/projects/cf8012122e99428b83a1f1a8cd9265e6/">Tower</a> 上。</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="ibox float-e-margins">
                                    <div class="ibox-title">
                                        <h5>后续功能开发</h5>
                                    </div>
                                    <div class="ibox-content">
                                        <p>进行版本快速迭代</p>
                                        <p>优先级排序开发：</p>
                                        <ol>
                                            <li>优先级一：影响现有功能操作的</li>
                                            <li>优先级二：新增功能经过评审快速完成</li>
                                            <li>优先级三：迭代开发的功能</li>
                                            <li>优先级四：bug修改</li>
                                        </ol>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="ibox float-e-margins">
                                    <div class="ibox-title">
                                        <h5>系统更新日志</h5>
                                    </div>
                                    <div class="ibox-content no-padding">

                                        <div class="panel-body">
                                            <div class="panel-group" id="version">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h5 class="panel-title">
                                                            <a data-toggle="collapse" data-parent="#version" href="index.html#v22">v1.0</a><code class="pull-right">2016.01.18更新</code>
                                                        </h5>
                                                    </div>
                                                    <div id="v10" class="panel-collapse collapse in">
                                                        <div class="panel-body">
                                                            <ol>
                                                                <li>后台用户登录；</li>
                                                                <li>录单系统完成；</li>
                                                                <li>订单统计查询；</li>
                                                                <li>系统管理；</li>

                                                            </ol>
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