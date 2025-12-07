package com.astafiev.api_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PythonService {

    private final WebClient webClient;

    public PythonService(@Value("${ai.service.url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public int evaluateTask(String taskText, Long userId, LocalDateTime dueDate) {
        Map<String, Object> request = Map.of(
                "task_text", taskText,
                "user_id", userId,
                "deadline", dueDate != null ? dueDate.toString() : null
        );

        String response = webClient.post()
                .uri("/evaluate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .map(m -> m.get("evaluation").toString())
                .block();

        try {
            return Integer.parseInt(response);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
