package com.kien.user_warehouse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "telegram_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String telegramId;
    private Integer balance;
    private String chatId;
    private Date createdDate;

}
