package com.sogokids.controller;

import com.sogokids.system.service.InstitutionService;
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
 * 机构信息
 * Created by hoze on 15/10/20.
 */
@Controller
@RequestMapping("/inst")
public class InstitutionController {

    @Autowired
    private UserService adminUserService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private MenuService menuService;

    /**
     *机构－列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, institutionService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_THREE));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.INST),context);
    }

    /**
     *机构－操作跳转
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.INST_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.INST_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, institutionService.get(id));
        }
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_THREE));
        return new ModelAndView(reStr,context);
    }

    /**
     * 机构－创建
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = institutionService.insert(institutionService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加机构数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加机构数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, institutionService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_THREE));
        return new ModelAndView(JumpPage.INST,context);
    }

    /**
     * 机构－编辑
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = institutionService.update(institutionService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改机构数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改机构数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, institutionService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_THREE));
        return new ModelAndView(JumpPage.INST,context);
    }

    /**
     * 机构－删除
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = institutionService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除机构数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除机构数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, institutionService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_THREE));
        return new ModelAndView(JumpPage.INST,context);
    }

}
