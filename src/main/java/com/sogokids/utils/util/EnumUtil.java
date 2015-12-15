package com.sogokids.utils.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/7/15.
 */
public class EnumUtil {

    private static final String sku_type = "[{id:0,name:'普通SKU'},{id:1,name:'抽奖SKU'}]";
    private static final String need = "[{id:0,name:'不需要'},{id:1,name:'需要'}]";
    private static final String sub_type = "[{id:1,name:'课程体系'},{id:2,name:'试听课程'}]";
    private static final String time_unit = "[{id:1,name:'月'},{id:2,name:'季度'},{id:3,name:'年'}]";
    private static final String pay_unit = "[{id:0,name:'请选择'},{id:1,name:'已下单未付款'},{id:2,name:'准备付款'},{id:3,name:'已付款'},{id:4,name:'已完成'},{id:5,name:'已申请退款'},{id:6,name:'已退款完成'}]";
    private static final String sex_unit = "[{id:2,name:'女'},{id:1,name:'男'}]";
    private static final String teacher_unit = "[{id:1,name:'讲师'},{id:2,name:'培训师'},{id:3,name:'助教'}]";
    private static final String coupon_time_type = "[{id:1,name:'时间单位'},{id:2,name:'固定时间'}]";
    private static final String platform_type = "[{id:0,name:'通用'},{id:1,name:'APP'},{id:2,name:'M站'}]";

    public static List<Map<String, Object>> getEnums(int intEnum) {
        List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
        switch (intEnum) {
            case Quantity.STATUS_ONE:
                ls.addAll(StringUtil.parseJSON2List(sku_type));
                break;
            case Quantity.STATUS_TWO:
                ls.addAll(StringUtil.parseJSON2List(need));
                break;
            case Quantity.STATUS_THREE:
                ls.addAll(StringUtil.parseJSON2List(sub_type));
                break;
            case Quantity.STATUS_FOUR:
                ls.addAll(StringUtil.parseJSON2List(time_unit));
                break;
            case Quantity.STATUS_FIVE:
                ls.addAll(StringUtil.parseJSON2List(pay_unit));
                break;
            case Quantity.STATUS_SIX:
                ls.addAll(StringUtil.parseJSON2List(sex_unit));
                break;
            case Quantity.STATUS_SEVEN:
                ls.addAll(StringUtil.parseJSON2List(teacher_unit));
                break;
            case Quantity.STATUS_EIGHT:
                ls.addAll(StringUtil.parseJSON2List(coupon_time_type));
                break;
            case Quantity.STATUS_NINE:
                ls.addAll(StringUtil.parseJSON2List(platform_type));
                break;
        }

        return ls;
    }

}
