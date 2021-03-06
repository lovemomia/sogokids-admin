package com.sogokids.controller;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseRecommend;
import com.sogokids.course.service.CourseRecommendService;
import com.sogokids.course.service.CourseService;
import com.sogokids.subject.model.SubjectSku;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.system.service.MenuService;
import com.sogokids.user.service.UserService;
import com.sogokids.utils.util.JumpPage;
import com.sogokids.utils.util.Quantity;
import org.apache.commons.io.IOUtils;
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
import java.util.List;
import java.util.Map;

/**
 * 课程推荐
 * Created by hoze on 15/10/13.
 */
@Controller
@RequestMapping("/one")
public class SetOneController {
    @Autowired
    private UserService adminUserService;

    @Autowired
    private SubjectSkuService subjectSkuService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRecommendService courseRecommendService;

    @Autowired
    private MenuService menuService;

    /**
     *推荐课程－课程列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, courseRecommendService.getCourses());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_FOUR));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.SET_ONE),context);
    }

    /**
     *课程推荐－操作跳转
     * @param uid
     * @param id
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id){
        String reStr = JumpPage.SET_ONE_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("course",courseService.get(id));
        List<CourseRecommend> courseRecommends = courseRecommendService.getEntitysByCourseId(id);
        CourseRecommend courseRecommend = new CourseRecommend();
        if (courseRecommends.size() > 0){
            courseRecommend = courseRecommends.get(0);
        }
        context.put("recommend",courseRecommend);
        context.put("skuHtml",subjectSkuService.getCourseSkuHtml(id));
//        context.put("time_unit", EnumUtil.getEnums(Quantity.STATUS_FOUR));

        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_FOUR));
        return new ModelAndView(reStr,context);
    }

    /**
     *课程推荐－编辑
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/edit")
    public String editEntity(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        int course_id = Integer.parseInt(req.getParameter("course_id"));
        int set_id = Integer.parseInt(req.getParameter("set_id"));
        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = courseService.updateNotice(course_id, req.getParameter("notice"));
        if (reDate > 0) {
//            CourseRecommend courseRecommend = courseRecommendService.get(set_id);
//            courseRecommend.setWeight(Integer.parseInt(req.getParameter("weight")));
//            int update_weight = courseRecommendService.update(courseRecommend);
//            if (update_weight > 0) {
//                context.put(Quantity.RETURN_SUCCESS, 0);
//                context.put(Quantity.RETURN_MSG, "推荐课程信息修改成功!");
//            }else {
//                context.put(Quantity.RETURN_SUCCESS, 1);
//                context.put(Quantity.RETURN_MSG, "推荐课程信息修改失败!");
//            }
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "推荐课程信息修改成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "推荐课程信息修改失败!");
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
     *课程推荐－创建sku
     * @param cursId
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/addSku")
    public String addSku(@RequestParam("courId") int cursId, HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int skuId = Integer.parseInt(req.getParameter("sku_id"));
        int reDate = 0;
        if (skuId == 0) {
            Course course = courseService.get(cursId);
            SubjectSku subjectSku = subjectSkuService.formEntityOne(req, course.getSubjectId(), Quantity.STATUS_ZERO);
            subjectSku.setCourseId(cursId);
            reDate = subjectSkuService.insertOne(subjectSku);
        }else{
            SubjectSku subjectSku = subjectSkuService.get(skuId);
            reDate = subjectSkuService.update(subjectSkuService.formEntityOne(req, subjectSku.getSubjectId(), skuId));
        }

        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "推荐课程sku信息保存成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "推荐课程sku信息保存失败!");
        }
        context.put("skuHtml",subjectSkuService.getCourseSkuHtml(cursId));
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
     *课程推荐－编辑sku
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/editSku")
    public String editSku(HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int skuId = Integer.parseInt(req.getParameter("skuId"));
        SubjectSku entity = subjectSkuService.get(skuId);
        context.put("sku",entity);
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
     *课程推荐－删除sku
     * @param courId
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/delSku")
    public String delSku(@RequestParam("courId") int courId, HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();

        int skuId = Integer.parseInt(req.getParameter("skuId"));
        int reDate = subjectSkuService.delete(skuId);
        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程包sku信息删除成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程包sku信息删除失败!");
        }
        context.put("skuHtml", subjectSkuService.getCourseSkuHtml(courId));
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
     *推荐课程－上下线操作
     * @param uid
     * @param id
     * @param mark
     * @return
     */
    @RequestMapping("/upOrDown")
    public ModelAndView updateStatus(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("mark") int mark){

        Map<String, Object> context = new HashMap<String, Object>();

        int reDate = courseService.updateStatus(id, mark);

        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"Course数据上下线成功!");
//            int sub_id = courseService.get(id).getSubjectId();
//            subjectService.updateStatus(sub_id, mark);
        }else{
            context.put(Quantity.RETURN_MSG,"Course数据上下线失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, courseRecommendService.getCourses());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_FOUR));
        return new ModelAndView(JumpPage.SET_ONE,context);
    }

    /**
     *推荐课程－取消推荐
     * @param uid
     * @param id
     * @return
     */
    @RequestMapping("/cancelCourse")
    public ModelAndView cancelCourse(@RequestParam("uid") int uid,@RequestParam("id") int id){
        Map<String, Object> context = new HashMap<String, Object>();
        int reData = courseService.update_cancelCourse(id);

        if (reData > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程取消推荐成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"课程取消推荐失败!");
        }

        context.put(Quantity.RETURN_ENTITY_LIST, courseRecommendService.getCourses());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_FOUR));
        return new ModelAndView(JumpPage.SET_ONE,context);
    }

    /**
     *课程推荐－预览课程
     * @param uid
     * @param id
     * @return
     */
    @RequestMapping("/preview")
    public ModelAndView preview(@RequestParam("uid") int uid,@RequestParam("id") int id) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("previewHtml", courseService.getPreview(id));
        context.put(Quantity.RETURN_ENTITY_LIST, courseRecommendService.getCourses());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_FOUR));
        return new ModelAndView(JumpPage.COURSE_SET_PREVIEW,context);
    }

    /**
     *课程推荐－取消sku
     * @param courseId
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/cancelSku")
    public String cancelSku(@RequestParam("courseId") int courseId, HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();

        int skuId = Integer.parseInt(req.getParameter("skuId"));
        int reDate = subjectSkuService.updateStatus(skuId, Quantity.STATUS_THREE);
        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "推荐课程sku信息取消成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "推荐课程sku信息取消失败!");
        }
        context.put("skuHtml", subjectSkuService.getCourseSkuHtml(courseId));
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
     * 推荐课程－移动数据排序
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/move")
    public ModelAndView moveWeight(@RequestParam("uid") int uid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();

        courseRecommendService.update_weight(req);

        context.put(Quantity.RETURN_ENTITY_LIST, courseRecommendService.getCourses());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_FOUR));

        return new ModelAndView(JumpPage.SET_ONE,context);
    }

}
