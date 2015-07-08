<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>多啦亲子互动平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
    <meta name="author" content="Muhammad Usman">

    <!-- The styles -->
    <link  href="${ctx}/admin/css/bootstrap-cerulean.css" rel="stylesheet">
    <style type="text/css">
        body {
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }
    </style>
    <link href="${ctx}/admin/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="${ctx}/admin/css/charisma-app.css" rel="stylesheet">
    <link href="${ctx}/admin/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
    <link href='${ctx}/admin/css/fullcalendar.css' rel='stylesheet'>
    <link href='${ctx}/admin/css/fullcalendar.print.css' rel='stylesheet'  media='print'>
    <link href='${ctx}/admin/css/chosen.css' rel='stylesheet'>
    <link href='${ctx}/admin/css/uniform.default.css' rel='stylesheet'>
    <link href='${ctx}/admin/css/colorbox.css' rel='stylesheet'>
    <link href='${ctx}/admin/css/jquery.cleditor.css' rel='stylesheet'>
    <link href='${ctx}/admin/css/jquery.noty.css' rel='stylesheet'>
    <link href='${ctx}/admin/css/noty_theme_default.css' rel='stylesheet'>
    <link href='${ctx}/admin/css/elfinder.min.css' rel='stylesheet'>
    <link href='${ctx}/admin/css/elfinder.theme.css' rel='stylesheet'>
    <link href='${ctx}/admin/css/jquery.iphone.toggle.css' rel='stylesheet'>
    <link href='${ctx}/admin/css/opa-icons.css' rel='stylesheet'>
    <link href='${ctx}/admin/css/uploadify.css' rel='stylesheet'>

    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- The fav icon -->
    <link rel="shortcut icon" href="${ctx}/admin/img/logo200.png">

</head>

<body>
<!-- topbar starts -->
<div class="navbar">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="brand" href="${ctx}/user/index.do?uid=${user.id}"> <img alt="Charisma Logo" src="${ctx}/admin/img/logo200.png" /> <span>哆啦亲子</span></a>

            <!-- user dropdown starts -->
            <div class="btn-group pull-right" >
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-user"></i><span class="hidden-phone">  ${user.username}</span>
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <!-- <li><a href="#">Profile</a></li> -->
                    <li class="divider"></li>
                    <li><a href="${ctx}/user/login.do">注销</a></li>
                </ul>
            </div>
            <!-- user dropdown ends -->
        </div>
    </div>
