//自助录单js文件

$(document).ready(function () {

    $("#fileurl").change(function(){
        $.ajaxFileUpload({
            url:'/upload/updateImg.do',//处理图片脚本
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

    /**
     * 上传图片
     */
    $("#btn_img_save").click(function(){
        $.ajaxFileUpload({
            url:'/upload/img.do',//处理图片脚本
            secureuri :false,
            fileElementId :'img_path',//file控件id
            dataType : 'text',
            success : function (data, status){
                var obj = JSON.parse(data);
                $("#cover").val(obj.path);
                var pic = $("#filepath").val();
                $("#img_a").attr("src", pic + obj.path);
            },
            error: function(data, status, e){
                alert(e);
            }
        });

        return false;
    });

});