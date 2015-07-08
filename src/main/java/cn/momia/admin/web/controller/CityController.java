package cn.momia.admin.web.controller;

import cn.momia.admin.web.common.FileUtil;
import cn.momia.admin.web.common.FinalUtil;
import cn.momia.admin.web.service.AdminUserService;
import cn.momia.admin.web.service.CityService;
import cn.momia.admin.web.service.QueryPageService;
import cn.momia.admin.web.service.RegionService;
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
    private AdminUserService adminUserService;

    @Autowired
    private QueryPageService queryPageService;

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid,@RequestParam("pageNo") int pageNo, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(6, pageNo)));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.CITY,context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, @RequestParam("mark") int mark,@RequestParam("pageNo") int pageNo,HttpServletRequest req){
        String reStr = FileUtil.CITY_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == 0){
            reStr = FileUtil.CITY_ADD;
        }else if (mark == 1){
//            reStr = FileUtil.PLACE_IMG;
//            context.put(FinalUtil.ENTITY,placeService.get(id));
//            context.put(FinalUtil.ENTITYS,placeImgService.getEntitysByKey(id));
        }
        else{
            context.put(FinalUtil.ENTITY, cityService.get(id));
        }
        context.put("pageNo",pageNo);
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid,@RequestParam("pageNo") int pageNo, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cityService.insert(cityService.formEntity(req,FinalUtil.ADD_INFO));
        if (reDate > 0){
            context.put(FinalUtil.RETURN_MSG,"添加city信息数据成功!");
        }else{
            context.put(FinalUtil.RETURN_MSG,"添加city信息数据失败!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(6, pageNo)));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.CITY,context);
    }

    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("pageNo") int pageNo,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cityService.update(cityService.formEntity(req, id));
        if (reDate > 0){
            context.put(FinalUtil.RETURN_MSG,"修改city信息数据成功!");
        }else{
            context.put(FinalUtil.RETURN_MSG,"修改city信息数据失败!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(6, pageNo)));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.CITY,context);
    }

    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("pageNo") int pageNo, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = cityService.delete(id);
        if (reDate > 0){
            context.put(FinalUtil.RETURN_MSG,"删除city信息数据成功!");
        }else{
            context.put(FinalUtil.RETURN_MSG,"删除city信息数据失败!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(6, pageNo)));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.CITY,context);
    }
}
