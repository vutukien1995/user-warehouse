package com.kien.user_warehouse.service;

import com.kien.user_warehouse.entity.TelegramUser;
import com.kien.user_warehouse.model.CreateTelegramUserInput;
import com.kien.user_warehouse.model.DepositInput;
import com.kien.user_warehouse.repository.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class TelegramUserService {

    @Autowired
    TelegramUserRepository telegramUserRepository;

    public Object create(CreateTelegramUserInput input) {
        List<TelegramUser> telegramUsers = telegramUserRepository.findByUsername(input.getUsername());

        if (CollectionUtils.isEmpty(telegramUsers)) {
            TelegramUser telegramUser = TelegramUser.builder()
                    .username(input.getUsername())
                    .telegramId(input.getTelegram_id())
                    .balance(0)
                    .createdDate(new Date())
                    .build();
            return new ResponseEntity<>(telegramUserRepository.save(telegramUser), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(telegramUsers.get(0), HttpStatus.OK);
    }

    public Object deposit(DepositInput input) {
        List<TelegramUser> telegramUsers = telegramUserRepository.findByUsername(input.getUsername());

        if (CollectionUtils.isEmpty(telegramUsers))
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);

        TelegramUser telegramUser = telegramUsers.get(0);
        telegramUser.setBalance(telegramUser.getBalance() + input.getAmount());
        telegramUserRepository.save(telegramUser);

        return new ResponseEntity<>(telegramUser, HttpStatus.OK);
    }

    public Object balance(String username) {
        List<TelegramUser> telegramUsers = telegramUserRepository.findByUsername(username);

        if (CollectionUtils.isEmpty(telegramUsers))
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(telegramUsers.get(0), HttpStatus.OK);
    }

}
