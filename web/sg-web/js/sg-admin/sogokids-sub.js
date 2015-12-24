//课程体系js文件

$(document).ready(function () {

    /**
     * 添加课程包图片
     */
    $("#btn_lb_save").click(function() {
        var sub_id = $('#sub_id').val();
        var imgfile = $('#file_img').val();
        if(sub_id == 0){
            layer.alert('请先保存基本信息！',5,'提示信息');
        }else if (imgfile == "" || imgfile == null) {
            layer.alert('没有选择上传的图片！',5,'提示信息');
        } else {
            $.ajaxFileUpload({
                url: '/sub/addImg.do?subId=' + sub_id,
                secureuri: false,
                fileElementId: 'file_img',
                dataType: "text",
                success: function (data, status) {
                    console.log(data);
                    var imgstr = data;
                    var divshow = $("#img_div");
                    divshow.text("");
                    divshow.append(imgstr);
                },
                error: function (data, status, error) {
                    layer.alert(error,10,'提示信息');
                }
            });
        }
        return false;
    });

    /**
     * 添加课程包的基本信息
     */
    $('#btn_sub_save').click(function(){
        //var re = /^[-]{0,1}[0-9]{1,}$/;
        var sub_id = $('#sub_id').val();
        var title = $('#title').val();
        var cover = $('#cover').val();
        var intro = $('#intro').val();
        if(title == null || title == ""){
            layer.alert('请填写标题信息！',3,'提示信息');
            return false;
        }else if(cover == null || cover == ""){
            layer.alert('请上传封面图！',3,'提示信息');
            return false;
        }else if(intro == null || intro == ""){
            layer.alert('请填写课程目标信息！',3,'提示信息');
            return false;
        }else{
            if(sub_id == 0){
                $.post("/sub/add.do", $("#sub_form").serialize(),
                    function(data){
                        //console.log(data);
                        if(data.success == 0){
                            $("#sub_id").attr("value",data.subId);
                        }
                        layer.alert(data.msg,10,'提示信息');
                    }, "json");
            }else{
                $.post("/sub/edit.do", $("#sub_form").serialize(),
                    function(data){
                        layer.alert(data.msg,10,'提示信息');
                    }, "json");
            }
        }
    });

    /**
     * 添加或修改客户须知信息
     */
    $('#btn_notice_save').click(function (){
        var subId = $('#sub_id').val();
        var n_title = $('#n_title').val();
        var n_content = $('#n_content').val();
        if(subId == 0){
            layer.alert('请先保存基本信息！',5,'提示信息');
            return false;
        }else if(n_title == null || n_title == ""){
            layer.alert('请填写客户须知标题信息！',3,'提示信息');
            return false;
        }else if(n_content == null || n_content == ""){
            layer.alert('请填写客户须知内容信息！',3,'提示信息');
            return false;
        }else{
            $.post("/sub/addNotice.do?subId="+subId, $("#notice_form").serialize(),
                function(data){
                    if(data.success == 0) {
                        clareNotice();
                        var divshow = $("#ncontent_div");
                        divshow.text("");
                        divshow.append(data.notices);
                    }
                    layer.alert(data.msg,10,'提示信息');
                }, "json");
        }
    });

    $('#btn_sku_save').click(function (){
        var strP=/^\d+(\.\d+)?$/;
        var re = /^[-]{0,1}[0-9]{1,}$/;
        var subId = $('#sub_id').val();
        var adult = $('#adult').val();
        var child = $('#child').val();
        var price = $('#price').val();
        var courseCount = $('#courseCount').val();
        var time = $('#time').val();
        var desc = $('#desc').val();
        if(subId == 0){
            layer.alert('请先保存基本信息！',5,'提示信息');
            return false;
        }else if(adult == null || adult == "" || !re.test(adult)){
            layer.alert('没有填写大人数量信息或格式不正确(只能填写整数值)！',3,'提示信息');
            return false;
        }else if(child == null || child == "" || !re.test(child)){
            layer.alert('没有填写儿童数量信息或格式不正确(只能填写整数值)！',3,'提示信息');
            return false;
        }else if(price == null || price == "" || !strP.test(price)){
            layer.alert('没有填写售卖价格信息或格式不正确(只能填写整数值或小数)！',3,'提示信息');
            return false;
        }else if(courseCount == null || courseCount == "" || !re.test(courseCount)){
            layer.alert('没有填写选课次数信息或格式不正确(只能填写整数值)！',3,'提示信息');
            return false;
        }else if(time == null || time == "" || !re.test(time)){
            layer.alert('没有填写选课期限信息或格式不正确(只能填写整数值)！',3,'提示信息');
            return false;
        }else if(desc == null || desc == ""){
            layer.alert('没有填写规则描述信息！',3,'提示信息');
            return false;
        }else{
            $.post("/sub/addSku.do?subId="+subId, $("#sku_form").serialize(),
                function(data){
                    if(data.success == 0) {
                        clareSku();
                        var divshow = $("#sku_div");
                        divshow.text("");// 清空数据
                        divshow.append(data.skuHtml);
                    }
                    layer.alert(data.msg,10,'提示信息');
                }, "json");
        }
    });

});

