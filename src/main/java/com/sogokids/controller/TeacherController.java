package com.sogokids.controller;

import com.sogokids.system.service.TeacherService;
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
 * Created by hoze on 15/11/13.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService adminUserService;

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, teacherService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.TEACHER,context);
    }

    @RequestMapping("/query")
    public ModelAndView query_teacher(@RequestParam("uid") int uid, @RequestParam("name") String name, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        String where = " ";
        if (name != null && !name.equals("")){
            where = where + " and Name LIKE '%" + name +"%' ";
        }
        context.put(Quantity.RETURN_ENTITY_LIST, teacherService.getEntitysByWhere(where));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put("name",name);
        return new ModelAndView(JumpPage.TEACHER,context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.TEACHER_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.TEACHER_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, teacherService.get(id));
        }
        context.put("sexs", EnumUtil.getEnums(Quantity.STATUS_SIX));
        context.put("jobs", EnumUtil.getEnums(Quantity.STATUS_SEVEN));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = teacherService.insert(teacherService.formEntity(req,Quantity.STATUS_ONE));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"添加讲师数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"添加讲师数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, teacherService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.TEACHER,context);
    }

    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = teacherService.update(teacherService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"修改讲师数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"修改讲师数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, teacherService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.TEACHER,context);
    }

    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = teacherService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除讲师数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除讲师数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, teacherService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.TEACHER,context);
    }
}
