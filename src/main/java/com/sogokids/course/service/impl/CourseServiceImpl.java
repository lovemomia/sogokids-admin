package com.sogokids.course.service.impl;

import cn.momia.common.config.Configuration;
import com.alibaba.fastjson.JSONObject;
import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseDetail;
import com.sogokids.course.model.CourseImg;
import com.sogokids.course.model.CourseRecommend;
import com.sogokids.course.model.CourseSku;
import com.sogokids.teacher.model.CourseTeacher;
import com.sogokids.course.service.CourseDetailService;
import com.sogokids.course.service.CourseImgService;
import com.sogokids.course.service.CourseRecommendService;
import com.sogokids.course.service.CourseService;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.teacher.service.CourseTeacherService;
import com.sogokids.http.model.HttpResult;
import com.sogokids.http.service.HttpClientService;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.model.SubjectSku;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.system.model.Institution;
import com.sogokids.system.model.Place;
import com.sogokids.teacher.model.Teacher;
import com.sogokids.system.service.InstitutionService;
import com.sogokids.system.service.PlaceService;
import com.sogokids.system.service.RegionService;
import com.sogokids.teacher.service.TeacherService;
import com.sogokids.utils.util.CastUtil;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import com.sogokids.utils.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/10/12.
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectSkuService subjectSkuService;

    @Autowired
    private CourseSkuService courseSkuService;

    @Autowired
    private CourseImgService courseImgService;

    @Autowired
    private CourseRecommendService courseRecommendService;

    @Autowired
    private CourseDetailService courseDetailService;

    @Autowired
    private CourseTeacherService courseTeacherService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private HttpClientService httpClientService;

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
        String sql = "select Id,ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,InstitutionId,KeyWord,Feature,`Order`,Status,AddTime from SG_Course where Status > ? order by Id desc";
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
                entity.setFeature(list.get(i).get("Feature") == null ? "" : list.get(i).get("Feature").toString());
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setMinAge(Integer.parseInt(list.get(i).get("MinAge").toString()));
                entity.setMaxAge(Integer.parseInt(list.get(i).get("MaxAge").toString()));
                entity.setJoined(Integer.parseInt(list.get(i).get("Joined").toString()));
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setGoal(list.get(i).get("Goal").toString());
                entity.setFlow(list.get(i).get("Flow").toString());
                entity.setTips(list.get(i).get("Tips").toString());
                entity.setInstitutionId(Integer.parseInt(list.get(i).get("InstitutionId").toString()));
                entity.setKeyWord(list.get(i).get("KeyWord").toString());
                entity.setOrder(Integer.parseInt(list.get(i).get("Order").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    /**
     * 过滤试听课下线课程
     * @param id
     * @return
     */
    @Override
    public List<Course> getEntitysBySubId(int id) {
        List<Course> reData = new ArrayList<Course>();
        String sql = "select Id,ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,InstitutionId,KeyWord,Feature,`Order`,Status,AddTime from SG_Course where SubjectId = ? and Status > ? and Status != ? ";
        Object [] params = new Object[]{id, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
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
                entity.setFeature(list.get(i).get("Feature") == null ? "" : list.get(i).get("Feature").toString());
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setMinAge(Integer.parseInt(list.get(i).get("MinAge").toString()));
                entity.setMaxAge(Integer.parseInt(list.get(i).get("MaxAge").toString()));
                entity.setJoined(Integer.parseInt(list.get(i).get("Joined").toString()));
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setGoal(list.get(i).get("Goal").toString());
                entity.setFlow(list.get(i).get("Flow").toString());
                entity.setTips(list.get(i).get("Tips").toString());
                entity.setInstitutionId(Integer.parseInt(list.get(i).get("InstitutionId").toString()));
                entity.setKeyWord(list.get(i).get("KeyWord").toString());
                entity.setOrder(Integer.parseInt(list.get(i).get("Order").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                if (courseRecommendService.getEntitysByCourseId(course_id).size() > 0){entity.setFlag(1);}

                if (getEntitysById(course_id).size() > 0){entity.setCopyFlag(1);}

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Course> getCoursesBySubId(int id) {
        List<Course> reData = new ArrayList<Course>();
        String sql = "select * from (select Id,ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,InstitutionId,KeyWord,Feature,`Order`,Status,AddTime from SG_Course where SubjectId = ? and Status > ? order by `Order` asc ) as course order by status asc ";
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
                entity.setFeature(list.get(i).get("Feature") == null ? "" : list.get(i).get("Feature").toString());
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setMinAge(Integer.parseInt(list.get(i).get("MinAge").toString()));
                entity.setMaxAge(Integer.parseInt(list.get(i).get("MaxAge").toString()));
                entity.setJoined(Integer.parseInt(list.get(i).get("Joined").toString()));
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setGoal(list.get(i).get("Goal").toString());
                entity.setFlow(list.get(i).get("Flow").toString());
                entity.setTips(list.get(i).get("Tips").toString());
                entity.setInstitutionId(Integer.parseInt(list.get(i).get("InstitutionId").toString()));
                entity.setKeyWord(list.get(i).get("KeyWord").toString());
                entity.setOrder(Integer.parseInt(list.get(i).get("Order").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                if (courseRecommendService.getEntitysByCourseId(course_id).size() > 0){entity.setFlag(1);}

                if (getEntitysById(course_id).size() > 0){entity.setCopyFlag(1);}

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Course> getCoursesTrialBySubId() {
        List<Course> reData = new ArrayList<Course>();
        String sql = "select * from (select a.Id,a.ParentId,a.SubjectId,a.Title,a.Cover,a.MinAge,a.MaxAge,a.Joined,a.Price,a.Goal,a.Flow,a.Tips,a.Feature,a.InstitutionId,a.Status,a.AddTime,a.KeyWord,b.Title as subjectTitle FROM SG_Course a,SG_Subject b where a.status > 0 and a.status != 3 and b.Status > 0 and a.SubjectId = b.Id and b.type = 2 order by a.addTime desc) as course order by status asc";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Course entity = new Course();
                int course_id = Integer.parseInt(list.get(i).get("Id").toString());
                entity.setId(course_id);
                entity.setParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                int sub_id = Integer.parseInt(list.get(i).get("SubjectId").toString());
                entity.setSubjectId(sub_id);
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setFeature(list.get(i).get("Feature") == null ? "" : list.get(i).get("Feature").toString());
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setMinAge(Integer.parseInt(list.get(i).get("MinAge").toString()));
                entity.setMaxAge(Integer.parseInt(list.get(i).get("MaxAge").toString()));
                entity.setJoined(Integer.parseInt(list.get(i).get("Joined").toString()));
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setGoal(list.get(i).get("Goal").toString());
                entity.setFlow(list.get(i).get("Flow").toString());
                entity.setTips(list.get(i).get("Tips").toString());
                entity.setInstitutionId(Integer.parseInt(list.get(i).get("InstitutionId").toString()));
                entity.setKeyWord(list.get(i).get("KeyWord").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                entity.setSubjectTitle(list.get(i).get("subjectTitle").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Course> getEntitysById(int id) {
        List<Course> reData = new ArrayList<Course>();
        String sql = "select Id,ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,InstitutionId,KeyWord,Feature,`Order`,Status,AddTime from SG_Course where ParentId = ? and Status > ? and Status != ? ";
        Object [] params = new Object[]{id, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Course entity = new Course();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                int sub_id = Integer.parseInt(list.get(i).get("SubjectId").toString());
                entity.setSubjectId(sub_id);
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setFeature(list.get(i).get("Feature") == null ? "" : list.get(i).get("Feature").toString());
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setMinAge(Integer.parseInt(list.get(i).get("MinAge").toString()));
                entity.setMaxAge(Integer.parseInt(list.get(i).get("MaxAge").toString()));
                entity.setJoined(Integer.parseInt(list.get(i).get("Joined").toString()));
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setGoal(list.get(i).get("Goal").toString());
                entity.setFlow(list.get(i).get("Flow").toString());
                entity.setTips(list.get(i).get("Tips").toString());
                entity.setInstitutionId(Integer.parseInt(list.get(i).get("InstitutionId").toString()));
                entity.setKeyWord(list.get(i).get("KeyWord").toString());
                entity.setOrder(Integer.parseInt(list.get(i).get("Order").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public Course get(int id) {
        String sql = "select Id,ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,Notice,InstitutionId,KeyWord,Feature,`Order`,Status,AddTime from SG_Course where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Course entity = new Course();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setParentId(rs.getInt("ParentId"));
                entity.setSubjectId(rs.getInt("SubjectId"));
                entity.setTitle(rs.getString("Title"));
                entity.setFeature(rs.getString("Feature"));
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
                entity.setKeyWord(rs.getString("KeyWord"));
                entity.setOrder(rs.getInt("Order"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));

                if (rs.getInt("InstitutionId") == 0) {
                    entity.setInstitution(new Institution());
                }else{
                    entity.setInstitution(institutionService.get(rs.getInt("InstitutionId")));
                }
            }
        });

        return entity;
    }

    @Override
    public List<Course> getRecommend() {
        List<Course> reData = new ArrayList<Course>();
        String sql = "select * from (select a.Id,a.ParentId,a.SubjectId,a.Title,a.Cover,a.MinAge,a.MaxAge,a.Joined,a.Price,a.Goal,a.Flow,a.Tips,a.Feature,a.InstitutionId,a.Status,a.AddTime,a.KeyWord FROM SG_Course a,SG_CourseRecommend b where a.status > 0 and a.status != 3 and b.Status > 0 and a.Id = b.CourseId order by b.Weight desc) as course order by status asc ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Course entity = new Course();
                int course_id = Integer.parseInt(list.get(i).get("Id").toString());
                entity.setId(course_id);
                entity.setParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                int sub_id = Integer.parseInt(list.get(i).get("SubjectId").toString());
                entity.setSubjectId(sub_id);
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setFeature(list.get(i).get("Feature") == null ? "" : list.get(i).get("Feature").toString());
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setMinAge(Integer.parseInt(list.get(i).get("MinAge").toString()));
                entity.setMaxAge(Integer.parseInt(list.get(i).get("MaxAge").toString()));
                entity.setJoined(Integer.parseInt(list.get(i).get("Joined").toString()));
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setGoal(list.get(i).get("Goal").toString());
                entity.setFlow(list.get(i).get("Flow").toString());
                entity.setTips(list.get(i).get("Tips").toString());
                entity.setInstitutionId(Integer.parseInt(list.get(i).get("InstitutionId").toString()));
                entity.setKeyWord(list.get(i).get("KeyWord").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                entity.setSubjectTitle(subjectService.get(Integer.parseInt(list.get(i).get("SubjectId").toString())).getTitle());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insert(Course entity) {
        String sql = "insert into SG_Course(ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,Notice,InstitutionId,KeyWord,Feature,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getParentId(), entity.getSubjectId(), entity.getTitle(), entity.getCover(), entity.getMinAge(), entity.getMaxAge(), entity.getJoined(), entity.getPrice(), entity.getGoal(), entity.getFlow(), entity.getTips(), entity.getNotice(), entity.getInstitutionId(), entity.getKeyWord(), entity.getFeature(), Quantity.STATUS_TWO};
        int reData = 0;
        try {
            reData = jdbcTemplate.update(sql, params);
        }catch(Exception _ex){
            log.error("CourseServiceImpl -> insert -> error info:"+_ex.getMessage());
        }
        return reData;
    }

    @Override
    public int insertKey(Course course) {
        final Course entity = course;
        final String sql = "insert into SG_Course(ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,Notice,InstitutionId,KeyWord,Feature,`Order`,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
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
                    ps.setString(++i, entity.getKeyWord());
                    ps.setString(++i, entity.getFeature());
                    ps.setInt(++i, entity.getOrder());
                    ps.setInt(++i, Quantity.STATUS_TWO);

                    return ps;
                }
            }, keyHolder);
        }catch (Exception _ex){
            log.error("CourseServiceImpl -> insertKey -> error info:" +_ex.getMessage());
        }
        if (reData > 0) {
            reData = keyHolder.getKey().intValue();
        }
        return reData;
    }

    @Override
    public int update(Course entity) {
        String sql = "update SG_Course set SubjectId = ?, Title = ?, Cover = ?, MinAge = ?, MaxAge = ?, Price = ?, Goal = ?, Flow = ?, Tips = ?, InstitutionId = ?, keyWord = ?, Feature = ? where Id = ? ";
        Object [] params = new Object[]{entity.getSubjectId(), entity.getTitle(), entity.getCover(), entity.getMinAge(), entity.getMaxAge(), entity.getPrice(), entity.getGoal(), entity.getFlow(), entity.getTips(), entity.getInstitutionId(), entity.getKeyWord(), entity.getFeature(), entity.getId()};
        int reData = 0;
        try{
            reData = jdbcTemplate.update(sql,params);
        }catch (Exception _ex){
            log.error("CourseServiceImpl -> update -> error info:" + _ex.getMessage());
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

    private int updateOrder(Course course) {
        String sql = "update SG_Course set `Order` = ? where Id = ? ";
        Object [] params = new Object[]{course.getOrder(), course.getId()};
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
        int sub_id = Integer.parseInt(request.getParameter("subjectId"));
        entity.setSubjectId(sub_id);
        entity.setTitle(request.getParameter("title"));
        entity.setFeature(request.getParameter("feature"));
        entity.setCover(request.getParameter("cover"));
        entity.setMinAge(Integer.parseInt(request.getParameter("minAge")));
        entity.setMaxAge(Integer.parseInt(request.getParameter("maxAge")));
        entity.setPrice(new BigDecimal(StringUtils.isEmpty(request.getParameter("price"))?"0":request.getParameter("price")));
        entity.setGoal("");
        entity.setFlow("");
        entity.setTips(request.getParameter("tips"));
        entity.setNotice("");
        entity.setInstitutionId(Integer.parseInt(request.getParameter("institutionId")));
        entity.setKeyWord(request.getParameter("keyWord"));
        entity.setOrder(this.getCourseOrderMaxBySubId(sub_id)+1);

        return entity;
    }

    @Override
    public Course formEntityTrial(HttpServletRequest request, Course entity) {

        entity.setTitle(request.getParameter("title"));
        entity.setFeature(request.getParameter("feature"));
        entity.setCover(request.getParameter("cover"));
        entity.setMinAge(Integer.parseInt(request.getParameter("minAge")));
        entity.setMaxAge(Integer.parseInt(request.getParameter("maxAge")));
        entity.setPrice(new BigDecimal(StringUtils.isEmpty(request.getParameter("price"))?"0":request.getParameter("price")));
        entity.setGoal("");
        entity.setFlow("");
        entity.setTips(request.getParameter("tips"));
        entity.setNotice("");
        entity.setInstitutionId(Integer.parseInt(request.getParameter("institutionId")));

        return entity;
    }

    /**
     * 复制试听课程
     * @param req
     * @return
     */
    @Override
    public int insert_copyCourseTrial(HttpServletRequest req){
        int reSuccess = 1;
        try{
            int course_id = Integer.parseInt(req.getParameter("course_id"));//父级课程id
            BigDecimal price = new BigDecimal(req.getParameter("price"));//页面填写的出售价格
//            BigDecimal originalPrice = new BigDecimal(req.getParameter("originalPrice"));//页面填写的原始价格
            Course cour = this.get(course_id);//获取父级课程对象
            Subject sub = subjectService.get(cour.getSubjectId());//获取父级课程所属课程体系对象
            sub.setType(Quantity.STATUS_TWO);//设置要生成的试听课的课程体系类型值为2（1:普通课程体系；2:试听课课程体系）
            sub.setTags(sub.getTitle());//设置要生成的试听课的课程体系标签为自己的标题内容
            int sub_id = subjectService.insertKey(sub);//添加新增试听课的所属课程体系并返回id值
            log.info("CourseServiceImpl -> insert_copyCourseTrial -> info:添加试听课subject结束.");
            if (sub_id > 0){
                cour.setParentId(cour.getId());//设置新增试听课的父级id值
                cour.setSubjectId(sub_id);//设置新增试听课的所属课程体系id值
                cour.setPrice(price);//设置新增试听课的价格值
                int course_new_id = this.insertKey(cour);//添加新增试听课的内容并返回id值
                log.info("CourseServiceImpl -> insert_copyCourseTrial -> info:添加新增试听课course结束.");
                if (course_new_id > 0){
                    int int_notice = this.updateNotice(course_new_id,req.getParameter("notice"));//更新试听课的购买须知
                    log.info("CourseServiceImpl -> insert_copyCourseTrial -> info:更新试听课的购买须知结束.");
                    if (int_notice > 0){
                        SubjectSku sub_sku = new SubjectSku();//创建新新增试听课的所属课程体系的sku对象
                        sub_sku.setSubjectId(sub_id);//设置sku所属课程体系id值
                        sub_sku.setCourseId(course_new_id);//设置sku所属试听课的id值
                        sub_sku.setPrice(price);//设置sku出售价格
                        sub_sku.setOriginalPrice(price);//设置sku原始价格等于售卖价格
                        sub_sku.setDesc("试听课程,每人只有一次试听机会.");//设置sku的描述
                        sub_sku.setAdult(Quantity.STATUS_ZERO);//设置参加试听课的大人数量0
                        sub_sku.setChild(Quantity.STATUS_ZERO);//设置参加试听课的儿童数量0
                        sub_sku.setCourseCount(Quantity.STATUS_ONE);//设置参加试听课的课程数量1
                        sub_sku.setTime(Quantity.STATUS_ONE);////设置参加试听课的时间数量1
                        sub_sku.setTimeUnit(Quantity.STATUS_ONE);//设置参加试听课的时间单位1个月
                        sub_sku.setLimit(Quantity.STATUS_ONE);//限购次数
                        int int_sub_sku = subjectSkuService.insertOne(sub_sku);//新增sku信息
                        log.info("CourseServiceImpl -> insert_copyCourseTrial -> info:新增subject的sku信息结束.");
                        if (int_sub_sku > 0){
                            String[] course_sku_id = req.getParameterValues("course_sku_id");//获取页面勾选的父级sku信息集合
                            int sum = 0;//定义试听课所属课程体系中的stock数量，赋值为0
                            for (int i = 0; i < course_sku_id.length; i++) {
                                int page_course_sku_id = Integer.parseInt(course_sku_id[i]);//获取勾选的父级sku集合中的单条sku的id值
                                CourseSku course_sku = courseSkuService.get(page_course_sku_id);//根据sku的id值获取父级课程的sku对象
                                int old_stock = course_sku.getStock();//获取父级sku的库存数量
                                int old_un_stock = course_sku.getUnlockedStock();//获取父级sku的库存剩余数量
                                String stock_str = "stock_" + page_course_sku_id;
                                int new_stock = Integer.parseInt(req.getParameter(stock_str));//获取页面填写的课程对应的sku的子级库存数量
                                int old_stock_1 = old_stock - new_stock; //获取父级sku的库存数量减去子级sku的库存数量的值
                                int old_un_stock_1 = old_un_stock - new_stock;//获取父级sku的剩余库存数量减去子级sku的库存数量的值

                                course_sku.setStock(old_stock_1);//设置父级sku的库存数量
                                course_sku.setUnlockedStock(old_un_stock_1);//设置父级sku的剩余库存数量
                                courseSkuService.update(course_sku);//更新父级sku信息
                                log.info("CourseServiceImpl -> insert_copyCourseTrial -> info:更新试听课父级sku信息结束."+i);

                                course_sku.setCourseId(course_new_id);//设置子级sku所属课程id值
                                course_sku.setStock(new_stock);//设置子级sku库存数量
                                course_sku.setUnlockedStock(new_stock);//设置子级sku剩余库存数量
                                course_sku.setLockedStock(Quantity.STATUS_ZERO);//设置子级sku售卖库存数量
                                course_sku.setParentId(course_sku.getId());//设置子级sku所属父级id的值

                                int int_course_sku = courseSkuService.insert(course_sku);//创建子级sku对象
                                log.info("CourseServiceImpl -> insert_copyCourseTrial -> info:创建子级sku对象结束."+i);

                                sum = sum + new_stock;//计算试听课所属课程体系中的stock数量
                            }

                            int update_stock = subjectService.updateStock(sub_id, sum);//更新试听课所属课程体系中的stock数量
                            log.info("CourseServiceImpl -> insert_copyCourseTrial -> info:更新试听课所属课程体系中的stock数量:"+sum);
//                            List<CourseImg> img_ls = courseImgService.getModelsBySub_Key(course_id);//获取父级课程轮播图片
//                            for (CourseImg img : img_ls) {
//                                img.setCourseId(course_new_id);
//                                courseImgService.insert(img);//添加子级课程的轮播图片
//                            }
//                            List<CourseBook> book_ls = courseBookService.getEntitys(course_id);//获取父级课程课前绘本
//                            for (CourseBook book : book_ls) {
//                                book.setCourseId(course_new_id);
//                                courseBookService.insert(book);//添加子级课程的课前绘本
//                            }
//
//                            List<CourseDetail> detail_ls = courseDetailService.getModelsByCourse_Key(course_id);//获取父级课程图文详情
//                            for (CourseDetail cour_detail : detail_ls) {
//                                cour_detail.setCourseId(course_new_id);
//                                courseDetailService.insert(cour_detail);//添加子级课程的图文详情
//                            }
//
//                            List<CourseTeacher> teacher_ls = courseTeacherService.getCourseTeachers(course_id);//获取父级课程讲师团
//                            for (CourseTeacher CourseTeacher : teacher_ls){
//                                courseTeacherService.insert(course_new_id,CourseTeacher.getTeacherId());//添加子级课程的讲师团
//                            }

                            reSuccess = 0;
                        }
                    }
                }
            }

        }catch (Exception _ex){
            log.error("CourseServiceImpl -> insert_copyCourseTrial -> error info:"+_ex.getMessage());
        }

        return reSuccess;
    }

    /**
     * 取消试听课
     * @param course_id
     * @return
     */
    @Override
    public int deleteTrialCourse(int course_id){
        int reSuccess = 0;
        try {
            List<Course> courses = getEntitysById(course_id);
            log.info("CourseServiceImpl -> deleteTrialCourse -> info:开始进行试听课取消操作.");
            if (courses.size() > 0) {
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                    Subject subject = subjectService.get(course.getSubjectId());
                    int sub_stock = subject.getStock();
                    List<CourseSku> courseSkus = courseSkuService.getModelsByCourse_Key(courses.get(i).getId());
                    if (courseSkus.size() > 0) {
                        for (int j = 0; j < courseSkus.size(); j++) {
                            CourseSku course_sku_c = courseSkus.get(j);
                            int parentId = course_sku_c.getParentId();
                            int c_stock_un = course_sku_c.getUnlockedStock();
                            if (c_stock_un == 0) {
                                continue;
                            } else {
                                CourseSku course_sku_p = courseSkuService.get(parentId);
                                course_sku_p.setStock(course_sku_p.getStock() + c_stock_un);
                                course_sku_p.setUnlockedStock(course_sku_p.getUnlockedStock() + c_stock_un);
                                sub_stock = sub_stock - c_stock_un;
                                courseSkuService.update(course_sku_p);
                            }
                        }
                    }
                    subjectService.updateStock(subject.getId(), sub_stock);
                    this.updateStatus(course.getId(),Quantity.STATUS_THREE);
                }
            }
            log.info("CourseServiceImpl -> deleteTrialCourse -> info:试听课取消操作结束.");
        }catch (Exception _ex){
            reSuccess = 1;
            log.error("CourseServiceImpl -> deleteTrialCourse -> error info:"+_ex.getMessage());
        }
        return reSuccess;
    }

    /**
     * 推荐课程
     * @param req
     * @return
     */
    @Override
    public Map<String,Object> insert_setOneCourse(HttpServletRequest req){
        int reSuccess = 1;
        Map<String,Object> map = new HashMap<String, Object>();
        int id = Integer.parseInt(req.getParameter("course_id"));
        int set_id = Integer.parseInt(req.getParameter("set_id"));
        int sku_id = Integer.parseInt(req.getParameter("sku_id"));
        int sub_id = Integer.parseInt(req.getParameter("sub_id"));
        String noticeStr = req.getParameter("notice");
        log.info("CourseServiceImpl -> insert_setOneCourse -> info:推荐课程操作开始.");
        try{
            if (set_id == 0){
                set_id = courseRecommendService.insertKey(courseRecommendService.formEntity(req, id, Quantity.STATUS_ZERO));
                if (set_id > 0) {
                    int notice = this.updateNotice(id, noticeStr);
                    if (notice > 0){
                        SubjectSku subjectSku = subjectSkuService.formEntityOne(req, Quantity.STATUS_ZERO, Quantity.STATUS_ZERO);
                        subjectSku.setCourseId(id);
                        subjectSku.setSubjectId(sub_id);
                        int sub_sku = subjectSkuService.insertOne(subjectSku);
                        if (sub_sku > 0){
                            reSuccess = 0;
                        }
                    }
                }else{
                    CourseRecommend courseRecommend = courseRecommendService.get(set_id);
                    int weight = Integer.parseInt(req.getParameter("weight"));
                    courseRecommend.setWeight(weight);
                    int course_recommend = courseRecommendService.update(courseRecommend);
                    if (course_recommend > 0){
                        int notice = this.updateNotice(id, noticeStr);
                        if (notice > 0) {
                            SubjectSku subjectSku = subjectSkuService.formEntityOne(req, Quantity.STATUS_ZERO, sku_id);
                            if (subjectSkuService.update(subjectSku) > 0){
                                reSuccess = 0;
                            }
                        }
                    }
                }
            }
        }catch (Exception _ex){
            log.info("CourseServiceImpl -> insert_setOneCourse -> error info:" + _ex.getMessage());
        }

        log.info("CourseServiceImpl -> insert_setOneCourse -> info:推荐课程操作结束.");

        map.put("success",reSuccess);
        map.put("set_id",set_id);
        map.put("sku_id",sku_id);

        return map;
    }

    /**
     * 取消推荐课程
     * @param course_id
     * @return
     */
    @Override
    public int update_cancelCourse(int course_id){
        int reSuccess = 1;
        try{
            List<CourseRecommend> courseRecommends = courseRecommendService.getEntitysByCourseId(course_id);
            if (courseRecommends.size() > 0){
                for (int i = 0; i < courseRecommends.size(); i++) {
                    courseRecommendService.delete(courseRecommends.get(i).getId());
                }
            }
            reSuccess = 0;
        }catch (Exception _ex){
            _ex.printStackTrace();
        }

        return reSuccess;
    }

    /**
     * 创建群组
     * @param req
     * @param course_id
     * @return
     */
    @Override
    public HttpResult createQz(HttpServletRequest req, int course_id, int sku_id) {
        String teacher_ids = "10000,";
        String upload_qz_url = configuration.getString(Quantity.UPLOAD_QZ);
        List<CourseTeacher> courseTeachers = courseTeacherService.getCourseTeachersBySkuId(course_id,sku_id);
        String qz_name = this.get(course_id).getKeyWord();
//        HttpResult result = new HttpResult();
//        if (courseTeachers.size() > 0){
            for (CourseTeacher courseTeacher : courseTeachers){
                Teacher teacher = teacherService.get(courseTeacher.getTeacherId());
                teacher_ids = teacher_ids + teacher.getUserId() + ",";
            }
            teacher_ids = teacher_ids.substring(0,teacher_ids.length()-1);//得到讲师的id串，以","隔开
            CourseSku courseSku = courseSkuService.get(sku_id);
            String time = DateUtil.getDateStr(DateUtil.StrToDate(courseSku.getStartTime()));
            Place place = placeService.get(courseSku.getPlaceId());
            String region_name = "";
            if (place.getRegionId() != 0){
                region_name = regionService.get(place.getRegionId()).getName();
            }
            String qz = time+qz_name+region_name;//组装群组名称

            StringBuffer sb = new StringBuffer();
            sb.append("coid=").append(course_id);
            long expired = DateUtil.getDateExpired(configuration.getInt(Quantity.SERVICE_PORT_TIME));//请求有效期
            sb.append("expired=").append(expired);
            sb.append("name=").append(qz);
            sb.append("sid=").append(sku_id);
            sb.append("tids=").append(teacher_ids);
            sb.append("key=").append(configuration.getString(Quantity.SERVICE_PORT_KEY));

            String sign = StringUtil.getSign(sb.toString());//请求加密串

            String param = "coid="+course_id+"&expired="+expired+"&name="+qz+"&sid="+sku_id+"&tids="+teacher_ids+"&sign="+sign;
            log.info("CourseServiceImpl -> createQz -> info:创建群组组装参数结束.");
            log.info("CourseServiceImpl -> createQz -> info:创建群组发出请求.");
            JSONObject jsonObject = httpClientService.httpPost(upload_qz_url, param);
            log.info("CourseServiceImpl -> createQz -> info:创建群组发出请求结束.");
            HttpResult result = CastUtil.toObject(jsonObject, HttpResult.class);
            log.info("CourseServiceImpl -> createQz -> info:创建群组发出请求结束,返回errno:"+result.getErrno());
//        }else{
//            result.setErrno(-1);
//            result.setErrmsg("error");
//            result.setTime((new Date()).getTime());
//            result.setData("false");
//        }

        return result;
    }


    /**
     * 课程，试听课，推荐课程预览
     * @param course_id
     * @return
     */
    @Override
    public String getPreview(int course_id){
        StringBuffer sb = new StringBuffer();
        Course course = this.get(course_id);
        if (course.getParentId() > 0){
            course_id = this.get(course.getParentId()).getId();
        }
        String fm_pic_url = configuration.getString(Quantity.DISPLAY_IMAGE) + course.getCover();
        List<CourseSku> courseSkus = courseSkuService.getModelsByCourse_Key(course.getId());
        List<CourseImg> courseImgs = courseImgService.getModelsBySub_Key(course_id);
        List<Teacher> teachers = courseTeacherService.getByCourseId(course_id);
//        List<CourseBook> courseBooks = courseBookService.getEntitys(course_id);
        List<CourseDetail> courseDetails = courseDetailService.getModelsByCourse_Key(course_id);
        sb.append("<div class='ibox-title'>");
        sb.append("<h5>" + course.getTitle() + "</h5>");
        sb.append("</div>");
        sb.append("<div class='ibox-content'>");
        sb.append("<div class='well'>");
        sb.append("<p  align='center'><img src='" + fm_pic_url + "' style='width: 300px;height: 200px'></p>");
        sb.append("</div>");

        sb.append("<div class='well'>");
        sb.append("<h3>活动图片</h3>");
        for (CourseImg courseImg : courseImgs) {
            String lb_pic_url = configuration.getString(Quantity.DISPLAY_IMAGE) + courseImg.getUrl();
            sb.append("<p  align='center'><img src='" + lb_pic_url + "' style='width: 300px;height: 200px'></p>");
        }
        sb.append("</div>");

        sb.append("<div class='well'>");
        sb.append("<h3>适合年龄</h3>");
        sb.append("<p>").append(course.getMinAge()+"至"+course.getMaxAge()+"岁").append("</p>");
        sb.append("</div>");

        sb.append("<div class='well'>");
        sb.append("<h3>课程表</h3>");
        for (CourseSku courseSku : courseSkus) {
            sb.append("<p>");
            sb.append(DateUtil.DateToStr_Cn(courseSku.getStartTime()) + " " + courseSku.getPlaceName());
            sb.append("</p>");
        }
        sb.append("</div>");

        sb.append("<div class='well'>");
        sb.append("<h3>课程内容</h3>");
        for (CourseDetail courseDetail : courseDetails) {
            sb.append("<p>"+courseDetail.getAbstracts()+"</p>");
            List<Map<String,Object>> detail_list = StringUtil.parseJSON2List(courseDetail.getDetail());
            for (int i = 0; i < detail_list.size(); i++) {
                sb.append("<h4>").append(detail_list.get(i).get("title")).append("</h4>");
                List<Map<String,String>> content_list = (List<Map<String, String>>) detail_list.get(i).get("content");
                for (int j = 0; j < content_list.size(); j++) {
                    if (content_list.get(j).get("text") != null){
                        sb.append("<p>"+content_list.get(j).get("text").toString().replace("\r\n","<br>")+"</p>");
                    }
                    if (content_list.get(j).get("img") != null){
                        String img_url = configuration.getString(Quantity.DISPLAY_IMAGE) + content_list.get(j).get("img");
                        sb.append("<p  align='center'><img src='"+img_url+"' style='width: 300px;height: 200px'></p>");
                    }
                }
            }
        }
        sb.append("</div>");

        sb.append("<div class='well'>");
        sb.append("<h3>注意事项</h3>");
        sb.append("<p>").append(course.getTips().replace("\r\n", "<br>")).append("</p>");
        sb.append("</div>");

        sb.append("<div class='well'>");
        sb.append("<h3>讲师团</h3>");
        for (Teacher teacher : teachers) {
            String pic_path = StringUtils.isEmpty(teacher.getAvatar())?teacher.getPic():teacher.getAvatar();
            String js_pic_url = configuration.getString(Quantity.DISPLAY_IMAGE) + pic_path;
            int job = teacher.getJob();
            String job_str = "";
            switch (job) {
                case Quantity.STATUS_ONE:
                    job_str = "讲师";
                    break;
                case Quantity.STATUS_TWO:
                    job_str = "培训师";
                    break;
                case Quantity.STATUS_THREE:
                    job_str = "助教";
                    break;
            }
            sb.append("<p><img src='"+js_pic_url+"' style='width: 100px;height: 80px'>").append("&numsp;&numsp;").append(teacher.getName() + " " + job_str).append("</p>");
        }
        sb.append("</div>");

//            sb.append("<div class='well'>");
//            sb.append("<h3>特别提示</h3>");
//            sb.append("<p>").append(course.getTips().replace("\r\n", "<br>")).append("</p>");
//            sb.append("</div>");

        sb.append("<div class='well'>");
            sb.append("<h3>合作机构</h3>");
            if (course.getInstitution().getId() > 0) {
                sb.append("<h4>").append(course.getInstitution().getName()).append("</h4>");
                String in_url = course.getInstitution().getCover();
                if (in_url != null && !in_url.equals("")) {
                    String img_url = configuration.getString(Quantity.DISPLAY_IMAGE) + in_url;
                    sb.append("<p  align='center'><img src='" + img_url + "' style='width: 300px;height: 200px'></p>");
                }
                sb.append("<p>").append(course.getInstitution().getIntro().replace("\r\n", "<br>")).append("</p>");
            }
            sb.append("</div>");

        sb.append("</div>");

        return sb.toString();
    }

//    @Override
    public int getCourseOrderMaxBySubId(int sub_id) {
        String sql = "SELECT max(`Order`) as `Order` FROM SG_Course where Status > 0 and Status != 3 and SubjectId ="+sub_id;
        final Map reData = new HashMap();
        jdbcTemplate.query(sql, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                reData.put("order", rs.getInt("Order"));
            }
        });

        return Integer.parseInt(reData.get("order").toString());
    }

    @Override
    public void update_order(HttpServletRequest req){
        int cour_id = Integer.parseInt(req.getParameter("id"));
        int sub_id = Integer.parseInt(req.getParameter("subid"));
        int flag = Integer.parseInt(req.getParameter("flag"));
        List<Course> courses = this.getCoursesBySubId(sub_id);

        try{
            update_ListIndex(courses, cour_id, flag);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
    }

    private void update_ListIndex(List<Course> courses, int cour_id, int flag){
        int list_index1 = 0;
        int list_index2 = 0;
        if (courses.size() > 0) {
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getId() == cour_id) {
                    list_index1 = i;
                    break;
                }
            }
            if (flag == 1) {
                list_index2 = list_index1 - 1;
                if (list_index2 >= 0) {
                    update_exchange(courses, list_index1, list_index2);
                }
            } else {
                list_index2 = list_index1 + 1;
                if (list_index2 <= courses.size()-1) {
                    update_exchange(courses, list_index1, list_index2);
                }
            }
        }
    }

    private int update_exchange(List<Course> courses, int list_index1, int list_index2){
        int reData = 1;
        Course entity1 = courses.get(list_index1);
        Course entity2 = courses.get(list_index2);
        if (entity1.getStatus() == entity2.getStatus()) {
            Course course1 = this.get(entity1.getId());
            int order1 = course1.getOrder();
            Course course2 = this.get(entity2.getId());
            int order2 = course2.getOrder();
            course1.setOrder(order2);
            course2.setOrder(order1);
            int index1_int = this.updateOrder(course1);
            if (index1_int > 0) {
                int index2_int = this.updateOrder(course2);
                if (index2_int > 0) {
                    reData = 0;
                }
            }
        }

        return reData;
    }

}
