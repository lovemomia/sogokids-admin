package com.sogokids.controller;


import com.sogokids.cooperator.model.CooperatorUser;
import com.sogokids.cooperator.service.CooperatorUserService;
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
 * 商家－后台用户
 * Created by hoze on 15/8/25.
 */
@Controller
@RequestMapping("/bus")
public class BusUserController {

    @Autowired
    private CooperatorUserService busUserService;


    /**
     *跳转登录页面
     * @param req
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_MSG, "");
        return new ModelAndView(JumpPage.BUS_LOGIN,context);
    }

    /**
     *验证登录信息
     * @param req
     * @return
     */
    @RequestMapping("/loginpage")
    public ModelAndView loginpage(HttpServletRequest req) {
        String reStr = JumpPage.BUS_LOGIN;
        Map<String, Object> context = new HashMap<String, Object>();
        String username = req.getParameter("name");
        String password = req.getParameter("password");
        CooperatorUser entity = busUserService.login(username, password);
        if(null != entity.getName() && !entity.getName().equals("")){
            context.put(Quantity.RETURN_BUS_USER, entity);
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "登录成功");
            reStr = JumpPage.BUS_INDEX;
        }else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "登录失败");
        }

        return new ModelAndView(reStr,context);
    }

    /**
     *登录跳转首页
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam("uid") int uid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        CooperatorUser entity = busUserService.get(uid);
        context.put(Quantity.RETURN_BUS_USER, entity);
        return new ModelAndView(JumpPage.BUS_INDEX,context);
    }

}
