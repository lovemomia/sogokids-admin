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

    <style>
        table tr{margin-top: 5px;margin-bottom: 5px;}
    </style>

</head>

<body>
<%--<%--%>
    <%--String tid = request.getParameter("tid");--%>
    <%--String mark = request.getParameter("mark");--%>
<%--%>--%>
<div id="wrapper">
        <div class="row" style="width: 60%;margin-right: 20%;margin-left: 20%;">
            <div class="ibox-content">
                <input id="tid" name="tid" type="hidden" value="${teacher.id}">
                <%--<input id="mark" name="mark" type="hidden" value="<%=mark%>">--%>
                <div id="examine">
                    <form>
                        <table style='margin-left: 40px;margin-right: 40px;'>
                            <tr>
                                <td style='width: 90%'><font style='font-size:200%;' color='black'>${teacher.name}</font> ${teacher.gender} ${teacher.age}岁</td>
                                <td rowspan='5'><img alt='image' src='${filepath}${teacher.pic}' style='width: 130px;height: 150px;'></td>
                            </tr>
                            <tr>
                                <td align='left'>出生日期:${teacher.birthday}</td>
                            </tr>
                            <tr>
                                <td align='left'>手机号码:${teacher.mobile}</td>
                            </tr>
                            <tr>
                                <td align='left'>身份证号:${teacher.idNo}</td>
                            </tr>
                            <tr>
                                <td align='left'>现居地址:${teacher.address}</td>
                            </tr>
                        </table>
                    </form>
                    <p>&numsp;</p>
                    <form>
                        <table style='margin-left: 40px;margin-right: 40px;'>
                            <tr><td colspan='2'><span class='badge'>&numsp;&numsp;工作经历&numsp;&numsp;</span></td></tr>
                        </table>
                        <table style='width:93%;margin-left: 40px;margin-right: 40px;'>
                            <c:forEach items="${teacherExperiences}" var="experience">
                                <tr><td colspan='2'>&numsp;</td></tr>
                                <tr>
                                    <td align='left' style='width: 80%;'>${experience.school}</td>
                                    <td style='align-text:right'>${experience.time}</td>
                                </tr>
                                <%--<tr><td colspan='2'>&numsp;</td></tr>--%>
                                <tr><td align='left' colspan='2'><font style='font-size:50%;' color='#a9a9a9'>${experience.post}</font></td></tr>
                                <tr><td colspan='2'>&numsp;</td></tr>
                                <tr><td align='left' colspan='2'>${experience.content}</td></tr>
                            </c:forEach>
                        </table>
                    </form>
                    <p>&numsp;</p>
                    <form>
                        <table style='margin-left: 40px;margin-right: 40px;'>
                            <tr><td colspan='2'><span class='badge'>&numsp;&numsp;教育经历&numsp;&numsp;</span></td></tr>
                        </table>
                        <table style='width:93%;margin-left: 40px;margin-right: 40px;'>
                            <c:forEach items="${teacherEducations}" var="education">
                                <tr><td colspan='2'>&numsp;</td></tr>
                                <tr>
                                    <td align='left' style='width: 80%;'>${education.school}</td>
                                    <td style='align-text:right'>${education.time}</td>
                                </tr>
                                <tr><td colspan='2'>&numsp;</td></tr>
                                <tr><td align='left' colspan='2'><font style='font-size:50%;' color='#a9a9a9'>${education.major} ${education.level}</font></td></tr>
                                <tr><td colspan='2'>&numsp;</td></tr>
                            </c:forEach>
                        </table>
                    </form>
                </div>
                <div>
                    <form id="sh_form" action="${ctx}/teacher/examineVerify.do?uid=${user.id}&tid=${teacher.id}" method="post">
                        <div class="hr-line-dashed"></div>
                        <table style='margin-left: 40px;margin-right: 40px;'>
                            <tr><td><h3>填写个人摘要</h3></td></tr>
                            <tr>
                                <td style="width: 100%"><textarea id="experience" name="experience" type="text" rows="5" cols="100" class="form-control" style="resize:none;">${teacher.experience}</textarea></td>
                            </tr>
                            <tr><td>&numsp;</td></tr>
                            <tr><td align="center"><button class="btn btn-primary" type="button" id="btn_sh_up" name="btn_sh_up" style="width:20%;">提交</button></td></tr>
                        </table>
                    </form>
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
//        var tid = $('#tid').val();
//        var mark = $('#mark').val();

//        alert(tid + "=--=" + mark);

//        $.post("/teacher/examineVerify.do",{tid:tid,mark:mark},
//                function(data){
////                    console.log(data.detailJson);
//                    var dataStr = data.teacherHtml;
//                    var divshow = $("#examine");
//                    divshow.text("");
//                    divshow.append(dataStr);
//                }, "json");

        $('#btn_sh_up').click(function (){
            var tid = $('#tid').val();
            var experience = $('#experience').val();
            $.post("/teacher/examineVerify.do",{tid:tid,experience:experience},
                    function(data){
                        if (data.success == 0) {
                            $.layer({
                                shade : ['',false],
                                area : ['auto','auto'],
                                dialog : {msg:data.msg, btns : 1, type : 4, btn : ['确定'],
                                    yes : function(){
                                        window.close();
                                    }
                                }
                            });
                        }else{
                            layer.alert(data.msg, 10, '错误信息');
                        }
                    }, "json");
        });

    });
</script>
</body>
</html>