package com.sogokids.controller;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.order.service.OrderService;
import com.sogokids.query.service.CourseTimeQueryService;
import com.sogokids.query.service.QueryService;
import com.sogokids.user.service.UserService;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.EnumUtil;
import com.sogokids.utils.util.JumpPage;
import com.sogokids.utils.util.Quantity;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询统计
 * Created by hoze on 15/10/13.
 */
@Controller
@RequestMapping("/query")
public class QueryController {
    @Autowired
    private UserService adminUserService;

    @Autowired
    private QueryService queryService;

    @Autowired
    private CourseTimeQueryService courseTimeQueryService;

    @Autowired
    private OrderService orderService;


    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("startTime", DateUtil.getDateSYM());
        context.put("endTime", DateUtil.NowDateW());
        context.put("title", "");
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.QUERY_COURSE),context);
    }

    @RequestMapping("/query")
    public ModelAndView query(@RequestParam("uid") int uid,HttpServletRequest req){
        String reStr = JumpPage.QUERY_COURSE;
        Map<String, Object> context = new HashMap<String, Object>();
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String title = req.getParameter("title");
        context.put("courseTimeQuery", courseTimeQueryService.getCourseTimeQuerys(title, startTime, endTime));

        context.put("startTime", startTime);
        context.put("endTime", endTime);
        context.put("title", title);
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/queryDetail")
    public ModelAndView query_detail(@RequestParam("uid") int uid, @RequestParam("id") int id,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();

        int sku_id = Integer.parseInt(req.getParameter("skuId"));
        context.put("startTime", req.getParameter("startTime"));
        context.put("endTime", req.getParameter("endTime"));
        context.put("title", req.getParameter("title"));

        context.put("courseName",req.getParameter("name"));
        context.put("courseUser",courseTimeQueryService.getCourseUsers(id,sku_id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.QUERY_DETAIL,context);
    }

    @RequestMapping("/order")
    public ModelAndView order(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("startTime", DateUtil.getDateSYM());
        context.put("endTime", DateUtil.NowDateW());
        context.put("status", EnumUtil.getEnums(Quantity.STATUS_FIVE));
        context.put("mobile", "");
        context.put("new_status", 0);
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.QUERY_ORDER),context);
    }

    @RequestMapping("/queryOrder")
    public ModelAndView queryOrder(@RequestParam("uid") int uid,HttpServletRequest req){
        String reStr = JumpPage.QUERY_ORDER;
        Map<String, Object> context = new HashMap<String, Object>();
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String mobile = req.getParameter("mobile");
        int new_status = Integer.parseInt(req.getParameter("status"));
        context.put("orders", queryService.getOrders(startTime, endTime, mobile, new_status));

        context.put("startTime", startTime);
        context.put("endTime", endTime);
        context.put("mobile", mobile);
        context.put("new_status", new_status);
        context.put("status", EnumUtil.getEnums(Quantity.STATUS_FIVE));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/orderDetail")
    public ModelAndView orderDetail(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String mobile = req.getParameter("mobile");
        int new_status = Integer.parseInt(req.getParameter("new_status"));
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("order", orderService.getOrder(id));
        context.put("startTime", startTime);
        context.put("endTime", endTime);
        context.put("mobile", mobile);
        context.put("new_status", new_status);
        context.put("status", EnumUtil.getEnums(Quantity.STATUS_FIVE));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.ORDER_DETAIL,context);
    }

    @RequestMapping("/refund")
    public ModelAndView refund(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String mobile = req.getParameter("mobile");
        int new_status = Integer.parseInt(req.getParameter("new_status"));
        int update_int = orderService.updateOrder(id);
        if(update_int > 0){
            context.put("msg","退款申请已提交,等待退款...");
        }
        context.put("orders", queryService.getOrders(startTime, endTime, mobile, new_status));

        context.put("startTime", startTime);
        context.put("endTime", endTime);
        context.put("mobile", mobile);
        context.put("new_status", new_status);
        context.put("status", EnumUtil.getEnums(Quantity.STATUS_FIVE));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.QUERY_ORDER,context);
    }

    @RequestMapping("/queryMobile")
    public ModelAndView queryMobile(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        //String reStr = JumpPage.QUERY_COURSE;
        Map<String, Object> context = new HashMap<String, Object>();
        String mobile = req.getParameter("mobile");
        context.put("success", 0);
        context.put("queryMobiles", courseTimeQueryService.getMobileUsers(mobile).get("mobileHtml"));
        context.put("mobile", mobile);

        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
        return null;
    }
}
