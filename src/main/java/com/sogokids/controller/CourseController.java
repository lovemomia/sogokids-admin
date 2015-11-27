package com.sogokids.controller;

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
import com.sogokids.images.model.Images;
import com.sogokids.images.service.ImagesService;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.model.SubjectSku;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.system.model.Institution;
import com.sogokids.system.service.InstitutionService;
import com.sogokids.system.service.PlaceService;
import com.sogokids.system.service.TeacherService;
import com.sogokids.user.service.UserService;
import com.sogokids.utils.util.EnumUtil;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/10/13.
 */
@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private UserService adminUserService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectSkuService subjectSkuService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseImgService courseImgService;

    @Autowired
    private CourseBookService courseBookService;

    @Autowired
    private CourseSkuService courseSkuService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private ImagesService imagesService;

    @Autowired
    private CourseDetailService courseDetailService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseTeacherService courseTeacherService;

    @Autowired
    private CourseRecommendService courseRecommendService;

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid,@RequestParam("subid") int subid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, courseService.getEntitysBySubId(subid));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put("subid", subid);
        return new ModelAndView(JumpPage.COURSE,context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("mark") int mark,@RequestParam("subid") int subid,HttpServletRequest req){
        String reStr = JumpPage.COURSE_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == 0) {
            reStr = JumpPage.COURSE_ADD;
        }else if (mark == 1){
            reStr = JumpPage.COURSE_SET_ONE;
            List<CourseRecommend> courseRecommends = courseRecommendService.getCancelByCourseId(id);
            if (courseRecommends.size() > 0){
                for (int i = 0; i < courseRecommends.size() ; i++) {
                    courseRecommendService.updateStatus(courseRecommends.get(i).getId());
                }
                context.put(Quantity.RETURN_ENTITY_LIST, courseService.getEntitysBySubId(subid));
                reStr = JumpPage.COURSE;
            }
            context.put(Quantity.RETURN_ENTITY, courseService.get(id));
            context.put("time_unit", EnumUtil.getEnums(Quantity.STATUS_FOUR));
        }else{
            context.put(Quantity.RETURN_ENTITY, courseService.get(id));
            context.put("imageJson",courseImgService.getImgHtml(id));
            context.put("bookJson",courseBookService.getBooksHtml(id));
            context.put("skuHtml",courseSkuService.getSkuHtml(id));
            CourseDetail courseDetail = new CourseDetail();
            List<CourseDetail> cds = courseDetailService.getModelsByCourse_Key(id);
            if (cds.size() > 0){
                courseDetail = cds.get(0);
            }
            context.put("detail", courseDetail);
        }

        List<Institution> insts = new ArrayList<Institution>();
        Institution inst = new Institution();
        inst.setId(0);
        inst.setName("请选择机构信息");
        insts.add(inst);
        insts.addAll(institutionService.getEntitys());
        context.put("subid", subid);
        context.put("insts", insts);
        context.put("places", placeService.getEntitys());
        context.put("subs", subjectService.getEntitys());
        context.put("y_teacher", teacherService.getY_Teachers(id));
        context.put("w_teacher",teacherService.getW_Teachers(id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/add")
    public String addEntity(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = courseService.insertKey(courseService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程基本信息添加成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程基本信息添加失败!");
        }
        context.put("courseId",reDate);
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

    @RequestMapping("/edit")
    public String editEntity(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        int course_id = Integer.parseInt(req.getParameter("course_id"));
        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = courseService.update(courseService.formEntity(req, course_id));
        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程基本信息修改成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程基本信息修改失败!");
        }
        context.put("courseId",course_id);
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

    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = courseService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除Course数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除Course数据失败!");
        }
        int sub_id = Integer.parseInt(req.getParameter("subid"));
        context.put(Quantity.RETURN_ENTITY_LIST, courseService.getEntitysBySubId(sub_id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put("subid", sub_id);
        return new ModelAndView(JumpPage.COURSE,context);
    }

    @RequestMapping("/addImg")
    public String addImg(@RequestParam("courseId") int courseId,HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Images image = imagesService.uploadImg(req, 0, "","true");
        int reDate = courseImgService.insert(courseImgService.formEntity(image, courseId));
        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(courseImgService.getImgHtml(courseId)).replace("\"", ""));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(writer);
        }

        return null;
    }

    @RequestMapping("/delImg")
    public String delImg(@RequestParam("courseId") int courseId,HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        String imgstr = req.getParameter("imgs");
        int reDate = courseImgService.delete(Integer.parseInt(imgstr));

        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "图片删除成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "图片删除失败!");
        }

        context.put("imageJson",courseImgService.getImgHtml(courseId));
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

    @RequestMapping("/addBook")
    public String addNotice(@RequestParam("courseId") int courseId,HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Images image = imagesService.uploadImg(req, 0, "","true");
        int reDate = courseBookService.insert(courseBookService.formEntity(image, courseId, Quantity.STATUS_ZERO));

        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(courseBookService.getBooksHtml(courseId)).replace("\"", ""));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(writer);
        }
        return null;
    }

    @RequestMapping("/editBook")
    public String editNotice(HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        int bookId = Integer.parseInt(req.getParameter("bookId"));
        Map<String, Object> context = new HashMap<String, Object>();
        CourseBook entity = courseBookService.get(bookId);

        context.put("book",entity);
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

    @RequestMapping("/delBook")
    public String delNotice(@RequestParam("courseId") int courseId, HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();

        int bookId = Integer.parseInt(req.getParameter("bookId"));
        int reDate = courseBookService.delete(bookId);
        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程课前绘本数据删除成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程课前绘本数据删除失败!");
        }
        context.put("books",courseBookService.getBooksHtml(courseId));
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

    @RequestMapping("/addSku")
    public String addSku(@RequestParam("courseId") int courseId, HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int skuId = Integer.parseInt(req.getParameter("sku_id"));
        int stock = Integer.parseInt(req.getParameter("stock"));
        Course course = courseService.get(courseId);
        Subject subject = subjectService.get(course.getSubjectId());
        int sum = subject.getStock();
        int reDate = 0;
        CourseSku courseSku = courseSkuService.get(skuId);
        int sku_stock = courseSku.getStock();
        int sku_un_stock = courseSku.getUnlockedStock();
        int int_stock = stock - sku_stock;
        int boolean_stock = sku_un_stock + int_stock;
        if (subject.getType() == 2){
            if (skuId == 0) {
                reDate = courseSkuService.insert(courseSkuService.formEntity(req, courseId, Quantity.STATUS_ZERO));
                sum = sum + stock;
                if (reDate > 0) {
                    subjectService.updateStock(subject.getId(), sum);
                    context.put(Quantity.RETURN_SUCCESS, 0);
                    context.put(Quantity.RETURN_MSG, "课程sku信息保存成功!");
                } else {
                    context.put(Quantity.RETURN_SUCCESS, 1);
                    context.put(Quantity.RETURN_MSG, "课程sku信息保存失败!");
                }
            }else{
                if (boolean_stock < 0){
                    context.put(Quantity.RETURN_SUCCESS, 1);
                    context.put(Quantity.RETURN_MSG, "修改sku的库存信息失败,修改库存数量为:"+stock+";剩余库存数量为:"+sku_un_stock);
                }else{
                    int p_sku_id = courseSku.getParentId();
                    CourseSku p_course_sku = courseSkuService.get(p_sku_id);
                    int p_stock = p_course_sku.getStock();
                    int p_un_stock = p_course_sku.getUnlockedStock();
                    int p_boolean_stock = p_un_stock - int_stock;
                    if (p_boolean_stock < 0){
                        context.put(Quantity.RETURN_SUCCESS, 1);
                        context.put(Quantity.RETURN_MSG, "修改关联sku的库存信息失败,修改关联库存数量为:"+int_stock+";关联sku剩余库存数量为:"+p_un_stock);
                    }else{
                        p_course_sku.setStock(p_stock - int_stock);
                        p_course_sku.setUnlockedStock(p_un_stock - int_stock);
                        reDate = courseSkuService.update(courseSkuService.formEntity(req, courseId, skuId));
                        sum = sum + int_stock;
                        if (reDate > 0) {
                            courseSkuService.update(p_course_sku);
                            subjectService.updateStock(subject.getId(), sum);
                            context.put(Quantity.RETURN_SUCCESS, 0);
                            context.put(Quantity.RETURN_MSG, "课程sku信息保存成功!");
                        } else {
                            context.put(Quantity.RETURN_SUCCESS, 1);
                            context.put(Quantity.RETURN_MSG, "课程sku信息保存失败!");
                        }
                    }
                }
            }
        }else{
            if (skuId == 0) {
                reDate = courseSkuService.insert(courseSkuService.formEntity(req, courseId, Quantity.STATUS_ZERO));
            }else{
                reDate = courseSkuService.update(courseSkuService.formEntity(req, courseId, skuId));
            }
            if (reDate > 0) {
                context.put(Quantity.RETURN_SUCCESS, 0);
                context.put(Quantity.RETURN_MSG, "课程sku信息保存成功!");
            } else {
                context.put(Quantity.RETURN_SUCCESS, 1);
                context.put(Quantity.RETURN_MSG, "课程sku信息保存失败!");
            }
        }

        context.put("skuHtml",courseSkuService.getSkuHtml(courseId));
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

    @RequestMapping("/editSku")
    public String editSku(HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int skuId = Integer.parseInt(req.getParameter("skuId"));
        CourseSku entity = courseSkuService.get(skuId);
        context.put("sku",entity);
        context.put("placeHtml", courseSkuService.getSkuPlaceHtml(skuId));
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

    @RequestMapping("/delSku")
    public String delSku(@RequestParam("courseId") int courseId, HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();

        int skuId = Integer.parseInt(req.getParameter("skuId"));

        Course course = courseService.get(courseId);
        Subject subject = subjectService.get(course.getSubjectId());
        int sum = subject.getStock();
        int sku_stock = courseSkuService.get(skuId).getUnlockedStock();
        sum = sum - sku_stock;
        int reDate = courseSkuService.delete(skuId);
        if (subject.getType() == 2) {
            int update_sub = subjectService.updateStock(subject.getId(), sum);
        }

        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程sku信息删除成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程sku信息删除失败!");
        }
        context.put("skuHtml", courseSkuService.getSkuHtml(courseId));
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

    @RequestMapping("/addplace")
    public ModelAndView addPlace(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = placeService.insert(placeService.formEntity(req, Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG,"地址信息保存成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"地址信息保存失败!");
        }
        context.put("placeHtml", courseSkuService.getSkuPlaceHtml(Quantity.STATUS_ZERO));
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

    @RequestMapping("/teacher")
    public String add_delTeacher(@RequestParam("courseId") int courseId,@RequestParam("mark") int mark, HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = 0;
        if (mark == 1){
            String [] teacherIds = req.getParameterValues("w_teacher");
            for (int i = 0; i < teacherIds.length; i++) {
                reDate = courseTeacherService.insert(courseId, Integer.parseInt(teacherIds[i]));
            }
        }else{
            String [] teacherIds = req.getParameterValues("y_teacher");
            for (int i = 0; i < teacherIds.length; i++) {
                reDate = courseTeacherService.delete(courseId, Integer.parseInt(teacherIds[i]));
            }
        }
        if (reDate > 0){
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG,"讲师信息操作成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"讲师信息操作失败!");
        }
        context.put("y_teacher", teacherService.getY_Teachers(courseId));
        context.put("w_teacher", teacherService.getW_Teachers(courseId));
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

    @RequestMapping("/addDetail")
    public String addDetail(@RequestParam("courseId") int courseId,HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int id = Integer.parseInt(req.getParameter("d_id"));
        int reDate = 0;
        if (id == 0){
            reDate = courseDetailService.insertKey(courseDetailService.setCourseDetail(req, courseId, id));
            id = reDate;
        }else{
            reDate = courseDetailService.update(courseDetailService.setCourseDetail(req, courseId, id));
        }

        if (reDate > 0){
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG,"课程图文详情信息操作成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"课程图文详情信息操作失败!");
        }
        context.put("d_id", id);
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

    @RequestMapping("/loadDetail")
    public String loadDetail(@RequestParam("courseId") int courseId,HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("detailJson",courseDetailService.getDetailContentStr(courseId));
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

    @RequestMapping("/upOrDown")
    public ModelAndView updateStatus(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("mark") int mark, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = courseService.updateStatus(id, mark);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"Course数据上下线成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"Course数据上下线失败!");
        }
        int sub_id = Integer.parseInt(req.getParameter("subid"));
        context.put(Quantity.RETURN_ENTITY_LIST, courseService.getEntitysBySubId(sub_id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put("subid", sub_id);
        return new ModelAndView(JumpPage.COURSE,context);
    }

    @RequestMapping("/isCopy")
    public ModelAndView is_copyCourse(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int id = Integer.parseInt(req.getParameter("course_id"));
        String[] course_sku_id = req.getParameterValues("course_sku_id");
        int success = 0;
        for (int i = 0; i < course_sku_id.length; i++) {
            int old_id = Integer.parseInt(course_sku_id[i]);
            CourseSku course_sku = courseSkuService.get(old_id);
            int old_un_stock = course_sku.getUnlockedStock();
            String stock_str = "stock_" + old_id;
            int new_stock = Integer.parseInt(req.getParameter(stock_str));
            int old_un_stock_1 = old_un_stock - new_stock;
            if (old_un_stock_1 < 0){
                success = 1;
                break;
            }
        }
        context.put("success",success);
        if (success == 1){
            context.put("msg","库存信息已经超出剩余库存，请重新填写!");
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

    @RequestMapping("/copyOper")
    public ModelAndView copyInfo(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("subid") int subid,HttpServletRequest req, HttpServletResponse rsp){
        String retStr = JumpPage.COURSE_COPY;
        Map<String, Object> context = new HashMap<String, Object>();
        List<Course> courses = courseService.getEntitysById(id);
        int c_ls = courses.size();
        if (c_ls > 0){
            retStr = JumpPage.COURSE_COPY_SKU;

        }

        context.put(Quantity.RETURN_ENTITY, courseService.get(id));
        context.put("sku_ls",courseSkuService.getCopySkuHtml(id));
        context.put("subid", subid);
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));

        return new ModelAndView(retStr,context);
    }

    @RequestMapping("/copy")
    public ModelAndView copyCourse(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        try {
            int id = Integer.parseInt(req.getParameter("course_id"));
            String title = req.getParameter("title");
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            Course cour = courseService.get(id);
            Subject sub = subjectService.get(cour.getSubjectId());
            sub.setType(Quantity.STATUS_TWO);
            sub.setTags(sub.getTitle());
            sub.setTitle(title);
            int sub_id = subjectService.insertKey(sub);

            cour.setParentId(cour.getId());
            cour.setSubjectId(sub_id);
            cour.setPrice(price);

            int course_id = 0;
            try {
                course_id = courseService.insertKey(cour);
                courseService.updateNotice(course_id,req.getParameter("notice"));
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }

            SubjectSku sub_sku = new SubjectSku();
            sub_sku.setSubjectId(sub_id);
            sub_sku.setCourseId(course_id);
            sub_sku.setPrice(price);
            sub_sku.setDesc("试听课程,每人只有一次试听机会.");
            sub_sku.setAdult(Quantity.STATUS_ZERO);
            sub_sku.setChild(Quantity.STATUS_ZERO);
            sub_sku.setCourseCount(Quantity.STATUS_ONE);
            sub_sku.setTime(Quantity.STATUS_ONE);
            sub_sku.setTimeUnit(Quantity.STATUS_ONE);
            sub_sku.setOriginalPrice(new BigDecimal(0));

            if (sub_id > 0) {
                subjectSkuService.insertOne(sub_sku);
            }

            if (course_id > 0){

                String[] course_sku_id = req.getParameterValues("course_sku_id");
                int sum = 0;
                for (int i = 0; i < course_sku_id.length; i++) {
                    int old_id = Integer.parseInt(course_sku_id[i]);
                    CourseSku course_sku = courseSkuService.get(old_id);
                    int old_stock = course_sku.getStock();
                    int old_un_stock = course_sku.getUnlockedStock();
                    String stock_str = "stock_" + old_id;
                    int new_stock = Integer.parseInt(req.getParameter(stock_str));
                    int old_stock_1 = old_stock - new_stock;
                    int old_un_stock_1 = old_un_stock - new_stock;

                    course_sku.setStock(old_stock_1);
                    course_sku.setUnlockedStock(old_un_stock_1);
                    courseSkuService.update(course_sku);

                    course_sku.setCourseId(course_id);
                    course_sku.setStock(new_stock);
                    course_sku.setUnlockedStock(new_stock);
                    course_sku.setLockedStock(Quantity.STATUS_ZERO);
                    course_sku.setParentId(course_sku.getId());

                    courseSkuService.insert(course_sku);

                    sum = sum + new_stock;

                }

                int update_stock = subjectService.updateStock(sub_id, sum);

                List<CourseImg> img_ls = courseImgService.getModelsBySub_Key(id);
                for (CourseImg img : img_ls) {
                    img.setCourseId(course_id);
                    courseImgService.insert(img);
                }
                List<CourseBook> book_ls = courseBookService.getEntitys(id);
                for (CourseBook book : book_ls) {
                    book.setCourseId(course_id);
                    courseBookService.insert(book);
                }

                List<CourseDetail> detail_ls = courseDetailService.getModelsByCourse_Key(id);
                for (CourseDetail cour_detail : detail_ls) {
                    cour_detail.setCourseId(course_id);
                    courseDetailService.insert(cour_detail);
                }

                List<CourseTeacher> teacher_ls = courseTeacherService.getCourseTeachers(id);
                for (CourseTeacher CourseTeacher : teacher_ls){
                    courseTeacherService.insert(course_id,CourseTeacher.getTeacherId());
                }

                context.put(Quantity.RETURN_SUCCESS, 0);
                context.put(Quantity.RETURN_MSG,"加入试听课成功!");
            }else{
                context.put(Quantity.RETURN_SUCCESS, 1);
                context.put(Quantity.RETURN_MSG,"加入试听课失败!");
            }

        }catch (Exception _ex){
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"加入试听课失败!");
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

    @RequestMapping("/copySku")
    public ModelAndView copyCourseSku(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        int success = 1;
        Map<String, Object> context = new HashMap<String, Object>();
        try {
            int id = Integer.parseInt(req.getParameter("course_id"));//父级课程ID
            Course cour = courseService.get(id);//父级课程对象
            Course c_cour = courseService.getEntitysById(cour.getId()).get(0);
            int c_sub_id = c_cour.getSubjectId();
            int c_cour_id = c_cour.getId();
            Subject sub = subjectService.get(c_sub_id);
            int sum = sub.getStock();
            String[] course_sku_id = req.getParameterValues("course_sku_id");//父级勾选sku的id的数组
            if (course_sku_id == null){
                context.put(Quantity.RETURN_SUCCESS, 1);
                context.put(Quantity.RETURN_MSG, "没有勾选任何sku信息，请勾选!");
            }else {
                for (int i = 0; i < course_sku_id.length; i++) {
                    int old_id = Integer.parseInt(course_sku_id[i]);//父级sku的id
                    CourseSku course_sku = courseSkuService.get(old_id);//父级sku对象
                    int old_stock = course_sku.getStock();//父级sku对象的库存
                    int old_un_stock = course_sku.getUnlockedStock();//父级sku对象的剩余库存
                    String stock_str = "stock_" + old_id;
                    int new_stock = Integer.parseInt(req.getParameter(stock_str));//页面填写的试听课库存
                    List<CourseSku> courseSkus = courseSkuService.getCourseSkuById(course_sku.getId());//通过父级sku的id获取下属sku对象集合
                    if (courseSkus.size() > 0) {
                        CourseSku courseSku = courseSkus.get(0);
                        int c_old_stock = courseSku.getStock();//如果有下属sku对象，获取第一个sku对象的库存
                        int c_old_un_stock = courseSku.getUnlockedStock();//如果有下属sku对象，获取第一个sku对象的剩余库存

                        int c_old_stock_subtract = new_stock - c_old_stock;

                        if (new_stock < c_old_stock) {
                            int a = c_old_un_stock + c_old_stock_subtract;

                            if (a < 0) {
                                context.put(Quantity.RETURN_SUCCESS, success);
                                context.put(Quantity.RETURN_MSG, "加入试听课失败,填写数值计算已超出现有试听课库存,现有库存:" + c_old_un_stock);
                                break;
                            } else {
                                int new_a = c_old_stock + c_old_stock_subtract;
                                int new_b = c_old_un_stock + c_old_stock_subtract;
                                courseSku.setStock(new_a);
                                courseSku.setUnlockedStock(new_b);
                                courseSkuService.update(courseSku);//更新所属sku

                                int old_a = old_stock - c_old_stock_subtract;
                                int old_b = old_un_stock - c_old_stock_subtract;
                                course_sku.setStock(old_a);
                                course_sku.setUnlockedStock(old_b);
                                courseSkuService.update(course_sku);//更新正常sku

                                sum = sum + c_old_stock_subtract;
                                success = 0;
                            }
                        } else if (new_stock > c_old_stock) {
                            int b = old_un_stock - c_old_stock_subtract;
                            if (b < 0) {
                                context.put(Quantity.RETURN_SUCCESS, success);
                                context.put(Quantity.RETURN_MSG, "加入试听课失败,填写数值计算已超出现有库存,现有库存:" + old_un_stock);
                                break;
                            } else {
                                int new_a = c_old_stock + c_old_stock_subtract;
                                int new_b = c_old_un_stock + c_old_stock_subtract;
                                courseSku.setStock(new_a);
                                courseSku.setUnlockedStock(new_b);
                                courseSkuService.update(courseSku);//更新所属sku

                                int old_a = old_stock - c_old_stock_subtract;
                                int old_b = old_un_stock - c_old_stock_subtract;

                                course_sku.setStock(old_a);
                                course_sku.setUnlockedStock(old_b);
                                courseSkuService.update(course_sku);//更新正常sku

                                sum = sum + c_old_stock_subtract;
                                success = 0;
                            }
                        }else{
                            break;
                        }
                    } else {

                        int old_stock_1 = old_stock - new_stock;
                        int old_un_stock_1 = old_un_stock - new_stock;

                        if (old_un_stock_1 < 0) {
                            context.put(Quantity.RETURN_SUCCESS, success);
                            context.put(Quantity.RETURN_MSG, "加入试听课失败,填写数值计算已超出现有库存,现有库存:" + old_un_stock);
                            break;
                        } else {
                            course_sku.setStock(old_stock_1);
                            course_sku.setUnlockedStock(old_un_stock_1);
                            courseSkuService.update(course_sku);//更新正常课程的SKU库存

                            course_sku.setCourseId(c_cour_id);
                            course_sku.setStock(new_stock);
                            course_sku.setUnlockedStock(new_stock);
                            course_sku.setLockedStock(Quantity.STATUS_ZERO);
                            course_sku.setParentId(course_sku.getId());
                            courseSkuService.insert(course_sku);//添加试听课的新的sku信息

                            sum = sum + new_stock;
                            success = 0;
                        }
                    }
                }
                if (success == 0) {
                    context.put(Quantity.RETURN_SUCCESS, success);
                    context.put(Quantity.RETURN_MSG, "加入试听课成功!");
                    subjectService.updateStock(sub.getId(), sum);//更新试听课的总sku数量
                }
            }
        }catch (Exception _ex){
            _ex.printStackTrace();
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

    @RequestMapping("/setOneCourse")
    public ModelAndView setOneCourse(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int id = Integer.parseInt(req.getParameter("course_id"));
        int set_id = Integer.parseInt(req.getParameter("set_id"));
        int sku_id = Integer.parseInt(req.getParameter("sku_id"));
        int sub_id = Integer.parseInt(req.getParameter("sub_id"));
        try {
            if (set_id == 0){
                set_id = courseRecommendService.insertKey(courseRecommendService.formEntity(req, id, Quantity.STATUS_ZERO));
                if (set_id > 0) {
                    courseService.updateNotice(id, req.getParameter("notice"));
                    SubjectSku subjectSku = subjectSkuService.formEntity(req, Quantity.STATUS_ZERO, Quantity.STATUS_ZERO);
                    subjectSku.setCourseId(id);
                    subjectSku.setSubjectId(sub_id);
                    subjectSkuService.insertOne(subjectSku);
                }
            }else{
                CourseRecommend courseRecommend = courseRecommendService.get(set_id);
                int weight = Integer.parseInt(req.getParameter("weight"));
                courseRecommend.setWeight(weight);
                int update_int = courseRecommendService.update(courseRecommend);
                if (update_int > 0){
                    courseService.updateNotice(id, req.getParameter("notice"));
                    SubjectSku subjectSku = subjectSkuService.formEntity(req, Quantity.STATUS_ZERO, sku_id);
                    subjectSkuService.update(subjectSku);
                }
            }
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG,"设置单独课程成功!");
        }catch (Exception _ex){
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"设置单独课程失败!");
        }

        context.put("set_id",set_id);
        context.put("sku_id",sku_id);

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

    @RequestMapping("/cancelCourse")
    public ModelAndView cancelCourse(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        try {
            List<CourseRecommend> courseRecommends = courseRecommendService.getEntitysByCourseId(id);

            int reData = 0;
            if (courseRecommends.size() > 0){
                for (int i = 0; i < courseRecommends.size(); i++) {
                    reData = courseRecommendService.delete(courseRecommends.get(i).getId());
                }
            }
            if (reData > 0) {
                context.put(Quantity.RETURN_SUCCESS, 0);
                context.put(Quantity.RETURN_MSG, "课程取消推荐成功!");
            }else{
                context.put(Quantity.RETURN_SUCCESS, 1);
                context.put(Quantity.RETURN_MSG,"课程取消推荐失败!");
            }
        }catch (Exception _ex){
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"课程取消推荐失败!");
        }

        int sub_id = Integer.parseInt(req.getParameter("subid"));
        context.put(Quantity.RETURN_ENTITY_LIST, courseService.getEntitysBySubId(sub_id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put("subid", sub_id);
        return new ModelAndView(JumpPage.COURSE,context);
    }


}
