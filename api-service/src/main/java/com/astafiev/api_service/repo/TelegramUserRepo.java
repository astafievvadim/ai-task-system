package com.astafiev.api_service.repo;

import com.astafiev.api_service.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserRepo extends JpaRepository<TelegramUser,Long> {
    TelegramUser findByTelegramId(Long telegramId);
}
