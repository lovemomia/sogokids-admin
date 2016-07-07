package com.sogokids.controller;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.query.model.Customer;
import com.sogokids.query.service.CustomerService;
import com.sogokids.system.service.AppVersionService;
import com.sogokids.system.service.CityService;
import com.sogokids.system.service.MenuService;
import com.sogokids.user.service.UserService;
import com.sogokids.utils.util.EnumUtil;
import com.sogokids.utils.util.JumpPage;
import com.sogokids.utils.util.Quantity;
import com.sogokids.wd.service.ExpertBannerService;
import com.sogokids.wd.service.ExpertCourseService;
import com.sogokids.wd.service.ExpertService;
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
import java.util.Map;

/**
 * Created by hoze on 16/6/28.
 */
@Controller
@RequestMapping("/expert")
public class WdExpertController {

    @Autowired
    private UserService adminUserService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private ExpertBannerService expertBannerService;

    @Autowired
    private ExpertCourseService expertCourseService;

    @Autowired
    private AppVersionService appVersionService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CityService cityService;

    @RequestMapping("/banner_info")
    public ModelAndView banner_info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, expertBannerService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_THREE));
        return new ModelAndView(adminUserService.isUserFunc(req, JumpPage.EXPERT_BANNER),context);
    }

    @RequestMapping("/banner_oper")
    public ModelAndView banner_oper(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.EXPERT_BANNER_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.EXPERT_BANNER_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, expertBannerService.get(id));
        }
        context.put("citys", cityService.getEntitys());
        context.put("versions", appVersionService.getEntitys());
        context.put("platforms", EnumUtil.getEnums(Quantity.STATUS_NINE));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_THREE));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/banner_add")
    public ModelAndView addBanner(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = expertBannerService.insert(expertBannerService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加e BANNER数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加e BANNER数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, expertBannerService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_THREE));
        return new ModelAndView(JumpPage.EXPERT_BANNER,context);
    }

    @RequestMapping("/banner_edit")
    public ModelAndView editBanner(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = expertBannerService.update(expertBannerService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改e BANNER数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改e BANNER数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, expertBannerService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_THREE));
        return new ModelAndView(JumpPage.EXPERT_BANNER,context);
    }

    @RequestMapping("/banner_del")
    public ModelAndView delBanner(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = expertBannerService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除e BANNER数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除e BANNER数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, expertBannerService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_THREE));
        return new ModelAndView(JumpPage.EXPERT_BANNER,context);
    }

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, expertService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_FOUR));
        return new ModelAndView(adminUserService.isUserFunc(req, JumpPage.EXPERT),context);
    }

    @RequestMapping("/oper")
    public ModelAndView expert_oper(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.EXPERT_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.EXPERT_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, expertService.get(id));
        }

        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_FOUR));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/add")
    public ModelAndView addExpert(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = expertService.insert(expertService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            customerService.updateCustomerStatus(Integer.parseInt(req.getParameter("userId")));
            context.put(Quantity.RETURN_SUCCESS,0);
            context.put(Quantity.RETURN_MSG,"添加expert数据成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS,1);
            context.put(Quantity.RETURN_MSG,"添加expert数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, expertService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_FOUR));
        return new ModelAndView(JumpPage.EXPERT,context);
    }

    @RequestMapping("/edit")
    public ModelAndView editExpert(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = expertService.update(expertService.formEntity(req, id));
        if (reDate > 0){
            customerService.updateCustomerStatus(Integer.parseInt(req.getParameter("userId")));
            context.put(Quantity.RETURN_SUCCESS,0);
            context.put(Quantity.RETURN_MSG,"修改expert数据成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS,1);
            context.put(Quantity.RETURN_MSG,"修改expert数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, expertService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_FOUR));
        return new ModelAndView(JumpPage.EXPERT,context);
    }

    @RequestMapping("/del")
    public ModelAndView delExpert(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = expertService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除expert数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除expert数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, expertService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_FOUR));
        return new ModelAndView(JumpPage.EXPERT,context);
    }

    @RequestMapping("/course_info")
    public ModelAndView course_info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, expertCourseService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_FIVE));
        return new ModelAndView(adminUserService.isUserFunc(req, JumpPage.EXPERT_COURSE),context);
    }

    @RequestMapping("/course_oper")
    public ModelAndView course_oper(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.EXPERT_COURSE_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.EXPERT_COURSE_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, expertCourseService.get(id));
        }

        context.put("experts",expertService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_FIVE));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/course_add")
    public ModelAndView addCourse(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = expertCourseService.insert(expertCourseService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加expert course数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加expert course数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, expertCourseService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_FIVE));
        return new ModelAndView(JumpPage.EXPERT_COURSE,context);
    }

    @RequestMapping("/course_edit")
    public ModelAndView editCourse(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = expertCourseService.update(expertCourseService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改expert course 数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改expert course数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, expertCourseService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_FIVE));
        return new ModelAndView(JumpPage.EXPERT_COURSE,context);
    }

    @RequestMapping("/course_del")
    public ModelAndView delCourse(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = expertCourseService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除expert course 数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除expert course 数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, expertCourseService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_FIVE));
        return new ModelAndView(JumpPage.EXPERT_COURSE,context);
    }

    /**
     * 验证专家与注册用户手机
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
            context.put(Quantity.RETURN_MSG,"操作信息成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS,1);
            context.put(Quantity.RETURN_MSG,"操作信息失败,原因:手机号验证不是注册用户!");
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

}
