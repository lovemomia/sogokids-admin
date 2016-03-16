package com.sogokids.controller;

import com.sogokids.system.model.Region;
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
 * 区域信息
 * Created by hoze on 15/7/6.
 */
@Controller
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private CityService cityService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private UserService adminUserService;

    /**
     * 区域－列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, regionService.getEntities(regionService.getEntitys()));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.REGION),context);
    }

    /**
     * 区域－操作跳转
     * @param uid
     * @param id
     * @param mark
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, @RequestParam("mark") int mark,HttpServletRequest req){
        String reStr = JumpPage.REGION_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == 0){
            reStr = JumpPage.REGION_ADD;
        }else if (mark == 1){
        }
        else{
            context.put(Quantity.RETURN_ENTITY,regionService.get(id));
        }
        List<Region> ls = new ArrayList<Region>();
        Region region = new Region();
        region.setId(0);
        region.setName("请选择区域");
        ls.add(region);
        ls.addAll(regionService.getEntitys());
        context.put(Quantity.RETURN_ENTITY_LIST,ls);
        context.put(Quantity.RETURN_CITY_S,cityService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    /**
     * 区域－创建
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = regionService.insert(regionService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加区域信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加区域信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, regionService.getEntities(regionService.getEntitys()));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.REGION,context);
    }

    /**
     * 区域－编辑
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = regionService.update(regionService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改区域信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改区域信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, regionService.getEntities(regionService.getEntitys()));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.REGION,context);
    }

    /**
     * 区域－删除
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate =regionService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除区域信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除区域信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, regionService.getEntities(regionService.getEntitys()));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.REGION,context);
    }
}
