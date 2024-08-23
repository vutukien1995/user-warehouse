package com.kien.user_warehouse.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author kienvt
 */
@Data
@Document(indexName = "user")
public class User {

    @Id
    private String _id;

    @Field(name = "ID")
    private String ID;

    @Field(name = "firstname")
    private String firstname;

    @Field(name = "lastname")
    private String lastname;

    @Field(name = "middlename")
    private String middlename;

    @Field(name = "name_suff")
    private String namesuff;

    @Field(name = "dob")
    private String dob;

    @Field(name = "address")
    private String address;

}
