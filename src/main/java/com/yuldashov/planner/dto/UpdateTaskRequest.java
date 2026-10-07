package com.yuldashov.planner.dto;

import com.yuldashov.planner.models.TaskStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class UpdateTaskRequest {
    private String title;
    private String description;
    private TaskStatus status;

    public UpdateTaskRequest(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public UpdateTaskRequest() {}

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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
