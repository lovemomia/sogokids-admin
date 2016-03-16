package com.sogokids.controller;

import com.sogokids.home.service.EventService;
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
 * event信息
 * Created by hoze on 15/10/13.
 */
@Controller
@RequestMapping("/event")
public class EventController {
    @Autowired
    private UserService adminUserService;

    @Autowired
    private EventService eventService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AppVersionService appVersionService;

    /**
     * event列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, eventService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.EVENT),context);
    }

    /**
     * event操作跳转
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.EVENT_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.EVENT_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, eventService.get(id));
        }
        context.put("versions", appVersionService.getEntitys());
        context.put("citys", cityService.getEntitys());
        context.put("platforms", EnumUtil.getEnums(Quantity.STATUS_NINE));
        context.put("types", EnumUtil.getEnums(Quantity.STATUS_ELEVEN));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    /**
     * 创建event信息
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = eventService.insert(eventService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加event数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加event数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, eventService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.EVENT,context);
    }

    /**
     * 编辑event信息
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = eventService.update(eventService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改event数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改event数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, eventService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.EVENT,context);
    }

    /**
     * 删除event信息
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = eventService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除event数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除event数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, eventService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.EVENT,context);
    }
}
