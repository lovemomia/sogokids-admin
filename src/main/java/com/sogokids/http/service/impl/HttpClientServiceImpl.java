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
import org.springframework.stereotype.Service;

/**
 * Created by hoze on 16/1/4.
 */
@Service
public class HttpClientServiceImpl implements HttpClientService {

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
                throw new RuntimeException("fail to execute request: " + httpPost);
            }else{
                HttpEntity resEntity = response.getEntity();
                String entityStr = EntityUtils.toString(resEntity);
                responseJson = JSON.parseObject(entityStr);
            }
        }catch (Exception _ioex){
            _ioex.printStackTrace();
        }

        return responseJson;
    }

}
