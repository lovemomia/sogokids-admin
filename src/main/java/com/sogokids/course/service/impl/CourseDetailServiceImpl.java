package com.sogokids.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.course.model.CourseDetail;
import com.sogokids.course.service.CourseDetailService;
import com.sogokids.system.service.PlaceService;
import com.sogokids.utils.entity.Content;
import com.sogokids.utils.entity.Detail;
import com.sogokids.utils.entity.DetailContent;
import com.sogokids.utils.util.FileUtil;
import com.sogokids.utils.util.Quantity;
import com.sogokids.utils.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/10/26.
 */
@Service
public class CourseDetailServiceImpl implements CourseDetailService {

    @Autowired
    private PlaceService placeService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CourseDetail> getEntitys() {
        List<CourseDetail> reData = new ArrayList<CourseDetail>();
        String sql = "select Id,CourseId,Abstracts,Detail,Status,AddTime from SG_CourseDetail where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseDetail entity = new CourseDetail();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setAbstracts(list.get(i).get("Abstracts").toString());
                entity.setDetail(list.get(i).get("Detail").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<CourseDetail> getModelsByCourse_Key(int course_id) {
        List<CourseDetail> reData = new ArrayList<CourseDetail>();
        String sql = "select Id,CourseId,Abstracts,Detail,Status,AddTime from SG_CourseDetail where CourseId = ? and Status > ? ";
        Object [] params = new Object[]{course_id, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseDetail entity = new CourseDetail();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setAbstracts(list.get(i).get("Abstracts").toString());
                entity.setDetail(list.get(i).get("Detail").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public CourseDetail get(int id) {
        String sql = "select Id,CourseId,Abstracts,Detail,Status,AddTime from SG_CourseDetail where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final CourseDetail entity = new CourseDetail();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCourseId(rs.getInt("CourseId"));
                entity.setAbstracts(rs.getString("Abstracts"));
                entity.setDetail(rs.getString("Detail"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(CourseDetail entity) {
        String sql = "insert into SG_CourseDetail(CourseId,Abstracts,Detail,Status,AddTime) value(?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getAbstracts(), entity.getDetail(), Quantity.STATUS_ONE};
        int reDate = 0;
        try {
            reDate = jdbcTemplate.update(sql, params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reDate;
    }

    @Override
    public int insertKey(CourseDetail courseDetail) {
        final CourseDetail entity = courseDetail;
        final String sql = "insert into SG_CourseDetail(CourseId,Abstracts,Detail,Status,AddTime) value(?, ?, ?, ?, NOW()) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int reData = jdbcTemplate.update( new PreparedStatementCreator(){
            public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException{

                int i = 0;
                java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(++i, entity.getCourseId());
                ps.setString(++i, entity.getAbstracts());
                ps.setString(++i, entity.getDetail());
                ps.setInt(++i, Quantity.STATUS_ONE);

                return ps;
            }
        },keyHolder);
        if (reData > 0) {
            reData = keyHolder.getKey().intValue();
        }
        return reData;
    }

    @Override
    public int update(CourseDetail entity) {
        String sql = "update SG_CourseDetail set CourseId = ?, Abstracts = ?, Detail = ? where Id = ? ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getAbstracts(), entity.getDetail(), entity.getId()};
        int reData = 0;
        try {
            reData = jdbcTemplate.update(sql, params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_CourseDetail set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public CourseDetail formEntity(HttpServletRequest request, int course_id, int id) {
        CourseDetail entity = new CourseDetail();

        entity.setId(id);
        entity.setCourseId(course_id);
        entity.setAbstracts(request.getParameter("abstracts"));
        entity.setDetail(request.getParameter("detail"));

        return entity;
    }

    @Override
    public CourseDetail setCourseDetail(HttpServletRequest req,int course_id, int id) {
        CourseDetail entity = null;
        if (id == 0) {
            entity = new CourseDetail();
            entity.setCourseId(course_id);
        }else{
            entity = this.get(id);
        }
        entity.setAbstracts(req.getParameter("abstracts"));
        String details = req.getParameter("details");
//        System.out.print(details);
        List<Detail> detail_ls = new ArrayList<Detail>();
        List<Map<String,Object>> ls = StringUtil.parseJSON2List(details);
        if (ls.size() > 0){
            for (int i = 0; i < ls.size(); i++) {
                Detail detail = new Detail();
                detail.setTitle(ls.get(i).get("title").toString());
                String content = ls.get(i).get("content").toString();
                String html = "<html><head><title>content</title></head><body>";
                html = html+content + "</body></html>";
                Document doc = Jsoup.parse(html);
                detail.setContent(getContent(doc));
                detail_ls.add(detail);
            }
        }
        entity.setDetail(JSONObject.toJSONString(detail_ls));
        return entity;
    }

    /**
     * 解析图文详情的html生成Content集合
     * @param doc
     * @return
     */
    private List<Content> getContent(Document doc){
        List<Content> contents = new ArrayList<Content>();
        Elements elem = doc.getElementsByTag("p");
        for(Element content_p : elem){
            Content content = new Content();
            Elements es_img = content_p.getElementsByTag("img");
            if(es_img != null && es_img.size() > 0){
                for (Element img : es_img){
                    String path = img.attr("src");
                    if (path.contains("http")){
                        path = path.replace(FileUtil.SERVER_PATH,"");
                    }
                    if (path.contains("_m.jpg")){
                        path = path.replace("_m.jpg",".jpg");
                    }
                    content.setImg(path);
                    contents.add(content);
                }
            }else{
                content.setText(content_p.text());
                contents.add(content);
            }
        }
        return contents;
    }

    /**
     * 输出课程图文详情的json串
     * @param course_id
     * @return
     */
    @Override
    public String getDetailContentStr(int course_id){
        List<DetailContent> re_ls = new ArrayList<DetailContent>();
        CourseDetail courseDetail = this.getByCourseKey(course_id);
        String details = courseDetail.getDetail();
        List<Map<String,Object>> ls = StringUtil.parseJSON2List(details);
        if (ls.size() > 0){
            for (int i = 0; i < ls.size(); i++) {
                DetailContent dc = new DetailContent();
                dc.setTitle(ls.get(i).get("title").toString());
                dc.setContent(getContentStr((List<Map<String, String>>) ls.get(i).get("content")));
                re_ls.add(dc);
            }
        }

        return JSONObject.toJSONString(re_ls);

    }

    /**
     * 获取detail数据列表
     * @param c_ls
     * @return
     */
    public String getContentStr(List<Map<String,String>> c_ls){
        StringBuffer sb = new StringBuffer();
        if (c_ls.size() > 0){
            for (int j = 0; j < c_ls.size(); j++) {
                if (c_ls.get(j).get("text") != null){
                    String text = c_ls.get(j).get("text").toString();
                    if (text.equals("")){
                        sb.append("<p>").append("</br>").append("</p>");
                    }else{
                        sb.append("<p>").append(text).append("</p>");
                    }
                }
                if (c_ls.get(j).get("img") != null){
                    String img = c_ls.get(j).get("img").toString();
                    img = FileUtil.SERVER_PATH + img.replace(".jpg","_m.jpg");
                    sb.append("<p>").append("<img src = '"+img+"' />").append("</p>");
                }
            }
        }

        return sb.toString();
    }

    public CourseDetail getByCourseKey(int course_id) {
        String sql = "select Id,CourseId,Abstracts,Detail,Status,AddTime from SG_CourseDetail where CourseId = ? and Status > ? ";
        final Object [] params = new Object[]{course_id, Quantity.STATUS_ZERO};
        final CourseDetail entity = new CourseDetail();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCourseId(rs.getInt("CourseId"));
                entity.setAbstracts(rs.getString("Abstracts"));
                entity.setDetail(rs.getString("Detail"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }
}
