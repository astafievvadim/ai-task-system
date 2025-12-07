package com.astafiev.api_service.service;

import com.astafiev.api_service.exception.TaskCreationException;
import com.astafiev.api_service.model.Task;
import com.astafiev.api_service.model.TelegramUser;
import com.astafiev.api_service.payload.TaskRequestDto;
import com.astafiev.api_service.payload.TaskResponseDto;
import com.astafiev.api_service.repo.TaskRepo;
import com.astafiev.api_service.repo.TelegramUserRepo;
import com.astafiev.api_service.util.TaskCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {

    private final TaskRepo taskRepo;
    private final TelegramUserRepo telegramUserRepo;
    private final PythonService pythonService;
    private final String encryptionKey;

    @Autowired
    public TaskService(TaskRepo taskRepo,
                       TelegramUserRepo telegramUserRepo,
                       PythonService pythonService,
                       @Value("${task.encryption.key}") String encryptionKey) {
        this.taskRepo = taskRepo;
        this.telegramUserRepo = telegramUserRepo;
        this.pythonService = pythonService;
        this.encryptionKey = encryptionKey;
    }

    public Page<Task> getAllTasksByUser(TelegramUser user, Pageable pageable) {
        return taskRepo.findAllByTelegramUser(user, pageable);
    }

    public TaskResponseDto createTask(TaskRequestDto r) throws TaskCreationException {
        String encryptedText;
        try {
            encryptedText = TaskCryptUtil.encrypt(encryptionKey, r.getText());
        } catch (Exception e) {
            throw new TaskCreationException("Failed to encrypt task text", e);
        }

        int evaluation;
        try {
            evaluation = pythonService.evaluateTask(r.getText(), r.getUserId(), r.getDueDate());
        } catch (Exception e) {
            throw new TaskCreationException("AI evaluation failed", e);
        }

        TelegramUser telegramUser = telegramUserRepo.findByTelegramId(r.getUserId());
        if (telegramUser == null) {
            throw new TaskCreationException("Telegram user not found: " + r.getUserId());
        }

        Task task = new Task();
        task.setTelegramUser(telegramUser);
        task.setText(encryptedText);
        task.setEvaluation(evaluation);
        task.setDueDate(r.getDueDate());
        task.setIsComplete(false);
        task.setUpdatedAt(LocalDateTime.now());

        task = taskRepo.save(task);

        return new TaskResponseDto(r.getUserId(), r.getText(), task.getEvaluation());
    }

    public Task markTaskComplete(Long taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));
        task.setIsComplete(true);
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepo.save(task);
    }
}


