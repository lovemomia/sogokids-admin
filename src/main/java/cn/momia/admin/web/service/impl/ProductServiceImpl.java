package cn.momia.admin.web.service.impl;

import cn.momia.admin.web.common.FinalUtil;
import cn.momia.admin.web.common.StringUtil;
import cn.momia.admin.web.entity.Category;
import cn.momia.admin.web.entity.ContentBody;
import cn.momia.admin.web.entity.ProductContent;
import cn.momia.admin.web.service.ContentBodyService;
import cn.momia.admin.web.service.ProductContentService;
import cn.momia.common.config.Configuration;
import cn.momia.admin.web.entity.City;
import cn.momia.admin.web.entity.Content;
import cn.momia.admin.web.entity.DataBean;
import cn.momia.admin.web.entity.Images;
import cn.momia.admin.web.entity.Place;
import cn.momia.admin.web.entity.Product;
import cn.momia.admin.web.entity.ProductImg;
import cn.momia.admin.web.entity.Sku;
import cn.momia.admin.web.entity.SkuPrice;
import cn.momia.admin.web.service.CategoryService;
import cn.momia.admin.web.service.CityService;
import cn.momia.admin.web.service.ImagesService;
import cn.momia.admin.web.service.PlaceService;
import cn.momia.admin.web.service.ProductImgService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.StringBuffer;

