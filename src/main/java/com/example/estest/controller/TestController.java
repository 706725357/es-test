package com.example.estest.controller;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/test")
@RestController
public class TestController {

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public TestController(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }


    //查找索引
    @GetMapping(value = "searchIndex")
    public GetIndexResponse searchIndex() {
        GetIndexRequest request = new GetIndexRequest().indices("house_space");
        request.includeDefaults(true);
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        GetIndexResponse getIndexResponse = null;
        try {
            getIndexResponse = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getIndexResponse;
    }

    //创建索引
    @GetMapping(value = "createIndex")
    public CreateIndexResponse createIndex() {
        CreateIndexRequest request = new CreateIndexRequest("twitter");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        Map<String, Object> jsonMap = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        message.put("type", "text");
        Map<String, Object> properties = new HashMap<>();
        properties.put("message", message);
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        jsonMap.put("_doc", mapping);
        request.mapping("_doc", jsonMap);
        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");

        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createIndexResponse;
    }

    //删除之前必须做索引的存在性检测
    @GetMapping(value = "deleteIndex")
    public AcknowledgedResponse deleteIndex() {
        DeleteIndexRequest request = new DeleteIndexRequest("twitter");
        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");
        AcknowledgedResponse acknowledgedResponse = null;
        try {
            acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acknowledgedResponse;
    }

    //索引存在性检测
    @GetMapping(value = "existsIndex")
    public boolean existsIndex() {
        GetIndexRequest request = new GetIndexRequest();
        request.indices("house_space");
        boolean exists = false;
        try {
            exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }

    //新增文档
    @GetMapping(value = "addDocument")
    public IndexResponse addDocument() {
        //如果没有索引则创建
        IndexRequest request = new IndexRequest(
                "posts",
                "doc",
                "1");
        Map<String, String> map = new HashMap<>();
        map.put("user", "limbo");
        map.put("postDate", "2019-01-30");
        map.put("message", "Elasticsearch");
    /*    String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";*/
        request.source(map, XContentType.JSON);
        IndexResponse indexResponse = null;
        try {
            indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexResponse;
    }

    //查询文档
    @GetMapping(value = "searchDocument")
    public GetResponse searchDocument() {
        GetRequest getRequest = new GetRequest(
                "posts",
                "doc",
                "1");
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getResponse;

    }

    //删除文档
    @GetMapping(value = "deleteDocument")
    public DeleteResponse deleteDocument() {
        DeleteRequest deleteRequest = new DeleteRequest(
                "posts",
                "doc",
                "1");
        DeleteResponse  deleteResponse = null;
        try {
            deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleteResponse;

    }

    //更新文档
    @GetMapping(value = "updateDocument")
    public UpdateResponse  updateDocument() {
        //指定索引，更改类型，和ID
        UpdateRequest request = new UpdateRequest(
                "posts",
                "doc",
                "1");
        UpdateResponse updateResponse = null;
        try {
            updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updateResponse;

    }


}
