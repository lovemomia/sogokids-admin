package com.sogokids.course.service.impl;

import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseRecommend;
import com.sogokids.course.service.CourseRecommendService;
import com.sogokids.course.service.CourseService;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/11/21.
 */
@Service
public class CourseRecommendServiceImpl implements CourseRecommendService {

    @Autowired
    private CourseService courseService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<CourseRecommend> getEntitys() {
        List<CourseRecommend> reData = new ArrayList<CourseRecommend>();
        String sql = "select Id,CourseId,Weight,Status,AddTime from SG_CourseRecommend where Status > ? order by Id desc";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseRecommend entity = new CourseRecommend();

                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                int course_id = Integer.parseInt(list.get(i).get("CourseId").toString());
                entity.setCourseId(course_id);
                entity.setWeight(Integer.parseInt(list.get(i).get("Weight").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

//                entity.setCourse(courseService.get(course_id));
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<Course>();
        List<CourseRecommend> courseRecommends = this.getEntitys();
        if (courseRecommends.size() > 0){
            for (CourseRecommend courseRecommend : courseRecommends) {
                courses.add(courseService.get(courseRecommend.getCourseId()));
            }
        }
        return courses;
    }

    @Override
    public List<CourseRecommend> getEntitysByCourseId(int id) {
        List<CourseRecommend> reData = new ArrayList<CourseRecommend>();
        String sql = "select Id,CourseId,Weight,Status,AddTime from SG_CourseRecommend where Status > ? and CourseId = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO, id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseRecommend entity = new CourseRecommend();

                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                int course_id = Integer.parseInt(list.get(i).get("CourseId").toString());
                entity.setCourseId(course_id);
                entity.setWeight(Integer.parseInt(list.get(i).get("Weight").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

//                entity.setCourse(courseService.get(course_id));
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<CourseRecommend> getCancelByCourseId(int id) {
        List<CourseRecommend> reData = new ArrayList<CourseRecommend>();
        String sql = "select Id,CourseId,Weight,Status,AddTime from SG_CourseRecommend where Status = ? and CourseId = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO, id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseRecommend entity = new CourseRecommend();

                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                int course_id = Integer.parseInt(list.get(i).get("CourseId").toString());
                entity.setCourseId(course_id);
                entity.setWeight(Integer.parseInt(list.get(i).get("Weight").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

//                entity.setCourse(courseService.get(course_id));
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public CourseRecommend get(int id) {
        String sql = "select Id,CourseId,Weight,Status,AddTime from SG_CourseRecommend where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final CourseRecommend entity = new CourseRecommend();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                int course_id = rs.getInt("CourseId");
                entity.setId(rs.getInt("Id"));
                entity.setCourseId(course_id);
                entity.setWeight(rs.getInt("Weight"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));

                entity.setCourse(courseService.get(course_id));
            }
        });

        return entity;
    }

    @Override
    public int insert(CourseRecommend entity) {
        String sql = "insert into SG_CourseRecommend(CourseId,Weight,Status,AddTime) value(?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getWeight(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int insertKey(CourseRecommend courseRecommend) {
        final CourseRecommend entity = courseRecommend;
        final String sql = "insert into SG_CourseRecommend(CourseId,Weight,Status,AddTime) value(?, ?, ?, NOW()) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int reData = 0;
        try {
            reData = jdbcTemplate.update(new PreparedStatementCreator() {
                public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {

                    int i = 0;
                    java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(++i, entity.getCourseId());
                    ps.setInt(++i, entity.getWeight());
                    ps.setInt(++i, Quantity.STATUS_ONE);

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
    public int update(CourseRecommend entity) {
        String sql = "update SG_CourseRecommend set Weight = ? where Id = ? ";
        Object [] params = new Object[]{entity.getWeight(), entity.getId()};
        int reData = 0;
        try{
            reData = jdbcTemplate.update(sql,params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int updateStatus(int id) {
        String sql = "update SG_CourseRecommend set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ONE,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_CourseRecommend set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public CourseRecommend formEntity(HttpServletRequest request, int course_id, int id) {
        CourseRecommend entity = new CourseRecommend();

        entity.setId(id);
        entity.setCourseId(course_id);
        entity.setWeight(Integer.parseInt(request.getParameter("weight")));

        return entity;
    }
}
