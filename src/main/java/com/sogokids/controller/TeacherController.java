package com.sogokids.controller;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.query.model.Customer;
import com.sogokids.query.service.CustomerService;
import com.sogokids.teacher.model.CourseMaterial;
import com.sogokids.teacher.model.Teacher;
import com.sogokids.teacher.model.TeacherEducation;
import com.sogokids.teacher.service.CourseMaterialService;
import com.sogokids.teacher.service.CourseTeacherService;
import com.sogokids.teacher.service.TeacherEducationService;
import com.sogokids.teacher.service.TeacherExperienceService;
import com.sogokids.teacher.service.TeacherInterviewService;
import com.sogokids.teacher.service.TeacherService;
import com.sogokids.teacher.service.CourseAssignService;
import com.sogokids.user.service.UserService;
import com.sogokids.utils.util.EnumUtil;
import com.sogokids.utils.util.JumpPage;
import com.sogokids.utils.util.Quantity;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 讲师信息
 * Created by hoze on 15/11/13.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService adminUserService;

    @Autowired
    private CourseAssignService courseAssignService;

    @Autowired
    private TeacherEducationService teacherEducationService;

    @Autowired
    private TeacherExperienceService teacherExperienceService;

    @Autowired
    private TeacherInterviewService teacherInterviewService;

    @Autowired
    private CourseTeacherService courseTeacherService;

    @Autowired
    private CourseMaterialService courseMaterialService;

    /**
     * 讲师列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, teacherService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.TEACHER),context);
    }

    /**
     * 讲师查询
     * @param uid
     * @param name
     * @param req
     * @return
     */
    @RequestMapping("/query")
    public ModelAndView query_teacher(@RequestParam("uid") int uid, @RequestParam("name") String name, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        String where = " ";
        if (name != null && !name.equals("")){
            where = where + " and Name LIKE '%" + name +"%' ";
        }
        context.put(Quantity.RETURN_ENTITY_LIST, teacherService.getEntitysByWhere(where));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put("name",name);
        return new ModelAndView(JumpPage.TEACHER,context);
    }

    /**
     * 讲师操作跳转
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.TEACHER_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.TEACHER_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, teacherService.get(id));
        }
        context.put("sexs", EnumUtil.getEnums(Quantity.STATUS_SIX));
        context.put("jobs", EnumUtil.getEnums(Quantity.STATUS_SEVEN));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    /**
     * 验证讲师与用户手机
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/isMobile")
    public String isMobile(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        Customer customer = customerService.getCustomerByMobile(req.getParameter("mobile"));
        if (customer.getId() > 0){
            context.put("userId",customer.getId());
            context.put(Quantity.RETURN_SUCCESS,0);
            context.put(Quantity.RETURN_MSG,"编辑讲师信息成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS,1);
            context.put(Quantity.RETURN_MSG,"编辑讲师信息失败,手机号验证失败!");
        }


        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
        return null;
    }

    /**
     * 创建讲师
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/add")
    public String addEntity(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int customer_id = Integer.parseInt(req.getParameter("userId"));
        int reDate = teacherService.insert(teacherService.formEntity(req, Quantity.STATUS_ONE));
        if (reDate > 0){
            customerService.updateCustomer(customer_id);
            context.put(Quantity.RETURN_SUCCESS,0);
            context.put(Quantity.RETURN_MSG,"添加讲师数据成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS,1);
            context.put(Quantity.RETURN_MSG,"添加讲师数据失败!");
        }

        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
        return null;

    }

    /**
     * 编辑讲师
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = teacherService.update(teacherService.formEntity(req, id));
        if (reDate > 0){
            customerService.updateCustomer(Integer.parseInt(req.getParameter("userId")));
            context.put(Quantity.RETURN_MSG,"修改讲师数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改讲师数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, teacherService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.TEACHER,context);
    }

    /**
     * 删除讲师
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = teacherService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除讲师数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除讲师数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, teacherService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.TEACHER,context);
    }

    /**
     * 分配讲师信息
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/assign")
    public ModelAndView assignInfo(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("w_assign", courseAssignService.getCourseAssigns().get("w_assign"));
        context.put("y_assign", courseAssignService.getCourseAssigns().get("y_assign"));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.ASSIGN),context);
    }

    /**
     * 教案更新
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/material")
    public ModelAndView materialInfo(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, courseMaterialService.getMaterials());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.MATERIAL),context);
    }

    /**
     * 审核讲师信息
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/check")
    public ModelAndView checkInfo(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("d_check", teacherService.getTeachers().get("d_check"));
        context.put("y_check", teacherService.getTeachers().get("y_check"));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.CHECK),context);
    }

    /**
     * 执行页面跳转0(待审核：简历不通过和邀请面试)1(面试中：面试不通过和面试通过)2(讲师力量：保存摘要)3(简历不通过和面试不通过：查看简历)
     * @param uid
     * @param tid
     * @param mark
     * @param req
     * @return
     */
    @RequestMapping("/examineOper")
    public ModelAndView examineOper(@RequestParam("uid") int uid,@RequestParam("tid") int tid,@RequestParam("mark") int mark, HttpServletRequest req){
        String JumpUrl = JumpPage.CHECK_TEACHER;
        Map<String, Object> context = new HashMap<String, Object>();
        Teacher teacher = teacherService.get(tid);
        if (mark == 1){
            JumpUrl = JumpPage.CHECK_TEACHER_1;
            context.put("interview", teacherInterviewService.getTeacherInterviews(teacher.getUserId()));
            context.put("ms_finals",EnumUtil.getEnums(Quantity.STATUS_TEN));
        }else if (mark == 2){
            JumpUrl = JumpPage.CHECK_TEACHER_2;
        }else if (mark == 3){
            JumpUrl = JumpPage.CHECK_TEACHER_3;
        }else{
            context.put("jl_finals",EnumUtil.getEnums(Quantity.STATUS_TEN));
        }

        context.put("teacher", teacher);
        context.put("teacherEducations", teacherEducationService.getTeacherEducations(teacher.getUserId()));
        context.put("teacherExperiences", teacherExperienceService.getTeacherExperiences(teacher.getUserId()));

        return new ModelAndView(JumpUrl,context);
    }

    /**
     * 更新状态并保存邀请面试信息,推送app和短信消息
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/add_invited")
    public ModelAndView addInvited(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int success = teacherService.addInvited(req);
        if (success == 0){
            context.put(Quantity.RETURN_SUCCESS,0);
            context.put(Quantity.RETURN_MSG,"邀请面试信息已发送!");
        }else{
            context.put(Quantity.RETURN_SUCCESS,1);
            context.put(Quantity.RETURN_MSG,"邀请面试信息发送失败!");
        }
        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(writer);
        }

        return null;
    }

    /**
     * 更新状态并更新简历不通过信息并进行属性msg更新,推送app和短信消息
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/send_msg")
    public ModelAndView updateMsg(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();

        int tid = Integer.parseInt(req.getParameter("tid"));
        Teacher teacher = teacherService.get(tid);
        teacher.setStatus(Quantity.STATUS_FOUR);
        String c_reason = req.getParameter("c_reason");
        int reason_index = Integer.parseInt(c_reason);
        if (c_reason.equals("3")){
            c_reason = req.getParameter("j_reason");
        }else{
            c_reason = StringUtils.replace(Quantity.BTG_TEMPLATE,"xxx",teacher.getName());
            c_reason = StringUtils.replace(c_reason, "####", EnumUtil.getEnums(Quantity.STATUS_TEN).get(reason_index).get("name").toString());
        }
        teacher.setMsg(c_reason);
        int up_teacher = teacherService.addSendMsg(teacher);
        if (up_teacher > 0){
            context.put(Quantity.RETURN_SUCCESS,0);
            context.put(Quantity.RETURN_MSG,"简历不通过信息已发送!");
        }else{
            context.put(Quantity.RETURN_SUCCESS,1);
            context.put(Quantity.RETURN_MSG,"简历不通过信息发送失败!");
        }

        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(writer);
        }

        return null;
    }

    /**
     * 当mark＝1时审核通过并进行摘要更新
     * 当mark＝2时审核不通过并进行属性msg更新,推送app和短信消息
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/status")
    public ModelAndView updateStatus(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        int up_teacher = 1;
        Map<String, Object> context = new HashMap<String, Object>();

        int tid = Integer.parseInt(req.getParameter("tid"));
        int mark = Integer.parseInt(req.getParameter("mark"));
        Teacher teacher = teacherService.get(tid);
        if (mark == 1) {
            teacher.setStatus(Quantity.STATUS_ONE);
            teacher.setJob(Quantity.STATUS_THREE);
            up_teacher = teacherService.updateStatus(teacher);
            if (up_teacher > 0){
                customerService.updateCustomer(teacher.getUserId());
            }
        }
        if (mark == 2) {
            teacher.setStatus(Quantity.STATUS_FIVE);
            String c_reason = req.getParameter("c_reason");
            int reason_index = Integer.parseInt(c_reason);
            if (c_reason.equals("3")){
                c_reason = req.getParameter("j_reason");
            }else{
                c_reason = StringUtils.replace(Quantity.BTG_TEMPLATE,"xxx",teacher.getName());
                c_reason = StringUtils.replace(c_reason, "####", EnumUtil.getEnums(Quantity.STATUS_TEN).get(reason_index).get("name").toString());
            }
            teacher.setMsg(c_reason);
            up_teacher = teacherService.addSendMsg(teacher);
        }

        if (up_teacher > 0){
            context.put(Quantity.RETURN_SUCCESS,0);
            context.put(Quantity.RETURN_MSG,"审核成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS,1);
            context.put(Quantity.RETURN_MSG,"审核失败!");
        }

        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(writer);
        }

        return null;
    }

    /**
     * 更新个人摘要
     * @param req
     * @return
     */
    @RequestMapping("/examineVerify")
    public ModelAndView examineVerify(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int tid = Integer.parseInt(req.getParameter("tid"));
        Teacher teacher = teacherService.get(tid);
        TeacherEducation teacherEducation = teacherEducationService.getTeacherEducation(teacher.getUserId());
        if(teacherEducation.getId() > 0) {
            String education = teacherEducation.getSchool() + " " + teacherEducation.getMajor() + teacherEducation.getLevel();
            teacher.setEducation(education);
        }

        String experience = req.getParameter("experience");
        teacher.setExperience(experience);

        int up_teacher = teacherService.updateStatus(teacher);
        if (up_teacher > 0){
            context.put(Quantity.RETURN_SUCCESS,0);
            context.put(Quantity.RETURN_MSG,"更新成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS,1);
            context.put(Quantity.RETURN_MSG,"更新失败!");
        }

        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(writer);
        }

        return null;
    }

    /**
     * 老师操作跳转
     * @param uid
     * @param id
     * @param sid
     * @param req
     * @return
     */
    @RequestMapping("/teacherOper")
    public ModelAndView teacherOperation(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("sid") int sid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();

        context.put("courseId", id);
        context.put("courseSkuId", sid);
        context.put("y_teacher", teacherService.getY_Teachers(id,sid));
        context.put("w_teacher",teacherService.getW_Teachers(id,sid));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));

        return new ModelAndView(JumpPage.ASSIGN_TEACHER,context);
    }

    /**
     * 分配讲师
     * @param mark
     * @param id
     * @param sid
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/assign_teacher")
    public String assign_add_delTeacher(@RequestParam("mark") int mark,@RequestParam("id") int id,@RequestParam("sid") int sid, HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = 0;
        if (mark == 1){
            String [] teacherIds = req.getParameterValues("w_teacher");
            for (int i = 0; i < teacherIds.length; i++) {
                Teacher teacher = teacherService.get(Integer.parseInt(teacherIds[i]));
                reDate = courseTeacherService.insert(id, sid, teacher.getUserId(), teacher.getId());
                if (reDate > 0){
                    teacherService.updateQz(id,sid,teacher.getUserId(),mark);
                }
            }
        }else{
            String [] teacherIds = req.getParameterValues("y_teacher");
            for (int i = 0; i < teacherIds.length; i++) {
                Teacher teacher = teacherService.get(Integer.parseInt(teacherIds[i]));
                reDate = courseTeacherService.delete(id, sid, Integer.parseInt(teacherIds[i]));
                if (reDate > 0){
                    teacherService.updateQz(id,sid,teacher.getUserId(),mark);
                }
            }
        }
        if (reDate > 0){
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG,"讲师信息操作成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"讲师信息操作失败!");
        }
        context.put("y_teacher", teacherService.getY_Teachers(id,sid));
        context.put("w_teacher", teacherService.getW_Teachers(id,sid));
        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
        return null;
    }

    @RequestMapping("/teacherCourse")
    public ModelAndView teacherCourse(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();

        context.put("teacherCourses", courseAssignService.getTeacherCourses(id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));

        return new ModelAndView(JumpPage.TEACHER_COURSE,context);
    }

    /**
     * 教案跳转
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/materialOper")
    public ModelAndView material_oper(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        String reStr = JumpPage.MATERIAL_ADD;

        if (id == 0) {

        }else{
            CourseMaterial courseMaterial = courseMaterialService.get(id);
            context.put("courseMaterial",courseMaterial);
            reStr = JumpPage.MATERIAL_EDIT;
        }
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));

        return new ModelAndView(reStr,context);
    }

    /**
     * 获取课程体系和课程联动的json数据
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/dataJson")
    public String getJson(HttpServletRequest req,HttpServletResponse rsp){
        int id = Integer.parseInt(req.getParameter("id"));
        rsp.setContentType("text/html; charset=UTF-8");
        String reStr = teacherService.getCourseJsonStr(id);
        try {
            rsp.getWriter().write(reStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 编辑教案信息
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/editMaterial")
    public ModelAndView edit_material(@RequestParam("uid") int uid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        int id = Integer.parseInt(req.getParameter("id"));
        int success = 1;
        if (id == 0) {
            int insert_index = courseMaterialService.insert(courseMaterialService.formEntity(req, id));
            if (insert_index > 0){
                success = 0;
            }
        }else{
            int update_index = courseMaterialService.update(courseMaterialService.formEntity(req, id));
            if (update_index > 0){
                success = 0;
            }
        }
        if (success == 0){
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG,"教案信息操作成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"教案信息操作失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, courseMaterialService.getMaterials());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.MATERIAL,context);
    }


    /**
     * 删除课程教案信息
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/delMaterial")
    public ModelAndView del_material(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        int del_index = courseMaterialService.delete(id);
        if (del_index > 0){
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG,"教案信息操作成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"教案信息操作失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, courseMaterialService.getMaterials());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.MATERIAL,context);
    }

}