</div>
<!-- topbar ends -->
<div class="container-fluid">
    <div class="row-fluid">

        <div id="content" class="span10">
            <!-- content starts -->
            <div class="box span12">
                <div class="row-fluid sortable">
                    <div class="box-header well" data-original-title>
                        <h2><i class="icon-user"></i> 活动内容</h2>
                        <div class="box-icon">
                            <a href="${ctx}/product/info.do?uid=${user.id}&pageNo=${pageNo}" class="btn btn-back btn-round"><i class="icon-remove"></i></a>
                        </div>
                    </div>
                    <div class="box-content">
                        <form class="form-horizontal" id="vform" action="${ctx}/product/addcontent.do?uid=${user.id}&pid=${model.id}&pageNo=${pageNo}" enctype="multipart/form-data" method="post">
                            <input id='contentsize' name='contentsize' type='hidden' value='1'>
                            <fieldset id="contentmain">
                                <div style="border:1px solid #000000" padding＝0>

                                    <div class="control-group">
                                        <div class="controls">
                                            标题 :<input id="title1" name="title1" value=""><br>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="controls">
                                            样式:<select id="style1" name="style1">
                                                    <option value="ol">ol</option>
                                                    <option value="ul">ul</option>
                                                    <option value="none" selected>none</option>
                                                </select>
                                        </div>
                                    </div>
                                    <div class="control-group" style="border:1px solid #0000ff">
                                        <div class="controls" style="border:1px solid #ff0000">
                                            控件:<select id="param11" name="param11">
                                                    <option value="0" selected>内容</option>
                                                    <option value="1">标签</option>
                                                    <option value="2">链接</option>
                                                    <option value="3">图片</option>
                                                </select><br>
                                            标签:<input id="label11" name="label11" value=""><br>
                                            内容:<textarea id="text11" name="text11" rows="3" cols="5" ></textarea><br>
                                            链接:<input id="link11" name="link11" value=""><br>
                                            图片:<input id="fileurl11" name="fileurl11" type="file" size="30" >
                                            <input id="images11" name="images11" type="hidden" value="fileurl11" >
                                            <input id='hidparam11' name='hidparam11' type='hidden' value='未上传图片' ><br>
                                        </div><br>
                                        <div class="controls" style="border:1px solid #ff0000">
                                            控件:<select id="param12" name="param12">
                                                    <option value="0" selected>内容</option>
                                                    <option value="1">标签</option>
                                                    <option value="2">链接</option>
                                                    <option value="3">图片</option>
                                                </select><br>
                                            标签:<input id="label12" name="label12" value=""><br>
                                            内容:<textarea id="text12" name="text12" rows="3" cols="5" ></textarea><br>
                                            链接:<input id="link12" name="link12" value=""><br>
                                            图片:<input id="fileurl12" name="fileurl12" type="file" size="30" >
                                            <input id="images12" name="images12" type="hidden" value="fileurl12" >
                                            <input id='hidparam12' name='hidparam12' type='hidden' value='未上传图片' ><br>
                                        </div><br>
                                        <div class="controls" style="border:1px solid #ff0000">
                                            控件:<select id="param13" name="param13">
                                                    <option value="0" selected>内容</option>
                                                    <option value="1">标签</option>
                                                    <option value="2">链接</option>
                                                    <option value="3">图片</option>
                                                </select><br>
                                            标签:<input id="label13" name="label13" value=""><br>
                                            内容:<textarea id="text13" name="text13" rows="3" cols="5" ></textarea><br>
                                            链接:<input id="link13" name="link13" value=""><br>
                                            图片:<input id="fileurl13" name="fileurl13" type="file" size="30" >
                                            <input id="images13" name="images13" type="hidden" value="fileurl13" >
                                            <input id='hidparam13' name='hidparam13' type='hidden' value='未上传图片' ><br>
                                        </div><br>
                                        <div class="controls" style="border:1px solid #ff0000">
                                            控件:<select id="param14" name="param14">
                                                    <option value="0" selected>内容</option>
                                                    <option value="1">标签</option>
                                                    <option value="2">链接</option>
                                                    <option value="3">图片</option>
                                                </select><br>
                                            标签:<input id="label14" name="label14" value=""><br>
                                            内容:<textarea id="text14" name="text14" rows="3" cols="5" ></textarea><br>
                                            链接:<input id="link14" name="link14" value=""><br>
                                            图片:<input id="fileurl14" name="fileurl14" type="file" size="30">
                                            <input id="images14" name="images14" type="hidden" value="fileurl14" >
                                            <input id='hidparam14' name='hidparam14' type='hidden' value='未上传图片' ><br>
                                        </div><br>
                                        <div class="controls" style="border:1px solid #ff0000">
                                            控件:<select id="param15" name="param15">
                                                    <option value="0" selected>内容</option>
                                                    <option value="1">标签</option>
                                                    <option value="2">链接</option>
                                                    <option value="3">图片</option>
                                                </select><br>
                                            标签:<input id="label15" name="label15" value=""><br>
                                            内容:<textarea id="text15" name="text15" rows="3" cols="5" ></textarea><br>
                                            链接:<input id="link15" name="link15" value=""><br>
                                            图片:<input id="fileurl15" name="fileurl15" type="file" size="30" >
                                            <input id="images15" name="images15" type="hidden" value="fileurl15" >
                                            <input id='hidparam15' name='hidparam15' type='hidden' value='未上传图片' ><br>
                                        </div><br>
                                        <div class="controls" style="border:1px solid #ff0000">
                                            控件:<select id="param16" name="param16">
                                            <option value="0" selected>内容</option>
                                            <option value="1">标签</option>
                                            <option value="2">链接</option>
                                            <option value="3">图片</option>
                                        </select><br>
                                            标签:<input id="label16" name="label16" value=""><br>
                                            内容:<textarea id="text16" name="text16" rows="3" cols="5" ></textarea><br>
                                            链接:<input id="link16" name="link16" value=""><br>
                                            图片:<input id="fileurl16" name="fileurl16" type="file" size="30" >
                                            <input id="images16" name="images16" type="hidden" value="fileurl16" >
                                            <input id='hidparam16' name='hidparam16' type='hidden' value='未上传图片' ><br>
                                        </div><br>
                                        <div class="controls" style="border:1px solid #ff0000">
                                            控件:<select id="param17" name="param17">
                                            <option value="0" selected>内容</option>
                                            <option value="1">标签</option>
                                            <option value="2">链接</option>
                                            <option value="3">图片</option>
                                        </select><br>
                                            标签:<input id="label17" name="label17" value=""><br>
                                            内容:<textarea id="text17" name="text17" rows="3" cols="5" ></textarea><br>
                                            链接:<input id="link17" name="link17" value=""><br>
                                            图片:<input id="fileurl17" name="fileurl17" type="file" size="30" >
                                            <input id="images17" name="images17" type="hidden" value="fileurl17" >
                                            <input id='hidparam17' name='hidparam17' type='hidden' value='未上传图片' ><br>
                                        </div><br>
                                        <div class="controls" style="border:1px solid #ff0000">
                                            控件:<select id="param18" name="param18">
                                            <option value="0" selected>内容</option>
                                            <option value="1">标签</option>
                                            <option value="2">链接</option>
                                            <option value="3">图片</option>
                                        </select><br>
                                            标签:<input id="label18" name="label18" value=""><br>
                                            内容:<textarea id="text18" name="text18" rows="3" cols="5" ></textarea><br>
                                            链接:<input id="link18" name="link18" value=""><br>
                                            图片:<input id="fileurl18" name="fileurl18" type="file" size="30" >
                                            <input id="images18" name="images18" type="hidden" value="fileurl18" >
                                            <input id='hidparam18' name='hidparam18' type='hidden' value='未上传图片' ><br>
                                        </div><br>
                                        <div class="controls" style="border:1px solid #ff0000">
                                            控件:<select id="param19" name="param19">
                                            <option value="0" selected>内容</option>
                                            <option value="1">标签</option>
                                            <option value="2">链接</option>
                                            <option value="3">图片</option>
                                        </select><br>
                                            标签:<input id="label19" name="label19" value=""><br>
                                            内容:<textarea id="text19" name="text19" rows="3" cols="5" ></textarea><br>
                                            链接:<input id="link19" name="link19" value=""><br>
                                            图片:<input id="fileurl19" name="fileurl19" type="file" size="30" >
                                            <input id="images19" name="images19" type="hidden" value="fileurl19" >
                                            <input id='hidparam19' name='hidparam19' type='hidden' value='未上传图片' ><br>
                                        </div><br>
                                    </div>
                                </div>
                            </fieldset>
                            <div class="form-actions">
                                <button type="submit" class="btn btn-primary" id="save" name="save" >确   定</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            </div>
            <!-- content ends -->
        </div><!--/#content.span10-->
    </div><!--/fluid-row-->

    <footer>
        <p class="pull-left">&copy; <a href="#" target="_blank">开发时间</a> @2015－06-18 </p>
        <p class="pull-right"><a href="#">多啦亲子互动平台</a></p>
    </footer>

