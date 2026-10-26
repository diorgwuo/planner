package com.yuldashov.planner.dto.task;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest (
    @NotBlank(message = "Заголовок не может быть пустым")
    String title,
    @NotBlank(message = "Описание не может быть пустым")
    String description) {}
