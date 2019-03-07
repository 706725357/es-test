package com.example.estest.EsService;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface DocumentService {

    //查询文档
    GetResponse searchDocument(String indexName,String type,String id);

    //新增文档
    IndexResponse addDocument(String indexName, String type, String id, Map map);

    //索引存在性检测
    boolean existsIndex(String indexName);

    //更新文档
    UpdateResponse updateDocument(String indexName,String type,String id,Map map);

}
