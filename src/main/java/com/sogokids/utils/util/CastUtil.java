package com.sogokids.utils.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoze on 16/1/4.
 */
public class CastUtil {

    public static <T> T toObject(JSON json, Class<T> clazz) {
        return JSON.toJavaObject(json, clazz);
    }

    public static <T> List<T> toList(JSON json, Class<T> clazz) {
        JSONArray jsonArray = (JSONArray) json;

        List<T> list = new ArrayList<T>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(jsonArray.getObject(i, clazz));
        }

        return list;
    }

}
