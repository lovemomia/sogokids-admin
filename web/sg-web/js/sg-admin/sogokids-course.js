//课程js文件

$(function () {

    $('.fancybox').fancybox({
        openEffect: 'none',
        closeEffect: 'none'
    });

    /**
     * 添加课程图片
     */
    $("#btn_lb_save").click(function() {
        var course_id = $('#course_id').val();
        var imgfile = $('#file_img').val();
        //创建FormData对象
        var data = new FormData();
        //为FormData对象添加数据
        $.each($('#file_img')[0].files, function(i, file) {
            data.append('upload_file'+i, file);
        });
        if(course_id == 0){
            layer.alert('请先保存基本信息！',5,'提示信息');
        }else if (imgfile == "" || imgfile == null) {
            layer.alert('没有选择上传的图片！',5,'提示信息');
        } else {
            $.ajax({
                url:'/course/addImg.do?courseId=' + course_id,
                type:'POST',
                data:data,
                cache: false,
                contentType: false,    //不可缺
                processData: false,    //不可缺
                success:function(data){
                    var obj = $.parseJSON(data);
                    if(obj.success == 0){
                        //layer.alert(obj.msg,10,'提示信息');
                    }else{
                        layer.alert(obj.msg,3,'提示信息');
                    }
                    var imgstr = obj.imgHtml;
                    var divshow = $("#img_div");
                    divshow.text("");
                    divshow.append(imgstr);
                    $('#file_img').val("");
                    $("#img_preview").text("");
                }
            });
        }
        return false;
    });

    /**
     * 添加课程的基本信息
     */
    $('#btn_cour_save').click(function(){
        var strP=/^\d+(\.\d+)?$/;
        var re = /^[-]{0,1}[0-9]{1,}$/;
        var course_id = $('#course_id').val();
        var title = $('#title').val();
        var cover = $('#cover').val();
        var keyWord = $('#keyWord').val();
        var price = $('#price').val();
        var minAge = $('#minAge').val();
        var maxAge = $('#maxAge').val();
        //var recommend = $('#recommend').val();

        if(title == null || title == ""){
            layer.alert('请填写标题信息！',3,'提示信息');
            return false;
        }else if(cover == null || cover == ""){
            layer.alert('请上传封面图！',3,'提示信息');
            return false;
        }else if(keyWord == null || keyWord == ""){
            layer.alert('请填写关键字信息！',3,'提示信息');
            return false;
        }else if(price == null || price == "" || !strP.test(price)){
            layer.alert('没有填写原价信息或格式不正确(只能填写整数值或小数)！',3,'提示信息');
            return false;
        }else if(minAge == null || minAge == "" || !re.test(minAge) || maxAge == null || maxAge == "" || !re.test(maxAge) || parseInt(maxAge) < parseInt(minAge)){
            layer.alert('没有填写年龄范围信息或格式不正确(只能填写整数值,举例格式"4至7岁")！',3,'提示信息');
            return false;
        }else {
            //$.post("/course/validation_recommend.do", {textarea_value: recommend, label_name: "推荐理由", max_row_value: 4, row_length: 20},
            //    function (data) {
            //        if (data.success == 0) {
            //
            //        } else {
            //            layer.alert(data.msg, 10, '提示信息');
            //        }
            //    }, "json");
            if (course_id == 0) {
                $.post("/course/add.do", $("#course_form").serialize(),
                    function (data) {
                        if (data.success == 0) {
                            $("#course_id").attr("value", data.courseId);
                        }
                        layer.alert(data.msg, 10, '提示信息');
                    }, "json");
            } else {
                $.post("/course/edit.do", $("#course_form").serialize(),
                    function (data) {
                        layer.alert(data.msg, 10, '提示信息');
                    }, "json");
            }
        }
    });

    /**
     * 添加或修改课前绘本信息
     */
    $('#btn_book_save').click(function (){
        var courseId = $('#course_id').val();
        var hb_img = $('#hb_img').val();
        if(courseId == 0){
            layer.alert('请先保存基本信息！',5,'提示信息');
            return false;
        }else if(hb_img == null || hb_img == ""){
            layer.alert('没有选择上传的图片！',3,'提示信息');
            return false;
        }else{
            $.ajaxFileUpload({
                url: '/course/addBook.do?courseId=' + courseId,
                secureuri: false,
                fileElementId: 'hb_img',
                dataType: "text",
                success: function (data, status) {
                    console.log(data);
                    var imgstr = data;
                    var divshow = $("#book_div");
                    divshow.text("");
                    divshow.append(imgstr);
                },
                error: function (data, status, error) {
                    layer.alert(error, 10, '提示信息');
                }
            });
        }
    });

    $('#btn_sku_save').click(function () {
        var re = /^[-]{0,1}[0-9]{1,}$/;
        var courseId = $('#course_id').val();
        var stock = $('#stock').val();
        var startTime = $('#startTime').val();
        var endTime = $('#endTime').val();
        var adult = $('#adult').val();
        var child = $('#child').val();
        if (courseId == 0) {
            layer.alert('请先保存基本信息！', 5, '提示信息');
            return false;
        }else if(stock == null || stock == "" || !re.test(stock)){
            layer.alert('没有填写库存信息或格式不正确(只能填写整数值)！',3,'提示信息');
            return false;
        }else if(adult == null || adult == "" || child == null || child == "" || !re.test(adult) || !re.test(child) ){
            layer.alert('没有填写成人或者儿童数量信息或格式不正确(只能填写整数值)！',3,'提示信息');
            return false;
        }else if(startTime == null || startTime == ""){
            layer.alert('请填写开始时间信息！',3,'提示信息');
            return false;
        }else if(endTime == null || endTime == ""){
            layer.alert('请填写结束时间信息！',3,'提示信息');
            return false;
        } else {
            $.post("/course/addSku.do?courseId=" + courseId, $("#sku_form").serialize(),
                function (data) {
                    if (data.success == 0) {
                        clareSku();
                        var divshow = $("#sku_div");
                        divshow.text("");// 清空数据
                        divshow.append(data.skuHtml);
                    }
                    layer.alert(data.msg, 10, '提示信息');
                }, "json");
        }
    });

    $('#btn_place_save').click(function (){
        var cityId = $('#placeCityId').val();
        var regionId= $('#regionId').val();
        var lng_lat = $('#lng_lat').val();
        var name= $('#name').val();
        var place_ads = $('#ads').val();
        var desc= $('#desc_place').val();
        var route = $('#route').val();
        if(place_ads = null || place_ads == ""){
            layer.alert("地址详情不能为空,请输入",3,'提示信息');
            return false;
        } else if(name == null || name == ""){
            layer.alert("地址名称不能为空,请输入",3,'提示信息');
            return false;
        }else if(lng_lat == null || lng_lat == ""){
            layer.alert("请输入经纬度信息",3,'提示信息');
            return false;
        }else if(lng_lat.indexOf(",") < 0){
            layer.alert("输入经纬度信息格式不正确,重新输入",3,'提示信息');
            return false;
        }else{
            $.post("/course/addplace.do", {cityId:cityId,regionId:regionId,name:name,address:$('#ads').val(),lng_lat:lng_lat,desc:desc,route:route},
                function(data){
                    if(data.success == 0) {
                        //console.log(data);
                        var divshow = $("#course_placeId");
                        divshow.text("");// 清空数据
                        divshow.append(data.placeHtml);
                        $('#myModal4').modal('hide');
                    }else{
                        layer.alert(data.msg,10,'提示信息');
                    }
                }, "json");
        }
    });

    /**
     * 添加讲师
     */
    //$("#add_teacher").click(function() {
    //    var courseId = $('#course_id').val();
    //    if(courseId == 0){
    //        layer.alert('请先保存基本信息！',5,'提示信息');
    //    } else {
    //        $.post("/course/teacher.do?courseId="+courseId+"&mark=1", $("#w_form").serialize(),
    //            function(data){
    //                if(data.success == 0) {
    //                    console.log(data);
    //                    var divshow_w = $("#w-teacher");
    //                    divshow_w.text("");// 清空数据
    //                    divshow_w.append(data.w_teacher);
    //                    var divshow_y = $("#y-teacher");
    //                    divshow_y.text("");// 清空数据
    //                    divshow_y.append(data.y_teacher);
    //
    //                    $('.dataTables-example').dataTable({
    //                        "aLengthMenu":[8],
    //                        //"bInfo": false,
    //                        "bSort": false //排序功能
    //                    });
    //                }
    //                layer.alert(data.msg,10,'提示信息');
    //            }, "json");
    //    }
    //});

    /**
     * 取消讲师
     */
    //$("#del_teacher").click(function() {
    //    var courseId = $('#course_id').val();
    //    if(courseId == 0){
    //        layer.alert('请先保存基本信息！',5,'提示信息');
    //    } else {
    //        $.post("/course/teacher.do?courseId="+courseId+"&mark=2", $("#y_form").serialize(),
    //            function(data){
    //                if(data.success == 0) {
    //                    console.log(data);
    //                    var divshow_w = $("#w-teacher");
    //                    divshow_w.text("");// 清空数据
    //                    divshow_w.append(data.w_teacher);
    //                    var divshow_y = $("#y-teacher");
    //                    divshow_y.text("");// 清空数据
    //                    divshow_y.append(data.y_teacher);
    //
    //                    $('.dataTables-example').dataTable({
    //                        "aLengthMenu":[8],
    //                        //"bInfo": false,
    //                        "bSort": false //排序功能
    //                    });
    //                }
    //                layer.alert(data.msg,10,'提示信息');
    //            }, "json");
    //    }
    //});

    $('#btn_courDetail_save').click(function(){
        var courseId = $('#course_id').val();
        var abstracts = $('#abstracts').val();
        if(courseId == 0){
            layer.alert('请先保存基本信息！',5,'提示信息');
        }else if(abstracts == null || abstracts == ""){
            layer.alert('请填写摘要信息！',3,'提示信息');
            return false;
        }else{
            $.post("/course/addDetail.do?courseId="+courseId, $("#t_w_xq").serialize(),
                function(data){
                    if(data.success == 0){
                        $("#d_id").attr("value",data.d_id);
                    }
                    layer.alert(data.msg,10,'提示信息');
                }, "json");
        }

    });


    $('#btn_sku_cancel').click(function () {
        var course_id = $('#course_id').val();
        var sms_content = $('#sms_content').val().trim();
        if(sms_content == null || sms_content == ""){
            layer.alert('请填写取消原因信息！',3,'提示信息');
            return false;
        }else {
            $.post("/course/cancelSku.do?courseId=" + course_id, $("#cancel_sku_form").serialize(),
                function (data) {
                    if (data.success == 0) {
                        $('#cancel_sku_id').val(0);
                        $('#sms_content').val("");
                        var divshow = $("#sku_div");
                        divshow.text("");
                        divshow.append(data.skuHtml);
                        layer.alert(data.msg, 10, '提示信息');
                        $('#myCancelGroup2').modal('hide');
                    } else {
                        layer.alert(data.msg, 10, '提示信息');
                    }
                }, "json");
        }
    });

});

