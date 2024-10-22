package com.kien.user_warehouse.repository;

import com.kien.user_warehouse.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransRepository extends JpaRepository<TelegramUser, Long> {
}