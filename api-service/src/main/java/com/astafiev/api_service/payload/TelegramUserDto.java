package com.astafiev.api_service.payload;

import io.swagger.v3.oas.annotations.media.Schema;

public class TelegramUserDto {


    @Schema(description = "Telegram user ID. The hidden one", example = "123")
    private Long telegramId;
    @Schema(description = "Telegram username", example = "@astafiev_vdm")
    private String username;

    public TelegramUserDto(String username, Long telegramId) {
        this.username = username;
        this.telegramId = telegramId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }
}
