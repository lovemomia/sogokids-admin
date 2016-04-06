package com.sogokids.controller;

import com.sogokids.cooperator.service.CooperatorActivityService;
import com.sogokids.cooperator.service.CooperatorService;
import com.sogokids.user.service.UserService;
import com.sogokids.utils.util.EnumUtil;
import com.sogokids.utils.util.JumpPage;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoze on 16/4/1.
 */
@Controller
@RequestMapping("/coop_activity")
public class CoopActivityController {

    @Autowired
    private UserService adminUserService;

    @Autowired
    private CooperatorService cooperatorService;

    @Autowired
    private CooperatorActivityService cooperatorActivityService;

    /**
     * 合作单位-活动信息－列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorActivityService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req, JumpPage.COOP_ACTIVITY),context);
    }

    /**
     * 合作单位-活动信息－操作控制
     * @param uid
     * @param id
     * @param mark
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, @RequestParam("mark") int mark,HttpServletRequest req){
        String reStr = JumpPage.COOP_ACTIVITY_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == Quantity.STATUS_ZERO){
            reStr = JumpPage.COOP_ACTIVITY_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, cooperatorActivityService.get(id));
        }
        context.put("coops",cooperatorService.getEntitys());
        context.put("needs", EnumUtil.getEnums(Quantity.STATUS_TWO));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    /**
     * 合作单位-活动信息－创建
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cooperatorActivityService.insert(cooperatorActivityService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加合作单位活动数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加合作单位活动数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorActivityService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.COOP_ACTIVITY,context);
    }

    /**
     * 合作单位-活动信息－编辑
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cooperatorActivityService.update(cooperatorActivityService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改合作单位活动数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改合作单位活动数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorActivityService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.COOP_ACTIVITY,context);
    }

    /**
     * 合作单位-活动信息－删除
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cooperatorActivityService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除合作单位活动数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除合作单位人员活动失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorActivityService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.COOP_ACTIVITY,context);
    }

    /**
     * 合作单位-活动信息－报名详情
     * @param uid
     * @param id
     * @return
     */
    @RequestMapping("/sign")
    public ModelAndView signUp(@RequestParam("uid") int uid,@RequestParam("id") int id){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY, cooperatorActivityService.getActivityEntrys(id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.COOP_ACTIVITY_ENTRY,context);
    }


}
