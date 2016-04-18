package com.sogokids.controller;


import com.sogokids.system.model.Menu;
import com.sogokids.system.service.MenuService;
import com.sogokids.user.model.AdminRole;
import com.sogokids.user.model.User;
import com.sogokids.user.service.AdminRoleService;
import com.sogokids.user.service.RoleService;
import com.sogokids.user.service.UserService;
import com.sogokids.utils.util.JumpPage;
import com.sogokids.utils.util.MenuUtil;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台用户
 * Created by hoze on 15/8/25.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private MenuService menuService;


    /**
     *跳转登录页面
     * @param req
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_MSG, "");
        return new ModelAndView(JumpPage.USER_LOGIN,context);
    }

    /**
     *验证登录信息
     * @param req
     * @return
     */
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
            context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(entity.getId(),Quantity.STATUS_ONE));
            reStr = JumpPage.INDEX;
        }else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "登录失败");
        }

        return new ModelAndView(reStr,context);
    }

    /**
     *登录跳转首页
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam("uid") int uid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        User entity = userService.get(uid);
        context.put(Quantity.RETURN_USER, entity);
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_ONE));
        return new ModelAndView(JumpPage.INDEX,context);
    }

    /**
     * 用户列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, userService.getEntityList(Quantity.WHERE));
        context.put(Quantity.RETURN_USER, userService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_SIX));
        return new ModelAndView(userService.isUserFunc(req,JumpPage.USER),context);
    }

    /**
     *用户操作跳转
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){
        String reStr = JumpPage.USER_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        int mark = Integer.parseInt(req.getParameter("mark"));
        if (mark == 0){
            reStr = JumpPage.USER_ADD;
        }else if(mark == 3){
            context.put("y_list",roleService.getYList(id));
            context.put("w_list",roleService.getWList(id));
            context.put(Quantity.RETURN_ENTITY,userService.get(id));
            reStr = JumpPage.USER_ROLE;
        }else if(mark == 2){
            context.put(Quantity.RETURN_ENTITY,userService.get(id));
            reStr = JumpPage.USER_PASSWORD_EDIT;
        }else{
            context.put(Quantity.RETURN_ENTITY,userService.get(id));
        }
        context.put(Quantity.RETURN_USER,userService.get(uid));
        context.put(Quantity.RETURN_MSG, "");
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_SIX));
//        context.put(Quantity.RETURN_MENUS, );
        return new ModelAndView(reStr,context);
    }

    /**
     *创建用户
     * @param uid
     * @param req
     * @return
     */
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
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_SIX));
        return new ModelAndView(JumpPage.USER,context);
    }

    /**
     *编辑用户名密码
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = userService.update(userService.formEntity(req, id));
        context.put(Quantity.RETURN_MSG,"");
        return new ModelAndView(JumpPage.USER_LOGIN,context);
    }

    /**
     *编辑用户
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/editEntity")
    public ModelAndView edit(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = userService.update(userService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改用户数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"用户名已存在!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, userService.getEntityList(Quantity.WHERE));
        context.put(Quantity.RETURN_USER, userService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_SIX));
        return new ModelAndView(JumpPage.USER,context);
    }

    /**
     * 删除用户
     * @param uid
     * @param id
     * @param req
     * @return
     */
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
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_SIX));
        return new ModelAndView(JumpPage.USER,context);
    }

    /**
     * 用户角色
     * @param uid
     * @param mark
     * @param req
     * @return
     */
    @RequestMapping("/userRole")
    public ModelAndView editRole(@RequestParam("uid") int uid,@RequestParam("mark") int mark, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();

        int user_id = 0;
        String [] ids = null;
        if (mark == 1){
            ids = req.getParameterValues("y_role");
            user_id = Integer.parseInt(req.getParameter("y_userId"));
        }else{
            ids = req.getParameterValues("w_role");
            user_id = Integer.parseInt(req.getParameter("w_userId"));
        }

        for (int i = 0; i < ids.length; i++) {
            int role_id = Integer.parseInt(ids[i]);
            AdminRole entity = new AdminRole();
            entity.setAdminId(user_id);
            entity.setRoleId(role_id);
            if (mark == 1) {
                adminRoleService.delete(entity);
            }else{
                adminRoleService.insert(entity);
            }
        }

        context.put("y_list",roleService.getYList(user_id));
        context.put("w_list",roleService.getWList(user_id));
        context.put(Quantity.RETURN_ENTITY,userService.get(user_id));
        context.put(Quantity.RETURN_USER, userService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_SIX));
        return new ModelAndView(JumpPage.USER_ROLE,context);
    }

}
