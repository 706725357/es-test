package com.example.estest.entity.request;

import java.io.Serializable;
import java.util.List;

public class SumAggregationByDate implements Serializable {

    //索引名
    private String indexName;

    //类型
    private String  types="doc";

    //match条件
    private List<Matches> matches;

    //时间字段名
    private String colName;

    //起始时间
    private String sDate;

    //截止时间
    private String eDate;

    //要聚合的字段名
    private String docName;

    //聚合结果字段名
    private String resultName;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }


    public List<Matches> getMatches() {
        return matches;
    }

    public void setMatches(List<Matches> matches) {
        this.matches = matches;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }
}
