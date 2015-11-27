//自助录单js文件

$(document).ready(function () {

    $('#tab1').click(function (){
        $('#tab-1').show();
        $('#tab-2').hide();
        $('#tab-3').hide();
        $('#tab-4').hide();
    });

    $('#tab2').click(function (){
        var pid = $('#pid').val();
        if(pid == 0){
            layer.alert('基本信息不存在,无法进行后续操作！',5,'提示信息');
            return false;
        }
        $('#tab-1').hide();
        $('#tab-2').show();
        $('#tab-3').hide();
        $('#tab-4').hide();
    });

    $('#tab3').click(function (){
        var pid = $('#pid').val();
        if(pid == 0){
            layer.alert('基本信息不存在,无法进行后续操作！',5,'提示信息');
            return false;
        }
        $('#tab-1').hide();
        $('#tab-2').hide();
        $('#tab-3').show();
        $('#tab-4').hide();
    });

    $('#tab4').click(function (){
        var pid = $('#pid').val();
        if(pid == 0){
            layer.alert('基本信息不存在,无法进行后续操作！',5,'提示信息');
            return false;
        }
        $('#tab-1').hide();
        $('#tab-2').hide();
        $('#tab-3').hide();
        $('#tab-4').show();
    });

    $('#btn_product_next').click(function(){
        var pid = $('#pid').val();
        if(pid == 0){
            layer.alert('基本信息不存在,无法进行下一步！',5,'提示信息');
            return false;
        }else{
            $('#tab_li_1').attr('class','');
            $('#tab-1').hide();
            $('#tab_li_2').attr('class','active');
            $('#tab-2').show();
        }
    });

    $('#btn_img_next').click(function(){
        var pid = $('#pid').val();
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

    $('#btn_content_next').click(function(){
        var pid = $('#pid').val();
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

});