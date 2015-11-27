//自助录单js文件

function validationData(){
    var limit = $('#limit').val();
    if(limit == null || limit == ""){
        layer.alert('请填写是否限购信息',3,'提示信息');
        return false;
    }else{
        if(isNaN(limit)){
            layer.alert('限购信息为数字,请重新输入',3,'提示信息');
            return false;
        }
    }
    var stock = $('#stock').val();
    if(stock == null || stock == ""){
        layer.alert('请填写库存信息',3,'提示信息');
        return false;
    }else{
        if(isNaN(stock)){
            layer.alert('库存信息为数字,请重新输入',3,'提示信息');
            return false;
        }
    }

    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    if(startTime == null || startTime == "" || endTime == null || endTime == ""){
        layer.alert('请填写活动时间信息',3,'提示信息');
        return false;
    }else{
        var start = new Date(startTime.replace("-", "/").replace("-", "/"));
        var end = new Date(endTime.replace("-", "/").replace("-", "/"));
        if (end < start) {
            layer.alert('活动结束日期不能小于开始日期',3,'提示信息');
            return false;
        }
    }

    var onlineTime = $('#onlineTime').val();
    var offlineTime = $('#offlineTime').val();

    if(onlineTime == null || onlineTime == "" || offlineTime == null || offlineTime == ""){
        layer.alert('请填写活动上下线时间信息',3,'提示信息');
        return false;
    }else{
        var online = new Date(onlineTime.replace("-", "/").replace("-", "/"));
        var offline = new Date(offlineTime.replace("-", "/").replace("-", "/"));
        if (offline < online) {
            layer.alert('下线日期不能小于上线日期',3,'提示信息');
            return false;
        }
    }
}

function clareSku(){
    $('#sid').val(0);
    $('#type').val(0);
    $('#needRealName').val(0);
    $('#limit').val(0);
    $('#stock').val("");
    $('#desc').val("");
    $('#startTime').val("");
    $('#endTime').val("");
    $('#onlineTime').val("");
    $('#offlineTime').val("");
    $('#anyTime').val(0);
    //$('input:radio').eq(0).attr('checked', 'true');
    //$("#input[type='radio'][name='anyTime'][value='0']").attr("checked",true);
    var tr_id_id = $("#price_table>tbody>tr:last").attr("id");
    tr_id_id++;
    for(var i = 1;i < tr_id_id;i++){
        var adult = "adult"+i;
        var child = "child"+i;
        var prices = "prices"+i;
        var desc_p = "desc_p"+i;
        $('#'+adult).val("");
        $('#'+child).val("");
        $('#'+prices).val("");
        $('#'+desc_p).val("");
    }
    if(tr_id_id > 2){
        for(var i = 2;i < tr_id_id;i++){
            $('#'+i).remove();
        }
    }

    //var strappend = "<tr id = '1'><td><input type='checkbox' id='price_box' name='price_box' checked hidden><input id='adult1' name='adult1' type='text' class='form-control'></td> " +
    //    "<td><input id='child1' name='child1' type='text' class='form-control'></td> " +
    //    "<td><input id='prices1' name='prices1' type='text' class='form-control'></td> " +
    //    "<td><input id='desc_p1' name='desc_p1' type='text' class='form-control'></td><td></td></tr>";
    //$('#price_table').append(strappend);
}

function valSku(attrs){
    var sku = attrs.sku;
    var obj = eval(sku.prices);
    $('#sid').val(sku.id);
    $('#type').val(sku.type);
    $('#needRealName').val(sku.needRealName);
    $('#limit').val(sku.limit);
    $('#stock').val(sku.stock);
    $('#desc').val(sku.desc);
    $('#startTime').val(sku.startTime);
    $('#endTime').val(sku.endTime);
    $('#onlineTime').val(sku.onlineTime);
    $('#offlineTime').val(sku.offlineTime);
    $('#anyTime').val(sku.anyTime);

    var divshow = $("#div_place");
    divshow.text("");// 清空数据
    divshow.append(attrs.placeHtml);

    var trid = $("#price_table>tbody>tr:last").attr("id");
    trid++;
    var strappend = "";
    for(var i=0;i< obj.length;i++){
        var intx = i + 1;
        var adult = "adult" + intx;
        var child = "child" + intx;
        var prices = "prices" + intx;
        var desc_p = "desc_p" + intx;

        if(intx < trid){
            $('#'+adult).val(obj[i].adult);
            $('#'+child).val(obj[i].child);
            $('#'+prices).val(obj[i].price);
            $('#'+desc_p).val(obj[i].desc);
        }else{
            var decs_c = obj[i].desc;
            if(decs_c == undefined){
                decs_c = "";
            }
            var adult_c = obj[i].adult;
            if(adult_c == undefined){
                adult_c = "";
            }
            var child_c = obj[i].child;
            if(child_c == undefined){
                child_c = "";
            }
            var prices_c = obj[i].price;
            if(prices_c == undefined){
                prices_c = "";
            }
            strappend = strappend + "<tr id = '"+intx+"'><td><input type='checkbox' id='price_box' name='price_box' checked hidden><input id='"+adult+"' name='"+adult+"' type='text' class='form-control' value='"+adult_c+"'></td> " +
                "<td><input id='"+child+"' name='"+child+"' type='text' class='form-control' value='"+child_c+"'></td> " +
                "<td><input id='"+prices+"' name='"+prices+"' type='text' class='form-control' value='"+prices_c+"'></td> " +
                "<td><input id='"+desc_p+"' name='"+desc_p+"' type='text' class='form-control' value='"+decs_c+"'></td><td></td></tr>";
        }
    }
    $('#price_table').append(strappend);
}