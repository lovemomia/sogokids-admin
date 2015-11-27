//自助录单价格输入js文件

function addtr(id){
    tr_id = $("#price_table>tbody>tr:last").attr("id");
    tr_id++;
    var adult = "adult"+tr_id;
    var child = "child"+tr_id;
    var prices = "prices"+tr_id;
    var desc_p = "desc_p"+tr_id;

    str = "<tr id = '"+tr_id+"'><td><input type='checkbox' id='price_box' name='price_box' checked hidden><input id='"+adult+"' name='"+adult+"' type='text' class='form-control'></td> " +
        "<td><input id='"+child+"' name='"+child+"' type='text' class='form-control'></td> " +
        "<td><input id='"+prices+"' name='"+prices+"' type='text' class='form-control'></td> " +
        "<td><input id='"+desc_p+"' name='"+desc_p+"' type='text' class='form-control'></td>" +
        "<td><input type='button' name='button' value='del' onclick='deltr(this);'></td></tr>";
    $('#'+id).append(str);
}

function deltr(clickTd){
    var tr = $(clickTd).parent().parent();
    tr.remove();
}