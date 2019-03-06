package com.example.estest.EsService.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.estest.EsService.EsSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EsSqlServiceImpl implements EsSqlService {

    private final RestTemplate restTemplate;
    private static final String PREFIX="http://118.25.52.191:9200/_xpack/sql?format=json";

    @Autowired
    public EsSqlServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public JSONObject sqlRequest(String sql) {
        JSONObject body = new JSONObject();
        body.put("query", sql);
        return restTemplate.postForObject(PREFIX,body,JSONObject.class);
    }
}
