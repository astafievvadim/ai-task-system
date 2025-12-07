package com.astafiev.api_service.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class TaskRequestDto {

    @Schema(description = "Telegram user ID", example = "123")
    private Long userId;

    @Schema(description = "Task text", example = "Be happy or just buy milk I guess")
    private String text;

    @Schema(description = "Due date", example = "2025-12-10T17:00")
    private LocalDateTime dueDate;

    public TaskRequestDto(Long userId, String text, LocalDateTime dueDate) {
        this.userId = userId;
        this.text = text;
        this.dueDate = dueDate;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
}
