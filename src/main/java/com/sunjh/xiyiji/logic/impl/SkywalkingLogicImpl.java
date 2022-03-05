package com.sunjh.xiyiji.logic.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunjh.xiyiji.logic.SkywalkingLogic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/12/15 5:28 下午
 */
@Service
public class SkywalkingLogicImpl implements SkywalkingLogic {

    @Value("${skywalking.datasource}")
    private String skywalkingUrl;

//    @Scheduled(cron = "*/5 * * * * ?")
//    public void test.txt() {
//        System.out.println("ttttttt");
//    }

    public JSONObject doPostForObject(String requestUrl, JSONObject requestBody) {
        RestTemplate rt = new RestTemplate();
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(requestBody, getHeader());
        JSONObject response = rt.postForObject(requestUrl, httpEntity, JSONObject.class);
        return null;
    }

    public JSONArray doPostForArray(String requestUrl, JSONObject requestBody) {
        RestTemplate rt = new RestTemplate();
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(requestBody, getHeader());
        JSONArray response = rt.postForObject(requestUrl, httpEntity, JSONArray.class);
        return null;
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
