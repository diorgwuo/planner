package com.yuldashov.planner.dto;

import com.yuldashov.planner.models.TaskStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class TaskRequest {
    @NotBlank(message = "Заголовок не может быть пустым")
    private String title;
    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    public TaskRequest(String description, String title) {
        this.description = description;
        this.title = title;
    }

    public TaskRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
