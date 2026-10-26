package com.yuldashov.planner.dto.task;

import com.yuldashov.planner.models.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse (
    Long id,
    String title,
    String description,
    TaskStatus status,
    LocalDateTime completedAt) {}
