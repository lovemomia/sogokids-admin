package com.sogokids.controller;

import com.sogokids.cooperator.service.CooperatorService;
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
 * Created by hoze on 16/3/31.
 */
@Controller
@RequestMapping("/coop")
public class CooperatorController {

    @Autowired
    private UserService adminUserService;

    @Autowired
    private CooperatorService cooperatorService;

    @Autowired
    private MenuService menuService;


    /**
     * 合作单位－列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_SEVENTEEN));
        return new ModelAndView(adminUserService.isUserFunc(req, JumpPage.COOP),context);
    }

    /**
     * 合作单位－操作控制
     * @param uid
     * @param id
     * @param mark
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, @RequestParam("mark") int mark,HttpServletRequest req){
        String reStr = JumpPage.COOP_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == Quantity.STATUS_ZERO){
            reStr = JumpPage.COOP_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, cooperatorService.get(id));
        }
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_SEVENTEEN));
        return new ModelAndView(reStr,context);
    }

    /**
     * 合作单位－创建
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cooperatorService.insert(cooperatorService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加合作单位数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加合作单位数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_SEVENTEEN));
        return new ModelAndView(JumpPage.COOP,context);
    }

    /**
     * 合作单位－编辑
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cooperatorService.update(cooperatorService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改合作单位数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改合作单位数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_SEVENTEEN));
        return new ModelAndView(JumpPage.COOP,context);
    }

    /**
     * 合作单位－删除
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cooperatorService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除合作单位数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除合作单位数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_SEVENTEEN));
        return new ModelAndView(JumpPage.COOP,context);
    }
}
