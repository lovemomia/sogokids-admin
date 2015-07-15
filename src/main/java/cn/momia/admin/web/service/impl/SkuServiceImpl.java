package cn.momia.admin.web.service.impl;

import cn.momia.admin.web.common.FinalUtil;
import cn.momia.admin.web.common.StringUtil;
import cn.momia.admin.web.entity.Sku;
import cn.momia.admin.web.entity.SkuOther;
import cn.momia.admin.web.entity.SkuPrice;
import cn.momia.admin.web.entity.SkuPropertyValue;
import cn.momia.admin.web.service.ProductService;
import cn.momia.admin.web.service.SkuService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/6/15.
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private ProductService productService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Sku get(int id) {
        String sql = "select id,productId,`desc`,type,properties,prices,`limit`,needRealName,stock,unlockedStock,lockedStock,status,addTime from t_sku where id = ? and status > ? ";
        final Object [] params = new Object[]{id, FinalUtil.DEL_STATUS};
        final Sku entity = new Sku();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setProductId(rs.getInt("productId"));
                entity.setDesc(rs.getString("desc"));
                entity.setType(rs.getInt("type"));
                entity.setProperties(rs.getString("properties"));
                entity.setPrices(rs.getString("prices"));
                entity.setLimit(rs.getInt("limit"));
                entity.setNeedRealName(rs.getInt("needRealName"));
                entity.setStock(rs.getInt("stock"));
                entity.setUnlockedStock(rs.getInt("unlockedStock"));
                entity.setLockedStock(rs.getInt("lockedStock"));
                entity.setStatus(rs.getInt("status"));
                entity.setAddTime(rs.getString("addTime"));
            }
        });

        return entity;
    }

    @Override
    public List<Sku> getEntitys() {
        List<Sku> reData = new ArrayList<Sku>();
        String sql = "select id,productId,`desc`,type,properties,prices,`limit`,needRealName,stock,unlockedStock,lockedStock,status,addTime from t_sku where status > ? order by id desc";
        Object [] params = new Object[]{FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Sku entity = new Sku();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setProductId(Integer.parseInt(list.get(i).get("productId").toString()));
                entity.setDesc(list.get(i).get("desc").toString());
                entity.setType(Integer.parseInt(list.get(i).get("type").toString()));
                entity.setProperties(list.get(i).get("properties").toString());
                entity.setPrices(list.get(i).get("prices").toString());
                entity.setLimit(Integer.parseInt(list.get(i).get("limit").toString()));
                entity.setNeedRealName(Integer.parseInt(list.get(i).get("needRealName").toString()));
                entity.setStock(Integer.parseInt(list.get(i).get("stock").toString()));
                entity.setUnlockedStock(Integer.parseInt(list.get(i).get("unlockedStock").toString()));
                entity.setLockedStock(Integer.parseInt(list.get(i).get("lockedStock").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Sku> getEntitysByKey(int productId) {
        List<Sku> reData = new ArrayList<Sku>();
        String sql = "select id,productId,`desc`,type,properties,prices,`limit`,needRealName,stock,unlockedStock,lockedStock,status,addTime from t_sku where productId = ? and status > ? ";
        Object [] params = new Object[]{productId, FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Sku entity = new Sku();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setProductId(Integer.parseInt(list.get(i).get("productId").toString()));
                entity.setDesc(list.get(i).get("desc").toString());
                entity.setType(Integer.parseInt(list.get(i).get("type").toString()));
                entity.setProperties(list.get(i).get("properties").toString());
                entity.setPrices(list.get(i).get("prices").toString());
                entity.setLimit(Integer.parseInt(list.get(i).get("limit").toString()));
                entity.setNeedRealName(Integer.parseInt(list.get(i).get("needRealName").toString()));
                entity.setStock(Integer.parseInt(list.get(i).get("stock").toString()));
                entity.setUnlockedStock(Integer.parseInt(list.get(i).get("unlockedStock").toString()));
                entity.setLockedStock(Integer.parseInt(list.get(i).get("lockedStock").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insert(Sku entity) {
        String sql = "insert into t_sku(productId,`desc`,type,properties,prices,`limit`,needRealName,stock,unlockedStock,lockedStock,status,addTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getProductId(), entity.getDesc(), entity.getType(), entity.getProperties(), entity.getPrices(), entity.getLimit(), entity.getNeedRealName(), entity.getStock(), entity.getUnlockedStock(), entity.getLockedStock(), FinalUtil.ADD_STATUS};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Sku entity) {
        String sql = "update t_sku set productId = ? , `desc` = ?, type = ? ,  properties = ?, prices = ?, `limit` = ?, needRealName = ?, stock = ?, unlockedStock = ? where id = ? ";
        Object [] params = new Object[]{entity.getProductId(), entity.getDesc(), entity.getType(), entity.getProperties(), entity.getPrices(), entity.getLimit(), entity.getNeedRealName(), entity.getStock(), entity.getUnlockedStock(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update_price(Sku entity) {
        String sql = "update t_sku set prices = ? where id = ? ";
        Object [] params = new Object[]{entity.getPrices(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update t_sku set status = ? where id = ? ";
        Object [] params = new Object[]{FinalUtil.DEL_STATUS,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Sku formEntity(HttpServletRequest req, int id) {
        List<SkuOther> mapls = new ArrayList<SkuOther>();
        SkuOther other = new SkuOther();
        String value = req.getParameter("properties");
        other.setName("time");
        other.setValue(value);
        mapls.add(other);
        String propertyValues = JSONObject.toJSONString(mapls);

        List<SkuPrice> mappricels = new ArrayList<SkuPrice>();
        for (int i = 0; i < 10 ; i++) {
            int intx = i+1;
            String adultx = "adult"+intx;
            String childx = "child"+intx;
            String pricex = "prices"+intx;
            SkuPrice skuPrice = new SkuPrice();
            String adult = req.getParameter(adultx);
            String child = req.getParameter(childx);
            String price = req.getParameter(pricex);
            if (adult == null && child==null && price == null ){
                break;
            }
            if(adult != null && !adult.equals("") && !adult.equals("0")){
                skuPrice.setAdult(adult);
            }
            if(child != null && !child.equals("") && !child.equals("0")){
                skuPrice.setChild(child);
            }
            skuPrice.setPrice(price);
            if (adult == null || adult.equals("") || adult.equals("0") || child == null || child.equals("") || child.equals("0")){
                skuPrice.setUnit("0");
            }else{
                skuPrice.setUnit("1");
            }
            mappricels.add(skuPrice);
        }

        String priceStr = JSONObject.toJSONString(mappricels);
        Sku entity = new Sku();
        entity.setId(id);
        entity.setProductId(Integer.parseInt(req.getParameter("productId")));
        entity.setProperties(propertyValues);
        entity.setPrices(priceStr);
        int stock = Integer.parseInt(req.getParameter("stock"));
        entity.setStock(stock);
        entity.setUnlockedStock(stock);
        entity.setLockedStock(0);
        entity.setDesc(req.getParameter("desc"));
        entity.setType(Integer.parseInt(req.getParameter("type")));
        entity.setLimit(Integer.parseInt(req.getParameter("limit")));
        entity.setNeedRealName(Integer.parseInt(req.getParameter("needRealName")));

        return entity;
    }

    @Override
    public Sku formEntity2(HttpServletRequest req, int id) {
        List<SkuPrice> mappricels = null;
        String price_hid = this.get(id).getPrices();
        if(price_hid != null && !price_hid.equals("")){
            mappricels = getSkuPrices(price_hid);
        }else{
            mappricels = new ArrayList<SkuPrice>();
        }

        SkuPrice skuPrice = new SkuPrice();
        String adult = req.getParameter("adult1");
        if(adult != null && !adult.equals("") && !adult.equals("0")){
            skuPrice.setAdult(adult);
        }
        String child = req.getParameter("child1");
        if(child != null && !child.equals("") && !child.equals("0")){
            skuPrice.setChild(child);
        }
        String prices = req.getParameter("prices1");
        skuPrice.setPrice(prices);
        if (adult == null || adult.equals("") || adult.equals("0") || child == null || child.equals("") || child.equals("0")){
            skuPrice.setUnit("0");
        }else{
            skuPrice.setUnit("1");
        }
        mappricels.add(skuPrice);
        String priceStr = JSONObject.toJSONString(mappricels);



        Sku entity = new Sku();
        entity.setId(id);
        entity.setPrices(priceStr);
        return entity;
    }

    private List<SkuPrice> getSkuPrices(String jsonPrices){
        List<SkuPrice> ls = new ArrayList<SkuPrice>();
        List<Map<String,Object>> list = StringUtil.parseJSON2List(jsonPrices);
        for (int i = 0; i < list.size(); i++) {
            SkuPrice entity = new SkuPrice();
            if (list.get(i).get("adult") != null && !list.get(i).get("adult").equals("")){
                entity.setAdult(list.get(i).get("adult").toString());
            }else{
                entity.setAdult("0");
            }
            if (list.get(i).get("child") != null && !list.get(i).get("child").equals("")){
                entity.setChild(list.get(i).get("child").toString());
            }else{
                entity.setChild("0");
            }
            entity.setPrice(list.get(i).get("price").toString());
            entity.setUnit(list.get(i).get("unit").toString());
            ls.add(entity);
        }
        return ls;
    }


    @Override
    public String getPricesMap(String jsonPrices){
        StringBuffer sb = new StringBuffer();
        List<Map<String,Object>> list = StringUtil.parseJSON2List(jsonPrices);
        System.out.print(list.size());
        for (int i = 0; i < list.size(); i++) {
            int intx = i+1;
            String adult = "adult"+intx;
            String child = "child"+intx;
            String price = "prices"+intx;
            String adult_value = "";
            String child_value = "";
            String price_value = list.get(i).get("price").toString();
            if(list.get(i).get("adult") != null && !list.get(i).get("adult").equals("")){
                adult_value = list.get(i).get("adult").toString();
            }
            if(list.get(i).get("child") != null && !list.get(i).get("child").equals("")){
                child_value = list.get(i).get("child").toString();
            }
            sb.append("<div class='controls'>");
            sb.append("成人<input class='input-xlarge focused' id='"+adult+"' name='"+adult+"' type='text' value='"+adult_value+"'><br>");
            sb.append("孩子<input class='input-xlarge focused' id='"+child+"' name='"+child+"' type='text' value='"+child_value+"'><br>");
            sb.append("价格<input class='input-xlarge focused' id='"+price+"' name='"+price+"' type='text' value='"+price_value+"'><br>");
            sb.append("</div><br>");
        }
        return sb.toString();
    }

    @Override
    public String getProperties(String jsonProperties){
        List<SkuOther> others = new ArrayList<SkuOther>();
        List<Map<String,Object>> list = StringUtil.parseJSON2List(jsonProperties);
        for (int i = 0; i < list.size(); i++) {
            SkuOther entity = new SkuOther();
            entity.setValue(list.get(i).get("value").toString());
            entity.setName(list.get(i).get("name").toString());
            others.add(entity);
        }
        return others.get(0).getValue();
    }

    @Override
    public SkuPrice getPricesMinValue(String jsonPrices){
        SkuPrice skuPrice = new SkuPrice();
        if (jsonPrices != null && !jsonPrices.equals("")){
            List<Map<String,Object>> list = StringUtil.parseJSON2List(jsonPrices);
            List<Map<String,Object>> templs = null;
            if (list.size() == 1){
                templs = new ArrayList<Map<String, Object>>();
                templs.add(list.get(0));
            }else{
                float temp = 0 ;
                for (int i = 0; i < list.size() ; i++) {
                    float price1 = Float.parseFloat(list.get(i).get("price").toString());
                    for (int j = 1; j < list.size() ; j++) {
                        float price2 = Float.parseFloat(list.get(j).get("price").toString());
                        if (i == 0) {
                            if (price1 < price2) {
                                temp = price1;
                                templs = new ArrayList<Map<String, Object>>();
                                templs.add(list.get(i));
                            } else {
                                temp = price2;
                                templs = new ArrayList<Map<String, Object>>();
                                templs.add(list.get(j));
                            }
                        }else{
                            if (temp > price2){
                                temp = price2;
                                templs = new ArrayList<Map<String, Object>>();
                                templs.add(list.get(j));
                            }
                        }
                    }
                }
            }
            if (templs.get(0).get("adult")!=null && !templs.get(0).get("adult").equals("")){
                skuPrice.setAdult(templs.get(0).get("adult").toString());
            }else{
                skuPrice.setAdult("0");
            }
            if (templs.get(0).get("child")!=null && !templs.get(0).get("child").equals("")){
                skuPrice.setChild(templs.get(0).get("child").toString());
            }else{
                skuPrice.setChild("0");
            }
            skuPrice.setPrice(templs.get(0).get("price").toString());
            skuPrice.setUnit(templs.get(0).get("unit").toString());
        }

        return skuPrice;
    }

    @Override
    public List<Sku> getEntities(List<Sku> skus){
        if(skus.size() > 0){
            for (int i = 0; i < skus.size(); i++) {
                Sku entity = skus.get(i);
                if (entity.getProperties() != null && !entity.getProperties().equals("")){
                    entity.setPropertiesValue(this.getProperties(entity.getProperties()));
                }

                if (entity.getPrices() != null && !entity.getPrices().equals("")) {
                    entity.setSkuPrices(this.getSkuPrices(entity.getPrices()));
                }

                entity.setProducttitle(productService.get(entity.getProductId()).getTitle());
            }
        }

        return skus;
    }

    @Override
    public List<Sku> getQueryPages(int start_row,int end_row) {
        List<Sku> reData = new ArrayList<Sku>();
        String sql = "select id,productId,`desc`,type,properties,prices,`limit`,needRealName,stock,unlockedStock,lockedStock,status,addTime from t_sku where status > ? order by id desc limit "+start_row+","+end_row;
        Object [] params = new Object[]{FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Sku entity = new Sku();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setProductId(Integer.parseInt(list.get(i).get("productId").toString()));
                entity.setDesc(list.get(i).get("desc").toString());
                entity.setType(Integer.parseInt(list.get(i).get("type").toString()));
                entity.setProperties(list.get(i).get("properties").toString());
                entity.setPrices(list.get(i).get("prices").toString());
                entity.setLimit(Integer.parseInt(list.get(i).get("limit").toString()));
                entity.setNeedRealName(Integer.parseInt(list.get(i).get("needRealName").toString()));
                entity.setStock(Integer.parseInt(list.get(i).get("stock").toString()));
                entity.setUnlockedStock(Integer.parseInt(list.get(i).get("unlockedStock").toString()));
                entity.setLockedStock(Integer.parseInt(list.get(i).get("lockedStock").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }
}
