package com.kien.user_warehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Document(indexName = "user_alias")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private String _id;

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

    @Field(name = "ssn")
    private String ssn;

    @Override
    public String toString() {
//        return handleNull(ssn) + "," +
//                handleNull(firstname) + "," +
//                handleNull(lastname) + "," +
//                handleNull(middlename) + "," +
//                handleNull(namesuff) + "," +
//                handleNull(dob)+ "," +
//                handleNull(address) + "," +
//                handleNull(city) + "," +
//                handleNull(countyname) + "," +
//                handleNull(st) + "," +
//                handleNull(zip) + ",";

        StringBuilder str = new StringBuilder();
        str.append(" First Name: ").append(handleNull(firstname)).append("\r\n");
        str.append(" Middle Name: ").append(handleNull(middlename)).append("\r\n");
        str.append(" Last Name: ").append(handleNull(lastname)).append("\r\n");
        str.append(" Full Name: ").append(handleNull(firstname)).append(" ")
                        .append(handleNull(middlename)).append(" ")
                        .append(handleNull(lastname)).append(" ").append("\r\n");
        str.append(" Date Of Birth: ").append(handleNull(dob)).append("\r\n");
        str.append(" Social Security Number: ").append(handleNull(ssn)).append("\r\n");
        str.append(" Addresses: ").append("\r\n");
        str.append("  No. 1 ").append("\r\n");
        str.append("   Full Address: ").append(handleNull(address)).append("\r\n");
        str.append("   City: ").append(handleNull(city)).append("\r\n");
        str.append("   State Code: ").append(handleNull(st)).append("\r\n");
        str.append("   Zip Code: ").append(handleNull(zip)).append("\r\n");

        return str.toString();
    }

    private String handleNull(String str) {
        return (str == null) ? "" : str;
    }
}