</div><!--/.fluid-container-->

<!-- external javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<!-- jQuery -->
<script src="${ctx}/admin/js/jquery-1.7.2.min.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/admin/js/jquery-ui-1.8.21.custom.min.js"></script>
<!-- transition / effect library -->
<script src="${ctx}/admin/js/bootstrap-transition.js"></script>
<!-- alert enhancer library -->
<script src="${ctx}/admin/js/bootstrap-alert.js"></script>
<!-- modal / dialog library -->
<script src="${ctx}/admin/js/bootstrap-modal.js"></script>
<!-- custom dropdown library -->
<script src="${ctx}/admin/js/bootstrap-dropdown.js"></script>
<!-- scrolspy library -->
<script src="${ctx}/admin/js/bootstrap-scrollspy.js"></script>
<!-- library for creating tabs -->
<script src="${ctx}/admin/js/bootstrap-tab.js"></script>
<!-- library for advanced tooltip -->
<script src="${ctx}/admin/js/bootstrap-tooltip.js"></script>
<!-- popover effect library -->
<script src="${ctx}/admin/js/bootstrap-popover.js"></script>
<!-- button enhancer library -->
<script src="${ctx}/admin/js/bootstrap-button.js"></script>
<!-- accordion library (optional, not used in demo) -->
<script src="${ctx}/admin/js/bootstrap-collapse.js"></script>
<!-- carousel slideshow library (optional, not used in demo) -->
<script src="${ctx}/admin/js/bootstrap-carousel.js"></script>
<!-- autocomplete library -->
<script src="${ctx}/admin/js/bootstrap-typeahead.js"></script>
<!-- tour library -->
<script src="${ctx}/admin/js/bootstrap-tour.js"></script>
<!-- library for cookie management -->
<script src="${ctx}/admin/js/jquery.cookie.js"></script>
<!-- calander plugin -->
<script src='${ctx}/admin/js/fullcalendar.min.js'></script>
<!-- data table plugin -->
<script src='${ctx}/admin/js/jquery.dataTables.min.js'></script>

