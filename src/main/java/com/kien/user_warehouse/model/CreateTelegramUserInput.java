package com.kien.user_warehouse.model;

import lombok.Data;

@Data
public class CreateTelegramUserInput {

    private String username;
    private String telegram_id;

}
