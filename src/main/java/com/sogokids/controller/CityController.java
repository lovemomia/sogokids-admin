package com.sogokids.controller;


import com.sogokids.system.service.CityService;
import com.sogokids.system.service.RegionService;
import com.sogokids.user.service.UserService;
import com.sogokids.utils.util.JumpPage;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/7/6.
 */
@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private UserService adminUserService;

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, cityService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.CITY),context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, @RequestParam("mark") int mark,HttpServletRequest req){
        String reStr = JumpPage.CITY_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == 0){
            reStr = JumpPage.CITY_ADD;
        }else if (mark == 1){
//            reStr = FileUtil.PLACE_IMG;
//            context.put(FinalUtil.ENTITY,placeService.get(id));
//            context.put(FinalUtil.ENTITYS,placeImgService.getEntitysByKey(id));
        }
        else{
            context.put(Quantity.RETURN_ENTITY, cityService.get(id));
        }
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cityService.insert(cityService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加city信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加city信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cityService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.CITY,context);
    }

    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cityService.update(cityService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改city信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改city信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cityService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.CITY,context);
    }

    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cityService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除city信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除city信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, cityService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.CITY,context);
    }
}
