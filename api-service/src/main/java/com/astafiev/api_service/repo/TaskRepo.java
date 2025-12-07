package com.astafiev.api_service.repo;

import com.astafiev.api_service.model.Task;
import com.astafiev.api_service.model.TelegramUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
    Page<Task> findAllByTelegramUser(TelegramUser user, Pageable pageable);
}
