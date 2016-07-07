package com.sogokids.controller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by hoze on 16/7/5.
 */
@Controller
@RequestMapping("/sg_weixin")
public class WeixinController {

    private final Logger log = LoggerFactory.getLogger(WeixinController.class);

    /**
     * 微信验证回调
     * @param request
     * @param response
     *
     *
     * <xml><ToUserName><![CDATA[gh_2a5540516edb]]></ToUserName>
    <FromUserName><![CDATA[odwP2jt6aTBu_Dl1ypoUJ8pN9UOs]]></FromUserName>
    <CreateTime>1425633941</CreateTime>
    <MsgType><![CDATA[event]]></MsgType>
    <Event><![CDATA[SCAN]]></Event>
    <EventKey><![CDATA[0]]></EventKey>
    <Ticket><![CDATA[gQGK8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2RIVlBsRVBsdEhITk9jQzRaMXV6AAIEgXL5VAMECAcAAA==]]></Ticket>
    </xml>
     */
    @RequestMapping(value="/init",method={RequestMethod.GET,RequestMethod.POST} )
    public void init(@RequestBody(required=false) String body,HttpServletRequest request,HttpServletResponse response){

        log.info("weixin>>>>>>>"+body.toString());
        log.info("================================微信URL测试=========================");
        SAXReader saxReader = new SAXReader();
        Document document;
        try {
            try {
                document = saxReader.read(new ByteArrayInputStream(body.toString().getBytes("UTF-8")));
                Element rootElt = document.getRootElement();
                log.info("FromUserName==="+rootElt.elementText("FromUserName"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
/*
     * 微信回调验证
     *
     * String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String token = "dmx";

        PrintWriter out;
        try {
            out = response.getWriter();
            out.println(echostr);
            out.close();
            response.flushBuffer();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/


    }

}
