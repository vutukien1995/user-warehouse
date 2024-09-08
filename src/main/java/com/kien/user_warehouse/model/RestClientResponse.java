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
public class RestClientResponse {

    private Integer status;
    private String body;

}
