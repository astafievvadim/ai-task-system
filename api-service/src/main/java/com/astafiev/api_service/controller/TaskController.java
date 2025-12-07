package com.astafiev.api_service.controller;

import com.astafiev.api_service.exception.TaskCreationException;
import com.astafiev.api_service.model.Task;
import com.astafiev.api_service.model.TelegramUser;
import com.astafiev.api_service.payload.TaskRequestDto;
import com.astafiev.api_service.payload.TaskResponseDto;
import com.astafiev.api_service.service.TaskService;
import com.astafiev.api_service.service.TelegramUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@Tag(name="Tasks", description = "Task management API")
public class TaskController {

    private final TaskService taskService;
    private final TelegramUserService telegramUserService;

    @Autowired
    public TaskController(TaskService taskService, TelegramUserService telegramUserService) {
        this.taskService = taskService;
        this.telegramUserService = telegramUserService;
    }

    @PostMapping
    @Operation(summary = "Create a new task")
    public TaskResponseDto createTask(@RequestBody TaskRequestDto r) throws TaskCreationException {
        return taskService.createTask(r);
    }

    @GetMapping("/users/{telegramId}/tasks")
    @Operation(summary = "Get all tasks for a telegram user (pagination, by evaluation)")
    public Page<TaskResponseDto> getAllTasksByUser(
            @PathVariable Long telegramId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("evaluation").descending());
        TelegramUser user = telegramUserService.getTelegramUserById(telegramId);
        if (user == null) throw new IllegalArgumentException("User not found with telegramId: " + telegramId);
        return taskService.getAllTasksByUser(user, pageable)
                .map(task -> new TaskResponseDto(
                        task.getTelegramUser().getTelegramId(),
                        task.getText(),
                        task.getEvaluation()
                ));
    }

    @PostMapping("/{taskId}/complete")
    @Operation(summary = "Mark a task as complete")
    public TaskResponseDto markTaskComplete(@PathVariable Long taskId) {
        Task task = taskService.markTaskComplete(taskId);
        return new TaskResponseDto(
                task.getTelegramUser().getTelegramId(),
                task.getText(),
                task.getEvaluation()
        );
    }
}

