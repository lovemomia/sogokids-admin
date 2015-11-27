package com.sogokids.controller;


import com.sogokids.user.model.User;
import com.sogokids.user.service.RoleService;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, roleService.getEntityList());
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.ROLE,context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){
        String reStr = JumpPage.ROLE_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0){
            reStr = JumpPage.ROLE_ADD;
        }else{
            context.put(Quantity.RETURN_ENTITY,roleService.get(id));
        }
        context.put(Quantity.RETURN_USER,userService.get(uid));
        context.put(Quantity.RETURN_MSG, "");
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();

        int reDate = roleService.insert(roleService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加用户角色数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加用户角色数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, roleService.getEntityList());
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.ROLE,context);
    }

    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = roleService.update(roleService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改用户角色数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改用户角色数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, roleService.getEntityList());
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.ROLE,context);
    }

    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = roleService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除用户角色数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除用户角色数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, roleService.getEntityList());
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.ROLE,context);
    }


}
