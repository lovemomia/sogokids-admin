package com.sogokids.controller;

import com.sogokids.home.service.BannerService;
import com.sogokids.home.service.IconService;
import com.sogokids.system.service.AppVersionService;
import com.sogokids.system.service.CityService;
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
 * Created by hoze on 15/10/13.
 */
@Controller
@RequestMapping("/icon")
public class IconController {
    @Autowired
    private UserService adminUserService;

    @Autowired
    private IconService iconService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AppVersionService appVersionService;

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, iconService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.ICON),context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.ICON_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.ICON_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, iconService.get(id));
        }
        context.put("versions", appVersionService.getEntitys());
        context.put("citys", cityService.getEntitys());
        context.put("platforms", EnumUtil.getEnums(Quantity.STATUS_NINE));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = iconService.insert(iconService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加icon数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加icon数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, iconService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.ICON,context);
    }

    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = iconService.update(iconService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改icon数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改icon数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, iconService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.ICON,context);
    }

    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = iconService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除icon数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除icon数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, iconService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.ICON,context);
    }
}
