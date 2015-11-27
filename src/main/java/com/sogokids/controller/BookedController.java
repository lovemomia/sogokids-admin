package com.sogokids.controller;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseBook;
import com.sogokids.course.model.CourseDetail;
import com.sogokids.course.model.CourseSku;
import com.sogokids.course.service.CourseBookService;
import com.sogokids.course.service.CourseDetailService;
import com.sogokids.course.service.CourseImgService;
import com.sogokids.course.service.CourseService;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.images.model.Images;
import com.sogokids.images.service.ImagesService;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.model.SubjectNotice;
import com.sogokids.subject.model.SubjectSku;
import com.sogokids.subject.service.SubjectImgService;
import com.sogokids.subject.service.SubjectNoticeService;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.system.model.Institution;
import com.sogokids.system.service.CityService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/10/13.
 */
@Controller
@RequestMapping("/book")
public class BookedController {
    @Autowired
    private UserService adminUserService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ImagesService imagesService;

    @Autowired
    private CourseImgService courseImgService;

    @Autowired
    private CourseBookService courseBookService;

    @Autowired
    private CourseSkuService courseSkuService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private CourseDetailService courseDetailService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, subjectService.getCoursesBySubjects());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.BOOKED,context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("mark") int mark,HttpServletRequest req){
        String reStr = JumpPage.BOOKED_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == 0) {
            reStr = JumpPage.BOOKED_ADD;
        }
//        else if(mark == 1){
//            List<CourseDetail> entity_ls = new ArrayList<CourseDetail>();
//            entity_ls = courseDetailService.getModelsByCourse_Key(id);
//            CourseDetail entity = new CourseDetail();
//            if (entity_ls.size() > 0){
//                entity = entity_ls.get(0);
//            }
//            context.put("detail", entity);
//            context.put("course", courseService.get(id));
//            reStr = JumpPage.COURSE_DETAIL;
//        }
////        else if(mark == 2){
////            context.put(Quantity.RETURN_ENTITY, courseService.get(id));
////            context.put("sku_ls",courseSkuService.getCopySkuHtml(id));
////            reStr = JumpPage.COURSE_COPY;
////        }
        else{
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
        context.put("insts", insts);

        context.put("places", placeService.getEntitys());
        context.put("subs", subjectService.getEntitys());
        context.put("y_teacher", teacherService.getY_Teachers(id));
        context.put("w_teacher",teacherService.getW_Teachers(id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

//    @RequestMapping("/edit")
//    public String editEntity(HttpServletRequest req, HttpServletResponse rsp){
//        rsp.setContentType("text/html; charset=UTF-8");
//        int course_id = Integer.parseInt(req.getParameter("course_id"));
//        Map<String, Object> context = new HashMap<String, Object>();
//        int reDate = courseService.update(courseService.formEntity(req, course_id));
//        if (reDate > 0) {
//            context.put(Quantity.RETURN_SUCCESS, 0);
//            context.put(Quantity.RETURN_MSG, "课程基本信息修改成功!");
//        } else {
//            context.put(Quantity.RETURN_SUCCESS, 1);
//            context.put(Quantity.RETURN_MSG, "课程基本信息修改失败!");
//        }
//        context.put("courseId",course_id);
//        Writer writer = null;
//        try {
//            writer = rsp.getWriter();
//            writer.write(JSONObject.toJSONString(context));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            IOUtils.closeQuietly(writer);
//        }
//        return null;
//    }
//
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = courseService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除试听课数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除试听课数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, subjectService.getCoursesBySubjects());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.BOOKED,context);
    }
