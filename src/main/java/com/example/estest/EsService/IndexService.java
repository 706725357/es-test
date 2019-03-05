package com.example.estest.EsService;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IndexService {

    //查找索引
    GetIndexResponse searchIndex(String indexName);

    //创建索引
    CreateIndexResponse createIndex(String indexName, int shards, int replicas, Map map);

    //索引存在性检测
    boolean existsIndex(String indexName);

    //删除索引
    AcknowledgedResponse deleteIndex(String indexName);
    
}
