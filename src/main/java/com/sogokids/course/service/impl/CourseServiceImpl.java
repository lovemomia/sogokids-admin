package com.sogokids.course.service.impl;

import cn.momia.common.config.Configuration;
import com.alibaba.fastjson.JSONObject;
import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseBook;
import com.sogokids.course.model.CourseDetail;
import com.sogokids.course.model.CourseImg;
import com.sogokids.course.model.CourseRecommend;
import com.sogokids.course.model.CourseSku;
import com.sogokids.course.model.CourseTeacher;
import com.sogokids.course.service.CourseBookService;
import com.sogokids.course.service.CourseDetailService;
import com.sogokids.course.service.CourseImgService;
import com.sogokids.course.service.CourseRecommendService;
import com.sogokids.course.service.CourseService;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.course.service.CourseTeacherService;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.model.SubjectImg;
import com.sogokids.subject.model.SubjectSku;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.system.model.Institution;
import com.sogokids.system.model.Place;
import com.sogokids.system.model.Region;
import com.sogokids.system.model.Teacher;
import com.sogokids.system.service.InstitutionService;
import com.sogokids.system.service.PlaceService;
import com.sogokids.system.service.RegionService;
import com.sogokids.system.service.TeacherService;
import com.sogokids.utils.entity.QzResult;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import com.sogokids.utils.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;
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

    @Autowired
    private SubjectSkuService subjectSkuService;

    @Autowired
    private CourseSkuService courseSkuService;

    @Autowired
    private CourseImgService courseImgService;

    @Autowired
    private CourseBookService courseBookService;

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

    /**
     * 过滤试听课下线课程
     * @param id
     * @return
     */
    @Override
    public List<Course> getEntitysBySubId(int id) {
        List<Course> reData = new ArrayList<Course>();
        String sql = "select Id,ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,InstitutionId,Status,AddTime from SG_Course where SubjectId = ? and Status > ? and Status != ? ";
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

                if (courseRecommendService.getEntitysByCourseId(course_id).size() > 0){entity.setFlag(1);}

                if (getEntitysById(course_id).size() > 0){entity.setCopyFlag(1);}

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Course> getEntitysById(int id) {
        List<Course> reData = new ArrayList<Course>();
        String sql = "select Id,ParentId,SubjectId,Title,Cover,MinAge,MaxAge,Joined,Price,Goal,Flow,Tips,InstitutionId,Status,AddTime from SG_Course where ParentId = ? and Status > ? and Status != ? ";
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
    public Course getRecommend(int id) {
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

                if (courseRecommendService.getEntitysByCourseId(rs.getInt("Id")).size() > 0){entity.setFlag(1);}

                if (getEntitysById(rs.getInt("Id")).size() > 0){entity.setCopyFlag(1);}
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
            if (sub_id > 0){
                cour.setParentId(cour.getId());//设置新增试听课的父级id值
                cour.setSubjectId(sub_id);//设置新增试听课的所属课程体系id值
                cour.setPrice(price);//设置新增试听课的价格值
                int course_new_id = this.insertKey(cour);//添加新增试听课的内容并返回id值
                if (course_new_id > 0){
                    int int_notice = this.updateNotice(course_new_id,req.getParameter("notice"));//更新试听课的购买须知
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

                                course_sku.setCourseId(course_new_id);//设置子级sku所属课程id值
                                course_sku.setStock(new_stock);//设置子级sku库存数量
                                course_sku.setUnlockedStock(new_stock);//设置子级sku剩余库存数量
                                course_sku.setLockedStock(Quantity.STATUS_ZERO);//设置子级sku售卖库存数量
                                course_sku.setParentId(course_sku.getId());//设置子级sku所属父级id的值

                                int int_course_sku = courseSkuService.insert(course_sku);//创建子级sku对象

                                sum = sum + new_stock;//计算试听课所属课程体系中的stock数量
                            }

                            int update_stock = subjectService.updateStock(sub_id, sum);//更新试听课所属课程体系中的stock数量

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
            _ex.printStackTrace();
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
        }catch (Exception _ex){
            reSuccess = 1;
            _ex.printStackTrace();
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
            _ex.printStackTrace();
        }

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
    public QzResult createQz(HttpServletRequest req, int course_id) {
        String teacher_ids = "";
        String upload_qz_url = configuration.getString(Quantity.UPLOAD_QZ);
        List<CourseTeacher> courseTeachers = courseTeacherService.getCourseTeachers(course_id);
        int sku_id = Integer.parseInt(req.getParameter("qzid"));
        String qz_name = req.getParameter("qzname");
        QzResult qzResult = new QzResult();
        if (courseTeachers.size() > 0){
            for (CourseTeacher courseTeacher : courseTeachers){
                Teacher teacher = teacherService.get(courseTeacher.getTeacherId());
                teacher_ids = teacher_ids + teacher.getUserId() + ",";
            }
            teacher_ids = teacher_ids.substring(0,teacher_ids.length()-1);
            CourseSku courseSku = courseSkuService.get(sku_id);
            String time = DateUtil.getDateStr(DateUtil.StrToDate(courseSku.getStartTime()));
            Place place = placeService.get(courseSku.getPlaceId());
            String region_name = "";
            if (place.getRegionId() != 0){
                region_name = regionService.get(place.getRegionId()).getName();
            }
            String qz = time+qz_name+region_name;


            try{
                HttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(upload_qz_url);
                StringEntity reqEntity = new StringEntity("coid="+course_id+"&sid="+sku_id+"&name="+qz+"&tids="+teacher_ids, "utf-8");
                reqEntity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(reqEntity);

                HttpResponse response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    throw new RuntimeException("fail to execute request: " + httpPost);
                }else{
                    HttpEntity resEntity = response.getEntity();
                    String entityStr = EntityUtils.toString(resEntity);
                    Map<String, Object> map = StringUtil.parseJSON2Map(entityStr);
                    qzResult.setErrno(Integer.parseInt(map.get("errno").toString()));
                    qzResult.setErrmsg(map.get("errmsg").toString());
                    qzResult.setTime(map.get("time").toString());
                    qzResult.setData(map.get("data").toString());
                }
            }catch (Exception _ioex){
                _ioex.printStackTrace();
            }
        }else{
            qzResult.setErrno(-1);
            qzResult.setErrmsg("error");
            qzResult.setTime("");
            qzResult.setData("false");
        }

        return qzResult;
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
        String fm_pic_url = configuration.getString(Quantity.DISPLAY_IMAGE) + course.getCover();
        List<CourseSku> courseSkus = courseSkuService.getModelsByCourse_Key(course_id);
        List<CourseImg> courseImgs = courseImgService.getModelsBySub_Key(course_id);
        List<Teacher> teachers = courseTeacherService.getByCourseId(course_id);
//        List<CourseBook> courseBooks = courseBookService.getEntitys(course_id);
        List<CourseDetail> courseDetails = courseDetailService.getModelsByCourse_Key(course_id);
        sb.append("<div class='ibox-title'>");
        sb.append("<h5>"+course.getTitle()+"</h5>");
        sb.append("</div>");
        sb.append("<div class='ibox-content'>");
        sb.append("<div class='well'>");
        sb.append("<p  align='center'><img src='"+fm_pic_url+"' style='width: 300px;height: 200px'></p>");
        sb.append("</div>");

        sb.append("<div class='well'>");
        sb.append("<h3>适合年龄</h3>");
        sb.append("<p>").append(course.getMinAge()+"至"+course.getMaxAge()+"岁").append("</p>");
        sb.append("</div>");

        sb.append("<div class='well'>");
        sb.append("<h3>课程目标</h3>");
        sb.append("<p>").append(course.getTips().replace("\r\n", "<br>")).append("</p>");
        sb.append("</div>");

        sb.append("<div class='well'>");
        sb.append("<h3>课程表</h3>");
        for (CourseSku courseSku : courseSkus) {
            sb.append("<p>");
            sb.append(DateUtil.DateToStr_Cn(courseSku.getStartTime())+" "+ courseSku.getPlaceName());
            sb.append("</p>");
        }
        sb.append("</div>");

        sb.append("<div class='well'>");
        sb.append("<h3>讲师团</h3>");
        for (Teacher teacher : teachers) {
            String js_pic_url = configuration.getString(Quantity.DISPLAY_IMAGE) + teacher.getAvatar();
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
            sb.append("<h3>特别提示</h3>");
            sb.append("<p>").append(course.getTips().replace("\r\n", "<br>")).append("</p>");
            sb.append("</div>");

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

        sb.append("<div class='well'>");
        sb.append("<h3>轮播图片</h3>");
        for (CourseImg courseImg : courseImgs) {
            String lb_pic_url = configuration.getString(Quantity.DISPLAY_IMAGE) + courseImg.getUrl();
            sb.append("<p  align='center'><img src='"+lb_pic_url+"' style='width: 300px;height: 200px'></p>");
        }
        sb.append("</div>");

        sb.append("</div>");

        return sb.toString();
    }

}
