package com.sogokids.course.service.impl;

import cn.momia.common.config.Configuration;
import com.sogokids.course.model.CourseBook;
import com.sogokids.course.model.CourseImg;
import com.sogokids.course.service.CourseBookService;
import com.sogokids.images.model.Images;
import com.sogokids.subject.model.SubjectNotice;
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
 * Created by hoze on 15/10/12.
 */
@Service
public class CourseBookServiceImpl implements CourseBookService {

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
    public List<CourseBook> getEntitys() {
        List<CourseBook> reData = new ArrayList<CourseBook>();
        String sql = "select Id,CourseId,Img,`Order`,Status,AddTime from SG_CourseBook where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseBook entity = new CourseBook();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setImg(list.get(i).get("Img").toString());
                entity.setOrder(Integer.parseInt(list.get(i).get("Order").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<CourseBook> getEntitys(int course_id) {
        List<CourseBook> reData = new ArrayList<CourseBook>();
        String sql = "select Id,CourseId,Img,`Order`,Status,AddTime from SG_CourseBook where CourseId = ? and Status > ? ";
        Object [] params = new Object[]{course_id, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseBook entity = new CourseBook();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setImg(list.get(i).get("Img").toString());
                entity.setOrder(Integer.parseInt(list.get(i).get("Order").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public CourseBook get(int id) {
        String sql = "select Id,CourseId,Img,`Order`,Status,AddTime from SG_CourseBook where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final CourseBook entity = new CourseBook();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCourseId(rs.getInt("CourseId"));
                entity.setImg(rs.getString("Img"));
                entity.setOrder(rs.getInt("Order"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    private int getMaxOrder(int course_id) {
        int max_order = 0;
        String sql = "select MAX(`Order`) as max_order from SG_CourseBook where CourseId = ? and Status > ? ";
        Object [] params = new Object[]{course_id, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            if (list.get(0).get("max_order") != null && !list.get(0).get("max_order").equals("")) {
                max_order = Integer.parseInt(list.get(0).get("max_order").toString());
            }
        }

        max_order = max_order + 1;

        return max_order;
    }

    @Override
    public int insert(CourseBook entity) {
        String sql = "insert into SG_CourseBook(CourseId,Img,`Order`,Status,AddTime) value(?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getImg(), entity.getOrder(), Quantity.STATUS_ONE};
        int reData = 0;
        try {
            reData = jdbcTemplate.update(sql, params);
        }catch(Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int update(CourseBook entity) {
        String sql = "update SG_CourseBook set CourseId = ?, Img = ?, `Order` = ? where Id = ? ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getImg(), entity.getOrder(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_CourseBook set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public CourseBook formEntity(Images images, int courseId, int id) {
        CourseBook entity = new CourseBook();

        entity.setId(id);
        entity.setCourseId(courseId);
        entity.setImg(images.getUrl());
        entity.setOrder(getMaxOrder(courseId));

        return entity;
    }

    @Override
    public String getBooksHtml(int course_id){
        StringBuffer sb = new StringBuffer();
        List<CourseBook> course_books = this.getEntitys(course_id);
        if (course_books.size() > 0){
            for (CourseBook courseBook : course_books){
                String url = configuration.getString(Quantity.DISPLAY_IMAGE) + courseBook.getImg();
//                sb.append("<a class='fancybox' href='javascript:void(0)' onclick='bookEdit("+courseBook.getId()+")' >");
//                sb.append("<img style='width: 50;height: 50px' alt='"+courseBook.getId()+"' src='" + url + "' />");
//                sb.append("</a>");
//                sb.append("<a href='javascript:void(0)' class='btn btn-white btn-sm' onclick='bookDel("+courseBook.getId()+")' ><i class='fa fa-times-circle'></i></a>");

                sb.append("<div class='div_img'>");
                sb.append("<ul>");
                sb.append("<li>");
                sb.append("<img class='img_w_h' src='" + url + "' />");
                sb.append("</li>");

                sb.append("<li>");
                sb.append("<a href='javascript:void(0)' class='btn btn-white btn-sm' onclick='bookDel("+courseBook.getId()+")'><i class='fa fa-times-circle'></i>删除</a>");
                sb.append("</li>");
                sb.append("</ul>");
                sb.append("</div>");

            }
        }

        return sb.toString();
    }
}
