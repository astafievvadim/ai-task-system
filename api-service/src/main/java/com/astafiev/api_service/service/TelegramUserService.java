package com.astafiev.api_service.service;

import com.astafiev.api_service.model.TelegramUser;
import com.astafiev.api_service.payload.TelegramUserDto;
import com.astafiev.api_service.repo.TelegramUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserService {

    private final TelegramUserRepo telegramUserRepo;

    @Autowired
    public TelegramUserService(TelegramUserRepo telegramUserRepo) {
        this.telegramUserRepo = telegramUserRepo;
    }

    public void createTelegramUser(TelegramUserDto dto) {

        TelegramUser existingUser = telegramUserRepo.findByTelegramId(dto.getTelegramId());

        if (existingUser != null) {
            existingUser.setUsername(dto.getUsername());
            telegramUserRepo.save(existingUser);
            return;
        }

        TelegramUser user = new TelegramUser();
        user.setTelegramId(dto.getTelegramId());
        user.setUsername(dto.getUsername());
        telegramUserRepo.save(user);
    }

    public TelegramUser getTelegramUserById(Long telegramId){

        return telegramUserRepo.findByTelegramId(telegramId);

    }

    public Page<TelegramUser> getAllUsers(Pageable pageable){

        return telegramUserRepo.findAll(pageable);

    }

    public TelegramUserDto toDto(TelegramUser user){

        return new TelegramUserDto(
                user.getUsername(),
                user.getTelegramId()
        );

    }

    public TelegramUser fromDto(TelegramUserDto dto){

        return telegramUserRepo.findByTelegramId(dto.getTelegramId());

    }
}
