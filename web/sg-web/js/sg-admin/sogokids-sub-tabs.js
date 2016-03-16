//tabs控制js

$(document).ready(function () {

    $('#tab1').click(function (){
        $('#tab-1').show();
        $('#tab-2').hide();
        $('#tab-3').hide();
        $('#tab-4').hide();
        $('#tab-5').hide();
    });

    $('#tab2').click(function (){
        var pid = $('#sub_id').val();
        if(pid == 0){
            layer.alert('基本信息不存在,无法进行后续操作！',5,'提示信息');
            return false;
        }
        $('#tab-1').hide();
        $('#tab-2').show();
        $('#tab-3').hide();
        $('#tab-4').hide();
        $('#tab-5').hide();
    });

    $('#tab3').click(function (){
        var pid = $('#sub_id').val();
        if(pid == 0){
            layer.alert('基本信息不存在,无法进行后续操作！',5,'提示信息');
            return false;
        }
        $('#tab-1').hide();
        $('#tab-2').hide();
        $('#tab-3').show();
        $('#tab-4').hide();
        $('#tab-5').hide();
    });

    $('#tab4').click(function (){
        var pid = $('#sub_id').val();
        if(pid == 0){
            layer.alert('基本信息不存在,无法进行后续操作！',5,'提示信息');
            return false;
        }
        $('#tab-1').hide();
        $('#tab-2').hide();
        $('#tab-3').hide();
        $('#tab-4').show();
        $('#tab-5').hide();
    });

    $('#tab5').click(function (){
        var pid = $('#sub_id').val();
        if(pid == 0){
            layer.alert('基本信息不存在,无法进行后续操作！',5,'提示信息');
            return false;
        }
        $('#tab-1').hide();
        $('#tab-2').hide();
        $('#tab-3').hide();
        $('#tab-4').hide();
        $('#tab-5').show();
    });

    $('#btn_sub_next_save').click(function(){
        var sub_id = $('#sub_id').val();
        var title = $('#title').val();
        var cover = $('#cover').val();
        var intro = $('#intro').val();
        if(title == null || title == ""){
            layer.alert('请填写主标题信息！',3,'提示信息');
            return false;
        }else if(cover == null || cover == ""){
            layer.alert('请上传首页图！',3,'提示信息');
            return false;
        }else if(intro == null || intro == ""){
            layer.alert('请填写课程目标信息！',3,'提示信息');
            return false;
        }else{
            if(sub_id == 0){
                $.post("/sub/add.do", $("#sub_form").serialize(),
                    function(data){
                        if(data.success == 0){
                            $("#sub_id").attr("value",data.subId);
                            $('#tab_li_1').attr('class','');
                            $('#tab-1').hide();
                            $('#tab_li_2').attr('class','active');
                            $('#tab-2').show();
                        }else{
                            layer.alert(data.msg,10,'提示信息');
                        }
                    }, "json");
            }else{
                $.post("/sub/edit.do", $("#sub_form").serialize(),
                    function(data){
                        if(data.success == 0){
                            $('#tab_li_1').attr('class','');
                            $('#tab-1').hide();
                            $('#tab_li_2').attr('class','active');
                            $('#tab-2').show();
                        }else{
                            layer.alert(data.msg,10,'提示信息');
                        }
                    }, "json");
            }
        }
    });

    $('#btn_pic_next_save').click(function(){
        var pid = $('#sub_id').val();
        if(pid == 0){
            layer.alert('基本信息不存在,无法进行下一步！',5,'提示信息');
            return false;
        }else{
            $('#tab_li_2').attr('class','');
            $('#tab-2').hide();
            $('#tab_li_3').attr('class','active');
            $('#tab-3').show();
        }
    });

    $('#btn_notice_next_save').click(function(){
        var pid = $('#sub_id').val();
        if(pid == 0){
            layer.alert('基本信息不存在,无法进行下一步！',5,'提示信息');
            return false;
        }else{
            $('#tab_li_3').attr('class','');
            $('#tab-3').hide();
            $('#tab_li_4').attr('class','active');
            $('#tab-4').show();
        }
    });

    $('#btn_sku_next_save').click(function(){
        var pid = $('#sub_id').val();
        if(pid == 0){
            layer.alert('基本信息不存在,无法进行下一步！',5,'提示信息');
            return false;
        }else{
            $('#tab_li_4').attr('class','');
            $('#tab-4').hide();
            $('#tab_li_5').attr('class','active');
            $('#tab-5').show();
        }
    });

});