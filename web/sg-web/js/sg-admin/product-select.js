//自助录单js文件

$(document).ready(function () {

    $(".selectList").each(function(){
        var url = "/place/datajson.do?id=0";
        var areaJson;
        var temp_html;
        var oProvince = $(this).find(".province");
        var oCity = $(this).find(".city");
        var oDistrict = $(this).find(".district");
        //初始化省
        var province = function(){
            $.each(areaJson,function(i,province){
                temp_html+="<option value='"+province.id+"'>"+province.name+"</option>";
            });
            oProvince.html(temp_html);
            city();
        };
        //赋值市
        var city = function(){
            temp_html = "";

            var n = oProvince.get(0).selectedIndex;
            var regions = areaJson[n].regions;
            if(regions == undefined){
            }else{
                $.each(areaJson[n].regions,function(i,city){
                    temp_html+="<option value='"+city.id+"'>"+city.name+"</option>";
                });
            }

            oCity.html(temp_html);
            district();
        };
        //赋值县
        var district = function(){
            temp_html = "";
            var m = oProvince.get(0).selectedIndex;
            var n = oCity.get(0).selectedIndex;

            if(n == -1){
            }else if(typeof(areaJson[m].regions[n].regchild) == "undefined"){
                oDistrict.css("display","none");
            }else{
                oDistrict.css("display","inline");
                $.each(areaJson[m].regions[n].regchild,function(i,district){
                    temp_html+="<option value='"+district.id+"'>"+district.name+"</option>";
                });
            };
            oDistrict.html(temp_html);
        };
        //选择省改变市
        oProvince.change(function(){
            city();
        });
        //选择市改变县
        oCity.change(function(){
            district();
        });
        //获取json数据
        $.getJSON(url,function(data){
            areaJson = data;
            province();
        });
    });

});