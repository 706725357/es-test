package com.example.estest.enums;

public enum EnumFieldTypes {

    DEFAULT("default"),
    NESTED("nested"),
    INTEGER("integer"),
    GEO_POINT("geo_point"),
    KEYWORD("keyword"),
    TEXT("text");

    private String value;

    EnumFieldTypes(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
