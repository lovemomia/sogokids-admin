package com.sogokids.http.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by hoze on 16/1/4.
 */
public interface HttpClientService {
    public JSONObject httpPost(String url, String param);
}
