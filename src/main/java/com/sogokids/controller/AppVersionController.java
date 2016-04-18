package com.sogokids.controller;

import com.sogokids.system.service.AppVersionService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * 终端版本
 * Created by hoze on 15/12/21.
 */
@Controller
@RequestMapping("/app")
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;

    @Autowired
    private UserService adminUserService;

    @Autowired
    private MenuService menuService;

    /**
     * app版本列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, appVersionService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_FOUR));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.APP),context);
    }

    /**
     * app相关操作跳转
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.APP_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.APP_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, appVersionService.get(id));
        }

        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_FOUR));
        return new ModelAndView(reStr,context);
    }

    /**
     * 创建app版本信息
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = appVersionService.insert(appVersionService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加AppVersion数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加AppVersion数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, appVersionService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_FOUR));
        return new ModelAndView(JumpPage.APP,context);
    }

    /**
     * 编辑app版本信息
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = appVersionService.update(appVersionService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改AppVersion数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改AppVersion数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, appVersionService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_FOUR));
        return new ModelAndView(JumpPage.APP,context);
    }

    /**
     * 删除app版本信息
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = appVersionService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除AppVersion数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除AppVersion数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, appVersionService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_FOUR));
        return new ModelAndView(JumpPage.APP,context);
    }
}
