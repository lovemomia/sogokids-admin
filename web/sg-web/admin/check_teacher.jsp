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
    <style>
        table tr{margin-top: 5px;margin-bottom: 5px;}
    </style>

</head>

<body>
<%
    String tid = request.getParameter("tid");
    String mark = request.getParameter("mark");
%>
<div id="wrapper">
        <div class="row" style="width: 60%;margin-right: 20%;margin-left: 20%;">
            <div class="ibox-content">
                    <input id="tid" name="tid" type="hidden" value="<%=tid%>">
                    <input id="mark" name="mark" type="hidden" value="<%=mark%>">
                    <div id="examine">
                        <form>
                            <table style="margin-left: 40px;margin-right: 40px;">
                                <tr>
                                    <td style="width: 90%"><font style="font-size:200%;" color="black">杨洋黄轩</font> 男 30岁</td>
                                    <td rowspan="5"><img alt="image" src="${ctx}/sg-web/img/a1.jpg" style="width: 130px;height: 150px;"></td>
                                </tr>
                                <tr>
                                    <td align="left">出生日期:1983-12-20</td>
                                </tr>
                                <tr>
                                    <td align="left">手机号码:18600900889</td>
                                </tr>
                                <tr>
                                    <td align="left">身份证号:410221090189189</td>
                                </tr>
                                <tr>
                                    <td align="left">现居地址:上海徐汇区</td>
                                </tr>
                            </table>
                        </form>

                        <p>&numsp;</p>
                        <form>
                            <table style="margin-left: 40px;margin-right: 40px;">
                                <tr><td colspan="2"><span class="badge">&numsp;&numsp;工作经历&numsp;&numsp;</span></td></tr>
                            </table>
                            <table style="width:93%;margin-left: 40px;margin-right: 40px;">
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" style="width: 80%;">南京大学</td>
                                    <td style="align-text:right">2010-2015</td>
                                </tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr><td align="left" colspan="2"><font style="font-size:50%;" color="#a9a9a9">物理教授</font></td></tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" colspan="2">
                                        手机号手机号手机号手机机号手机号手机号手机
                                    </td>
                                </tr>

                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" style="width: 80%;">南京大学</td>
                                    <td style="align-text:right">2010-2015</td>
                                </tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr><td align="left" colspan="2"><font style="font-size:50%;" color="#a9a9a9">物理教授</font></td></tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" colspan="2">
                                        手机号手机号手机号手机机号手
                                    </td>
                                </tr>

                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" style="width: 80%;">南京大学</td>
                                    <td style="align-text:right">2010-2015</td>
                                </tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr><td align="left" colspan="2"><font style="font-size:50%;" color="#a9a9a9">物理教授</font></td></tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" colspan="2">
                                        手机号手机号手机号手机机号手机号
                                    </td>
                                </tr>

                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" style="width: 80%;">南京大学</td>
                                    <td style="align-text:right">2010-2015</td>
                                </tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr><td align="left" colspan="2"><font style="font-size:50%;" color="#a9a9a9">物理教授</font></td></tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" colspan="2">
                                        手机号手机号手机号手机机号手机号手机号手机
                                    </td>
                                </tr>

                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" style="width: 80%;">南京大学</td>
                                    <td style="align-text:right">2010-2015</td>
                                </tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr><td align="left" colspan="2"><font style="font-size:50%;" color="#a9a9a9">物理教授</font></td></tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" colspan="2">
                                        手机号手机号手机号手机机号手机手机号手机号手机号手机机号手机手机号手机号手机号手机机号手机手机号手机号手机号手机机号手机手机号手机号手机号手机机号手机手机号手机号手机号手机机号手机手机号手机号手机号手机机号手机手机号手机号手机号手机机号手机
                                    </td>
                                </tr>
                            </table>
                        </form>

                        <p>&numsp;</p>
                        <form>
                            <table style="margin-left: 40px;margin-right: 40px;">
                                <tr><td colspan="2"><span class="badge">&numsp;&numsp;教育经历&numsp;&numsp;</span></td></tr>
                            </table>
                            <table style="width:93%;margin-left: 40px;margin-right: 40px;">
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" style="width: 80%;">南京大学</td>
                                    <td style="align-text:right">2010-2015</td>
                                </tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr><td align="left" colspan="2"><font style="font-size:50%;" color="#a9a9a9">教育专业 硕士</font></td></tr>

                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" style="width: 80%;">南京大学</td>
                                    <td style="align-text:right">2010-2015</td>
                                </tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr><td align="left" colspan="2"><font style="font-size:50%;" color="#a9a9a9">教育专业 硕士</font></td></tr>

                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" style="width: 80%;">南京大学</td>
                                    <td style="align-text:right">2010-2015</td>
                                </tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr><td align="left" colspan="2"><font style="font-size:50%;" color="#a9a9a9">教育专业 硕士</font></td></tr>

                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" style="width: 80%;">南京大学</td>
                                    <td style="align-text:right">2010-2015</td>
                                </tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr><td align="left" colspan="2"><font style="font-size:50%;" color="#a9a9a9">教育专业 硕士</font></td></tr>

                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr>
                                    <td align="left" style="width: 80%;">南京大学</td>
                                    <td style="align-text:right">2010-2015</td>
                                </tr>
                                <tr><td colspan="2">&numsp;</td></tr>
                                <tr><td align="left" colspan="2"><font style="font-size:50%;" color="#a9a9a9">教育专业 硕士</font></td></tr>
                            </table>
                        </form>
                        <form>

                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div>
                                    <button class="btn btn-danger" type="button" id="btn_teacher_save" name="btn_cour_save" style="width:100px;margin-left: 35%;margin-right: 80px;">简历不通过</button>
                                    <button class="btn btn-warning" type="button" id="btn_teacher_up" name="btn_cour_next_save" style="width:100px;">邀请面试</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
</div>
<!-- Page-Level Scripts -->
<script>
    $(document).ready(function () {
        $('.dataTables-example').dataTable({
            "aLengthMenu":[25,50,100],
            "bSort": false //排序功能
        });
        var tid = $('#tid').val();
        var mark = $('#mark').val();

        alert(tid + "=--=" + mark);
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


    });
</script>
</body>
</html>