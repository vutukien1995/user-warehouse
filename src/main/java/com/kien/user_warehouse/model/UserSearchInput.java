package com.kien.user_warehouse.model;

import lombok.Data;

/**
 * @author kienvt
 */
@Data
public class UserSearchInput {

    private String firstname;
    private String lastname;
    private String address;
    private String dob;
    private String zipcode;
    private Integer page;
    private Integer size;

}
