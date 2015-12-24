package com.sogokids.controller;


import com.sogokids.user.model.AdminRole;
import com.sogokids.user.model.RoleFunc;
import com.sogokids.user.model.User;
import com.sogokids.user.service.FuncService;
import com.sogokids.user.service.RoleFuncService;
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
    private RoleFuncService roleFuncService;

    @Autowired
    private UserService userService;

    @Autowired
    private FuncService funcService;

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, roleService.getEntityList());
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(userService.isUserFunc(req,JumpPage.ROLE),context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){
        String reStr = JumpPage.ROLE_EDIT;
        int mark = Integer.parseInt(req.getParameter("mark"));
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == 0){
            reStr = JumpPage.ROLE_ADD;
        }else if(mark == 3){
            context.put("y_list",funcService.getYList(id));
            context.put("w_list",funcService.getWList(id));
            context.put(Quantity.RETURN_ENTITY,roleService.get(id));
            reStr = JumpPage.ROLE_FUNC;
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

    @RequestMapping("/roleFunc")
    public ModelAndView editFunc(@RequestParam("uid") int uid,@RequestParam("mark") int mark, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();

        int role_id = 0;
        String [] ids = null;
        if (mark == 1){
            ids = req.getParameterValues("y_func");
            role_id = Integer.parseInt(req.getParameter("y_roleId"));
        }else{
            ids = req.getParameterValues("w_func");
            role_id = Integer.parseInt(req.getParameter("w_roleId"));
        }

        for (int i = 0; i < ids.length; i++) {
            int func_id = Integer.parseInt(ids[i]);
            RoleFunc entity = new RoleFunc();
            entity.setRoleId(role_id);
            entity.setFuncId(func_id);
            if (mark == 1) {
                roleFuncService.delete(entity);
            }else{
                roleFuncService.insert(entity);
            }
        }

        context.put("y_list",funcService.getYList(role_id));
        context.put("w_list",funcService.getWList(role_id));
        context.put(Quantity.RETURN_ENTITY,roleService.get(role_id));
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.ROLE_FUNC,context);
    }


}
