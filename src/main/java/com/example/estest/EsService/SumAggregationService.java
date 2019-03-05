package com.example.estest.EsService;

import com.example.estest.entity.request.SumAggregationByDate;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Service;

@Service
public interface SumAggregationService {

    //统计一个时间段内某field的和
    SearchResponse sumAggregationByDate(SumAggregationByDate sumAggregationByDate);

}
