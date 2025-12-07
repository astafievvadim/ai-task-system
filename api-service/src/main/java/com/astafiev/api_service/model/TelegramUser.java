package com.astafiev.api_service.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class TelegramUser {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private Long telegramId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private final LocalDateTime registeredAt = LocalDateTime.now();

    @OneToMany(mappedBy = "telegramUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    public TelegramUser() {
    }

    public TelegramUser(Long telegramId, String username, List<Task> tasks) {
        this.telegramId = telegramId;
        this.username = username;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
