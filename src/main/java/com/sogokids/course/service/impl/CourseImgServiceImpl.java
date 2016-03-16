package com.sogokids.course.service.impl;

import cn.momia.common.config.Configuration;
import com.sogokids.course.model.CourseImg;
import com.sogokids.course.service.CourseImgService;
import com.sogokids.images.model.Images;
import com.sogokids.utils.util.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Created by hoze on 15/10/16.
 */
@Service
public class CourseImgServiceImpl implements CourseImgService{

    private final Logger log = LoggerFactory.getLogger(CourseImgServiceImpl.class);

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
        String sql = "select Id,CourseId,Url,Width,Height,SortValue,Status,AddTime from SG_CourseImg where Status > ? order by SortValue ASC ";
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
                entity.setSortValue(Integer.parseInt(list.get(i).get("SortValue").toString()));
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
        String sql = "select Id,CourseId,Url,Width,Height,SortValue,Status,AddTime from SG_CourseImg where CourseId = ? and status > ? order by SortValue ASC ";
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
                entity.setSortValue(Integer.parseInt(list.get(i).get("SortValue").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public CourseImg get(int id) {
        String sql = "select Id,CourseId,Url,Width,Height,SortValue,Status,AddTime from SG_CourseImg where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final CourseImg entity = new CourseImg();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCourseId(rs.getInt("CourseId"));
                entity.setUrl(rs.getString("Url"));
                entity.setWidth(rs.getInt("Width"));
                entity.setHeight(rs.getInt("Height"));
                entity.setSortValue(rs.getInt("SortValue"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(CourseImg entity) {
        entity.setSortValue(this.getMaxSortValue(entity.getCourseId()));
        String sql = "insert into SG_CourseImg(CourseId,Url,Width,Height,SortValue,Status,AddTime) value(?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getUrl(), entity.getWidth(), entity.getHeight(), entity.getSortValue(), Quantity.STATUS_ONE};
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
                        if (p_img.size() > 1) {
                            if (i == 0) {
                                sb.append("<a href='javascript:void(0)' class='btn btn-warning btn-sm' onclick='afterImg(" + p_img.get(i).getId() + ")'>后移<i class='fa fa-angle-right'></i></a>");
                                sb.append("&numsp;");
                            } else {
                                if (i + 1 == p_img.size()) {
                                    sb.append("<a href='javascript:void(0)' class='btn btn-primary btn-sm' onclick='beforeImg(" + p_img.get(i).getId() + ")'><i class='fa fa-angle-left'></i>前移</a>");
                                    sb.append("&numsp;");
                                } else {
                                    sb.append("<a href='javascript:void(0)' class='btn btn-primary btn-sm' onclick='beforeImg(" + p_img.get(i).getId() + ")'><i class='fa fa-angle-left'></i>前移</a>");
                                    sb.append("&numsp;");
                                    sb.append("<a href='javascript:void(0)' class='btn btn-warning btn-sm' onclick='afterImg(" + p_img.get(i).getId() + ")'>后移<i class='fa fa-angle-right'></i></a>");
                                    sb.append("&numsp;");
                                }
                            }
                        }
                        sb.append("<a href='javascript:void(0)' class='btn btn-danger btn-sm' onclick='delimg("+p_img.get(i).getId()+")'><i class='fa fa-times-circle'></i>删除</a>");
                        sb.append("</li>");
                    sb.append("</ul>");
                sb.append("</div>");
            }
        }
        return sb.toString();
    }

    @Override
    public String moveBefore(int cou_id,int imgId,int flag){
        List<CourseImg> p_img = this.getModelsBySub_Key(cou_id);
        CourseImg courseImg = this.get(imgId);
        int int_x = 0;
        for (int i = 0; i < p_img.size(); i++) {
            if (courseImg.getId() == p_img.get(i).getId()){
                if (flag == 1){//前移
                    int_x = i-1;
                }else{//后移
                    int_x = i+1;
                }
                break;
            }
        }
        int sortValue_1 = courseImg.getSortValue();
        CourseImg img = p_img.get(int_x);
        int sortValue_2 = img.getSortValue();
        this.updateSortValue(courseImg.getId(),sortValue_2);
        this.updateSortValue(img.getId(),sortValue_1);

        return this.getImgHtml(cou_id);
    }


    /**
     * 根据课程id获取当前最大排序值
     * @param course_id
     * @return
     */
    private int getMaxSortValue(int course_id){
        String sql = "select max(sortValue) as max_value from SG_CourseImg where CourseId = ? ";
        final Object [] params = new Object[]{course_id};
        final Map map = new HashMap();
        jdbcTemplate.query(sql, params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                if (rs == null){
                    map.put("max_value", 0);
                }else{
                    map.put("max_value", rs.getInt("max_value"));
                }
            }
        });

        int reData = Integer.parseInt(map.get("max_value").toString())+Quantity.STATUS_ONE;

        return reData;
    }


    /**
     * 移动课程图片的前后顺序
     * @param imgId
     * @param sortValue
     * @return
     */
    private int updateSortValue(int imgId,int sortValue) {
        String sql = "update SG_CourseImg set SortValue = ? where Id = ? ";
        Object [] params = new Object[]{sortValue,imgId};
        int reData = 0;
        try{
            reData = jdbcTemplate.update(sql,params);
        }catch (Exception _ex){
            log.error("CourseImgServiceImpl -> updateSortValue -> error : "+_ex.getMessage());
        }
        return reData;
    }
}
