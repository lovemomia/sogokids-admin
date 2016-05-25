package com.sogokids.controller;

import com.sogokids.home.service.RecommendService;
import com.sogokids.system.service.AppVersionService;
import com.sogokids.system.service.CityService;
import com.sogokids.system.service.MenuService;
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
 * Created by hoze on 16/5/23.
 */
@Controller
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private UserService adminUserService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AppVersionService appVersionService;

    @Autowired
    private MenuService menuService;

    /**
     * 推荐列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, recommendService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_TWO));
        return new ModelAndView(adminUserService.isUserFunc(req, JumpPage.RECOMMEND),context);
    }

    /**
     * 推荐操作跳转
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.RECOMMEND_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.RECOMMEND_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, recommendService.get(id));
        }
        context.put("versions", appVersionService.getEntitys());
        context.put("citys", cityService.getEntitys());
        context.put("platforms", EnumUtil.getEnums(Quantity.STATUS_NINE));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_TWO));
        return new ModelAndView(reStr,context);
    }

    /**
     * 创建推荐信息
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = recommendService.insert(recommendService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加推荐数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加推荐数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, recommendService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_TWO));
        return new ModelAndView(JumpPage.RECOMMEND,context);
    }

    /**
     * 编辑推荐信息
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = recommendService.update(recommendService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改推荐数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改推荐数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, recommendService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_TWO));
        return new ModelAndView(JumpPage.RECOMMEND,context);
    }

    /**
     * 删除推荐信息
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = recommendService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除推荐数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除推荐数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, recommendService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_THIRTY_TWO));
        return new ModelAndView(JumpPage.RECOMMEND,context);
    }
}
