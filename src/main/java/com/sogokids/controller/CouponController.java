package com.sogokids.controller;

import com.sogokids.coupon.service.CouponService;
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
 * 优惠设置
 * Created by hoze on 15/7/14.
 */
@Controller
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private UserService adminUserService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MenuService menuService;

    /**
     * 优惠设置－列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, couponService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_FIVE));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.COUPON),context);
    }

    /**
     * 优惠设置－操作控制
     * @param uid
     * @param id
     * @param mark
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, @RequestParam("mark") int mark,HttpServletRequest req){
        String reStr = JumpPage.COUPON_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == Quantity.STATUS_ZERO){
            reStr = JumpPage.COUPON_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, couponService.get(id));
        }
        context.put("timeUnits", EnumUtil.getEnums(Quantity.STATUS_FOUR));
        context.put("timeTypes", EnumUtil.getEnums(Quantity.STATUS_EIGHT));
        context.put("couponSrc", EnumUtil.getEnums(Quantity.STATUS_TWELVE));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_FIVE));
        return new ModelAndView(reStr,context);
    }

    /**
     * 优惠设置－创建
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = couponService.insert(couponService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加红包信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加红包信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, couponService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_FIVE));
        return new ModelAndView(JumpPage.COUPON,context);
    }

    /**
     * 优惠设置－编辑
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = couponService.update(couponService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改红包信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改红包信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, couponService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_FIVE));
        return new ModelAndView(JumpPage.COUPON,context);
    }

    /**
     * 优惠设置－删除
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = couponService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除红包信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除红包信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, couponService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_TWENTY_FIVE));
        return new ModelAndView(JumpPage.COUPON,context);
    }

}