//
//    @RequestMapping("/addImg")
//    public String addImg(@RequestParam("courseId") int courseId,HttpServletRequest req, HttpServletResponse rsp) {
//        rsp.setContentType("text/html; charset=UTF-8");
//        Images image = imagesService.uploadImg(req, 0, "","true");
//        int reDate = courseImgService.insert(courseImgService.formEntity(image, courseId));
//        Writer writer = null;
//        try {
//            writer = rsp.getWriter();
//            writer.write(JSONObject.toJSONString(courseImgService.getImgHtml(courseId)).replace("\"", ""));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            IOUtils.closeQuietly(writer);
//        }
//
//        return null;
//    }
//
//    @RequestMapping("/delImg")
//    public String delImg(@RequestParam("courseId") int courseId,HttpServletRequest req, HttpServletResponse rsp) {
//        rsp.setContentType("text/html; charset=UTF-8");
//        Map<String, Object> context = new HashMap<String, Object>();
//        String imgstr = req.getParameter("imgs");
//        int reDate = courseImgService.delete(Integer.parseInt(imgstr));
//
//        if (reDate > 0) {
//            context.put(Quantity.RETURN_SUCCESS, 0);
//            context.put(Quantity.RETURN_MSG, "图片删除成功!");
//        } else {
//            context.put(Quantity.RETURN_SUCCESS, 1);
//            context.put(Quantity.RETURN_MSG, "图片删除失败!");
//        }
//
//        context.put("imageJson",courseImgService.getImgHtml(courseId));
//        Writer writer = null;
//        try {
//            writer = rsp.getWriter();
//            writer.write(JSONObject.toJSONString(context));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            IOUtils.closeQuietly(writer);
//        }
//        return null;
//    }
//
//    @RequestMapping("/addBook")
//    public String addNotice(@RequestParam("courseId") int courseId,HttpServletRequest req, HttpServletResponse rsp) {
//        rsp.setContentType("text/html; charset=UTF-8");
//        int bookId = Integer.parseInt(req.getParameter("book_id"));
//        int order = Integer.parseInt(req.getParameter("order"));
//        String img = req.getParameter("book_img");
//        Images images = new Images();
//        images.setUrl(img);
//        Map<String, Object> context = new HashMap<String, Object>();
//        int reDate = 0;
//        if (bookId == 0) {
//            reDate = courseBookService.insert(courseBookService.formEntity(images, courseId, bookId, order));
//        }else{
//            reDate = courseBookService.update(courseBookService.formEntity(images, courseId, bookId, order));
//        }
//        if (reDate > 0) {
//            context.put(Quantity.RETURN_SUCCESS, 0);
//            context.put(Quantity.RETURN_MSG, "课程课前绘本添加数据成功!");
//        } else {
//            context.put(Quantity.RETURN_SUCCESS, 1);
//            context.put(Quantity.RETURN_MSG, "课程课前绘本添加数据失败!");
//        }
//        context.put("books",courseBookService.getBooksHtml(courseId));
//        Writer writer = null;
//        try {
//            writer = rsp.getWriter();
//            writer.write(JSONObject.toJSONString(context));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            IOUtils.closeQuietly(writer);
//        }
//        return null;
//    }
//
//    @RequestMapping("/editBook")
//    public String editNotice(HttpServletRequest req, HttpServletResponse rsp) {
//        rsp.setContentType("text/html; charset=UTF-8");
//        int bookId = Integer.parseInt(req.getParameter("bookId"));
//        Map<String, Object> context = new HashMap<String, Object>();
//        CourseBook entity = courseBookService.get(bookId);
//
//        context.put("book",entity);
//        Writer writer = null;
//        try {
//            writer = rsp.getWriter();
//            writer.write(JSONObject.toJSONString(context));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            IOUtils.closeQuietly(writer);
//        }
//        return null;
//    }
//
//    @RequestMapping("/delBook")
//    public String delNotice(@RequestParam("courseId") int courseId, HttpServletRequest req, HttpServletResponse rsp) {
//        rsp.setContentType("text/html; charset=UTF-8");
//        Map<String, Object> context = new HashMap<String, Object>();
//
//        int bookId = Integer.parseInt(req.getParameter("bookId"));
//        int reDate = courseBookService.delete(bookId);
//        if (reDate > 0) {
//            context.put(Quantity.RETURN_SUCCESS, 0);
//            context.put(Quantity.RETURN_MSG, "课程课前绘本数据删除成功!");
//        } else {
//            context.put(Quantity.RETURN_SUCCESS, 1);
//            context.put(Quantity.RETURN_MSG, "课程课前绘本数据删除失败!");
//        }
//        context.put("books",courseBookService.getBooksHtml(courseId));
//        Writer writer = null;
//        try {
//            writer = rsp.getWriter();
//            writer.write(JSONObject.toJSONString(context));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            IOUtils.closeQuietly(writer);
//        }
//        return null;
//    }
//
//    @RequestMapping("/addSku")
//    public String addSku(@RequestParam("courseId") int courseId, HttpServletRequest req, HttpServletResponse rsp) {
//        rsp.setContentType("text/html; charset=UTF-8");
//        Map<String, Object> context = new HashMap<String, Object>();
//        int skuId = Integer.parseInt(req.getParameter("sku_id"));
//        int stock = Integer.parseInt(req.getParameter("stock"));
//        Course course = courseService.get(courseId);
//        Subject subject = subjectService.get(course.getSubjectId());
//        int sum = subject.getStock();
//        int reDate = 0;
//        if (skuId == 0) {
//            reDate = courseSkuService.insert(courseSkuService.formEntity(req, courseId, Quantity.STATUS_ZERO));
//            sum = sum + stock;
//        }else{
//            int sku_stock = courseSkuService.get(skuId).getStock();
//            reDate = courseSkuService.update(courseSkuService.formEntity(req, courseId, skuId));
//            int int_stock = stock - sku_stock;
//            sum = sum + int_stock;
//        }
//
//        if (reDate > 0) {
//            if (subject.getType() == 2) {
//                int update_sub = subjectService.updateStock(subject.getId(), sum);
//            }
//
//            context.put(Quantity.RETURN_SUCCESS, 0);
//            context.put(Quantity.RETURN_MSG, "课程sku信息保存成功!");
//        } else {
//            context.put(Quantity.RETURN_SUCCESS, 1);
//            context.put(Quantity.RETURN_MSG, "课程sku信息保存失败!");
//        }
//        context.put("skuHtml",courseSkuService.getSkuHtml(courseId));
//        Writer writer = null;
//        try {
//            writer = rsp.getWriter();
//            writer.write(JSONObject.toJSONString(context));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            IOUtils.closeQuietly(writer);
//        }
//        return null;
//    }
//
//    @RequestMapping("/editSku")
//    public String editSku(HttpServletRequest req, HttpServletResponse rsp) {
//        rsp.setContentType("text/html; charset=UTF-8");
//        Map<String, Object> context = new HashMap<String, Object>();
//        int skuId = Integer.parseInt(req.getParameter("skuId"));
//        CourseSku entity = courseSkuService.get(skuId);
//        context.put("sku",entity);
//        context.put("placeHtml", courseSkuService.getSkuPlaceHtml(skuId));
//        Writer writer = null;
//        try {
//            writer = rsp.getWriter();
//            writer.write(JSONObject.toJSONString(context));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            IOUtils.closeQuietly(writer);
//        }
//        return null;
//    }
//
//    @RequestMapping("/delSku")
//    public String delSku(@RequestParam("courseId") int courseId, HttpServletRequest req, HttpServletResponse rsp) {
//        rsp.setContentType("text/html; charset=UTF-8");
//        Map<String, Object> context = new HashMap<String, Object>();
//
//        int skuId = Integer.parseInt(req.getParameter("skuId"));
//
//        Course course = courseService.get(courseId);
//        Subject subject = subjectService.get(course.getSubjectId());
//        int sum = subject.getStock();
//        int sku_stock = courseSkuService.get(skuId).getStock();
//        sum = sum - sku_stock;
//        int reDate = courseSkuService.delete(skuId);
//        if (subject.getType() == 2) {
//            int update_sub = subjectService.updateStock(subject.getId(), sum);
//        }
//
//        if (reDate > 0) {
//            context.put(Quantity.RETURN_SUCCESS, 0);
//            context.put(Quantity.RETURN_MSG, "课程sku信息删除成功!");
//        } else {
//            context.put(Quantity.RETURN_SUCCESS, 1);
//            context.put(Quantity.RETURN_MSG, "课程sku信息删除失败!");
//        }
//        context.put("skuHtml", courseSkuService.getSkuHtml(courseId));
//        Writer writer = null;
//        try {
//            writer = rsp.getWriter();
//            writer.write(JSONObject.toJSONString(context));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            IOUtils.closeQuietly(writer);
//        }
//        return null;
//    }
//
//    @RequestMapping("/addplace")
//    public ModelAndView addPlace(HttpServletRequest req, HttpServletResponse rsp){
//        rsp.setContentType("text/html; charset=UTF-8");
//        Map<String, Object> context = new HashMap<String, Object>();
//        int reDate = placeService.insert(placeService.formEntity(req, Quantity.STATUS_ONE));
//        if (reDate > 0){
//            context.put(Quantity.RETURN_SUCCESS, 0);
//            context.put(Quantity.RETURN_MSG,"地址信息保存成功!");
//        }else{
//            context.put(Quantity.RETURN_SUCCESS, 1);
//            context.put(Quantity.RETURN_MSG,"地址信息保存失败!");
//        }
//        context.put("placeHtml", courseSkuService.getSkuPlaceHtml(Quantity.STATUS_ZERO));
//        Writer writer = null;
//        try {
//            writer = rsp.getWriter();
//            writer.write(JSONObject.toJSONString(context));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            IOUtils.closeQuietly(writer);
//        }
//        return null;
//    }
//
//    @RequestMapping("/addDetail")
//    public String addDetail(HttpServletRequest req, HttpServletResponse rsp){
//        rsp.setContentType("text/html; charset=UTF-8");
//        Map<String, Object> context = new HashMap<String, Object>();
//        int course_id = Integer.parseInt(req.getParameter("course_id"));
//        int id = Integer.parseInt(req.getParameter("d_id"));
//        int reDate = 0;
//        if (id == 0){
//            reDate = courseDetailService.insertKey(courseDetailService.formEntity(req, course_id, id));
//            id = reDate;
//        }else{
//            reDate = courseDetailService.update(courseDetailService.formEntity(req, course_id, id));
//        }
//
//        if (reDate > 0){
//            context.put(Quantity.RETURN_SUCCESS, 0);
//            context.put(Quantity.RETURN_MSG,"详情信息操作成功!");
//        }else{
//            context.put(Quantity.RETURN_SUCCESS, 1);
//            context.put(Quantity.RETURN_MSG,"详情信息操作失败!");
//        }
//        context.put("d_id", id);
//        Writer writer = null;
//        try {
//            writer = rsp.getWriter();
//            writer.write(JSONObject.toJSONString(context));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            IOUtils.closeQuietly(writer);
//        }
//        return null;
//    }

    @RequestMapping("/upOrDown")
    public ModelAndView updateStatus(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("mark") int mark, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();

        int reDate = courseService.updateStatus(id, mark);

        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"Course数据上下线成功!");
            int sub_id = courseService.get(id).getSubjectId();
//            System.out.print(sub_id);
            subjectService.updateStatus(sub_id, mark);
        }else{
            context.put(Quantity.RETURN_MSG,"Course数据上下线失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, subjectService.getCoursesBySubjects());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.BOOKED,context);
    }


}
