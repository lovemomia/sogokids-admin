package com.sogokids.teacher.service.impl;

import cn.momia.common.config.Configuration;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sogokids.course.model.Course;
import com.sogokids.course.service.CourseService;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.http.model.HttpResult;
import com.sogokids.http.service.HttpClientService;
import com.sogokids.query.model.Customer;
import com.sogokids.query.service.CustomerService;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.system.service.PlaceService;
import com.sogokids.system.service.RegionService;
import com.sogokids.teacher.model.CourseMaterial;
import com.sogokids.teacher.model.Teacher;
import com.sogokids.teacher.model.TeacherInterview;
import com.sogokids.teacher.service.CourseMaterialService;
import com.sogokids.teacher.service.CourseTeacherService;
import com.sogokids.teacher.service.TeacherEducationService;
import com.sogokids.teacher.service.TeacherInterviewService;
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
 * Created by hoze on 15/11/13.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    private Configuration configuration;

    @Autowired
    private TeacherEducationService teacherEducationService;

    @Autowired
    private CourseMaterialService courseMaterialService;

    @Autowired
    private TeacherInterviewService teacherInterviewService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CourseSkuService courseSkuService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private RegionService regionService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Teacher> getEntitys() {
        List<Teacher> reData = new ArrayList<Teacher>();
        String sql = "select Id,UserId,Name,Avatar,Pic,IdNo,Gender,Address,Education,Experience,Job,Mobile,Status,AddTime from SG_Teacher where Status = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ONE};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Teacher entity = new Teacher();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setUserId(Integer.parseInt(list.get(i).get("UserId").toString()));
                entity.setName(list.get(i).get("Name").toString());
                if (list.get(i).get("Pic") == null){
                    entity.setPic(list.get(i).get("Avatar").toString());
                }else{
                    entity.setPic(list.get(i).get("Pic").toString());
                }
                if (list.get(i).get("Education") == null){
                    entity.setEducation("");
                }else{
                    entity.setEducation(list.get(i).get("Education").toString());
                }
                if (list.get(i).get("Experience") == null){
                    entity.setExperience("");
                }else{
                    entity.setExperience(list.get(i).get("Experience").toString());
                }

                entity.setGender(list.get(i).get("Gender").toString());
                if (list.get(i).get("Job") == null){
                    entity.setJob(0);
                }else{
                    entity.setJob(Integer.parseInt(list.get(i).get("Job").toString()));
                }
                Customer customer = customerService.getCustomer(Integer.parseInt(list.get(i).get("UserId").toString()));
                if (list.get(i).get("Address") == null){
                    entity.setAddress(customer.getAddress());
                }else{
                    entity.setAddress(list.get(i).get("Address").toString());
                }
                if (list.get(i).get("Mobile") == null){
                    entity.setMobile(customer.getMobile());
                }else{
                    entity.setMobile(list.get(i).get("Mobile").toString());
                }
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public Map<String, List<Teacher>> getTeachers() {
        Map<String, List<Teacher>> map = new HashMap<String, List<Teacher>>();
        List<Teacher> d_check = new ArrayList<Teacher>();
        List<Teacher> y_check = new ArrayList<Teacher>();
        String sql = "select Id,UserId,Name,Avatar,Pic,IdNo,Gender,Address,Sex,Birthday,Job,Mobile,Status,AddTime from SG_Teacher where Status >= ? and Status <= ? order by Status desc";
        Object [] params = new Object[]{Quantity.STATUS_THREE,Quantity.STATUS_SIX};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Teacher entity = new Teacher();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                int userId = Integer.parseInt(list.get(i).get("UserId").toString());
                entity.setUserId(userId);
                entity.setName(list.get(i).get("Name").toString());
                if (list.get(i).get("Pic") == null){
                    entity.setPic(list.get(i).get("Avatar").toString());
                }else{
                    entity.setPic(list.get(i).get("Pic").toString());
                }
                entity.setIdNo(list.get(i).get("IdNo").toString());
                entity.setGender(list.get(i).get("Gender").toString());
                String birthday = list.get(i).get("Birthday").toString();
                entity.setBirthday(birthday);
                entity.setAge(""+DateUtil.getAge(birthday));

                if (list.get(i).get("Job") == null){
                    entity.setJob(0);
                }else{
                    entity.setJob(Integer.parseInt(list.get(i).get("Job").toString()));
                }
                Customer customer = customerService.getCustomer(Integer.parseInt(list.get(i).get("UserId").toString()));
                if (list.get(i).get("Address") == null){
                    entity.setAddress(customer.getAddress());
                }else{
                    entity.setAddress(list.get(i).get("Address").toString());
                }
                if (list.get(i).get("Mobile") == null){
                    entity.setMobile(customer.getMobile());
                }else{
                    entity.setMobile(list.get(i).get("Mobile").toString());
                }
                int status = Integer.parseInt(list.get(i).get("Status").toString());
                entity.setStatus(status);
                entity.setTeacherEducation(teacherEducationService.getTeacherEducation(userId));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                if (status == 3 || status == 6){
                    d_check.add(entity);
                }else{
                    y_check.add(entity);
                }
            }
        }

        map.put("d_check",d_check);
        map.put("y_check",y_check);

        return map;
    }

    @Override
    public List<Teacher> getEntitysByWhere(String where) {
        String whereStr = " where Status = ? ";
        whereStr = whereStr + where;
        List<Teacher> reData = new ArrayList<Teacher>();
        String sql = "select Id,UserId,Name,Avatar,Pic,IdNo,Gender,Address,Education,Experience,Sex,Job,Mobile,Status,AddTime from SG_Teacher " + whereStr;
        Object [] params = new Object[]{Quantity.STATUS_ONE};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Teacher entity = new Teacher();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setUserId(Integer.parseInt(list.get(i).get("UserId").toString()));
                entity.setName(list.get(i).get("Name").toString());
                if (list.get(i).get("Pic") == null){
                    entity.setPic(list.get(i).get("Avatar").toString());
                }else{
                    entity.setPic(list.get(i).get("Pic").toString());
                }
                if (list.get(i).get("IdNo") == null){
                    entity.setIdNo("");
                }else{
                    entity.setIdNo(list.get(i).get("IdNo").toString());
                }

                if (list.get(i).get("Gender") == null){
                    entity.setGender("");
                }else{
                    entity.setGender(list.get(i).get("Gender").toString());
                }

                Customer customer = customerService.getCustomer(Integer.parseInt(list.get(i).get("UserId").toString()));
                if (list.get(i).get("Address") == null){
                    entity.setAddress(customer.getAddress());
                }else{
                    entity.setAddress(list.get(i).get("Address").toString());
                }

                entity.setEducation(list.get(i).get("Education").toString());
                if (list.get(i).get("Experience") == null){
                    entity.setExperience("");
                }else{
                    entity.setExperience(list.get(i).get("Experience").toString());
                }
                entity.setSex(Integer.parseInt(list.get(i).get("Sex").toString()));
                entity.setJob(Integer.parseInt(list.get(i).get("Job").toString()));
                if (list.get(i).get("Mobile") == null){
                    entity.setMobile(customer.getMobile());
                }else{
                    entity.setMobile(list.get(i).get("Mobile").toString());
                }
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public Teacher get(int id) {
        String sql = "select Id,UserId,Name,Avatar,Pic,IdNo,Gender,Birthday,Msg,Education,Experience,Sex,Job,Mobile,Status,AddTime,Address from SG_Teacher where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Teacher entity = new Teacher();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setUserId(rs.getInt("UserId"));
                entity.setName(rs.getString("Name"));
                if (StringUtils.isNotEmpty(rs.getString("Pic").trim())){
                    entity.setPic(rs.getString("Pic"));
                }else{
                    entity.setPic(rs.getString("Avatar"));
                }
                entity.setIdNo(rs.getString("IdNo"));
                entity.setGender(rs.getString("Gender"));
                entity.setBirthday(rs.getString("Birthday"));
                entity.setAge(""+DateUtil.getAge(rs.getString("Birthday")));
                entity.setMsg(rs.getString("Msg"));
                entity.setEducation(rs.getString("Education"));
                entity.setExperience(rs.getString("Experience"));
                entity.setSex(rs.getInt("Sex"));
                entity.setJob(rs.getInt("Job"));
                Customer customer = customerService.getCustomer(rs.getInt("UserId"));
                if (StringUtils.isNotEmpty(rs.getString("Mobile").trim())){
                    entity.setMobile(rs.getString("Mobile"));
                }else{
                    entity.setMobile(customer.getMobile());
                }
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
                if (StringUtils.isNotEmpty(rs.getString("Address").trim())){
                    entity.setAddress(rs.getString("Address"));
                }else{
                    entity.setAddress(customer.getAddress());
                }
            }
        });

        return entity;
    }

    @Override
    public int insert(Teacher entity) {
        String sql = "insert into SG_Teacher(UserId,Name,Avatar,Education,Experience,Sex,Job,Mobile,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getUserId(), entity.getName(), entity.getAvatar(), entity.getEducation(), entity.getExperience(), entity.getSex(), entity.getJob(), entity.getMobile(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Teacher entity) {
        String sql = "update SG_Teacher set UserId = ?, Name = ?, Avatar = ?, Education = ?, Experience = ?, Sex = ?, Job = ?, Mobile = ?  where Id = ? ";
        Object [] params = new Object[]{entity.getUserId(), entity.getName(), entity.getAvatar(), entity.getEducation(), entity.getExperience(), entity.getSex(), entity.getJob(), entity.getMobile(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int updateStatus(Teacher entity) {
        String sql = "update SG_Teacher set Education = ?, Experience = ?, Msg = ?, Status = ? where Id = ? ";
        Object [] params = new Object[]{entity.getEducation(), entity.getExperience(), entity.getMsg(), entity.getStatus(), entity.getId()};
        int reData = 0;
        try {
            reData = jdbcTemplate.update(sql, params);
        }catch (Exception _ex){
            log.error(_ex.getMessage());
        }
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Teacher set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Teacher formEntity(HttpServletRequest request, int id) {
        Teacher entity = new Teacher();

        entity.setId(id);
        entity.setUserId(Integer.parseInt(request.getParameter("userId")));
        entity.setName(request.getParameter("name"));
        entity.setAvatar(request.getParameter("cover"));
        entity.setEducation(request.getParameter("education"));
        entity.setExperience(request.getParameter("experience"));
        entity.setSex(Integer.parseInt(request.getParameter("sex")));
        entity.setJob(Integer.parseInt(request.getParameter("job")));
        entity.setMobile(request.getParameter("mobile"));

        return entity;
    }

    @Override
    public String getY_Teachers(int course_id, int sku_id) {
        StringBuffer sb = new StringBuffer();
        List<Teacher> reData = new ArrayList<Teacher>();
        String sql = "select Id,UserId,Name,Avatar,Pic,Education,Experience,Sex,Job,Mobile,Status,AddTime from SG_Teacher where Status = ? and Id in (SELECT TeacherId FROM sogokids.SG_CourseTeacher where Status > ? and CourseId = ? and CourseSkuId = ? ) ";
        Object [] params = new Object[]{Quantity.STATUS_ONE, Quantity.STATUS_ZERO, course_id, sku_id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        sb.append(getHtmlTableStrat1());
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                sb.append("<tr>");
                sb.append("<td>");
                    sb.append("<input type='checkbox' id='y_teacher' name='y_teacher' value='"+list.get(i).get("Id")+"'>");
                sb.append("</td>");
                String pic = "";
                if (list.get(i).get("Pic") == null){
                    pic = list.get(i).get("Avatar").toString();
                }else{
                    pic = list.get(i).get("Pic").toString();
                }
                String url = configuration.getString(Quantity.DISPLAY_IMAGE) + pic;
                sb.append("<td>");
                    sb.append("<img class='teacher_w_h' src='" + url + "' style='width: 50px;height: 50px;' />").append("&numsp;&numsp;");
                    sb.append(list.get(i).get("Name"));
                sb.append("</td>");
                sb.append("</tr>");
            }
        }
        sb.append(getHtmlTableEnd());
        return sb.toString();
    }

    @Override
    public String getW_Teachers(int course_id, int sku_id) {
        StringBuffer sb = new StringBuffer();
        List<Teacher> reData = new ArrayList<Teacher>();
        String sql = "select Id,UserId,Name,Avatar,Pic,Education,Experience,Sex,Job,Mobile,Status,AddTime from SG_Teacher where Status = ? and Id not in (SELECT TeacherId FROM sogokids.SG_CourseTeacher where Status > ? and CourseId = ? and CourseSkuId = ? ) ";
        Object [] params = new Object[]{Quantity.STATUS_ONE, Quantity.STATUS_ZERO, course_id, sku_id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        sb.append(getHtmlTableStrat());
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                sb.append("<tr>");
                sb.append("<td>");
                sb.append("<input type='checkbox' id='w_teacher' name='w_teacher' value='"+list.get(i).get("Id")+"'>");
                sb.append("</td>");
                String pic = "";
                if (list.get(i).get("Pic") == null){
                    pic = list.get(i).get("Avatar").toString();
                }else{
                    pic = list.get(i).get("Pic").toString();
                }
                String url = configuration.getString(Quantity.DISPLAY_IMAGE) + pic;
                sb.append("<td>");
                sb.append("<img class='teacher_w_h' src='" + url + "' style='width: 50px;height: 50px;'/>").append("&numsp;&numsp;");
                sb.append(list.get(i).get("Name"));
                sb.append("</td>");
                sb.append("</tr>");
            }
        }
        sb.append(getHtmlTableEnd());

        return sb.toString();
    }

    private String getHtmlTableStrat(){
        StringBuffer sb = new StringBuffer();
        sb.append("<table class='table table-striped table-bordered table-hover dataTables-example'>");
        sb.append("<thead>");
        sb.append("<tr class='gradeX'>");
        sb.append("<th>勾选</th>");
        sb.append("<th>讲师</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");

        return sb.toString();
    }

    private String getHtmlTableStrat1(){
        StringBuffer sb = new StringBuffer();
        sb.append("<table class='table table-striped table-bordered table-hover'>");
        sb.append("<thead>");
        sb.append("<tr class='gradeX'>");
        sb.append("<th>勾选</th>");
        sb.append("<th>讲师</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");

        return sb.toString();
    }

    private String getHtmlTableEnd(){
        StringBuffer sb = new StringBuffer();
        sb.append("</tbody>");
        sb.append("</table>");

        return sb.toString();
    }

    @Override
    public String getCourseJsonStr(int material_id){
        List<Subject> subjects = new ArrayList<Subject>();
        if (material_id == 0){
            List<Subject> sub_ls = subjectService.getEntitys(Quantity.STATUS_ONE);
            if (sub_ls.size() > 0){
                for (int i = 0; i < sub_ls.size(); i++) {
                    Subject subject = sub_ls.get(i);
                    List<Course> courses = courseService.getEntitysBySubId(subject.getId());
                    List<Course> course_ls = new ArrayList<Course>();
                    for (int j = 0; j < courses.size(); j++) {
                        Course entity = courses.get(j);
                        if (!courseMaterialService.isMaterial(entity.getId())){
                            course_ls.add(entity);
                        }
                    }
                    subject.setCourses(course_ls);
                    subjects.add(subject);
                }
            }
        }else{
            CourseMaterial courseMaterial = courseMaterialService.get(material_id);
            Course course = courseService.get(courseMaterial.getCourseId());
            List<Subject> sub_ls = subjectService.getEntitys(Quantity.STATUS_ONE);
            if (sub_ls.size() > 0){
                for (int i = 0; i < sub_ls.size(); i++) {
                    Subject subject = sub_ls.get(i);
                    if (subject.getId() == course.getSubjectId()){
                        subject.setSelected(1);
                    }
                    List<Course> course_ls = new ArrayList<Course>();
                    List<Course> courses = courseService.getEntitysBySubId(subject.getId());
                    for (int j = 0; j < courses.size(); j++) {
                        Course entity = courses.get(j);
                        if (entity.getId() == course.getId() && subject.getId() == entity.getSubjectId()){
                            entity.setSelected(1);
                            course_ls.add(entity);
                        }else {
                            if (!courseMaterialService.isMaterial(entity.getId())) {
                                course_ls.add(entity);
                            }
                        }
                    }
                    subject.setCourses(course_ls);
                    subjects.add(subject);
                }
            }


        }

        String reData = JSON.toJSONString(subjects);

        return reData;
    }

    /**
     * 进行邀请面试操作,并发送app和短信信息
     * @param req
     * @return
     */
    @Override
    public int addInvited(HttpServletRequest req){
        int reDate = 1;
        String app_service_url = configuration.getString(Quantity.UPLOAD_APP_PUSH);
        int tid = Integer.parseInt(req.getParameter("tid"));
        Teacher teacher = this.get(tid);
        teacher.setStatus(Quantity.STATUS_SIX);
        log.info("TeacherServiceImpl -> addInvited -> info:开始进行邀请面试操作,更新老师状态为'"+Quantity.STATUS_SIX+"'.");
        int up_teacher = this.updateStatus(teacher);
        if (up_teacher > 0){
            log.info("TeacherServiceImpl -> addInvited -> info:开始进行邀请面试操作,更新老师状态成功.");
            TeacherInterview teacherInterview = teacherInterviewService.formEntity(req, teacher.getUserId());
            int add_interview = teacherInterviewService.insert(teacherInterview);
            if (add_interview > 0){
                log.info("TeacherServiceImpl -> addInvited -> info:开始进行邀请面试,并保存面试信息操作成功.");
                reDate = 0;
                String app_param = this.getAppPushParam(teacher,teacherInterview.getInterviewDate(),teacherInterview.getAddress());
                log.info("TeacherServiceImpl -> addInvited -> info:开始进行邀请面试,发送app推送请求组装参数完成.");
                JSONObject jSONObject = httpClientService.httpPost(app_service_url,app_param);
                HttpResult result = CastUtil.toObject(jSONObject, HttpResult.class);
                if(result.getErrno() == 0){
                    log.info("TeacherServiceImpl -> addInvited -> info:开始进行邀请面试,发送app推送请求成功.");
                }else{
                    log.error("TeacherServiceImpl -> addInvited -> error info:"+ result.getErrno() + result.getErrmsg());
                }
                String sms_service_url = configuration.getString(Quantity.UPLOAD_SMS);
                String sms_param = this.getMessageParam(teacher, teacherInterview.getInterviewDate(), teacherInterview.getAddress());
                log.info("TeacherServiceImpl -> addInvited -> info:开始进行邀请面试,发送短信请求组装参数完成.");
                JSONObject sms_jSONObject = httpClientService.httpPost(sms_service_url,sms_param);
                HttpResult sms_result = CastUtil.toObject(sms_jSONObject, HttpResult.class);
                if (sms_result.getErrno() == 0){
                    log.info("TeacherServiceImpl -> addInvited -> info:开始进行邀请面试,发送短信请求成功.");
                }else{
                    log.error("TeacherServiceImpl -> addInvited -> error info:"+ result.getErrno() + sms_result.getErrmsg());
                }

            }
        }

        return reDate;
    }

    private String getAppPushParam(Teacher teacher, String dateTime, String address){
        StringBuffer sb_str = new StringBuffer();
        String content = Quantity.MS_TEMPLATE;
        log.info("TeacherServiceImpl -> getAppPushParam -> info:开始进行邀请面试,发送app推送请求组装参数.");
        content = StringUtils.replace(content, "xxx", teacher.getName());
        content = StringUtils.replace(content, "####", DateUtil.getTimeCn_NotWeek(dateTime));
        content = StringUtils.replace(content, "*****", address);
        sb_str.append("content=").append(content);
        long expired = DateUtil.getDateExpired(configuration.getInt(Quantity.SERVICE_PORT_TIME));//请求有效期
        sb_str.append("expired=").append(expired);
        sb_str.append("uid=").append(teacher.getUserId());
        sb_str.append("key=").append(configuration.getString(Quantity.SERVICE_PORT_KEY));

        String sign = StringUtil.getSign(sb_str.toString());
        String app_param = "content="+content+"&expired="+expired+"&uid="+teacher.getUserId()+"&sign="+sign;

        return app_param;
    }

    private String getMessageParam(Teacher teacher, String dateTime, String address){
        StringBuffer sb_str = new StringBuffer();

        String content = Quantity.MS_TEMPLATE;
        log.info("TeacherServiceImpl -> getMessageParam -> info:开始进行邀请面试,发送短信请求组装参数.");
        content = StringUtils.replace(content, "xxx", teacher.getName());
        content = StringUtils.replace(content, "####", DateUtil.getTimeCn_NotWeek(dateTime));
        content = StringUtils.replace(content, "*****", address);

        long expired = DateUtil.getDateExpired(configuration.getInt(Quantity.SERVICE_PORT_TIME));//请求有效期
        sb_str.append("expired=").append(expired);
        sb_str.append("message=").append(content);
        sb_str.append("mobile=").append(teacher.getMobile().trim());
        sb_str.append("key=").append(configuration.getString(Quantity.SERVICE_PORT_KEY));

        String sign = StringUtil.getSign(sb_str.toString());
        String sms_param = "expired="+expired+"&message="+content+"&mobile="+teacher.getMobile().trim()+"&sign="+sign;

        return sms_param;
    }


    /**
     * 审核不通过操作,并发送app和短信信息
     * @param teacher
     * @return
     */
    @Override
    public int addSendMsg(Teacher teacher){
        int reDate = 0;
        String app_service_url = configuration.getString(Quantity.UPLOAD_APP_PUSH);

        int up_teacher = this.updateStatus(teacher);
        if (up_teacher > 0){
            log.info("TeacherServiceImpl -> addSendMsg -> info:开始进行审核不通过操作,更新老师状态成功.");
            reDate = 1;
            StringBuffer app_sb = new StringBuffer();
            app_sb.append("content=").append(teacher.getMsg());
            long expired = DateUtil.getDateExpired(configuration.getInt(Quantity.SERVICE_PORT_TIME));//请求有效期
            app_sb.append("expired=").append(expired);
            app_sb.append("uid=").append(teacher.getUserId());
            app_sb.append("key=").append(configuration.getString(Quantity.SERVICE_PORT_KEY));

            String sign = StringUtil.getSign(app_sb.toString());
            String app_param = "content="+teacher.getMsg()+"&expired="+expired+"&uid="+teacher.getUserId()+"&sign="+sign;
            log.info("TeacherServiceImpl -> addSendMsg -> info:开始进行审核不通过操作,发送app推送请求组装参数完成.");
            JSONObject jSONObject = httpClientService.httpPost(app_service_url,app_param);
            HttpResult result = CastUtil.toObject(jSONObject, HttpResult.class);
            if(result.getErrno() == 0){
                log.info("TeacherServiceImpl -> addSendMsg -> info:开始进行审核不通过操作,发送app推送请求成功.");
            }else{
                log.error("TeacherServiceImpl -> addSendMsg -> error info:"+ result.getErrno() + result.getErrmsg());
            }

            String sms_service_url = configuration.getString(Quantity.UPLOAD_SMS);

            StringBuffer msg_sb = new StringBuffer();

            long sms_expired = DateUtil.getDateExpired(configuration.getInt(Quantity.SERVICE_PORT_TIME));//请求有效期
            msg_sb.append("expired=").append(sms_expired);
            msg_sb.append("message=").append(teacher.getMsg());
            msg_sb.append("mobile=").append(teacher.getMobile().trim());
            msg_sb.append("key=").append(configuration.getString(Quantity.SERVICE_PORT_KEY));

            String sms_sign = StringUtil.getSign(msg_sb.toString());
            String sms_param = "expired="+sms_expired+"&message="+teacher.getMsg()+"&mobile="+teacher.getMobile().trim()+"&sign="+sms_sign;

            log.info("TeacherServiceImpl -> addSendMsg -> info:开始进行审核不通过操作,发送短信请求组装参数完成.");
            JSONObject sms_jSONObject = httpClientService.httpPost(sms_service_url,sms_param);
            HttpResult sms_result = CastUtil.toObject(sms_jSONObject, HttpResult.class);
            if (sms_result.getErrno() == 0){
                log.info("TeacherServiceImpl -> addSendMsg -> info:开始进行审核不通过操作,发送短信请求成功.");
            }else{
                log.error("TeacherServiceImpl -> addSendMsg -> error info:"+ result.getErrno() + sms_result.getErrmsg());
            }
        }

        return reDate;
    }

    /**
     * 讲师分配课程，群组信息进行更新(取消/添加成员)
     * @param course_id
     * @param sku_id
     * @return
     */
    @Override
    public HttpResult updateQz(int course_id, int sku_id, int user_id, int mark) {
        String upload_qz_url = "";
        if (mark == 1){
            upload_qz_url = configuration.getString(Quantity.UPLOAD_QZ_JOIN);
        }else{
            upload_qz_url = configuration.getString(Quantity.UPLOAD_QZ_LEAVE);
        }
        StringBuffer sb = new StringBuffer();
        sb.append("coid=").append(course_id);
        long expired = DateUtil.getDateExpired(configuration.getInt(Quantity.SERVICE_PORT_TIME));//请求有效期
        sb.append("expired=").append(expired);
        sb.append("sid=").append(sku_id);
        sb.append("uid=").append(user_id);
        sb.append("key=").append(configuration.getString(Quantity.SERVICE_PORT_KEY));

        String sign = StringUtil.getSign(sb.toString());//请求加密串

        String param = "coid="+course_id+"&expired="+expired+"&sid="+sku_id+"&uid="+user_id+"&sign="+sign;
        log.info("TeacherServiceImpl -> updateQz -> info:更新群组组装参数结束.");
        log.info("TeacherServiceImpl -> updateQz -> info:更新群组发出请求.");
        JSONObject jsonObject = httpClientService.httpPost(upload_qz_url, param);
        log.info("TeacherServiceImpl -> updateQz -> info:更新群组发出请求结束.");
        HttpResult result = CastUtil.toObject(jsonObject, HttpResult.class);
        if (result.getErrno() == 0) {
            log.info("TeacherServiceImpl -> updateQz -> info:更新群组发出请求结束,返回errno:" + result.getErrno());
        }else{
            log.info("TeacherServiceImpl -> updateQz -> info:更新群组发出请求结束,返回errno:" + result.getErrno()+result.getErrmsg() +" userId:"+user_id);
        }

        return result;
    }

}
