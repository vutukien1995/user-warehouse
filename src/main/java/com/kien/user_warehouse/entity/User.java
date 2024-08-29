package com.kien.user_warehouse.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

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

    @Field(name = "city")
    private String city;

    @Field(name = "county_name")
    private String countyname;

    @Field(name = "st")
    private String st;

    @Field(name = "zip")
    private String zip;

    @Field(name = "phone1")
    private String phone1;

    @Field(name = "aka1fullname")
    private String aka1fullname;

    @Field(name = "aka2fullname")
    private String aka2fullname;

    @Field(name = "aka3fullname")
    private String aka3fullname;

    @Field(name = "StartDat")
    private String StartDat;

    @Field(name = "alt1DOB")
    private String alt1DOB;

    @Field(name = "alt2DOB")
    private String alt2DOB;

    @Field(name = "alt3DOB")
    private String alt3DOB;

    @Field(name = "ssn")
    private String ssn;

    @Override
    public String toString() {
        return handleNull(ID) + "," +
                handleNull(firstname) + "," +
                handleNull(lastname) + "," +
                handleNull(middlename) + "," +
                handleNull(namesuff) + "," +
                handleNull(dob)+ "," +
                handleNull(address) + "," +
                handleNull(city) + "," +
                handleNull(countyname) + "," +
                handleNull(st) + "," +
                handleNull(zip) + "," +
                handleNull(phone1) + "," +
                handleNull(aka1fullname) + "," +
                handleNull(aka2fullname) + "," +
                handleNull(aka3fullname) + "," +
                handleNull(StartDat) + "," +
                handleNull(alt1DOB) + "," +
                handleNull(alt2DOB) + "," +
                handleNull(alt3DOB) + "," +
                handleNull(ssn) + ",";
    }

    private String handleNull(String str) {
        return (str == null) ? "" : str;
    }
}
