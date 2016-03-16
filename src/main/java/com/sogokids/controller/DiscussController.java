package com.sogokids.controller;

import com.sogokids.discuss.service.DiscussTopicService;
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
 *话题信息
 * Created by hoze on 16/2/23.
 */
@Controller
@RequestMapping("/discuss")
public class DiscussController {

    @Autowired
    private DiscussTopicService discussTopicService;

    @Autowired
    private UserService adminUserService;

    /**
     *话题列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, discussTopicService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req, JumpPage.DISCUSS),context);
    }

    /**
     *话题操作跳转
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.DISCUSS_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0){
            reStr = JumpPage.DISCUSS_ADD;
        }else{
            context.put(Quantity.RETURN_ENTITY, discussTopicService.get(id));
        }
//        context.put("citys", cityService.getEntitys());
//        context.put("subs",subjectService.getEntitys(Quantity.STATUS_ONE));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    /**
     *创建话题
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = discussTopicService.insert(discussTopicService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加话题信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加话题信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, discussTopicService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.DISCUSS,context);
    }

    /**
     *编辑话题
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = discussTopicService.update(discussTopicService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改话题信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改话题信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, discussTopicService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.DISCUSS,context);
    }

    /**
     * 删除话题
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = discussTopicService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除话题信息数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除话题信息数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, discussTopicService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.DISCUSS,context);
    }

    /**
     * 话题上下线
     * @param uid
     * @param id
     * @param mark
     * @param req
     * @return
     */
    @RequestMapping("/upOrDown")
    public ModelAndView updateStatus(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("mark") int mark, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = discussTopicService.updateStatus(id, mark);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"话题上下线成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"话题上下线失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, discussTopicService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.DISCUSS,context);
    }
}
