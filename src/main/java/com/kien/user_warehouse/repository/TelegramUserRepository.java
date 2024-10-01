package com.kien.user_warehouse.repository;

import com.kien.user_warehouse.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    List<TelegramUser> findByUsername(String username);

}
