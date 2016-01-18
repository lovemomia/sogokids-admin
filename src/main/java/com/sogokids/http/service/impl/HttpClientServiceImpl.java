package com.sogokids.http.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sogokids.http.service.HttpClientService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by hoze on 16/1/4.
 */
@Service
public class HttpClientServiceImpl implements HttpClientService {

    private final Logger log = LoggerFactory.getLogger(HttpClientServiceImpl.class);

    @Override
    public JSONObject httpPost(String url, String param){
        JSONObject responseJson = new JSONObject();
        try{
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            StringEntity reqEntity = new StringEntity(param, "utf-8");
            reqEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(reqEntity);

            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                log.info("fail to execute request: " + httpPost);
            }else{
                HttpEntity resEntity = response.getEntity();
                String entityStr = EntityUtils.toString(resEntity);
                responseJson = JSON.parseObject(entityStr);
            }
        }catch (IOException _ex){
            log.error("error info:"+_ex.getMessage());
        }

        return responseJson;
    }

}
