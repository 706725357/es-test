package com.example.estest;

import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ClientTest {

    @Autowired
    private static RestHighLevelClient restHighLevelClient;

    public static void main(String[] args) {
        GetIndexRequest request = new GetIndexRequest().indices("house_space");
        request.includeDefaults(true);
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        try {
            GetIndexResponse getIndexResponse=restHighLevelClient.indices().get(request,RequestOptions.DEFAULT);
            System.out.println(getIndexResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
