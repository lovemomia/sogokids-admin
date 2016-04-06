package com.sogokids.controller;

import com.sogokids.cooperator.service.CooperatorActivityService;
import com.sogokids.cooperator.service.CooperatorService;
import com.sogokids.cooperator.service.CooperatorUserService;
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
 * Created by hoze on 16/4/1.
 */
@Controller
@RequestMapping("/bus_coop_activity")
public class BusCoopActivityController {

    @Autowired
    private CooperatorUserService busUserService;

    @Autowired
    private CooperatorActivityService cooperatorActivityService;

    /**
     * 合作单位-活动信息－列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, cooperatorActivityService.getEntitys());
        context.put(Quantity.RETURN_BUS_USER,busUserService.get(uid));
        return new ModelAndView(JumpPage.BUS_COOP_ACTIVITY,context);
    }

    /**
     * 合作单位-活动信息－报名详情
     * @param uid
     * @param id
     * @return
     */
    @RequestMapping("/sign")
    public ModelAndView signUp(@RequestParam("uid") int uid,@RequestParam("id") int id){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY, cooperatorActivityService.getActivityEntrys(id));
        context.put(Quantity.RETURN_BUS_USER,busUserService.get(uid));
        return new ModelAndView(JumpPage.BUS_COOP_ACTIVITY_ENTRY,context);
    }


}
