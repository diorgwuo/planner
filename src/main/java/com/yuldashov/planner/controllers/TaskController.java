package com.yuldashov.planner.controllers;

import com.yuldashov.planner.dto.task.TaskRequest;
import com.yuldashov.planner.dto.task.TaskResponse;
import com.yuldashov.planner.dto.task.UpdateTaskRequest;
import com.yuldashov.planner.repository.UserRepository;
import com.yuldashov.planner.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponse> getTasks(Authentication auth) {
        return taskService.getTasks(auth);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(Authentication auth,
                                        @Valid @RequestBody TaskRequest task) {
        return taskService.createTask(auth, task);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse updateTask(@PathVariable Long id, Authentication auth,
                                        @Valid @RequestBody UpdateTaskRequest updateTaskRequest) {
        return taskService.updateTask(auth, updateTaskRequest, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id, Authentication auth) {
        taskService.deleteTask(id, auth);
    }
}
