package com.sogokids.controller;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.cooperator.service.CooperatorService;
import com.sogokids.cooperator.service.CooperatorUserService;
import com.sogokids.user.service.UserService;
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
 * Created by hoze on 16/3/31.
 */
@Controller
@RequestMapping("/coop_user")
public class CoopUserController {

    @Autowired
    private UserService adminUserService;

    @Autowired
    private CooperatorService cooperatorService;

    @Autowired
    private CooperatorUserService cooperatorUserService;

    /**
     * 合作单位-人员信息－列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorUserService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req, JumpPage.COOP_USER),context);
    }

    /**
     * 合作单位-人员信息－操作控制
     * @param uid
     * @param id
     * @param mark
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, @RequestParam("mark") int mark,HttpServletRequest req){
        String reStr = JumpPage.COOP_USER_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == Quantity.STATUS_ZERO){
            reStr = JumpPage.COOP_USER_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, cooperatorUserService.get(id));
        }
        context.put("coops",cooperatorService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    /**
     * 合作单位-人员信息－创建
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cooperatorUserService.insert(cooperatorUserService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加合作单位人员数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加合作单位人员数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorUserService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.COOP_USER,context);
    }

    /**
     * 合作单位-人员信息－编辑
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cooperatorUserService.update(cooperatorUserService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改合作单位人员数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改合作单位人员数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorUserService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.COOP_USER,context);
    }

    /**
     * 合作单位-人员信息－删除
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cooperatorUserService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除合作单位人员数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除合作单位人员数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorUserService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.COOP_USER,context);
    }

    /**
     * 合作单位-人员信息－验证用户名重复
     * @param rsp
     * @param req
     * @return
     */
    @RequestMapping("/verify")
    public ModelAndView verifyName(HttpServletRequest req,HttpServletResponse rsp){

        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        String name = StringUtils.isEmpty(req.getParameter("name"))?"":req.getParameter("name").trim();
        if (StringUtils.isEmpty(name)){
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG,"名称不能为空!");
        }else{
            int success = cooperatorUserService.verifyName(name);
            if (success == 0){
                context.put(Quantity.RETURN_SUCCESS, 1);
                context.put(Quantity.RETURN_MSG,"验证名字出现重复,请重新填写!");
            }else{
                context.put(Quantity.RETURN_SUCCESS, 0);
                context.put(Quantity.RETURN_MSG,"验证成功!");
            }
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
