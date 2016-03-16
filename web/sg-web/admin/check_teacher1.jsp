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
                            <%--<tr><td colspan='2'>&numsp;</td></tr>--%>
                            <tr><td align='left' colspan='2'><font style='font-size:50%;' color='#a9a9a9'>${education.major} ${education.level}</font></td></tr>
                            <tr><td colspan='2'>&numsp;</td></tr>
                        </c:forEach>
                    </table>
                </form>
                <div id="ms_div">
                    <form>
                        <div class='hr-line-dashed'></div>
                        <table style='margin-left: 40px;margin-right: 40px;'>
                            <tr><td colspan='2'>面试时间:${interview.interviewDate}</td></tr>
                            <tr><td colspan='2'>&numsp;</td></tr>
                            <tr><td colspan='2'>面试地点:${interview.address}</td></tr>
                        </table>
                    </form>
                </div>
                <div id="ex_div">
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
            <div id="sh_div">
                <form>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div>
                            <button class="btn btn-danger" data-toggle="modal" data-target="#myTeacher2" type="button" id="btn_up_1" name="btn_up_1" style="width:100px;margin-left: 35%;margin-right: 80px;">面试不通过</button>
                            <button class="btn btn-primary" type="button" id="btn_up_2" name="btn_up_2" style="width:100px;">面试通过</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!--面试通过弹出层-->
    <%--<div class="modal inmodal" id="myTeacher1" tabindex="-1" role="dialog"  aria-hidden="true">--%>
        <%--<div class="modal-dialog">--%>
            <%--<div class="modal-content animated fadeIn">--%>
                <%--<div class="modal-header">--%>
                    <%--<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>--%>
                    <%--&lt;%&ndash;<i class="fa fa-picture-o modal-icon"></i>&ndash;%&gt;--%>
                    <%--<h4 class="modal-title">填写个人摘要</h4>--%>
                    <%--&lt;%&ndash;<small>这里可以显示副标题。</small>&ndash;%&gt;--%>
                <%--</div>--%>
                <%--<div class="modal-body">--%>
                    <%--<form class="form-horizontal" id="sh_form" action="" method="post">--%>
                        <%--<fieldset>--%>
                            <%--<div class="form-group">--%>
                                <%--&lt;%&ndash;<label class="col-sm-2 control-label"> </label>&ndash;%&gt;--%>
                                <%--<div class="col-sm-12">--%>
                                    <%--<textarea id="experience" name="experience" type="text" rows="5" cols="100" class="form-control" style="resize:none;"></textarea>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</fieldset>--%>
                    <%--</form>--%>
                <%--</div>--%>
                <%--<div class="modal-footer">--%>
                    <%--<div class="col-sm-8 col-sm-offset-1">--%>
                        <%--<button type="button" class="btn btn-primary" id="btn_sh_save" name="btn_up_save" style="width: 30%;margin-left: 35%;margin-right: 80px;">确定</button>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <!--面试不通过弹出层-->
    <div class="modal inmodal" id="myTeacher2" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content animated fadeIn">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <%--<i class="fa fa-picture-o modal-icon"></i>--%>
                    <h4 class="modal-title">面试不通过</h4>
                    <%--<small>这里可以显示副标题。</small>--%>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="up_form" action="" method="post">
                        <fieldset>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">常用理由 </label>
                                <div class="col-sm-9">
                                    <div id="teacher_Final">
                                        <select id="c_reason" name="c_reason" class="form-control" >
                                            <c:forEach items="${ms_finals}" var="node">
                                                <option value="${node.id}">${node.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div id="j_chang">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">具体理由 </label>
                                    <div class="col-sm-9">
                                        <textarea id="j_reason" name="j_reason" class="form-control" rows="3" style="resize: none"></textarea>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <div class="modal-footer">
                    <div class="col-sm-8 col-sm-offset-1">
                        <button type="button" class="btn btn-primary" id="btn_up_save" name="btn_up_save" style="width: 30%;margin-left: 35%;margin-right: 80px;">发送通知</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Page-Level Scripts -->
<script>
    $(document).ready(function () {

        $('#j_chang').hide();
        $('#ex_div').hide();

        $('#c_reason').change(function (){
            var value = $('#c_reason').val();
            if(value == 3){
                $('#j_chang').show();
            }else{
                $('#j_chang').hide();
            }
        });


        $('.dataTables-example').dataTable({
            "aLengthMenu":[25,50,100],
            "bSort": false //排序功能
        });
        var tid = $('#tid').val();

        $('#btn_up_save').click(function (){
            var c_reason = $('#c_reason').val();
            var j_reason = $('#j_reason').val();
            $.post("/teacher/status.do",{tid:tid,mark:2,c_reason:c_reason,j_reason:j_reason},
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

        $('#btn_up_2').click(function (){
//            layer.confirm('此讲师审核通过,是否执行？', function(index){
//            var experience = $('#experience').val();
                $.post("/teacher/status.do",{tid:tid,mark:1},
                        function(data){
                            if (data.success == 0) {
                                $('#ms_div').hide();
                                $('#sh_div').hide();
                                $('#ex_div').show();
                            }else{
                                layer.alert(data.msg, 10, '错误信息');
                            }
                        }, "json");
//                layer.close(index);
//            });
        });

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