/**
 * 删除课程包图片
 * @param id
 */
function delimg(id){
    var sub_id = $('#sub_id').val();
    layer.confirm('您确定要删除此图片吗？', function(index){
        $.post("/sub/delImg.do?subId="+sub_id, {imgs:id},
            function(data){
                //console.log(data);
                if(data.success == 0){
                    var divshow = $("#img_div");
                    divshow.text("");// 清空数据
                    divshow.append(data.imageJson);
                }else{
                    layer.alert(data.msg,10,'提示信息');
                }
            }, "json");
        layer.close(index);
    });
}

/**
 * 获取要修改的客户须知信息
 * @param noticeId
 */
function noticeEdit(noticeId){
    $.post("/sub/editNotice.do", {noticeId:noticeId},
        function(data){
            clareNotice();
            valNotice(data);
        }, "json");

}

/**
 * 删除客户须知信息
 * @param noticeId
 */
function noticeDel(noticeId){
    var subId = $('#sub_id').val();
    layer.confirm('您确定要删除此客户须知吗？', function(index){
        $.post("/sub/delNotice.do?subId="+subId, {noticeId:noticeId},
            function(data){
                if(data.success == 0) {
                    var divshow = $("#ncontent_div");
                    divshow.text("");
                    divshow.append(data.notices);
                }else{
                    layer.alert(data.msg,10,'提示信息');
                }
            }, "json");
        layer.close(index);
    });
}

/**
 * 清空客户须知控件信息
 */
function clareNotice(){
    $('#notice_id').val(0);
    $('#n_title').val("");
    $('#n_content').val("");
}

/**
 * 修改客户须知信息赋值
 * @param attrs
 */
function valNotice(attrs){
    var notice = attrs.notice;
    $('#notice_id').val(notice.id);
    $('#n_title').val(notice.title);
    $('#n_content').val(notice.content);
}

/**
 * 获取要修改的sku信息
 * @param id
 */
function skuEdit(id){
    $.post("/sub/editSku.do", {skuId:id},
        function(data){
            clareSku();
            valSku(data);
        }, "json");
}

/**
 * 删除sku信息
 * @param id
 */
function skuDel(id){
    var subId = $('#sub_id').val();
    layer.confirm('您确定要删除此SKU吗？', function(index){
        $.post("/sub/delSku.do?subId="+subId, {skuId:id},
            function(data){
                if(data.success == 0) {
                    var divshow = $("#sku_div");
                    divshow.text("");
                    divshow.append(data.skuHtml);
                }else{
                    layer.alert(data.msg,10,'提示信息');
                }
            }, "json");
        layer.close(index);
    });
}

/**
 * 取消sku信息
 * @param id
 */
function cancelSku(id){
    var subId = $('#sub_id').val();
    layer.confirm('您确定要取消此SKU吗？', function(index){
        $.post("/sub/cancelSku.do?subId="+subId, {skuId:id},
            function(data){
                if(data.success == 0) {
                    var divshow = $("#sku_div");
                    divshow.text("");
                    divshow.append(data.skuHtml);
                }else{
                    layer.alert(data.msg,10,'提示信息');
                }
            }, "json");
        layer.close(index);
    });
}

/**
 * 清空sku控件信息
 */
function clareSku(){
    $('#sku_id').val(0);
    $('#desc').val("");
    $('#price').val("");
    $('#adult').val(1);
    $('#child').val(1);
    $('#courseCount').val("");
    $('#time').val("");
}

/**
 * 修改sku信息赋值
 * @param attrs
 */
function valSku(attrs){
    var sku = attrs.sku;
    $('#sku_id').val(sku.id);
    $('#desc').val(sku.desc);
    $('#price').val(sku.price);
    $('#adult').val(sku.adult);
    $('#child').val(sku.child);
    $('#courseCount').val(sku.courseCount);
    $('#time').val(sku.time);
    var timeUnit = sku.timeUnit;
    var timehtml = "";
    if(timeUnit == 1){
        timehtml = "<select id='timeUnit' name='timeUnit' class='form-control m-b'> <option value=1 selected>月</option><option value=2 >季度</option><option value=3 >年</option></select>";
    }else if(timeUnit == 2){
        timehtml = "<select id='timeUnit' name='timeUnit' class='form-control m-b'> <option value=1 >月</option><option value=2 selected>季度</option><option value=3 >年</option></select>";
    }else{
        timehtml = "<select id='timeUnit' name='timeUnit' class='form-control m-b'> <option value=1 >月</option><option value=2 >季度</option><option value=3 selected>年</option></select>";
    }

    var divshow = $("#div_time");
    divshow.text("");
    divshow.append(timehtml);

}