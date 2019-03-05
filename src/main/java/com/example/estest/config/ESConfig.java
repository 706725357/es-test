package com.example.estest.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.*;

@SpringBootConfiguration
@ComponentScan(basePackageClasses=ESClientFactory.class)
@PropertySource(value = {"classpath:elasticsearch.properties"}, ignoreResourceNotFound = true)
public class ESConfig {

    @Value("${elasticsearch.host}")
    private static String host="118.25.52.191";

    @Value("${elasticsearch.port}")
    private static int port=9200;

    @Value("${elasticsearch.scheme}")
    private static String scheme="http";

    @Value("${elasticsearch.CONNECT_NUM}")
    private static int CONNECT_NUM=10;

    @Value("${elasticsearch.CONNECT_PER_ROUTE}")
    private static int CONNECT_PER_ROUTE=50;

    @Bean
    public HttpHost httpHost(){
        return new HttpHost(host,port,scheme);
    }

    @Bean(initMethod="init",destroyMethod="close")
    public ESClientFactory getFactory(){
        return ESClientFactory.
                build(httpHost(), CONNECT_NUM, CONNECT_PER_ROUTE);
    }

    @Bean
    @Scope("singleton")
    public RestClient getRestClient(){
        return getFactory().getClient();
    }

    @Bean
    @Scope("singleton")
    public RestHighLevelClient getRHLClient(){
        return getFactory().getRhlClient();
    }

}
