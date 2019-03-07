package com.example.estest.annotation;

import com.example.estest.enums.EnumFieldTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
//映射关系注解
public @interface EsMappings {

    String objectName() default "";

    String fieldName() default "type";

    EnumFieldTypes fieldType() default EnumFieldTypes.TEXT;
}