/**
 * Created by hoze on 15/6/15.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductImgService productImgService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ImagesService imagesService;

    @Autowired
    private Configuration configuration;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductContentService productContentService;

    @Autowired
    private ContentBodyService contentBodyService;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product get(int id) {
        String sql = "select id,cityId,categoryId,tags,placeId,title,abstracts,thumb,cover,crowd,content,sales,startTime,endTime,onlineTime,offlineTime,status,addTime from t_product where id = ? and status > ? ";
        final Object [] params = new Object[]{id, FinalUtil.DEL_STATUS};
        final Product entity = new Product();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCityId(rs.getInt("cityId"));
                entity.setCategoryId(rs.getInt("categoryId"));
                entity.setTags(rs.getString("tags"));
                entity.setPlaceId(rs.getInt("placeId"));
                entity.setThumb(rs.getString("thumb"));
                entity.setCover(rs.getString("cover"));
                entity.setCrowd(rs.getString("crowd"));
                entity.setTitle(rs.getString("title"));
                entity.setAbstracts(rs.getString("abstracts"));
                entity.setContent(rs.getString("content"));
                entity.setSales(rs.getInt("sales"));
                entity.setStartTime(rs.getString("startTime"));
                entity.setEndTime(rs.getString("endTime"));
                entity.setOnlineTime(rs.getString("onlineTime"));
                entity.setOfflineTime(rs.getString("offlineTime"));
                entity.setStatus(rs.getInt("status"));
                entity.setAddTime(rs.getString("addTime"));
            }
        });

        return entity;
    }

    @Override
    public List<Product> getEntitys() {
        List<Product> reData = new ArrayList<Product>();
        String sql = "select id,cityId,categoryId,tags,placeId,title,abstracts,thumb,cover,crowd,content,sales,startTime,endTime,onlineTime,offlineTime,status,addTime from t_product where status > ? order by id desc";
        Object [] params = new Object[]{FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Product entity = new Product();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("cityId").toString()));
                entity.setCategoryId(Integer.parseInt(list.get(i).get("categoryId").toString()));
                entity.setTags(list.get(i).get("tags").toString());
                entity.setPlaceId(Integer.parseInt(list.get(i).get("placeId").toString()));
                entity.setTitle(list.get(i).get("title").toString());
                entity.setAbstracts(list.get(i).get("abstracts").toString());
                entity.setThumb(list.get(i).get("thumb").toString());
                entity.setCover(list.get(i).get("cover").toString());
                entity.setCrowd(list.get(i).get("crowd").toString());
                entity.setContent(list.get(i).get("content").toString());
                entity.setSales(Integer.parseInt(list.get(i).get("sales").toString()));
                entity.setStartTime(list.get(i).get("startTime").toString());
                entity.setEndTime(list.get(i).get("endTime").toString());
                entity.setOnlineTime(list.get(i).get("onlineTime").toString());
                entity.setOfflineTime(list.get(i).get("offlineTime").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Product> getEntitysByKey(int categoryId) {
        List<Product> reData = new ArrayList<Product>();
        String sql = "select id,cityId,categoryId,tags,placeId,title,abstracts,thumb,cover,crowd,content,sales,startTime,endTime,onlineTime,offlineTime,status,addTime from t_product where categoryId = ? and status > ? ";
        Object [] params = new Object[]{categoryId, FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Product entity = new Product();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("cityId").toString()));
                entity.setCategoryId(Integer.parseInt(list.get(i).get("categoryId").toString()));
                entity.setTags(list.get(i).get("tags").toString());
                entity.setPlaceId(Integer.parseInt(list.get(i).get("placeId").toString()));
                entity.setTitle(list.get(i).get("title").toString());
                entity.setAbstracts(list.get(i).get("abstracts").toString());
                entity.setThumb(list.get(i).get("thumb").toString());
                entity.setCover(list.get(i).get("cover").toString());
                entity.setCrowd(list.get(i).get("crowd").toString());
                entity.setContent(list.get(i).get("content").toString());
                entity.setSales(Integer.parseInt(list.get(i).get("sales").toString()));
                entity.setStartTime(list.get(i).get("startTime").toString());
                entity.setEndTime(list.get(i).get("endTime").toString());
                entity.setOnlineTime(list.get(i).get("onlineTime").toString());
                entity.setOfflineTime(list.get(i).get("offlineTime").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insert(Product entity) {
        String sql = "insert into t_product(cityId,categoryId,tags,placeId,title,abstracts,thumb,cover,crowd,content,sales,startTime,endTime,onlineTime,offlineTime,status,addTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCityId(),entity.getCategoryId(), entity.getTags(), entity.getPlaceId(), entity.getTitle(), entity.getAbstracts(), entity.getThumb(), entity.getCover(), entity.getCrowd(), "", FinalUtil.ADD_INFO, entity.getStartTime(),entity.getEndTime(), entity.getOnlineTime(), entity.getOfflineTime(), FinalUtil.OFFLINE_STATUS};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Product entity) {
        String sql = "update t_product set cityId = ?, categoryId = ?, tags = ?, placeId = ?, title = ?, abstracts = ?, thumb = ?, cover = ?, crowd = ?, startTime = ?, endTime = ?, onlineTime = ?, offlineTime = ? where id = ? ";
        Object [] params = new Object[]{entity.getCityId(), entity.getCategoryId(), entity.getTags(), entity.getPlaceId(), entity.getTitle(), entity.getAbstracts(), entity.getThumb(), entity.getCover(), entity.getCrowd(), entity.getStartTime(), entity.getEndTime(), entity.getOnlineTime(), entity.getOfflineTime(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update_content(int pid,String contentJson) {
        String sql = "update t_product set content = ? where id = ? ";
        Object [] params = new Object[]{contentJson,pid};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update t_product set status = ? where id = ? ";
        Object [] params = new Object[]{FinalUtil.DEL_STATUS,id};
        int reData = jdbcTemplate.update(sql,params);
        if (reData > 0) {
            String sql_imgs = "update t_product_img set status = ? where productId = ? ";
            Object[] params_imgs = new Object[]{FinalUtil.DEL_STATUS, id};
            jdbcTemplate.update(sql_imgs, params_imgs);

            String sql_sku = "update t_sku set status = ? where productId = ? ";
            Object[] params_sku = new Object[]{FinalUtil.DEL_STATUS, id};
            jdbcTemplate.update(sql_sku, params_sku);
        }
        return reData;
    }

    @Override
    public int update_releaseOroffline(int id, int status) {
        String sql = "update t_product set status = ? where id = ? ";
        Object [] params = new Object[]{status,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Product formEntity(HttpServletRequest request, int id) {
        Product entity = new Product();
        entity.setId(id);
        entity.setCityId(Integer.parseInt(request.getParameter("cityId")));
        entity.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        entity.setPlaceId(Integer.parseInt(request.getParameter("placeId")));
        entity.setTitle(request.getParameter("title"));
        entity.setAbstracts(request.getParameter("abstracts"));
        entity.setThumb(request.getParameter("thumb"));
        entity.setCover(request.getParameter("cover"));
        entity.setCrowd(request.getParameter("crowd"));
        entity.setStartTime(request.getParameter("startTime"));
        entity.setEndTime(request.getParameter("endTime"));
        entity.setOnlineTime(request.getParameter("onlineTime"));
        entity.setOfflineTime(request.getParameter("offlineTime"));
        String [] tags = request.getParameterValues("tag");
        String temp = "";
        if (null != tags && tags.length > 0){
            for (int i = 0; i < tags.length; i++) {
                temp = temp + "," + tags[i];
            }
            entity.setTags(temp.substring(1,temp.length()));
        }else{
            entity.setTags("");
        }

        return entity;
    }

    /**
     * 获取内容Content 的Json的字符串 －－title,style,body
     * @param req
     * @param contentJson
     * @param flag
     * @return
     */
    @Override
    public String getContentJsonStr(HttpServletRequest req, String contentJson, int flag){
        List<DataBean> ls = new ArrayList<DataBean>();
        if (flag == 1){
            if (contentJson != null && !contentJson.equals("")){
                ls.addAll(getDataBean(contentJson));
            }
        }

        int contentsize = Integer.parseInt(req.getParameter("contentsize"));
        for (int j = 0; j < contentsize; j++) {

            DataBean bean = new DataBean();
            int intx = j+1;
            String titleid = "title" + intx;
            String styleid = "style" + intx;
            String title = req.getParameter(titleid);
            String style = req.getParameter(styleid);
            if (title == null || title.equals("")){
                continue;
            }else {
                bean.setTitle(title);
                bean.setStyle(style);
                bean.setBody(getBodys(req, intx));
                ls.add(bean);
            }
        }

        return JSONObject.toJSONString(ls);
    }

    /**
     * 获取内容Content 的Json的字符串 －－body
     * @param req
     * @param intx
     * @return
     */
    public List<Content> getBodys(HttpServletRequest req,int intx){
        List<Content> ls_c = new ArrayList<Content>();

        for (int i = 1; i < 10; i++) {
            Content content = new Content();

            String texti = "text" + intx + i;
            String labeli = "label" + intx + i;
            String linki = "link" + intx + i;
            String imagesi = "images" + intx + i;
            String parami = "param" + intx + i;
            String param = req.getParameter(parami);
            if (param.equals("0")){
                String text = req.getParameter(texti);
                content.setText(text);
            }else if (param.equals("1")){
                String text = req.getParameter(texti);
                content.setText(text);
                String label = req.getParameter(labeli);
                content.setLabel(label);
            }else if (param.equals("2")){
                String text = req.getParameter(texti);
                content.setText(text);
                String link = req.getParameter(linki);
                content.setLink(link);
            }else{
                String text = req.getParameter(texti);
                content.setText(text);
                String images = req.getParameter(imagesi);
                Images imgs = imagesService.uploadImg(req,1,images);
                if (imgs.getUrl() == null || imgs.getUrl().trim().equals("")){
                    String hidparami = "hidparam" + intx + i;
                    String url = req.getParameter(hidparami);
                    content.setImg(url);
                }else{
                    content.setImg(imgs.getUrl());
                }
            }
            if((content.getText() != null && !content.getText().trim().equals(""))
                    || (content.getLabel() != null && !content.getLabel().trim().equals(""))
                    || (content.getLink() != null && !content.getLink().trim().equals(""))
                    || (content.getImg() != null && !content.getImg().equals(""))){
                ls_c.add(content);
            }
        }
        return ls_c;
    }

    /**
     * 修改图片开始
     * @param //contentJson
     * @return
     */
    @Override
    public Map<String, String> getContentJsontoMap(String contentJson) {
        Map<String, String> reData = new HashMap<String, String>();
        List<DataBean> ls = new ArrayList<DataBean>();
        StringBuffer hidden = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        StringBuffer buffer = new StringBuffer();
        if (contentJson != null && !contentJson.equals("")) {
            ls = getDataBean(contentJson);
            hidden.append("<input id='contentsize' name='contentsize' type='hidden' value='" + ls.size() + "'>");
            reData.put("hidden", hidden.toString());
            for (int i = 0; i < ls.size(); i++) {
                DataBean entity = ls.get(i);
                int intx = i + 1;
                String hrefname = "#tabs-" + intx;
                sb.append("<li><a href='" + hrefname + "'>" + entity.getTitle() + "</a></li>");
                buffer.append(getTitleAndStyle(entity,intx));
            }

            reData.put("tabsli", sb.toString());
            reData.put("contentstr", buffer.toString());
        }
        return reData;
    }

    /**
     * 根据content信息组装标题(title)和样式(style)
     * @param entity
     * @param intx
     * @return
     */
    public String getTitleAndStyle(DataBean entity,int intx){
        StringBuffer buffer = new StringBuffer();
        String idname = "tabs-" + intx;
        buffer.append("<div id='" + idname + "' style='width:90%'>");
        buffer.append("<div style='border:1px solid #000000'>");
        buffer.append("<div class='control-group'>");
        buffer.append("<div class='controls'>");
        String titleid = "title" + intx;
        buffer.append("标题 :<input id='" + titleid + "' name='" + titleid + "' value='" + entity.getTitle() + "'><br>");
        buffer.append("</div>");
        buffer.append("<div class='controls'>");
        String styleid = "style" + intx;
        buffer.append("样式:<select id='" + styleid + "' name='" + styleid + "'>");
        if (entity.getStyle().equals("ol")) {
            buffer.append("<option value='ol' selected>ol</option>");
            buffer.append("<option value='ul' >ul</option>");
            buffer.append("<option value='none' >none</option>");
        }else if (entity.getStyle().equals("ul")) {
            buffer.append("<option value='ol' >ol</option>");
            buffer.append("<option value='ul' selected>ul</option>");
            buffer.append("<option value='none' >none</option>");
        }else{
            buffer.append("<option value='ol' >ol</option>");
            buffer.append("<option value='ul' >ul</option>");
            buffer.append("<option value='none' selected>none</option>");
        }
        buffer.append("</select>");
        buffer.append("</div>");
        buffer.append("</div>");
        buffer.append(getContentJsonStr(entity.getBody(), intx));
        buffer.append("</div>");
        buffer.append("</div>");
        return buffer.toString();

    }

    /**
     *根据content列表组装修改页面的标签及内容
     * @param ls
     * @param intx
     * @return
     */
    public String getContentJsonStr(List<Content> ls,int intx){
        StringBuffer sb = new StringBuffer();
        sb.append("<div class='control-group' style='border:1px solid #0000ff'>");
        for (int i = 0; i < ls.size(); i++) {
            Content entity = ls.get(i);
            int intnum = i + 1;
            String textid = "text" + intx + intnum;
            String labelid = "label" + intx + intnum;
            String linkid = "link" + intx + intnum;
            String imgid = "fileurl" + intx + intnum;
            String imgname = "images" + intx + intnum;
            String param = "param" + intx + intnum;

            String imgparam = "imgparam" + intx + intnum;
            String hidparam = "hidparam" + intx + intnum;

            sb.append("<div class='controls' style='border:1px solid #ff0000'>");
            if (entity.getLabel() != null && !entity.getLabel().equals("")){
                sb.append("控件:<select id='"+param+"' name='"+param+"'>");
                sb.append("<option value='0' >内容</option>");
                sb.append("<option value='1' selected>标签</option>");
                sb.append("<option value='2' >链接</option>");
                sb.append("<option value='3' >图片</option>");
                sb.append("</select><br>");
            }else if (entity.getLink() != null && !entity.getLink().equals("")){
                sb.append("控件:<select id='"+param+"' name='"+param+"'>");
                sb.append("<option value='0' >内容</option>");
                sb.append("<option value='1' >标签</option>");
                sb.append("<option value='2' selected>链接</option>");
                sb.append("<option value='3' >图片</option>");
                sb.append("</select><br>");
            }else if (entity.getImg() != null && !entity.getImg().equals("")){
                sb.append("控件:<select id='"+param+"' name='"+param+"'>");
                sb.append("<option value='0' >内容</option>");
                sb.append("<option value='1' >标签</option>");
                sb.append("<option value='2' >链接</option>");
                sb.append("<option value='3' selected>图片</option>");
                sb.append("</select><br>");
            }else {
                sb.append("控件:<select id='"+param+"' name='"+param+"'>");
                sb.append("<option value='0' selected>内容</option>");
                sb.append("<option value='1' >标签</option>");
                sb.append("<option value='2' >链接</option>");
                sb.append("<option value='3' >图片</option>");
                sb.append("</select><br>");
            }

            if(entity.getLabel() == null) {
                sb.append("标签:<input id='" + labelid + "' name='" + labelid + "' value=''><br>");
            }else{
                sb.append("标签:<input id='" + labelid + "' name='" + labelid + "' value='" + entity.getLabel() + "'><br>");
            }

            if(entity.getText() == null) {
                sb.append("内容:<textarea id='"+textid+"' name='"+textid+"' rows='3' cols='5' ></textarea><br>");
            }else{
                sb.append("内容:<textarea id='"+textid+"' name='"+textid+"' rows='3' cols='5' >"+entity.getText()+"</textarea><br>");
            }

            if(entity.getLink() == null) {
                sb.append("链接:<input id='"+linkid+"' name='"+linkid+"' value=''><br>");
            }else{
                sb.append("链接:<input id='"+linkid+"' name='"+linkid+"' value='"+entity.getLink()+"'><br>");
            }

            if(entity.getImg() == null) {
                sb.append("图片:<input id='"+imgid+"' name='"+imgid+"' type='file'>未上传");
                sb.append("<input id='"+imgname+"' name='"+imgname+"' type='hidden' value='"+imgid+"' ><br>");
            }else{
                String url = entity.getImg();
                if (!url.contains("http")){
                    url = configuration.getString(FinalUtil.DISPLAY_IMAGE) + url;
                }
                sb.append("图片:<input id='"+imgid+"' name='"+imgid+"' type='file'><br>");
                sb.append("<img id='"+imgparam+"' src='"+url+"' height='100ps' width='250ps'></img>");
                sb.append("<input id='"+hidparam+"' name='"+hidparam+"' type='hidden' value='"+entity.getImg()+"' ><br>");
                sb.append("<input id='"+imgname+"' name='"+imgname+"' type='hidden' value='"+imgid+"' ><br>");
            }

            sb.append("</div><br>");
        }
        sb.append(getFillContent(intx,ls.size()));

        sb.append("</div>");
        return sb.toString();
    }

    /**
     * 根据每个标签的已有数量，补充够9个总数
     * @param intx
     * @param intsize
     * @return
     */
    public String getFillContent(int intx,int intsize){

        StringBuffer sb = new StringBuffer();
        int inty = 9 - intsize;
        int intz = intsize + 1;
        if (inty > 0) {
            for (int j = intz; j < 10; j++) {
                String textid = "text" + intx + j;
                String labelid = "label" + intx + j;
                String linkid = "link" + intx + j;
                String imgid = "fileurl" + intx + j;
                String imgname = "images" + intx + j;
                String param = "param" + intx + j;
                sb.append("<div class='controls' style='border:1px solid #ff0000'>");

                sb.append("控件:<select id='"+param+"' name='"+param+"'>");
                sb.append("<option value='0' selected>内容</option>");
                sb.append("<option value='1' >标签</option>");
                sb.append("<option value='2' >链接</option>");
                sb.append("<option value='3' >图片</option>");
                sb.append("</select><br>");

                sb.append("标签:<input id='"+labelid+"' name='"+labelid+"' value=''><br>");
                sb.append("内容:<textarea id='"+textid+"' name='"+textid+"' rows='3' cols='5' ></textarea><br>");
                sb.append("链接:<input id='"+linkid+"' name='"+linkid+"' value=''><br>");
                sb.append("图片:<input id='"+imgid+"' name='"+imgid+"' type='file'>未上传");
                sb.append("<input id='"+imgname+"' name='"+imgname+"' type='hidden' value='"+imgid+"' ><br>");

                sb.append("</div><br>");
            }
        }

        return sb.toString();
    }


    /**
     * 预览活动信息
     * @param id
     * @return
     */
    @Override
    public String getPreviewInfo(int id){
        Product product = this.get(id);

        StringBuffer reData = new StringBuffer();
        //标题
        reData.append("<tr><p><h2>");
        reData.append(product.getTitle());
        reData.append("</h2></p></tr>");
        //图片
        reData.append("<tr>");
        List<ProductImg> imgs = productImgService.getEntitysByKey(id);
        if (imgs.size()>0) {
            for (int i = 0; i < imgs.size(); i++) {
                int intx = i + 1;
                String img_id = "img" + intx;
                String imgurl = imgs.get(i).getUrl();
                if(!imgurl.contains("http")){
                    imgurl = configuration.getString(FinalUtil.DISPLAY_IMAGE) + imgurl;
                }
                reData.append("<p><img id='" + img_id + "' src='" +imgurl+ "'></img><br></p>");
            }
        }else{
            reData.append("<p><img id='img1' src='' alt = '没有图片'></img><br></p>");
        }
        reData.append("</tr>");

        Place place = placeService.get(product.getPlaceId());
        Category category = categoryService.get(product.getCategoryId());
        City city = cityService.get(product.getCityId());

        List<Sku> sku = skuService.getEntitysByKey(id);
        String skuProperties ="";
        String skuprice = "0.0";
        SkuPrice skuPriceEntity = null;
        if (sku.size() > 0){
            skuPriceEntity = skuService.getPricesMinValue(sku.get(0).getPrices());
            skuprice = skuPriceEntity.getPrice();
            skuProperties = skuService.getProperties(sku.get(0).getProperties());
        }

        reData.append("<tr><p><h2><font color='red'>");
        reData.append("￥"+skuprice);
        reData.append("</font></h2></p></tr>");

        reData.append("<tr><p><h3>");
        reData.append(product.getCrowd());
        reData.append("</h3></p></tr>");

        reData.append("<tr><p><h3>");
        reData.append("已购买："+product.getSales());
        reData.append("</h3></p></tr>");

        reData.append("<tr><p><h3>");
        reData.append(skuProperties);
        reData.append("</h3></p></tr>");

        reData.append("<tr><p><h3>");
        reData.append(place.getAddress());
        reData.append("</h3></p></tr>");

        List<DataBean> beans = getDataBean(product.getContent());
        for (int j = 0; j < beans.size(); j++) {
            reData.append("<tr>");
            DataBean bean = beans.get(j);
            List<Content> cons = beans.get(j).getBody();
            reData.append("<p><h3><strong>"+bean.getTitle()+"</strong></h3></p>");
            for (int k = 0; k < cons.size(); k++) {
                Content con = cons.get(k);
                String text = con.getText();
                String label = con.getLabel();
                String link = con.getLink();
                String img = con.getImg();
                if(label != null && !label.equals("")){
                    reData.append("<p>");
                    reData.append(label+":"+text);
                    reData.append("</p>");
                }else  if(link != null && !link.equals("")){
                    reData.append("<p><a href="+link+" >"+text+"</a></p>");
                }else if(img != null && !img.equals("")){
                    if(text != null && !text.equals("")){
                        reData.append(text);
                        reData.append("<br>");
                    }
                    if(!img.contains("http")){
                        img = configuration.getString(FinalUtil.DISPLAY_IMAGE) + img;
                    }
                    reData.append("<img id='drimg"+k+"' src='"+ img +"'></img><br>");
                    reData.append("<br>");
                }else{
                    if(text != null && !text.equals("")){
                        reData.append("<p>");
                        reData.append(text);
                        reData.append("</p>");
                    }
                }
            }

        }
        return reData.toString();
    }

    /**
     * 获取内容title/style/body列表
     * @param contentJson
     * @return
     */
    public List<DataBean> getDataBean(String contentJson){
        List<DataBean> ls = new ArrayList<DataBean>();
        List<Map<String,Object>> list = StringUtil.parseJSON2List(contentJson);
        for (int i = 0; i < list.size(); i++) {
            DataBean entity = new DataBean();
            entity.setTitle(list.get(i).get("title").toString());
            entity.setStyle(list.get(i).get("style").toString());
            entity.setBody(getContentBean((List<Map<String, String>>) list.get(i).get("body")));
            ls.add(entity);
        }

        return ls;
    }

    /**
     * 获取body数据列表
     * @param body
     * @return
     */
    public List<Content> getContentBean(List<Map<String,String>> body){
        List<Content> ls = new ArrayList<Content>();
        if (body != null) {
            for (int i = 0; i < body.size(); i++) {
                Content entity = new Content();
                entity.setText(body.get(i).get("text"));
                entity.setLink(body.get(i).get("link"));
                entity.setImg(body.get(i).get("img"));
                entity.setLabel(body.get(i).get("label"));
                ls.add(entity);
            }
        }

        return ls;
    }

    /**
     * 根据活动列表，填充其他相关数据
     * @param products
     * @return
     */
    @Override
    public List<Product> getEntities(List<Product> products){
        if(products.size() > 0){
            for (int i = 0; i <products.size() ; i++) {
                Product entity = products.get(i);
                entity.setCityname(cityService.get(entity.getCityId()).getName());
                entity.setCatename(categoryService.get(entity.getCategoryId()).getName());
                entity.setPlacename(placeService.get(entity.getPlaceId()).getName());
                List<ProductImg> ls_imgs = new ArrayList<ProductImg>();
                ls_imgs = productImgService.getEntitysByKey(entity.getId());
                entity.setImgs(ls_imgs);
                List<Sku> ls = skuService.getEntitysByKey(entity.getId());
                if (ls.size() > 0){
                    entity.setSkustatus(1);
                }else{
                    entity.setSkustatus(0);
                }
            }
        }

        return products;
    }

    @Override
    public int updateContentJson(int pid){
        List<DataBean> bean_ls = new ArrayList<DataBean>();
        List<ProductContent> ls = productContentService.getEntitysByProductId(pid,FinalUtil.SQL_QUERY_SORT_ASC);
        if (ls.size() > 0){
            for (int i = 0; i < ls.size(); i++) {
                DataBean bean = new DataBean();
                bean.setTitle(ls.get(i).getTitle());
                bean.setStyle(ls.get(i).getStyle());
                List<ContentBody> body_ls = contentBodyService.getEntitysByContentId(ls.get(i).getId(),FinalUtil.SQL_QUERY_SORT_ASC);
                List<Content> con_ls = new ArrayList<Content>();
                if (body_ls.size() > 0){
                    for (int j = 0; j < body_ls.size(); j++) {
                        Content con = new Content();
                        int type = body_ls.get(j).getType();
                        if(type == 0){
                            con.setText(body_ls.get(j).getText());
                        }
                        if(type == 1){
                            con.setLabel(body_ls.get(j).getLabel());
                            con.setText(body_ls.get(j).getText());
                        }
                        if(type == 2){
                            con.setLink(body_ls.get(j).getLink());
                            con.setText(body_ls.get(j).getText());
                        }
                        if(type == 3){
                            con.setImg(body_ls.get(j).getImg());
                        }
                        con_ls.add(con);
                    }
                }
                bean.setBody(con_ls);
                bean_ls.add(bean);
            }
        }

        return update_content(pid, JSONObject.toJSONString(bean_ls));
    }

    /**
     * 历史数据进行修改
     * @param pid
     */
    @Override
    public void updateData(int pid){
        Product product = this.get(pid);
        List<ProductContent> ls = productContentService.getEntitysByProductId(pid,FinalUtil.SQL_QUERY_SORT_DESC);
        if (ls.size() > 0){}else{
            if(!product.getContent().equals("") && product.getContent().length() > 10){
                List<DataBean> data_ls = getDataBean(product.getContent());
                for (int i = 0; i < data_ls.size(); i++) {
                    ProductContent content = new ProductContent();
                    content.setTitle(data_ls.get(i).getTitle());
                    content.setStyle(data_ls.get(i).getStyle());
                    content.setProductId(pid);

                    int content_id = productContentService.insertKey(content);
                    List<Content> body_ls = data_ls.get(i).getBody();
                    for (int j = 0; j < body_ls.size(); j++) {
                        ContentBody body = new ContentBody();
                        String img = body_ls.get(j).getImg();
                        String text = body_ls.get(j).getText();
                        String label = body_ls.get(j).getLabel();
                        String link = body_ls.get(j).getLink();
                        body.setContentId(content_id);
                        if (img != null && !img.equals("")) {
                            body.setType(3);
                            body.setImg(img);
                        }
                        if (text != null && !text.equals("")) {
                            if (label != null && !label.equals("")) {
                                body.setType(1);
                                body.setLabel(label);
                                body.setText(text);
                            }else if (link != null && !link.equals("")) {
                                body.setType(2);
                                body.setLink(link);
                                body.setText(text);
                            } else {
                                body.setType(0);
                                body.setText(text);
                            }
                        }
                        contentBodyService.insert(body);
                    }
                }
            }
        }
    }

    /**
     * 活动分页方法
     * @param start_row
     * @param end_row
     * @return
     */
    @Override
    public List<Product> getQueryPages(int start_row,int end_row) {
        List<Product> reData = new ArrayList<Product>();
        String sql = "select id,cityId,categoryId,placeId,title,thumb,cover,crowd,content,sales,startTime,endTime,onlineTime,offlineTime,status,addTime from t_product where status > ? order by id desc limit "+start_row+","+end_row;
        Object [] params = new Object[]{FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Product entity = new Product();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("cityId").toString()));
                entity.setCategoryId(Integer.parseInt(list.get(i).get("categoryId").toString()));
                entity.setPlaceId(Integer.parseInt(list.get(i).get("placeId").toString()));
                entity.setTitle(list.get(i).get("title").toString());
                entity.setThumb(list.get(i).get("thumb").toString());
                entity.setCover(list.get(i).get("cover").toString());
                entity.setCrowd(list.get(i).get("crowd").toString());
                entity.setContent(list.get(i).get("content").toString());
                entity.setStartTime(list.get(i).get("startTime").toString());
                entity.setEndTime(list.get(i).get("endTime").toString());
                entity.setOnlineTime(list.get(i).get("onlineTime").toString());
                entity.setOfflineTime(list.get(i).get("offlineTime").toString());
                entity.setSales(Integer.parseInt(list.get(i).get("sales").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }
}
