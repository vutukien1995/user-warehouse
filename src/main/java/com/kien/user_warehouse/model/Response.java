package com.kien.user_warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kienvt
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private Boolean success;
    private Integer total;
    private T data;

}
