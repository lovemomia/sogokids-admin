package com.sogokids.controller;


import com.sogokids.user.service.FuncService;
import com.sogokids.user.service.UserService;
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
 * Created by hoze on 15/8/25.
 */
@Controller
@RequestMapping("/func")
public class FuncController {

    @Autowired
    private FuncService funcService;

    @Autowired
    private UserService userService;

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, funcService.getEntityList());
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(userService.isUserFunc(req,JumpPage.FUNC),context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){
        String reStr = JumpPage.FUNC_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0){
            reStr = JumpPage.FUNC_ADD;
        }else{
            context.put(Quantity.RETURN_ENTITY,funcService.get(id));
        }
        context.put(Quantity.RETURN_USER,userService.get(uid));
        context.put(Quantity.RETURN_MSG, "");
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();

        int reDate = funcService.insert(funcService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加用户权限数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加用户权限数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, funcService.getEntityList());
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.FUNC,context);
    }

    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = funcService.update(funcService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改用户权限数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改用户权限数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, funcService.getEntityList());
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.FUNC,context);
    }

    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = funcService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除用户权限数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除用户权限数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, funcService.getEntityList());
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.FUNC,context);
    }



}
