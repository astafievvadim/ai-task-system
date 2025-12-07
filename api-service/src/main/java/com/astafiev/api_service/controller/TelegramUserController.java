package com.astafiev.api_service.controller;

import com.astafiev.api_service.model.TelegramUser;
import com.astafiev.api_service.payload.TelegramUserDto;
import com.astafiev.api_service.service.TelegramUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Telegram users", description = "Endpoints for managing Telegram users")
public class TelegramUserController {

    private final TelegramUserService telegramUserService;

    @Autowired
    public TelegramUserController(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    @PostMapping
    @Operation(summary = "Create a new telegram user")
    public ResponseEntity<String> createUser(@RequestBody TelegramUserDto dto) {
        telegramUserService.createTelegramUser(dto);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{telegramId}")
    @Operation(summary = "Get a telegram user by their telegram ID")
    public ResponseEntity<TelegramUserDto> getUser(@PathVariable Long telegramId) {
        TelegramUser user = telegramUserService.getTelegramUserById(telegramId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(telegramUserService.toDto(user));
    }

    @Operation(summary = "Get all Telegram users (pagination)")
    @GetMapping
    public Page<TelegramUserDto> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return telegramUserService.getAllUsers(pageable)
                .map(telegramUserService::toDto);
    }
}
