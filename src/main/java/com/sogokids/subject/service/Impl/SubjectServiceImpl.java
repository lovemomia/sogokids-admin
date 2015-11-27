package com.sogokids.subject.service.Impl;

import com.sogokids.course.model.Course;
import com.sogokids.course.service.CourseService;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.system.service.CityService;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/10/12.
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private CityService cityService;

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
    public List<Subject> getEntitys() {
        List<Subject> reData = new ArrayList<Subject>();
        String sql = "select Id,`Type`,CityId,Title,Cover,Tags,Intro,Notice,Stock,Status,AddTime from SG_Subject where Status > ? order by Id desc";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Subject entity = new Subject();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setType(Integer.parseInt(list.get(i).get("Type").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("CityId").toString()));
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setTags(list.get(i).get("Tags").toString());
                entity.setIntro(list.get(i).get("Intro").toString());
                entity.setNotice(list.get(i).get("Notice").toString());
                entity.setStock(Integer.parseInt(list.get(i).get("Stock").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                entity.setCityName(cityService.get(Integer.parseInt(list.get(i).get("CityId").toString())).getName());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Subject> getEntitys(int type) {
        List<Subject> reData = new ArrayList<Subject>();
        String sql = "select Id,`Type`,CityId,Title,Cover,Tags,Intro,Notice,Stock,Status,AddTime from SG_Subject where `Type` = ? and Status > ? order by Id desc";
        Object [] params = new Object[]{type, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Subject entity = new Subject();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setType(Integer.parseInt(list.get(i).get("Type").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("CityId").toString()));
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setTags(list.get(i).get("Tags").toString());
                entity.setIntro(list.get(i).get("Intro").toString());
                entity.setNotice(list.get(i).get("Notice").toString());
                entity.setStock(Integer.parseInt(list.get(i).get("Stock").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                entity.setCityName(cityService.get(Integer.parseInt(list.get(i).get("CityId").toString())).getName());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Subject> getModelsByType() {
        List<Subject> reData = new ArrayList<Subject>();
        String sql = "select Id,`Type`,CityId,Title,Cover,Tags,Intro,Notice,Stock,Status,AddTime from SG_Subject where `Type` = 1 and Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Subject entity = new Subject();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setTitle(list.get(i).get("Title").toString());
                reData.add(entity);
            }
        }

        List<Subject> sub_ls = new ArrayList<Subject>();
        Subject sub = new Subject();
        sub.setId(0);
        sub.setTitle("请选择");
        sub_ls.add(sub);
        sub_ls.addAll(reData);

        return sub_ls;
    }

    @Override
    public Subject get(int id) {
        String sql = "select Id,`Type`,CityId,Title,Cover,Tags,Intro,Notice,Stock,Status,AddTime from SG_Subject where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Subject entity = new Subject();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setType(rs.getInt("Type"));
                entity.setCityId(rs.getInt("CityId"));
                entity.setCover(rs.getString("Cover"));
                entity.setTitle(rs.getString("Title"));
                entity.setTags(rs.getString("Tags"));
                entity.setIntro(rs.getString("Intro"));
                entity.setNotice(rs.getString("Notice"));
                entity.setStock(rs.getInt("Stock"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(Subject entity) {
        String sql = "insert into SG_Subject(`Type`,CityId,Title,Cover,Tags,Intro,Notice,Stock,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getType(), entity.getCityId(), entity.getTitle(), entity.getCover(), entity.getTags(), entity.getIntro(), entity.getNotice(), entity.getStock(), Quantity.STATUS_TWO};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int insertKey(Subject subject) {
        final Subject entity = subject;
        final String sql = "insert into SG_Subject(`Type`,CityId,Title,Cover,Tags,Intro,Notice,Stock,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int reData = jdbcTemplate.update( new PreparedStatementCreator(){
            public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException{

                int i = 0;
                java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(++i, entity.getType());
                ps.setInt(++i, entity.getCityId());
                ps.setString(++i, entity.getTitle());
                ps.setString(++i, entity.getCover());
                ps.setString(++i, entity.getTags());
                ps.setString(++i, entity.getIntro());
                ps.setString(++i, entity.getNotice());
                ps.setInt(++i, entity.getStock());
                ps.setInt(++i, Quantity.STATUS_TWO);

                return ps;
            }
        },keyHolder);
        if (reData > 0) {
            reData = keyHolder.getKey().intValue();
        }
        return reData;
    }

    @Override
    public int update(Subject entity) {
        String sql = "update SG_Subject set `Type` = ?, CityId = ?, Title = ?, Cover = ?, Tags = ?, Intro = ? where Id = ? ";
        Object [] params = new Object[]{entity.getType(), entity.getCityId(), entity.getTitle(), entity.getCover(), entity.getTags(), entity.getIntro(), entity.getId()};
        int reData = 0;
        try {
            reData = jdbcTemplate.update(sql, params);
        }catch(Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int updateNotice(int sub_id, String contents) {
        String sql = "update SG_Subject set Notice = ? where Id = ? ";
        Object [] params = new Object[]{contents, sub_id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int updateStatus(int sub_id, int status) {
        String sql = "update SG_Subject set Status = ? where Id = ? ";
        Object [] params = new Object[]{status, sub_id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int updateStock(int sub_id, int status) {
        String sql = "update SG_Subject set Stock = ? where Id = ? ";
        Object [] params = new Object[]{status, sub_id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Subject set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Subject formEntity(HttpServletRequest request, int id) {
        Subject entity = new Subject();


        entity.setId(id);
        int type = Integer.parseInt(request.getParameter("type"));
        entity.setType(type);
        entity.setCityId(Integer.parseInt(request.getParameter("cityId")));
        entity.setTitle(request.getParameter("title"));
        entity.setCover(request.getParameter("cover"));
        int tags = Integer.parseInt(request.getParameter("tags"));
        if (tags == 0){
            entity.setTags("");
        }else{
            entity.setTags(this.get(tags).getTitle());
        }
        if (id == 0){
            if (type == 1){
                entity.setStock(-1);
            }else{
                entity.setStock(0);
            }
            entity.setNotice("[]");
        }else{
            entity.setNotice(request.getParameter("notice"));
        }
//        System.out.print(request.getParameter("intro"));
        entity.setIntro(request.getParameter("intro"));

        return entity;
    }

    @Override
    public List<Course> getCoursesBySubjects(){
        List<Course> courses = new ArrayList<Course>();
        List<Subject> subjects = this.getEntitys(Quantity.STATUS_TWO);
        if (subjects.size() > 0){
            for(Subject subject : subjects){
                courses.addAll(courseService.getEntitysBySubId(subject.getId()));
            }
        }
        return courses;
    }
}
