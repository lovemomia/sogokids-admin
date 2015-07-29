package cn.momia.admin.web.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by hoze on 15/7/15.
 */
public class EnumUtil {

    private static final String sku_type = "[{id:0,name:'普通SKU'},{id:1,name:'特殊SKU'}]";
    private static final String need = "[{id:0,name:'不需要'},{id:1,name:'需要'}]";

    public static List<Map<String, Object>> getEnums(int intEnum) {
        List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
        switch (intEnum) {
            case FinalUtil.TYPE_1:
                ls.addAll(StringUtil.parseJSON2List(sku_type));
                break;
            case FinalUtil.TYPE_2:
                ls.addAll(StringUtil.parseJSON2List(need));
                break;
        }

        return ls;
    }

}
