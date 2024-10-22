package com.kien.user_warehouse.controller;

import com.kien.user_warehouse.model.RegisterTelegramUserInput;
import com.kien.user_warehouse.model.DepositInput;
import com.kien.user_warehouse.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telegram_user")
public class TelegramUserApi {

    @Autowired
    TelegramUserService telegramUserService;

    @GetMapping("/hello")
    public String hello () {
        return "telegram user hello !";
    }

    @GetMapping("")
    public Object get () {
        return telegramUserService.getAll();
    }

    @PostMapping("/register")
    public Object register(@RequestBody RegisterTelegramUserInput input) {
        return telegramUserService.register(input);
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