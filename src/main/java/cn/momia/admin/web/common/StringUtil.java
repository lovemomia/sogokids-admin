package cn.momia.admin.web.common;

import cn.momia.admin.web.entity.DataBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/6/15.
 */
public class StringUtil {

    public static List<Map<String, Object>> parseJSON2List(String jsonStr){
        JSONArray jsonArr = JSONArray.fromObject(jsonStr);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Iterator<JSONObject> it = jsonArr.iterator();
        while(it.hasNext()){
            JSONObject json2 = it.next();
            list.add(parseJSON2Map(json2.toString()));
        }
        return list;
    }


    public static Map<String, Object> parseJSON2Map(String jsonStr){
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for(Object k : json.keySet()){
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if(v instanceof JSONArray){
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                Iterator<JSONObject> it = ((JSONArray)v).iterator();
                while(it.hasNext()){
                    JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }


    public static List<Map<String, Object>> getListByUrl(String url){
        try {
            //通过HTTP获取JSON数据
            InputStream in = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
            return parseJSON2List(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Map<String, Object> getMapByUrl(String url){
        try {
            //通过HTTP获取JSON数据
            InputStream in = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
            return parseJSON2Map(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //test
    public static void main(String[] args) {
        String str = "{\"data\":{\"height\":525,\"path\":\"/2015-07-07/e0b6a01f33105af23eb2fba0780402b6.jpg\",\"width\":800},\"errmsg\":\"success\",\"errno\":0,\"time\":1436259899603}";
        String jstr = "";
        Map<String, Object> map = parseJSON2Map(str);
        System.out.print("data:"+map.get("data"));
        System.out.print("errmsg:"+map.get("errmsg"));
        System.out.print("errno:"+map.get("errno"));
        Map<String, Object> map2 = parseJSON2Map(map.get("data").toString());
        System.out.print("height:"+map2.get("height"));
        System.out.print("width:"+map2.get("width"));
        System.out.print("path:"+map2.get("path"));

        //DataBean bean = new DataBean();
//        for (int i = 0; i < list.size() ; i++) {
//            if (list.get(i).get("title").equals("活动特色")){
//                List<Map<String,String>> lists = (List<Map<String, String>>) list.get(i).get("body");
//                bean.setTitle("hdts");
//                bean.setStyle(list.get(i).get("style").toString());
//                //bean.setBody(lists);
//                System.out.println(bean.getBody());
//
//            }
//        }
    }
}
