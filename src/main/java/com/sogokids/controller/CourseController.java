package com.sogokids.controller;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseBook;
import com.sogokids.course.model.CourseDetail;
import com.sogokids.course.model.CourseRecommend;
import com.sogokids.course.model.CourseSku;
import com.sogokids.teacher.model.CourseTeacher;
import com.sogokids.course.service.CourseBookService;
import com.sogokids.course.service.CourseDetailService;
import com.sogokids.course.service.CourseImgService;
import com.sogokids.course.service.CourseRecommendService;
import com.sogokids.course.service.CourseService;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.teacher.service.CourseTeacherService;
import com.sogokids.http.model.HttpResult;
import com.sogokids.images.model.Images;
import com.sogokids.images.service.ImagesService;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.system.model.Institution;
import com.sogokids.system.service.InstitutionService;
import com.sogokids.system.service.PlaceService;
import com.sogokids.teacher.service.TeacherService;
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
        context.put(Quantity.RETURN_ENTITY_LIST, courseService.getCoursesBySubId(subid));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put("subid", subid);
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.COURSE),context);
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
            context.put("gmxz", Quantity.GMXZ_STR);
            context.put("gzms", Quantity.GZMS_STR);
            context.put(Quantity.RETURN_ENTITY, courseService.get(id));
//            context.put("time_unit", EnumUtil.getEnums(Quantity.STATUS_FOUR));
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
//        context.put("y_teacher", teacherService.getY_Teachers(id));
//        context.put("w_teacher",teacherService.getW_Teachers(id));
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
            List<Course> courses = courseService.getEntitysById(course_id);
            if (courses.size() > 0){
                Course course = courses.get(0);
                int st_course = courseService.update(courseService.formEntityTrial(req, course));
                if (st_course > 0) {
                    context.put(Quantity.RETURN_SUCCESS, 0);
                    context.put(Quantity.RETURN_MSG, "课程基本信息修改成功!");
                }else{
                    context.put(Quantity.RETURN_SUCCESS, 1);
                    context.put(Quantity.RETURN_MSG, "课程基本信息修改失败!");
                }
            }else{
                context.put(Quantity.RETURN_SUCCESS, 0);
                context.put(Quantity.RETURN_MSG, "课程基本信息修改成功!");
            }
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
        context.put(Quantity.RETURN_ENTITY_LIST, courseService.getCoursesBySubId(sub_id));
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
//                List<CourseTeacher> courseTeachers = courseTeacherService.getCourseTeachers(courseId);
//                if (courseTeachers.size() > 0){
                    reDate = courseSkuService.insertKey(courseSkuService.formEntity(req, courseId, Quantity.STATUS_ZERO));
                    if (reDate > 0){
                        HttpResult result = courseService.createQz(req,courseId,reDate);
                        if (result.getErrno() == 0){
                            boolean data = (Boolean)result.getData();
                            if (data) {
                                context.put(Quantity.RETURN_SUCCESS, 0);
                                context.put(Quantity.RETURN_MSG, "创建课程表和群组成功!");
                            }else{
                                context.put(Quantity.RETURN_SUCCESS, 1);
                                context.put(Quantity.RETURN_MSG, "创建群组失败!");
                            }
                        }else if (result.getErrno() == -1){
                            context.put(Quantity.RETURN_SUCCESS, 1);
                            context.put(Quantity.RETURN_MSG, "创建群组失败,无讲师信息!");
                        }else{
                            context.put(Quantity.RETURN_SUCCESS, 1);
                            context.put(Quantity.RETURN_MSG, "创建群组失败!");
                        }
                    }else{
                        context.put(Quantity.RETURN_SUCCESS, 1);
                        context.put(Quantity.RETURN_MSG, "课程sku信息保存失败!");
                    }
//                }
//                else{
//                    context.put(Quantity.RETURN_SUCCESS, 1);
//                    context.put(Quantity.RETURN_MSG, "创建群组失败,无讲师信息!");
//                }
            }else{
                reDate = courseSkuService.update(courseSkuService.formEntity(req, courseId, skuId));
                if (reDate > 0) {
                    context.put(Quantity.RETURN_SUCCESS, 0);
                    context.put(Quantity.RETURN_MSG, "课程sku信息保存成功!");
                } else {
                    context.put(Quantity.RETURN_SUCCESS, 1);
                    context.put(Quantity.RETURN_MSG, "课程sku信息保存失败!");
                }
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
        context.put(Quantity.RETURN_ENTITY_LIST, courseService.getCoursesBySubId(sub_id));
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
//        List<Course> courses = courseService.getEntitysById(id);
//        int c_ls = courses.size();
//        if (c_ls > 0){
//            retStr = JumpPage.COURSE_COPY_SKU;
//        }
        context.put(Quantity.RETURN_ENTITY, courseService.get(id));
        context.put("st_gmxz_str",Quantity.ST_GMXZ_STR);
        context.put("sku_ls",courseSkuService.getCopySkuHtml(id));
        context.put("subid", subid);
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));

        return new ModelAndView(retStr,context);
    }

    @RequestMapping("/copy")
    public ModelAndView copyCourse(@RequestParam("uid") int uid,HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int success = courseService.insert_copyCourseTrial(req);
        if (success == 0){
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG,"加入试听课成功!");
        }else{
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

    @RequestMapping("/cancelCopy")
    public ModelAndView cancelTrialCourse(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();

        int reData = courseService.deleteTrialCourse(id);

        if (reData > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "取消试听课成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"取消试听课失败!");
        }

        int sub_id = Integer.parseInt(req.getParameter("subid"));
        context.put(Quantity.RETURN_ENTITY_LIST, courseService.getCoursesBySubId(sub_id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put("subid", sub_id);
        return new ModelAndView(JumpPage.COURSE,context);
    }

    @RequestMapping("/setOneCourse")
    public ModelAndView setOneCourse(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();

        Map<String, Object> re_context = courseService.insert_setOneCourse(req);
        int success = Integer.parseInt(re_context.get("success").toString());
        if(success == 0){
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG,"设置单独课程成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"设置单独课程失败!");
        }

//        context.put("set_id",re_context.get("set_id"));
//        context.put("sku_id",re_context.get("sku_id"));

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
        int reData = courseService.update_cancelCourse(id);

        if (reData > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程取消推荐成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"课程取消推荐失败!");
        }

        int sub_id = Integer.parseInt(req.getParameter("subid"));
        context.put(Quantity.RETURN_ENTITY_LIST, courseService.getCoursesBySubId(sub_id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put("subid", sub_id);
        return new ModelAndView(JumpPage.COURSE,context);
    }

    @RequestMapping("/cancelSku")
    public String cancelSku(@RequestParam("courseId") int courseId, HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();

        int skuId = Integer.parseInt(req.getParameter("skuId"));
        int reDate = courseSkuService.update_CourseSku(courseId,skuId);
        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程sku信息取消成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程sku信息取消失败!");
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

    @RequestMapping("/preview")
    public ModelAndView preview(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("previewHtml", courseService.getPreview(id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put("subid", Integer.parseInt(req.getParameter("subid")));
        return new ModelAndView(JumpPage.COURSE_PREVIEW,context);
    }
}
