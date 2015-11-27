package com.sogokids.controller;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.images.model.Images;
import com.sogokids.images.service.ImagesService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoze on 15/6/15.
 */

@Controller
@RequestMapping("/upload")
public class ImgController {

    @Autowired
    private ImagesService imagesService;

    @RequestMapping("/img")
    public String uploadImg(HttpServletRequest req,HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        Images images = imagesService.uploadImg(req,0,"","true");
        context.put("msg","0");
        context.put("path",images.getUrl());
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

    @RequestMapping("/updateImg")
    public String uploadImg1(HttpServletRequest req,HttpServletResponse rsp){
        rsp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        Images images = imagesService.uploadImg(req,0,"","false");
        context.put("msg","0");
        context.put("path",images.getUrl());
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