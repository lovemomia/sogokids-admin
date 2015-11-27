package com.sogokids.controller;


import com.sogokids.user.model.User;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_MSG, "");
        return new ModelAndView(JumpPage.USER_LOGIN,context);
    }

    @RequestMapping("/loginpage")
    public ModelAndView loginpage(HttpServletRequest req) {
        String reStr = JumpPage.USER_LOGIN;
        Map<String, Object> context = new HashMap<String, Object>();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User entity = userService.users_exist(username, password);
        if(null != entity.getUsername() && !entity.getUsername().equals("")){
            context.put(Quantity.RETURN_USER, entity);
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "登录成功");
            reStr = JumpPage.INDEX;
        }else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "登录失败");
        }

        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/index")
    public ModelAndView index(@RequestParam("uid") int uid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        User entity = userService.get(uid);
        context.put(Quantity.RETURN_USER, entity);
        return new ModelAndView(JumpPage.INDEX,context);
    }

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, userService.getEntityList(Quantity.WHERE));
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.USER,context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){
        String reStr = JumpPage.USER_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0){
            reStr = JumpPage.USER_ADD;
        }else{
            context.put(Quantity.RETURN_ENTITY,userService.get(id));
        }
        context.put(Quantity.RETURN_USER,userService.get(uid));
        context.put(Quantity.RETURN_MSG, "");
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        String reStr = JumpPage.USER_ADD;
        Map<String, Object> context = new HashMap<String, Object>();

        int reDate = userService.insert(userService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加用户数据成功!");
            reStr = JumpPage.USER;
        }else{
            context.put(Quantity.RETURN_MSG,"用户名已存在!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, userService.getEntityList(Quantity.WHERE));
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.USER,context);
    }

    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = userService.update(userService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改用户数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"用户名已存在!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, userService.getEntityList(Quantity.WHERE));
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.USER,context);
    }

    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = userService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除分类数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除分类数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, userService.getEntityList(Quantity.WHERE));
        context.put(Quantity.RETURN_USER, userService.get(uid));
        return new ModelAndView(JumpPage.USER,context);
    }


}
