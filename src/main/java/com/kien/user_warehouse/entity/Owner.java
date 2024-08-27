package com.kien.user_warehouse.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author kienvt
 */
@Data
public class Owner {

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Boolean, name = "isActive")
    private Boolean isActive;

}
