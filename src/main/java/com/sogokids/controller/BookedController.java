package com.sogokids.controller;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseDetail;
import com.sogokids.course.model.CourseSku;
import com.sogokids.course.service.CourseBookService;
import com.sogokids.course.service.CourseDetailService;
import com.sogokids.course.service.CourseImgService;
import com.sogokids.course.service.CourseService;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.images.service.ImagesService;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.model.SubjectSku;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.system.model.Institution;
import com.sogokids.system.service.InstitutionService;
import com.sogokids.system.service.MenuService;
import com.sogokids.system.service.PlaceService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 试听课信息
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
    private SubjectSkuService subjectSkuService;

    @Autowired
    private MenuService menuService;

    /**
     *试听课列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, subjectService.getCoursesBySubjects());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THREE));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.BOOKED),context);
    }

    /**
     *试听课相关操作跳转
     * @param uid
     * @param id
     * @param mark
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("mark") int mark){
        String reStr = JumpPage.BOOKED_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == 0) {
            reStr = JumpPage.BOOKED_ADD;
        } else{
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
//        context.put("y_teacher", teacherService.getY_Teachers(id));
//        context.put("w_teacher",teacherService.getW_Teachers(id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THREE));
        return new ModelAndView(reStr,context);
    }

    /**
     *删除试听课
     * @param uid
     * @param id
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id){

        Map<String, Object> context = new HashMap<String, Object>();
        List<CourseSku> courseSkus = courseSkuService.getModelsByCourse_Key(id);
        for (int i = 0; i < courseSkus.size(); i++) {
            courseSkuService.delete(courseSkus.get(i).getId());
        }
        int reDate = courseService.delete(id);
        if (reDate > 0){

            context.put(Quantity.RETURN_MSG,"删除试听课数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除试听课数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, subjectService.getCoursesBySubjects());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THREE));
        return new ModelAndView(JumpPage.BOOKED,context);
    }

    /**
     *试听课上下线
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
            int sub_id = courseService.get(id).getSubjectId();
            subjectService.updateStatus(sub_id, mark);
        }else{
            context.put(Quantity.RETURN_MSG,"Course数据上下线失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, subjectService.getCoursesBySubjects());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THREE));
        return new ModelAndView(JumpPage.BOOKED,context);
    }

    /**
     *获取试听课相关对象信息
     * @param uid
     * @param id
     * @param mark
     * @return
     */
    @RequestMapping("/subcopy")
    public ModelAndView sub_copy(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("mark") int mark){
        Map<String, Object> context = new HashMap<String, Object>();

        Course course = courseService.get(id);//试听课对象
        int parentId = course.getParentId();
        SubjectSku subjectSku = subjectSkuService.getSubjectSku(course.getSubjectId(),id);
        context.put(Quantity.RETURN_ENTITY, courseService.get(parentId));//父级课程对象
        context.put("subSku", subjectSku);//subject sku 对象
        context.put("sku_ls",courseSkuService.getCopySkuHtml(parentId));//html字符串对象
        context.put("course_c", course);

        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THREE));
        return new ModelAndView(JumpPage.SUB_COPY,context);
    }

    /**
     *编辑试听课信息
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/copy")
    public ModelAndView editSubCourse(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        int success = 1;
        Map<String, Object> context = new HashMap<String, Object>();
        try {
            int id = Integer.parseInt(req.getParameter("course_id"));//父级课程ID
            BigDecimal price = new BigDecimal(req.getParameter("price"));
//            BigDecimal originalPrice = new BigDecimal(req.getParameter("originalPrice"));
            String notice = req.getParameter("notice");
            Course cour = courseService.get(id);//父级课程对象

            Course c_cour = courseService.getEntitysById(cour.getId()).get(0);//试听课课程对象
            int c_sub_id = c_cour.getSubjectId();
            int c_cour_id = c_cour.getId();

            Subject sub = subjectService.get(c_sub_id);//课程包对象
            int sum = sub.getStock();//库存总数计算
            String[] course_sku_id = req.getParameterValues("course_sku_id");//父级勾选sku的id的数组
            if (course_sku_id == null){
                SubjectSku subjectSku = subjectSkuService.getSubjectSku(sub.getId(), c_cour_id);
                subjectSku.setPrice(price);
                subjectSku.setOriginalPrice(price);//设置原始价格为售卖价格
                subjectSkuService.update(subjectSku);
                courseService.updateNotice(c_cour.getId(),notice);
                courseSkuService.update_un_check(cour.getId(),course_sku_id);
                success = 0;
                context.put(Quantity.RETURN_SUCCESS, success);
                context.put(Quantity.RETURN_MSG, "试听课编辑成功!");
            }else {
                for (int i = 0; i < course_sku_id.length; i++) {
                    int old_id = Integer.parseInt(course_sku_id[i]);//父级sku的id
                    CourseSku course_sku = courseSkuService.get(old_id);//父级sku对象
                    int old_stock = course_sku.getStock();//父级sku对象的库存
                    int old_un_stock = course_sku.getUnlockedStock();//父级sku对象的剩余库存
                    String stock_str = "stock_" + old_id;
                    int new_stock = Integer.parseInt(req.getParameter(stock_str));//页面填写的试听课库存
                    List<CourseSku> courseSkus = courseSkuService.getCourseSkuById(c_cour_id,course_sku.getId());//通过父级sku的id获取下属sku对象集合
                    if (courseSkus.size() > 0) {
                        CourseSku courseSku = courseSkus.get(0);
                        int c_old_stock = courseSku.getStock();//如果有下属sku对象，获取第一个sku对象的库存
                        int c_old_un_stock = courseSku.getUnlockedStock();//如果有下属sku对象，获取第一个sku对象的剩余库存

                        int c_old_stock_subtract = new_stock - c_old_stock;

                        if (new_stock < c_old_stock) {
                            int a = c_old_un_stock + c_old_stock_subtract;

                            if (a < 0) {
                                context.put(Quantity.RETURN_SUCCESS, success);
                                context.put(Quantity.RETURN_MSG, "试听课编辑失败,填写数值计算已超出现有试听课库存,现有库存:" + c_old_un_stock);
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
                                context.put(Quantity.RETURN_MSG, "试听课编辑失败,填写数值计算已超出现有库存,现有库存:" + old_un_stock);
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
                            success = 0;
                            continue;
                        }
                    } else {

                        int old_stock_1 = old_stock - new_stock;
                        int old_un_stock_1 = old_un_stock - new_stock;

                        if (old_un_stock_1 < 0) {
                            context.put(Quantity.RETURN_SUCCESS, success);
                            context.put(Quantity.RETURN_MSG, "试听课编辑失败,填写数值计算已超出现有库存,现有库存:" + old_un_stock);
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
                    subjectService.updateStock(sub.getId(), sum);//更新试听课的总sku数量
                    SubjectSku subjectSku = subjectSkuService.getSubjectSku(sub.getId(), c_cour_id);
                    subjectSku.setPrice(price);
                    subjectSku.setOriginalPrice(price);
                    subjectSkuService.update(subjectSku);
                    courseService.updateNotice(c_cour.getId(),notice);
                    courseSkuService.update_un_check(cour.getId(),course_sku_id);
                    context.put(Quantity.RETURN_SUCCESS, success);
                    context.put(Quantity.RETURN_MSG, "试听课编辑成功!");
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

    /**
     *取消试听课
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/cancelCopy")
    public ModelAndView cancelTrialCourse(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();

        int reData = courseService.deleteTrialCourse(courseService.get(id).getParentId());

        if (reData > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "取消试听课成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"取消试听课失败!");
        }

        context.put(Quantity.RETURN_ENTITY_LIST, subjectService.getCoursesBySubjects());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THREE));
        return new ModelAndView(JumpPage.BOOKED,context);
    }

    /**
     * 试听课预览
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/preview")
    public ModelAndView preview(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("previewHtml", courseService.getPreview(id));
        context.put(Quantity.RETURN_ENTITY_LIST, subjectService.getCoursesBySubjects());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THREE));
        return new ModelAndView(JumpPage.BOOKED_PREVIEW,context);
    }

}
