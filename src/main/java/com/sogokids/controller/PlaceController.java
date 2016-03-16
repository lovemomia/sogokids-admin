package com.sogokids.controller;

import com.sogokids.images.service.ImagesService;
import com.sogokids.system.service.CityService;
import com.sogokids.system.service.PlaceImgService;
import com.sogokids.system.service.PlaceService;
import com.sogokids.system.service.RegionService;
import com.sogokids.user.service.UserService;
import com.sogokids.images.model.Images;
import com.sogokids.utils.util.JumpPage;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 商户地址信息
 * Created by hoze on 15/6/15.
 */
@Controller
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private UserService adminUserService;

    @Autowired
    private PlaceImgService placeImgService;

    @Autowired
    private ImagesService imagesService;

    @Autowired
    private CityService cityService;

    @Autowired
    private RegionService regionService;

    /**
     *商家地址－列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, placeService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.PLACE),context);
    }

    /**
     *商家地址－操作跳转
     * @param uid
     * @param id
     * @param mark
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, @RequestParam("mark") int mark,HttpServletRequest req){
        String reStr = JumpPage.PLACE_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == 0){
            reStr = JumpPage.PLACE_ADD;
        }else if (mark == 1){
            reStr = JumpPage.PLACE_IMG;
            context.put(Quantity.RETURN_ENTITY,placeService.get(id));
            context.put(Quantity.RETURN_ENTITY_LIST,placeImgService.getEntitysByKey(id));
        }
        else{
            context.put(Quantity.RETURN_ENTITY,placeService.get(id));
        }
        context.put(Quantity.RETURN_CITY_S,cityService.getEntitys());
        context.put(Quantity.RETURN_REGION_S,regionService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    /**
     *商家地址－创建
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = placeService.insert(placeService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加地址信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加地址信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, placeService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.PLACE,context);
    }

    /**
     *商家地址－编辑
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = placeService.update(placeService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改地址信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改地址信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, placeService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.PLACE,context);
    }

    /**
     *商家地址－删除
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = placeService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除地址信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除地址信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, placeService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.PLACE,context);
    }

    /**
     *商家地址－创建图片
     * @param uid
     * @param pid
     * @param req
     * @return
     */
    @RequestMapping("/addimg")
    public ModelAndView addimg(@RequestParam("uid") int uid,@RequestParam("pid") int pid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();

        Images image = imagesService.uploadImg(req,0,"","true");
        int reDate = placeImgService.insert(placeImgService.formEntity(image,pid));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加地址图片数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加地址图片数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, placeImgService.getEntitysByKey(pid));
        context.put(Quantity.RETURN_ENTITY,placeService.get(pid));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.PLACE_IMG,context);
    }

    /**
     *商家地址－删除图片
     * @param uid
     * @param id
     * @param pid
     * @param req
     * @return
     */
    @RequestMapping("/delimg")
    public ModelAndView delimg(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("pid") int pid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = placeImgService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除地址图片数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除地址图片数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, placeImgService.getEntitysByKey(pid));
        context.put(Quantity.RETURN_ENTITY,placeService.get(pid));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.PLACE_IMG,context);
    }

    /**
     * 商家地址－获取联动Json数据
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/datajson")
    public String getJson(HttpServletRequest req,HttpServletResponse rsp){
        int id = Integer.parseInt(req.getParameter("id"));
        rsp.setContentType("text/html; charset=UTF-8");
        String reStr = placeService.getDataJsonStr(id);
        try {
            rsp.getWriter().write(reStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
