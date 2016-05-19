package com.sogokids.controller;

import com.sogokids.dealGroup.service.DealGroupJoinedService;
import com.sogokids.dealGroup.service.DealGroupService;
import com.sogokids.dealGroup.service.DealGroupSubjectService;
import com.sogokids.system.service.MenuService;
import com.sogokids.user.service.UserService;
import com.sogokids.utils.util.JumpPage;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoze on 16/4/22.
 */
@Controller
@RequestMapping("/deal")
public class DealGroupController {

    @Autowired
    private UserService adminUserService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DealGroupSubjectService dealGroupSubjectService;

    @Autowired
    private DealGroupService dealGroupService;

    @Autowired
    private DealGroupJoinedService dealGroupJoinedService;

    /**
     * 拼团列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, dealGroupSubjectService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_NINE));
        return new ModelAndView(adminUserService.isUserFunc(req, JumpPage.DEAL_SUBJECT),context);
    }

    /**
     * 拼团相关操作跳转
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.DEAL_SUBJECT_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.DEAL_SUBJECT_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, dealGroupSubjectService.get(id));
        }
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_NINE));
        return new ModelAndView(reStr,context);
    }

    /**
     * 创建拼团信息
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = dealGroupSubjectService.insert(dealGroupSubjectService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加拼团数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加拼团数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, dealGroupSubjectService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_NINE));
        return new ModelAndView(JumpPage.DEAL_SUBJECT,context);
    }

    /**
     * 编辑拼团信息
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = dealGroupSubjectService.update(dealGroupSubjectService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改拼团数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改拼团数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, dealGroupSubjectService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_NINE));
        return new ModelAndView(JumpPage.DEAL_SUBJECT,context);
    }

    /**
     * 删除拼团信息
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = dealGroupSubjectService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除拼团数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除拼团数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, dealGroupSubjectService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_NINE));
        return new ModelAndView(JumpPage.DEAL_SUBJECT,context);
    }

    /**
     * 获取课程体系和sku联动的json数据
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/dataJson")
    public String getJson(HttpServletRequest req,HttpServletResponse rsp){
        int id = Integer.parseInt(req.getParameter("id"));
        rsp.setContentType("text/html; charset=UTF-8");
        String reStr = dealGroupSubjectService.getSubjectSkuJsonStr(id);
        try {
            rsp.getWriter().write(reStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 拼团上下线
     * @param uid
     * @param id
     * @param mark
     * @param req
     * @return
     */
    @RequestMapping("/upOrDown")
    public ModelAndView updateStatus(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("mark") int mark, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = dealGroupSubjectService.updateStatus(id, mark);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"拼团上下线成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"拼团上下线失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, dealGroupSubjectService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_NINE));
        return new ModelAndView(JumpPage.DEAL_SUBJECT,context);
    }

    /**
     * 用户拼团列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/group")
    public ModelAndView group(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, dealGroupService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY));
        return new ModelAndView(adminUserService.isUserFunc(req, JumpPage.DEAL_GROUP),context);
    }

    /**
     * 用户拼团人员详情
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/join")
    public ModelAndView join(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        int deal_group_id = Integer.parseInt(req.getParameter("id"));
        context.put(Quantity.RETURN_ENTITY_LIST, dealGroupJoinedService.getJoinsByGroupId(deal_group_id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY));
        return new ModelAndView(JumpPage.DEAL_GROUP,context);
    }


}
