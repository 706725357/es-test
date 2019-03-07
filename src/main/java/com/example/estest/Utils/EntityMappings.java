package com.example.estest.Utils;

import com.example.estest.annotation.EsMappings;
import com.example.estest.enums.EnumFieldTypes;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.lang.reflect.Field;

public class EntityMappings {

    //生成Mapping
    public static XContentBuilder generateMappingBuilder(Class<?> clazz) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        Field[] fields = clazz.getDeclaredFields();
        builder.startObject();
        {
            builder.startObject("_doc");
            {
                builder.startObject("properties");
                {
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(EsMappings.class)) {
                            EsMappings declaredAnnotation = field.getDeclaredAnnotation(EsMappings.class);
                            String fieldName = declaredAnnotation.fieldName();
                            String objectName = declaredAnnotation.objectName();
                            String fieldType = declaredAnnotation.fieldType().getValue();
                            //map.put("fieldNames", field.getName());
                            //map.put("fieldName", fieldName);
                            //map.put("objectName", objectName);
                            //map.put("fieldType", fieldType);
                            builder.startObject(objectName);
                            {
                                /*if (fieldType.equals(EnumFieldTypes.DEFAULT.getValue())){
                                    fieldName="";
                                }*/
                                builder.field(fieldName, fieldType);
                            }
                            builder.endObject();
                        }
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            return builder;
        }
    }

}
