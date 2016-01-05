package com.sogokids.controller;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.images.model.Images;
import com.sogokids.images.service.ImagesService;
import com.sogokids.subject.model.SubjectNotice;
import com.sogokids.subject.model.SubjectSku;
import com.sogokids.subject.service.SubjectImgService;
import com.sogokids.subject.service.SubjectNoticeService;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.system.service.CityService;
import com.sogokids.user.service.UserService;
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
 * Created by hoze on 15/10/13.
 */
@Controller
@RequestMapping("/sub")
public class SubjectController {
    @Autowired
    private UserService adminUserService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectImgService subjectImgService;

    @Autowired
    private SubjectNoticeService subjectNoticeService;

    @Autowired
    private SubjectSkuService subjectSkuService;

    @Autowired
    private ImagesService imagesService;

    @Autowired
    private CityService cityService;

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(Quantity.RETURN_ENTITY_LIST, subjectService.getEntitys(Quantity.STATUS_ONE));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(adminUserService.isUserFunc(req,JumpPage.SUB),context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id,HttpServletRequest req){
        String reStr = JumpPage.SUB_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (id == 0) {
            reStr = JumpPage.SUB_ADD;
        } else{
            context.put(Quantity.RETURN_ENTITY, subjectService.get(id));
            context.put("imageJson",subjectImgService.getImgHtml(id));
            context.put("notices",subjectNoticeService.getContentsHtml(id));
            context.put("skuHtml",subjectSkuService.getSkuHtml(id));
        }

        context.put("sub_type", EnumUtil.getEnums(Quantity.STATUS_THREE));
        context.put("time_unit", EnumUtil.getEnums(Quantity.STATUS_FOUR));
//        context.put("tags", subjectService.getModelsByType());
        context.put("citys", cityService.getEntitys());
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/add")
    public String addEntity(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
//        int subId = Integer.parseInt(req.getParameter("sub_id"));
        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = subjectService.insertKey(subjectService.formEntity(req,Quantity.STATUS_ZERO));
        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程包基本信息添加成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程包基本信息添加失败!");
        }
        context.put("subId",reDate);
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

    @RequestMapping("/edit")
    public String editEntity(HttpServletRequest req, HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        int sub_id = Integer.parseInt(req.getParameter("sub_id"));
        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = subjectService.update(subjectService.formEntity(req, sub_id));
        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程包基本信息修改成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程包基本信息修改失败!");
        }
        context.put("subId",sub_id);
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

    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = subjectService.delete(id);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"删除Subject数据成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"删除Subject数据失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, subjectService.getEntitys(Quantity.STATUS_ONE));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.SUB,context);
    }

    @RequestMapping("/addImg")
    public String addImg(@RequestParam("subId") int subId,HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Images image = imagesService.uploadImg(req, 0, "","true");
        int reDate = subjectImgService.insert(subjectImgService.formEntity(image, subId));
        Writer writer = null;
        try {
            writer = rsp.getWriter();
            writer.write(JSONObject.toJSONString(subjectImgService.getImgHtml(subId)).replace("\"", ""));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(writer);
        }

        return null;
    }

    @RequestMapping("/delImg")
    public String delImg(@RequestParam("subId") int subId,HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        String imgstr = req.getParameter("imgs");
        //String [] imgs = imgstr.split(",");
        int reDate = 0;
        //for (int i = 0; i < imgs.length; i++) {
        reDate = subjectImgService.delete(Integer.parseInt(imgstr));
        //}

        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "图片删除成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "图片删除失败!");
        }

        context.put("imageJson",subjectImgService.getImgHtml(subId));
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

    @RequestMapping("/addNotice")
    public String addNotice(@RequestParam("subId") int subId,HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        int noticeId = Integer.parseInt(req.getParameter("notice_id"));
        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = 0;
        if (noticeId == 0) {
            reDate = subjectNoticeService.insert(subjectNoticeService.formEntity(req, subId, noticeId));
        }else{
            reDate = subjectNoticeService.update(subjectNoticeService.formEntity(req, subId, noticeId));
        }

        subjectService.updateNotice(subId,subjectNoticeService.getContents(subId));

        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程包客户须知数据成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程包客户须知数据失败!");
        }
        context.put("notices",subjectNoticeService.getContentsHtml(subId));
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

    @RequestMapping("/editNotice")
    public String editNotice(HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        int noticeId = Integer.parseInt(req.getParameter("noticeId"));
        Map<String, Object> context = new HashMap<String, Object>();
        SubjectNotice entity = subjectNoticeService.get(noticeId);

        context.put("notice",entity);
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

    @RequestMapping("/delNotice")
    public String delNotice(@RequestParam("subId") int subId, HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();

        int noticeId = Integer.parseInt(req.getParameter("noticeId"));
        int reDate = subjectNoticeService.delete(noticeId);
        subjectService.updateNotice(subId, subjectNoticeService.getContents(subId));
        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程包客户须知数据删除成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程包客户须知数据删除失败!");
        }
        context.put("notices",subjectNoticeService.getContentsHtml(subId));
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

    @RequestMapping("/addSku")
    public String addSku(@RequestParam("subId") int subId, HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int skuId = Integer.parseInt(req.getParameter("sku_id"));
        int reDate = 0;
        if (skuId == 0) {
            reDate = subjectSkuService.insert(subjectSkuService.formEntity(req, subId, Quantity.STATUS_ZERO));
        }else{
            reDate = subjectSkuService.update(subjectSkuService.formEntity(req, subId, skuId));
        }

        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程包sku信息保存成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程包sku信息保存失败!");
        }
        context.put("skuHtml",subjectSkuService.getSkuHtml(subId));
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

    @RequestMapping("/editSku")
    public String editSku(HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        int skuId = Integer.parseInt(req.getParameter("skuId"));
        SubjectSku entity = subjectSkuService.get(skuId);
        context.put("sku",entity);
//        context.put("placeHtml", activityService.getSkuPlaceHtml(sid));
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

    @RequestMapping("/delSku")
    public String delSku(@RequestParam("subId") int subId, HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();

        int skuId = Integer.parseInt(req.getParameter("skuId"));
        int reDate = subjectSkuService.delete(skuId);
        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程包sku信息删除成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程包sku信息删除失败!");
        }
        context.put("skuHtml", subjectSkuService.getSkuHtml(subId));
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

    @RequestMapping("/cancelSku")
    public String cancelSku(@RequestParam("subId") int subId, HttpServletRequest req, HttpServletResponse rsp) {
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();

        int skuId = Integer.parseInt(req.getParameter("skuId"));
        int reDate = subjectSkuService.updateStatus(skuId, Quantity.STATUS_THREE);
        if (reDate > 0) {
            context.put(Quantity.RETURN_SUCCESS, 0);
            context.put(Quantity.RETURN_MSG, "课程体系sku信息取消成功!");
        } else {
            context.put(Quantity.RETURN_SUCCESS, 1);
            context.put(Quantity.RETURN_MSG, "课程体系sku信息取消失败!");
        }
        context.put("skuHtml", subjectSkuService.getSkuHtml(subId));
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

    @RequestMapping("/upOrDown")
    public ModelAndView updateStatus(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("mark") int mark, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = subjectService.updateStatus(id, mark);
        if (reDate > 0){
            context.put(Quantity.RETURN_MSG,"Subject数据上下线成功!");
        }else{
            context.put(Quantity.RETURN_MSG,"Subject数据上下线失败!");
        }
        context.put(Quantity.RETURN_ENTITY_LIST, subjectService.getEntitys(Quantity.STATUS_ONE));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.SUB,context);
    }

    @RequestMapping("/preview")
    public ModelAndView preview(@RequestParam("uid") int uid,@RequestParam("id") int id, HttpServletRequest req) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("previewHtml", subjectService.getPreview(id));
        context.put(Quantity.RETURN_USER,adminUserService.get(uid));
        return new ModelAndView(JumpPage.SUB_PREVIEW,context);
    }


}
