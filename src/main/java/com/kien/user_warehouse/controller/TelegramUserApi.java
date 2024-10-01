package com.kien.user_warehouse.controller;

import com.kien.user_warehouse.model.CreateTelegramUserInput;
import com.kien.user_warehouse.model.DepositInput;
import com.kien.user_warehouse.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("telegram_user")
@RequiredArgsConstructor
public class TelegramUserApi {

    @Autowired
    TelegramUserService telegramUserService;

    @GetMapping("hello")
    public String hello () {
        return "telegram user hello !";
    }

    @PostMapping("")
    public Object create(@RequestBody CreateTelegramUserInput input) {
        return telegramUserService.create(input);
    }

    @PostMapping("/deposit")
    public Object deposit(@RequestBody DepositInput input) {
        return telegramUserService.deposit(input);
    }

    @GetMapping("/balance")
    public Object balance(@RequestParam String username) {
        return telegramUserService.balance(username);
    }

}
