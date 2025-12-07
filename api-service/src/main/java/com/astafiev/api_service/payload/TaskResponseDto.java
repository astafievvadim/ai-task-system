package com.astafiev.api_service.payload;

import io.swagger.v3.oas.annotations.media.Schema;

public class TaskResponseDto {
    @Schema(description = "Telegram user ID. The hidden one", example = "123")
    private Long userId;
    @Schema(description = "Contents of a task", example = "Be happy or just buy milk I guess")
    private String text;
    @Schema(description = "Value that python service assigned to this task", example = "21")
    private int evaluation;

    public TaskResponseDto(Long userId, String text, int evaluation) {
        this.userId = userId;
        this.text = text;
        this.evaluation = evaluation;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }
}