/**
 * 删除课程图片
 * @param id
 */
function delimg(id){
    var course_id = $('#course_id').val();
    layer.confirm('您确定要删除课程图片吗？', function(index){
        $.post("/course/delImg.do?courseId="+course_id, {imgs:id},
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
//
///**
// * 获取要修改的课前绘本信息
// * @param bookId
// */
//function bookEdit(bookId){
//    $.post("/course/editBook.do", {bookId:bookId},
//        function(data){
//            clareBook();
//            valBook(data);
//        }, "json");
//
//}

/**
 * 删除课前绘本信息
 * @param bookId
 */
function bookDel(bookId){
    var courseId = $('#course_id').val();
    layer.confirm('您确定要删除绘本图片吗？', function(index){
        $.post("/course/delBook.do?courseId="+courseId, {bookId:bookId},
            function(data){
                if(data.success == 0) {
                    var divshow = $("#book_div");
                    divshow.text("");
                    divshow.append(data.books);
                }else{
                    layer.alert(data.msg,10,'提示信息');
                }
            }, "json");
        layer.close(index);
    });
}

///**
// * 清空客户须知控件信息
// */
//function clareBook(){
//    $('#book_id').val(0);
//    $('#order').val("");
//    $('#book_img').val("");
//    $("#img_book").attr("src", "");
//}

///**
// * 修改客户须知信息赋值
// * @param attrs
// */
//function valBook(attrs){
//    var book = attrs.book;
//    $('#book_id').val(book.id);
//    $('#order').val(book.order);
//    $('#book_img').val(book.img);
//    var pic = $("#filepath").val();
//    $("#img_book").attr("src", pic + book.img);
//}

/**
 * 获取要修改的sku信息
 * @param id
 */
function skuEdit(id){
    $.post("/course/editSku.do", {skuId:id},
        function(data){
            clareSku();
            valSku(data);
            var divshow = $("#course_placeId");
            divshow.text("");
            divshow.append(data.placeHtml);
        }, "json");
}

/**
 * 删除sku信息
 * @param id
 */
function skuDel(id){
    var courseId = $('#course_id').val();
    layer.confirm('您确定要删除课程SKU吗？', function(index){
        $.post("/course/delSku.do?courseId="+courseId, {skuId:id},
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
    $('#cancel_sku_id').val(id);
    //layer.confirm('您确定要取消课程SKU吗？', function(index){
    //    $('#cancel_sku_id').val(id);
    //    layer.close(index);
    //});
}

/**
 * 清空sku控件信息
 */
function clareSku(){
    $('#sku_id').val(0);
    $('#minBooked').val(8);
    $('#stock').val(10);
    $('#startTime').val("");
    $('#endTime').val("");
    $('#adult').val(1);
    $('#child').val(1);
}

/**
 * 修改sku信息赋值
 * @param attrs
 */
function valSku(attrs){
    var sku = attrs.sku;
    $('#sku_id').val(sku.id);
    $('#minBooked').val(sku.minBooked);
    $('#stock').val(sku.stock);
    $('#startTime').val(sku.startTime);
    $('#endTime').val(sku.endTime);
    $('#adult').val(sku.adult);
    $('#child').val(sku.child);
}

/**
 * 前移课程图片
 * @param id
 */
function beforeImg(id){
    var course_id = $('#course_id').val();
    $.post("/course/moveImg.do?courseId="+course_id, {imgId:id,flag:1},
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
}

/**
 * 后移课程图片
 * @param id
 */
function afterImg(id){
    var course_id = $('#course_id').val();
    $.post("/course/moveImg.do?courseId="+course_id, {imgId:id,flag:2},
        function(data){
            if(data.success == 0){
                var divshow = $("#img_div");
                divshow.text("");// 清空数据
                divshow.append(data.imageJson);
            }else{
                layer.alert(data.msg,10,'提示信息');
            }
        }, "json");
}

/**
 * 多张图片上传预览
 * @returns {boolean}
 */
function setImagePreviews() {

    var docObj = document.getElementById("file_img");

    var dd = document.getElementById("img_preview");

    dd.innerHTML = "";

    var fileList = docObj.files;

    for (var i = 0; i < fileList.length; i++) {

        dd.innerHTML += "<div style='float:left;margin-left: 10px;margin-right: 10px;margin-top: 10px;margin-bottom: 10px;border: solid 1px #b3b3b3;' > <img id='img" + i + "'  /> </div>";

        var imgObjPreview = document.getElementById("img"+i);

        if (docObj.files && docObj.files[i]) {

            //火狐下，直接设img属性

            imgObjPreview.style.display = 'block';

            imgObjPreview.style.width = '100px';

            imgObjPreview.style.height = '80px';

            //imgObjPreview.src = docObj.files[0].getAsDataURL();

            //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式

            imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);

        }

        else {

            //IE下，使用滤镜

            docObj.select();

            var imgSrc = document.selection.createRange().text;

            //alert(imgSrc)

            var localImagId = document.getElementById("img" + i);

            //必须设置初始大小

            localImagId.style.width = "100px";

            localImagId.style.height = "80px";

            //图片异常的捕捉，防止用户修改后缀来伪造图片

            try {

                localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";

                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;

            }

            catch (e) {

                alert("您上传的图片格式不正确，请重新选择!");

                return false;

            }

            imgObjPreview.style.display = 'none';

            document.selection.empty();

        }

    }

    return true;

}

