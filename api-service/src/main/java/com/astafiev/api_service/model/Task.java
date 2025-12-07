package com.astafiev.api_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "telegram_user_id")
    private TelegramUser telegramUser;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private int evaluation;

    @Column(nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean isComplete = false;

    @Column
    private LocalDateTime dueDate;

    public Task() {}

    public Task(TelegramUser telegramUser, String text, int evaluation, LocalDateTime updatedAt, Boolean isComplete, LocalDateTime dueDate) {
        this.telegramUser = telegramUser;
        this.text = text;
        this.evaluation = evaluation;
        this.updatedAt = updatedAt;
        this.isComplete = isComplete;
        this.dueDate = dueDate;
    }

    public Long getId() { return id; }
    public TelegramUser getTelegramUser() { return telegramUser; }
    public void setTelegramUser(TelegramUser telegramUser) { this.telegramUser = telegramUser; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public int getEvaluation() { return evaluation; }
    public void setEvaluation(int evaluation) { this.evaluation = evaluation; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public Boolean getIsComplete() { return isComplete; }
    public void setIsComplete(Boolean isComplete) { this.isComplete = isComplete; }
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }


}
