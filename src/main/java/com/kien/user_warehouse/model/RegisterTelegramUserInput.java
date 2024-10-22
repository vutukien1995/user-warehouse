package com.kien.user_warehouse.model;

import lombok.Data;

@Data
public class RegisterTelegramUserInput {

    private String username;
    private String telegram_id;

}