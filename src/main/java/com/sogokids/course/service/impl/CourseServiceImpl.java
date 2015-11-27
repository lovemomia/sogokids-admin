package com.sogokids.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseBook;
import com.sogokids.course.model.CourseDetail;
import com.sogokids.course.model.CourseImg;
import com.sogokids.course.model.CourseSku;
import com.sogokids.course.service.CourseBookService;
import com.sogokids.course.service.CourseDetailService;
import com.sogokids.course.service.CourseImgService;
import com.sogokids.course.service.CourseRecommendService;
import com.sogokids.course.service.CourseService;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.model.SubjectSku;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.utils.util.Quantity;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/10/12.
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private SubjectService subjectService;

//    @Autowired
//    private SubjectSkuService subjectSkuService;
//
//    @Autowired
//    private CourseSkuService courseSkuService;
//
//    @Autowired
//    private CourseImgService courseImgService;
//
//    @Autowired
//    private CourseBookService courseBookService;

    @Autowired
    private CourseRecommendService courseRecommendService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Course> getEntitys() {
        List<Course> reData = new ArrayList<Course>();
        String sql = "select Id,ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,InstitutionId,Status,AddTime from SG_Course where Status > ? order by Id desc";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Course entity = new Course();
                int id = Integer.parseInt(list.get(i).get("Id").toString());
                entity.setId(id);
                entity.setParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                int sub_id = Integer.parseInt(list.get(i).get("SubjectId").toString());
                entity.setSubjectId(sub_id);
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setMinAge(Integer.parseInt(list.get(i).get("MinAge").toString()));
                entity.setMaxAge(Integer.parseInt(list.get(i).get("MaxAge").toString()));
                entity.setJoined(Integer.parseInt(list.get(i).get("Joined").toString()));
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setGoal(list.get(i).get("Goal").toString());
                entity.setFlow(list.get(i).get("Flow").toString());
                entity.setTips(list.get(i).get("Tips").toString());
                entity.setInstitutionId(Integer.parseInt(list.get(i).get("InstitutionId").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
//                if (subjectService.get(sub_id).getType() == 2){
//                    entity.setFlag(1);
//                }else if(getEntitysById(id).size() > 0){
//                    entity.setFlag(1);
//                }else{
//                    entity.setFlag(0);
//                }

                entity.setSubTitle(subjectService.get(Integer.parseInt(list.get(i).get("SubjectId").toString())).getTitle());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Course> getEntitysBySubId(int id) {
        List<Course> reData = new ArrayList<Course>();
        String sql = "select Id,ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,InstitutionId,Status,AddTime from SG_Course where SubjectId = ? and Status > ? ";
        Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Course entity = new Course();
                int course_id = Integer.parseInt(list.get(i).get("Id").toString());
                entity.setId(course_id);
                entity.setParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                int sub_id = Integer.parseInt(list.get(i).get("SubjectId").toString());
                entity.setSubjectId(sub_id);
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setMinAge(Integer.parseInt(list.get(i).get("MinAge").toString()));
                entity.setMaxAge(Integer.parseInt(list.get(i).get("MaxAge").toString()));
                entity.setJoined(Integer.parseInt(list.get(i).get("Joined").toString()));
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setGoal(list.get(i).get("Goal").toString());
                entity.setFlow(list.get(i).get("Flow").toString());
                entity.setTips(list.get(i).get("Tips").toString());
                entity.setInstitutionId(Integer.parseInt(list.get(i).get("InstitutionId").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                if (courseRecommendService.getEntitysByCourseId(course_id).size() > 0){
                    entity.setFlag(1);
                }

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Course> getEntitysById(int id) {
        List<Course> reData = new ArrayList<Course>();
        String sql = "select Id,ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,InstitutionId,Status,AddTime from SG_Course where ParentId = ? and Status > ? ";
        Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Course entity = new Course();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                int sub_id = Integer.parseInt(list.get(i).get("SubjectId").toString());
                entity.setSubjectId(sub_id);
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setMinAge(Integer.parseInt(list.get(i).get("MinAge").toString()));
                entity.setMaxAge(Integer.parseInt(list.get(i).get("MaxAge").toString()));
                entity.setJoined(Integer.parseInt(list.get(i).get("Joined").toString()));
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setGoal(list.get(i).get("Goal").toString());
                entity.setFlow(list.get(i).get("Flow").toString());
                entity.setTips(list.get(i).get("Tips").toString());
                entity.setInstitutionId(Integer.parseInt(list.get(i).get("InstitutionId").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public Course get(int id) {
        String sql = "select Id,ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,Notice,InstitutionId,Status,AddTime from SG_Course where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Course entity = new Course();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setParentId(rs.getInt("ParentId"));
                entity.setSubjectId(rs.getInt("SubjectId"));
                entity.setTitle(rs.getString("Title"));
                entity.setCover(rs.getString("Cover"));
                entity.setMinAge(rs.getInt("MinAge"));
                entity.setMaxAge(rs.getInt("MaxAge"));
                entity.setJoined(rs.getInt("Joined"));
                entity.setPrice(rs.getBigDecimal("Price"));
                entity.setGoal(rs.getString("Goal"));
                entity.setFlow(rs.getString("Flow"));
                entity.setTips(rs.getString("Tips"));
                entity.setNotice(rs.getString("Notice"));
                entity.setInstitutionId(rs.getInt("InstitutionId"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(Course entity) {
        String sql = "insert into SG_Course(ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,Notice,InstitutionId,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getParentId(), entity.getSubjectId(), entity.getTitle(), entity.getCover(), entity.getMinAge(), entity.getMaxAge(), entity.getJoined(), entity.getPrice(), entity.getGoal(), entity.getFlow(), entity.getTips(), entity.getNotice(), entity.getInstitutionId(), Quantity.STATUS_TWO};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int insertKey(Course course) {
        final Course entity = course;
        final String sql = "insert into SG_Course(ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,Notice,InstitutionId,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int reData = 0;
        try {
            reData = jdbcTemplate.update(new PreparedStatementCreator() {
                public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {

                    int i = 0;
                    java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(++i, entity.getParentId());
                    ps.setInt(++i, entity.getSubjectId());
                    ps.setString(++i, entity.getTitle());
                    ps.setString(++i, entity.getCover());
                    ps.setInt(++i, entity.getMinAge());
                    ps.setInt(++i, entity.getMaxAge());
                    ps.setInt(++i, Quantity.STATUS_ZERO);
                    ps.setBigDecimal(++i, entity.getPrice());
                    ps.setString(++i, entity.getGoal());
                    ps.setString(++i, entity.getFlow());
                    ps.setString(++i, entity.getTips());
                    ps.setString(++i, entity.getNotice());
                    ps.setInt(++i, entity.getInstitutionId());
                    ps.setInt(++i, Quantity.STATUS_TWO);

                    return ps;
                }
            }, keyHolder);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        if (reData > 0) {
            reData = keyHolder.getKey().intValue();
        }
        return reData;
    }

    @Override
    public int update(Course entity) {
        String sql = "update SG_Course set SubjectId = ?, Title = ?, Cover = ?, MinAge = ?, MaxAge = ?, Price = ?, Goal = ?, Flow = ?, Tips = ?, InstitutionId = ? where Id = ? ";
        Object [] params = new Object[]{entity.getSubjectId(), entity.getTitle(), entity.getCover(), entity.getMinAge(), entity.getMaxAge(), entity.getPrice(), entity.getGoal(), entity.getFlow(), entity.getTips(), entity.getInstitutionId(), entity.getId()};
        int reData = 0;
        try{
            reData = jdbcTemplate.update(sql,params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int updateStatus(int course_id, int status) {
        String sql = "update SG_Course set Status = ? where Id = ? ";
        Object [] params = new Object[]{status, course_id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int updateNotice(int course_id, String notice) {
        String sql = "update SG_Course set Notice = ? where Id = ? ";
        Object [] params = new Object[]{notice, course_id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Course set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Course formEntity(HttpServletRequest request, int id) {
        Course entity = new Course();

        entity.setId(id);
        entity.setParentId(Quantity.STATUS_ZERO);
        entity.setSubjectId(Integer.parseInt(request.getParameter("subjectId")));
        entity.setTitle(request.getParameter("title"));
        entity.setCover(request.getParameter("cover"));
        entity.setMinAge(Integer.parseInt(request.getParameter("minAge")));
        entity.setMaxAge(Integer.parseInt(request.getParameter("maxAge")));
        entity.setPrice(new BigDecimal(request.getParameter("price")));
        entity.setGoal(request.getParameter("goal"));
        entity.setFlow(request.getParameter("flow"));
        entity.setTips(request.getParameter("tips"));
        entity.setNotice("");
        entity.setInstitutionId(Integer.parseInt(request.getParameter("institutionId")));

        return entity;
    }

}
