package com.yuldashov.planner.dto.task;

import com.yuldashov.planner.models.TaskStatus;

public record UpdateTaskRequest (
    String title,
    String description,
    TaskStatus status) {}
