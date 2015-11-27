//自助录单js文件

$(document).ready(function () {

    $('#btn_product_save').click(function(){
        var psid = $('#pid').val();
        var p_title = $('#p_title').val();
        layer.alert(p_title,3,'提示信息');
        if(p_title == null || p_title == ""){
            layer.alert('请填写活动标题信息！',3,'提示信息');
            return false;
        }
        var cover = $('#cover').val();
        if(cover == null || cover == ""){
            layer.alert('请上传活动封面图！',3,'提示信息');
            return false;
        }
        var crowd = $('#crowd').val();
        if(crowd == null || crowd == ""){
            layer.alert('请填写活动适合人群信息！',3,'提示信息');
            return false;
        }
        if(psid == 0){
            $.post("/act/add.do", $("#product_form").serialize(),
                function(data){
                    //console.log(data);
                    if(data.success == 0){
                        $("#pid").attr("value",data.pid);
                    }
                    layer.alert(data.msg,10,'提示信息');
                }, "json");
        }else{
            $.post("/act/edit.do", $("#product_form").serialize(),
                function(data){
                    layer.alert(data.msg,10,'提示信息');
                }, "json");
        }
    });

    $('#btn_content_save').click(function (){
        var pid = $('#pid').val();
        var title = $('#title').val();
        if(title == null || title == ""){
            layer.alert('请填写流程名称！',3,'提示信息');
            return false;
        }else{
            $.post("/act/addnode.do?pid="+pid, $("#node_form").serialize(),
                function(data){
                    //console.log(data);
                    if(data.success == 0) {
                        $("#title").val("");
                        $("#style").val("none");
                        $("#cid").val("0");
                        var divshow = $("#node_div");
                        divshow.text("");// 清空数据
                        divshow.append(data.nodeJson);
                        var node_id = data.nodeId;
                        if (node_id.length > 0) {
                            var nodes = node_id.split(",");
                            if (nodes.length > 0) {
                                for(var i=0; i < nodes.length; i++){
                                    new baidu.editor.ui.Editor({initialContent: '', textarea: 'editorValue',toolbars: [["bold","italic","simpleupload","link","unlink"]], minFrameHeight:500, initialFrameWidth:1000}).render(nodes[i]);
                                }
                            }
                        }
                    }
                    layer.alert(data.msg,10,'提示信息');
                }, "json");

        }
    });

    $('#btn_sku_save').click(function (){
        var pid = $('#pid').val();
        validationData();
        $.post("/act/addsku.do?pid="+pid, $("#sku_form").serialize(),
            function(data){
                if(data.success == 0) {
                    clareSku();
                    var divshow = $("#sku_list");
                    divshow.text("");// 清空数据
                    divshow.append(data.skuHtml);
                }
                layer.alert(data.msg,10,'提示信息');
            }, "json");
    });

    $('#btn_place_save').click(function (){
        var cityId = $('#placeCityId').val();
        var regionId= $('#regionId').val();
        var name= $('#name').val();
        var place_ads = $('#ads').val();
        var desc= $('#desc_place').val();
        if(place_ads = null || place_ads == ""){
            layer.alert("地址详情不能为空,请输入",3,'提示信息');
            return false;
        } else if(name == null || name == ""){
            layer.alert("地址名称不能为空,请输入",3,'提示信息');
            return false;
        }else{
            $.post("/act/addplace.do", {cityId:cityId,regionId:regionId,name:name,address:$('#ads').val(),lng:0,lat:0,desc:desc},
                function(data){
                    if(data.success == 0) {
                        console.log(data);
                        var divshow = $("#div_place");
                        divshow.text("");// 清空数据
                        divshow.append(data.placeHtml);

                        $('#myModal4').modal('hide')
                    }
                    layer.alert(data.msg,10,'提示信息');
                }, "json");
        }
    });



});

function textsubmit(form_id,cid,type){
    var pid = document.getElementById("pid").value;
    var con_id = document.getElementById(cid).value;
    var con_type = document.getElementById(type).value;
    $.post("/act/addbody.do?pid="+pid+"&cid="+con_id+"&ctype="+con_type, $('#'+form_id).serialize(),
        function(data){
            if(data.success == 0){
                layer.alert(data.msg,10,'提示信息');
            }
        }, "json");
}

function nodeEditsubmit(cid,title,style){
    $('#cid').val(cid);
    $('#title').val(title);
    $('#style').val(style);
}

function nodeDelsubmit(cid){
    var pid = document.getElementById("pid").value;
    $.post("/act/delnode.do?pid="+pid, {cid:cid},
        function(data){
            if(data.success == 0) {
                var divshow = $("#node_div");
                divshow.text("");// 清空数据
                divshow.append(data.nodeJson);
                var node_id = data.nodeId;
                if (node_id.length > 0) {
                    var nodes = node_id.split(",");
                    if (nodes.length > 0) {
                        for(var i=0; i < nodes.length; i++){
                            new baidu.editor.ui.Editor({initialContent: '', textarea: 'editorValue',toolbars: [["bold","italic","simpleupload","link","unlink"]], minFrameHeight:500, initialFrameWidth:1000}).render(nodes[i]);
                        }
                    }
                }
            }
        }, "json");
}

function skuEditsubmit(sid){
    $.post("/act/editsku.do", {sid:sid},
        function(data){
            clareSku();
            valSku(data);
        }, "json");

}

function skuDelsubmit(sid){
    var pid = $('#pid').val();
    $.post("/act/delsku.do?pid="+pid, {sid:sid},
        function(data){
            if(data.success == 0) {
                clareSku();
                var divshow = $("#sku_list");
                divshow.text("");
                divshow.append(data.skuHtml);
            }
            layer.alert(data.msg,10,'提示信息');
        }, "json");
}

