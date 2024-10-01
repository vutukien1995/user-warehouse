package com.kien.user_warehouse.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.Date;

@Data
@Entity
@Table(name = "telegram_user")
public class Trans {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer amount;
    private String username;
    private String type;
    private Date createdDate;

}
