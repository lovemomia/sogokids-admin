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
        if(course_id == 0){
            layer.alert('请先保存基本信息！',5,'提示信息');
        }else if (imgfile == "" || imgfile == null) {
            layer.alert('没有选择上传的图片！',5,'提示信息');
        } else {
            $.ajaxFileUpload({
                url: '/course/addImg.do?courseId=' + course_id,
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
        }else{
            if(course_id == 0){
                $.post("/course/add.do", $("#course_form").serialize(),
                    function(data){
                        if(data.success == 0){
                            $("#course_id").attr("value",data.courseId);
                        }
                        layer.alert(data.msg,10,'提示信息');
                    }, "json");
            }else{
                $.post("/course/edit.do", $("#course_form").serialize(),
                    function(data){
                        layer.alert(data.msg,10,'提示信息');
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
    var courseId = $('#course_id').val();
    layer.confirm('您确定要取消此SKU吗？', function(index){
        $.post("/course/cancelSku.do?courseId="+courseId, {skuId:id},
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
    $('#stock').val(10);
    $('#startTime').val("");
    $('#endTime').val("");
    $('#adult').val("");
    $('#child').val("");
}

/**
 * 修改sku信息赋值
 * @param attrs
 */
function valSku(attrs){
    var sku = attrs.sku;
    $('#sku_id').val(sku.id);
    $('#stock').val(sku.stock);
    $('#startTime').val(sku.startTime);
    $('#endTime').val(sku.endTime);
    $('#adult').val(sku.adult);
    $('#child').val(sku.child);
}

