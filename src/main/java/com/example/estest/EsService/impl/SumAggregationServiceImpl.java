package com.example.estest.EsService.impl;

import com.example.estest.EsService.SumAggregationService;
import com.example.estest.entity.request.Matches;
import com.example.estest.entity.request.SumAggregationByDate;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SumAggregationServiceImpl implements SumAggregationService {

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public SumAggregationServiceImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    //统计一个时间段内某field的和(单match条件)
    @Override
    public SearchResponse sumAggregationByDate(SumAggregationByDate sumAggregationByDate) {
        SearchResponse searchResponse = null;
        //索引名
        String indexName = sumAggregationByDate.getIndexName();
        //类型
        String types = sumAggregationByDate.getTypes();
        //match条件
        List<Matches> matchsList = sumAggregationByDate.getMatches();
        //时间字段名
        String colName = sumAggregationByDate.getColName();
        //起始时间
        String sDate = sumAggregationByDate.getsDate();
        //截止时间
        String eDate = sumAggregationByDate.geteDate();
        //要聚合的字段名
        String docName = sumAggregationByDate.getDocName();
        //聚合结果字段名
        String resultName = sumAggregationByDate.getResultName();

        //指定文档
        SearchRequest request = new SearchRequest(indexName).types(types);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //条件池
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //如果还需要指定某个状态,循环遍历导入条件
        if (!matchsList.isEmpty()) {
            for (Matches matches : matchsList) {
                //match条件名
                String matchName = matches.getMatchName();
                //match条件值
                String matchValue = matches.getMatchValue();
                MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(matchName, matchValue);
                //加入条件池
                boolQueryBuilder.must(matchQueryBuilder);
            }
        }
        //指定范围(注意时间格式)
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(colName)
                .from(sDate)
                .to(eDate);
             /*   .from("2017-05-16T21:57:09")
                .to("2019-05-16T21:57:09");*/
        //加入搜索条件池
        boolQueryBuilder.must(rangeQueryBuilder);
        //指定聚合
        SumAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum(docName).field(resultName);
        try {
            //搜索
            searchSourceBuilder.query(boolQueryBuilder).aggregation(sumAggregationBuilder);
            request.source(searchSourceBuilder);
            searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchResponse;
    }
}