<!-- chart libraries start -->
<script src="${ctx}/admin/js/excanvas.js"></script>
<script src="${ctx}/admin/js/jquery.flot.min.js"></script>
<script src="${ctx}/admin/js/jquery.flot.pie.min.js"></script>
<script src="${ctx}/admin/js/jquery.flot.stack.js"></script>
<script src="${ctx}/admin/js/jquery.flot.resize.min.js"></script>
<!-- chart libraries end -->

<!-- select or dropdown enhancer -->
<script src="${ctx}/admin/js/jquery.chosen.min.js"></script>
<!-- checkbox, radio, and file input styler -->
<script src="${ctx}/admin/js/jquery.uniform.min.js"></script>
<!-- plugin for gallery image view -->
<script src="${ctx}/admin/js/jquery.colorbox.min.js"></script>
<!-- rich text editor library -->
<script src="${ctx}/admin/js/jquery.cleditor.min.js"></script>
<!-- notification plugin -->
<script src="${ctx}/admin/js/jquery.noty.js"></script>
<!-- file manager library -->
<script src="${ctx}/admin/js/jquery.elfinder.min.js"></script>
<!-- star rating plugin -->
<script src="${ctx}/admin/js/jquery.raty.min.js"></script>
<!-- for iOS style toggle switch -->
<script src="${ctx}/admin/js/jquery.iphone.toggle.js"></script>
<!-- autogrowing textarea plugin -->
<script src="${ctx}/admin/js/jquery.autogrow-textarea.js"></script>
<!-- multiple file upload plugin -->
<script src="${ctx}/admin/js/jquery.uploadify-3.1.min.js"></script>
<!-- history.js for cross-browser state change on ajax -->
<script src="${ctx}/admin/js/jquery.history.js"></script>
<!-- application script for Charisma demo -->
<script src="${ctx}/admin/js/charisma.js"></script>
<script src="${ctx}/admin/js/ajaxfileupload.js"></script>
<script language="JavaScript">

    $(function() {

//        var i = 2;
//        $('#addcontent').click(function() {
//            if (i < 11) {
//                  var  textname = "hdts"+i;
//                $('#contentmain').append('<div class="controls">内容:<textarea id="'+textname+'" name="'+textname+'" rows="3" cols="5"></textarea></div><br>');
//                i++;
//            } else {
//                alert("对不起,最多只能添加10个");
//            }
//        });
    });

</script>

</body>
</html>