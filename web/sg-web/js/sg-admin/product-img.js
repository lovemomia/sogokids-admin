//自助录单js文件

$(document).ready(function () {

    $("#fileurl").change(function(){
        $.ajaxFileUpload({
            url:'/upload/img.do',//处理图片脚本
            secureuri :false,
            fileElementId :'fileurl',//file控件id
            dataType : 'text',
            success : function (data, status){
                var obj = JSON.parse(data);
                $("#cover").val(obj.path);
                var pic = $("#filepath").val();
                $("#img_a").attr("src", pic + obj.path);
            },
            error: function(data, status, e){
            }
        });
        return false;
    });

    $("#btn_img_save").click(function() {
        var psid = $('#pid').val();
        var imgfile = $('#file_img').val();
        if (imgfile == "" || imgfile == null) {
            layer.alert('没有选择上传的图片！',5,'提示信息');
        } else {
            $.ajaxFileUpload({
                url: '/act/addimg.do?pid=' + psid,
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
                error: function (data, status, e) {
                    layer.alert(e,10,'提示信息');
                }
            });
        }
        return false;
    });

});

    function delimg(id){
        var pid = $('#pid').val();
        $.post("/act/delimg.do?pid="+pid, {imgs:id},
            function(data){
                //console.log(data);
                if(data.success == 0){
                    var divshow = $("#img_div");
                    divshow.text("");// 清空数据
                    divshow.append(data.imageJson);
                }
                layer.alert(data.msg,10,'提示信息');
            }, "json");
    }