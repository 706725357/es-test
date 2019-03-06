package com.example.estest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.estest.EsService.SumAggregationService;
import com.example.estest.entity.request.SumAggregationByDate;
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
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/test")
@RestController
public class TestController {

    private final RestHighLevelClient restHighLevelClient;
    private final SumAggregationService sumAggregationService;
    private final RestTemplate restTemplate;

    @Autowired
    public TestController(RestHighLevelClient restHighLevelClient, SumAggregationService sumAggregationService, RestTemplate restTemplate) {
        this.restHighLevelClient = restHighLevelClient;
        this.sumAggregationService = sumAggregationService;
        this.restTemplate = restTemplate;
    }

    //sql方式
    @GetMapping(value = "sqlSearch")
    public JSONObject sql() {
        JSONObject sql = new JSONObject();
        sql.put("query", "select sum(rental_price) from house_space");
        System.out.println(sql.toString());
        return restTemplate.postForObject("http://118.25.52.191:9200/_xpack/sql?format=json",sql,JSONObject.class);
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
        CreateIndexRequest request = new CreateIndexRequest("es_house_space");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        CreateIndexResponse createIndexResponse = null;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("_doc");
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("message");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            request.mapping("_doc", builder);
            request.timeout(TimeValue.timeValueMinutes(2));
            request.timeout("2m");
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
        request.doc(new HashMap());
        UpdateResponse updateResponse = null;
        try {
            updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updateResponse;

    }

    //求和(度量聚合)
    @GetMapping(value = "sumAggregation")
    public SearchResponse sumAggregation() throws IOException {
        SearchRequest request = new SearchRequest("house_space").types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().size(0);
        SumAggregationBuilder sumAggregationBuilder=AggregationBuilders.sum("sumPrice").field("rental_price");
        searchSourceBuilder.aggregation(sumAggregationBuilder);
        request.source(searchSourceBuilder);
        return restHighLevelClient.search(request,RequestOptions.DEFAULT);
    }

    //按时间段求和(度量聚合)
    @GetMapping(value = "sumAggregationByDate")
    public SearchResponse sumAggregationByDate() throws IOException {
        //指定文档
        SearchRequest request = new SearchRequest("house_space").types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //条件池
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //如果还需要指定某个状态
        MatchQueryBuilder matchQueryBuilder=QueryBuilders.matchQuery("house_space_status","RENTABLE");
        boolQueryBuilder.must(matchQueryBuilder);
        //指定范围(注意时间格式)
        RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("create_time")
                .from("2017-05-16T21:57:09")
                .to("2019-05-16T21:57:09");
        //加入搜索条件池
        boolQueryBuilder.must(rangeQueryBuilder);
        //指定聚合
        SumAggregationBuilder sumAggregationBuilder=AggregationBuilders.sum("sumPrice").field("rental_price");
        //搜索
        searchSourceBuilder.query(boolQueryBuilder).aggregation(sumAggregationBuilder);
        request.source(searchSourceBuilder);
        return restHighLevelClient.search(request,RequestOptions.DEFAULT);
    }

    //按时间段求和(度量聚合)
    @PostMapping(value = "sumAggregationByDateI")
    public SearchResponse sumAggregationByDateI(@RequestBody SumAggregationByDate sumAggregationByDate) {
        return sumAggregationService.sumAggregationByDate(sumAggregationByDate);
    }

}
