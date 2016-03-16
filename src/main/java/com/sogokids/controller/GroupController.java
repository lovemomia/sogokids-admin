package com.sogokids.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sogokids.group.service.GroupCourseService;
import com.sogokids.group.service.GroupUserService;
import com.sogokids.group.service.SelectionGroupService;
import com.sogokids.http.model.HttpResult;
import com.sogokids.query.service.CustomerService;
import com.sogokids.user.service.UserService;
import com.sogokids.utils.util.CastUtil;
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
import java.util.List;
import java.util.Map;

/**
 * 批量选课
 * Created by hoze on 15/12/28.
 */
@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private SelectionGroupService selectionGroupService;

    @Autowired
    private UserService adminUserService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private GroupCourseService groupCourseService;

    /**
     * 批量选课－列表
     * @param uid
     * @param req
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, selectionGroupService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req, JumpPage.GROUP),context);
    }

    /**
     * 批量选课－操作控制
     * @param uid
     * @param id
     * @param mark
     * @param req
     * @return
     */
    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("mark") int mark,HttpServletRequest req){
        String reStr = JumpPage.GROUP_USER;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == 1) {
            reStr = JumpPage.GROUP_COURSE;
        } else{
            context.put(Quantity.RETURN_ENTITY_LIST, selectionGroupService.getGroupCustomers(id,""));
        }
        context.put(Quantity.RETURN_ENTITY, selectionGroupService.get(id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    /**
     * 批量选课－创建
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/add")
    public String addEntity(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        String reDate = selectionGroupService.addGroup(req);
        if (reDate.equals("")) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "分组信息添加成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "["+reDate+"],无用户信息!");
        }
        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(writer);
        }
        return null;
    }

    /**
     * 批量选课－添加用户
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/add_users")
    public String addGroupUser(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int gid = Integer.parseInt(req.getParameter("g_id"));
        String mobiles = req.getParameter("mobiles");
        String reDate = selectionGroupService.addGroupUser(gid, mobiles);
        if (reDate.equals("")) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "分组用户信息添加成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "["+reDate+"],无用户信息!");
        }
        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(writer);
        }
        return null;
    }

    /**
     * 批量选课－编辑
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editEntity(HttpServletRequest req, HttpServletResponse rsp){

        Map<String, Object> context = new HashMap<String, Object>();
        int id = Integer.parseInt(req.getParameter("g_id"));
        int reDate = selectionGroupService.update(selectionGroupService.formEntity(req, id));
        if (reDate > 0){
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG,"修改分组数据成功!");
        }else{
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG,"修改分组数据失败!");
        }
        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(writer);
        }
        return null;
    }

    /**
     * 批量选课－删除
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = selectionGroupService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除分组数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除分组数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, selectionGroupService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.GROUP,context);
    }

    /**
     * 批量选课－删除用户列表
     * @param uid
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("/del_users")
    public ModelAndView delGroupUser(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = groupUserService.delete(id);
        int gid = Integer.parseInt(req.getParameter("gid"));
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除分组用户数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除分组用户数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, selectionGroupService.getGroupCustomers(gid,""));
        context.put(Quantity.RETURN_ENTITY, selectionGroupService.get(gid));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.GROUP_USER,context);
    }

    /**
     * 批量选课－查询用户列表
     * @param uid
     * @param gid
     * @param req
     * @return
     */
    @RequestMapping("/query_users")
    public ModelAndView queryGroupUser(@RequestParam("uid") int uid,@RequestParam("gid") int gid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        String mobile = req.getParameter("mobile");
        String where = "";
        if (mobile != null && !mobile.equals("")){
            where = where + " and c.Mobile LIKE '%" + mobile +"%'";
        }
        context.put(Quantity.RETURN_ENTITY_LIST, selectionGroupService.getGroupCustomers(gid,where));
        context.put(Quantity.RETURN_ENTITY, selectionGroupService.get(gid));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));

        return new ModelAndView(JumpPage.GROUP_USER,context);
    }

    /**
     * 批量选课－查询课程
     * @param uid
     * @param gid
     * @param req
     * @return
     */
    @RequestMapping("/query_courses")
    public ModelAndView queryCourse(@RequestParam("uid") int uid,@RequestParam("gid") int gid,HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        String title = req.getParameter("title");
        String dateTime = req.getParameter("dateTime");

        context.put(Quantity.RETURN_ENTITY_LIST, selectionGroupService.getGroupCourses(title, dateTime));
        context.put(Quantity.RETURN_ENTITY, selectionGroupService.get(gid));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        context.put("title",title);
        context.put("dateTime",dateTime);

        return new ModelAndView(JumpPage.GROUP_COURSE,context);
    }

    /**
     * 批量选课－选择课程
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping("/add_courses")
    public ModelAndView addCourse(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int gid = Integer.parseInt(req.getParameter("gid"));
        if (req.getParameter("course_sku_id") == null){
            context.put(Quantity.RETURN_SUCCESS,1);
            context.put(Quantity.RETURN_MSG,"对不起,没有选择任何课程!");
        }else{
            int skuId = Integer.parseInt(req.getParameter("course_sku_id"));
            HttpResult result = groupCourseService.insertGroupCourse(gid,skuId);
            List<Long> failedUserIds = CastUtil.toList((JSON) result.getData(), Long.class);
            if (result.getErrno() == 1001){
                context.put(Quantity.RETURN_SUCCESS,1);
                context.put(Quantity.RETURN_MSG,"对不起,没有分组成员信息!");
            }else if (result.getErrno() == 1002){
                context.put(Quantity.RETURN_SUCCESS,0);
                context.put(Quantity.RETURN_MSG,"对不起,所有分组成员信息选课失败,用户信息:"+customerService.getUserInfo(failedUserIds));
            }else if (result.getErrno() == 0){
                context.put(Quantity.RETURN_SUCCESS,0);
                if (failedUserIds.size() > 0){
                    context.put(Quantity.RETURN_MSG,"选课成功,选课失败的用户信息:"+customerService.getUserInfo(failedUserIds));
                }else{
                    context.put(Quantity.RETURN_MSG,"选择课程成功!");
                }
            }else{
                context.put(Quantity.RETURN_SUCCESS,1);
                context.put(Quantity.RETURN_MSG,"软件出现异常,请与开发人员联系!");
            }
        }
        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(context));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(writer);
        }
        return null;
    }


}
