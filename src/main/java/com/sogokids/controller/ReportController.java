package com.sogokids.controller;

import com.sogokids.system.service.MenuService;
import com.sogokids.task.service.RegUserService;
import com.sogokids.task.service.ReportOrderService;
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
 * banner信息
 * Created by hoze on 16/04/11.
 */
@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private UserService adminUserService;

    @Autowired
    private RegUserService regUserService;

    @Autowired
    private ReportOrderService reportOrderService;

    @Autowired
    private MenuService menuService;

    /**
     * banner列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        Map map = regUserService.getregUsers();
        context.put("x_date",map.get("user_date"));
        context.put("y_number",map.get("user_number"));
        Map map_order = reportOrderService.getReportOrders();
        context.put("x_order_date",map_order.get("x_order_date"));
        context.put("y_st_number",map_order.get("y_st_number"));
        context.put("y_tj_number",map_order.get("y_tj_number"));
        context.put("y_kc_number",map_order.get("y_kc_number"));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put(Quantity.RETURN_MENUS, menuService.getMenuStrings(uid, Quantity.STATUS_SEVEN));
        return new ModelAndView(JumpPage.REPORT,context);
    }

//    /**
//     * banner操作跳转
//     * @param uid
//     * @param id
//     * @param req
//     * @return
//     */
//    @RequestMapping("/oper")
//    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
//        String reStr = JumpPage.BANNER_EDIT;
//        Map<String, Object> context = new HashMap<String, Object>();
//        if (id == 0) {
//            reStr = JumpPage.BANNER_ADD;
//        } else{
//            context.put(Quantity.RETURN_ENTITY, bannerService.get(id));
//        }
//        context.put("versions", appVersionService.getEntitys());
//        context.put("citys", cityService.getEntitys());
//        context.put("platforms", EnumUtil.getEnums(Quantity.STATUS_NINE));
//        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
//        return new ModelAndView(reStr,context);
//    }
//
//    /**
//     * 创建banner信息
//     * @param uid
//     * @param req
//     * @return
//     */
//    @RequestMapping("/add")
//    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){
//
//        Map<String, Object> context = new HashMap<String, Object>();
//        int reDate = bannerService.insert(bannerService.formEntity(req,Quantity.STATUS_ONE));
//        if (reDate > 0){
//            context.put(Quantity.RETURN_MSG,"添加BANNER数据成功!");
//        }else{
//            context.put(Quantity.RETURN_MSG,"添加BANNER数据失败!");
//        }
//        context.put(Quantity.RETURN_ENTITY_LIST, bannerService.getEntitys());
//        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
//        return new ModelAndView(JumpPage.BANNER,context);
//    }
//
//    /**
//     * 编辑banner信息
//     * @param uid
//     * @param id
//     * @param req
//     * @return
//     */
//    @RequestMapping("/edit")
//    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){
//
//        Map<String, Object> context = new HashMap<String, Object>();
//        int reDate = bannerService.update(bannerService.formEntity(req, id));
//        if (reDate > 0){
//            context.put(Quantity.RETURN_MSG,"修改BANNER数据成功!");
//        }else{
//            context.put(Quantity.RETURN_MSG,"修改BANNER数据失败!");
//        }
//        context.put(Quantity.RETURN_ENTITY_LIST, bannerService.getEntitys());
//        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
//        return new ModelAndView(JumpPage.BANNER,context);
//    }
//
//    /**
//     * 删除banner信息
//     * @param uid
//     * @param id
//     * @param req
//     * @return
//     */
//    @RequestMapping("/del")
//    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){
//
//        Map<String, Object> context = new HashMap<String, Object>();
//        int reDate = bannerService.delete(id);
//        if (reDate > 0){
//            context.put(Quantity.RETURN_MSG,"删除BANNER数据成功!");
//        }else{
//            context.put(Quantity.RETURN_MSG,"删除BANNER数据失败!");
//        }
//        context.put(Quantity.RETURN_ENTITY_LIST, bannerService.getEntitys());
//        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
//        return new ModelAndView(JumpPage.BANNER,context);
//    }
}
