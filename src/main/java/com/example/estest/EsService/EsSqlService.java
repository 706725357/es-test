package com.example.estest.EsService;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface EsSqlService {

    //发起请求
    JSONObject sqlRequest(String sql);
}
