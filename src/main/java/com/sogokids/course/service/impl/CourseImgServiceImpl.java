package com.sogokids.course.service.impl;

import cn.momia.common.config.Configuration;
import com.sogokids.course.model.CourseImg;
import com.sogokids.course.service.CourseImgService;
import com.sogokids.images.model.Images;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/10/16.
 */
@Service
public class CourseImgServiceImpl implements CourseImgService{

    @Autowired
    private Configuration configuration;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CourseImg> getEntitys() {
        List<CourseImg> reData = new ArrayList<CourseImg>();
        String sql = "select Id,CourseId,Url,Width,Height,Status,AddTime from SG_CourseImg where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseImg entity = new CourseImg();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setUrl(list.get(i).get("Url").toString());
                entity.setWidth(Integer.parseInt(list.get(i).get("Width").toString()));
                entity.setHeight(Integer.parseInt(list.get(i).get("Height").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<CourseImg> getModelsBySub_Key(int courseId) {
        List<CourseImg> reData = new ArrayList<CourseImg>();
        String sql = "select Id,CourseId,Url,Width,Height,Status,AddTime from SG_CourseImg where CourseId = ? and status > ? ";
        Object [] params = new Object[]{courseId, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseImg entity = new CourseImg();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setUrl(list.get(i).get("Url").toString());
                entity.setWidth(Integer.parseInt(list.get(i).get("Width").toString()));
                entity.setHeight(Integer.parseInt(list.get(i).get("Height").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public CourseImg get(int id) {
        String sql = "select Id,CourseId,Url,Width,Height,Status,AddTime from SG_CourseImg where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final CourseImg entity = new CourseImg();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCourseId(rs.getInt("CourseId"));
                entity.setUrl(rs.getString("Url"));
                entity.setWidth(rs.getInt("Width"));
                entity.setHeight(rs.getInt("Height"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(CourseImg entity) {
        String sql = "insert into SG_CourseImg(CourseId,Url,Width,Height,Status,AddTime) value(?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getUrl(), entity.getWidth(), entity.getHeight(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(CourseImg entity) {
        String sql = "update SG_CourseImg set CourseId = ?, Url = ?, Width = ?, Height = ? where Id = ? ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getUrl(), entity.getWidth(), entity.getHeight(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_CourseImg set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public CourseImg formEntity(HttpServletRequest request, int id) {
        CourseImg entity = new CourseImg();

        entity.setId(id);
        entity.setCourseId(Integer.parseInt(request.getParameter("CourseId")));
        entity.setUrl(request.getParameter("Url"));
        entity.setWidth(Integer.parseInt(request.getParameter("Width")));
        entity.setHeight(Integer.parseInt(request.getParameter("Height")));

        return entity;
    }

    @Override
    public CourseImg formEntity(Images img, int id) {
        CourseImg entity = new CourseImg();
        entity.setCourseId(id);
        entity.setUrl(img.getUrl());
        entity.setWidth(img.getWidth());
        entity.setHeight(img.getHeigth());

        return entity;
    }

    @Override
    public String getImgHtml(int cou_id) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        List<CourseImg> p_img = this.getModelsBySub_Key(cou_id);
        if (p_img.size() > 0){
            for (int i = 0; i < p_img.size(); i++) {
                //String altstr = p_img.get(i).getWidth() + "x" + p_img.get(i).getHeight();
                String url = configuration.getString(Quantity.DISPLAY_IMAGE) + p_img.get(i).getUrl();

                sb.append("<div class='div_img'>");
                    sb.append("<ul>");
                        sb.append("<li>");
                            sb.append("<img class='img_w_h' src='" + url + "' />");
                        sb.append("</li>");

                        sb.append("<li>");
                            sb.append("<a href='javascript:void(0)' class='btn btn-white btn-sm' onclick='delimg("+p_img.get(i).getId()+")'><i class='fa fa-times-circle'></i>删除</a>");
                        sb.append("</li>");
                    sb.append("</ul>");
                sb.append("</div>");
            }
        }
        return sb.toString();
    }
}
