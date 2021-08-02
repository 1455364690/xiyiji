package com.sunjh.xiyiji.service.impl;

import com.sunjh.xiyiji.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/8/3 12:39 上午
 */

@Service
public class HttpServiceImpl implements HttpService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String post(String url, Map<String, String> parms) {
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(url, generatePostJson(parms), String.class);
        return apiResponse.getBody();
    }

    @Override
    public String get(String url) {
        ResponseEntity responseEntity = restTemplate.getForEntity(generateRequestParameters(url, null), String.class);
        return (String) responseEntity.getBody();
    }

    @Override
    public String get(String url, Map<String, String> params) {
        ResponseEntity responseEntity = restTemplate.getForEntity(generateRequestParameters(url, params), String.class);
        return (String) responseEntity.getBody();
    }

    public HttpEntity<Map<String, String>> generatePostJson(Map<String, String> jsonMap) {

        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        httpHeaders.setContentType(type);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(jsonMap, httpHeaders);
        return httpEntity;
    }

    public String generateRequestParameters(String uri, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(uri);
        if (null != params && !params.isEmpty()) {
            sb.append("?");
            for (Map.Entry map : params.entrySet()) {
                sb.append(map.getKey())
                        .append("=")
                        .append(map.getValue())
                        .append("&");
            }
            uri = sb.substring(0, sb.length() - 1);
            return uri;
        }
        return sb.toString();
    }
}
