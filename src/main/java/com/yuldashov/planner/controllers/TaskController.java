package com.yuldashov.planner.controllers;

import com.yuldashov.planner.dto.TaskRequest;
import com.yuldashov.planner.dto.TaskResponse;
import com.yuldashov.planner.dto.UpdateTaskRequest;
import com.yuldashov.planner.models.User;
import com.yuldashov.planner.repository.UserRepository;
import com.yuldashov.planner.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final UserRepository userRepository;
    private final TaskService taskService;


    public TaskController(UserRepository userRepository, TaskService taskService) {
        this.userRepository = userRepository;
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getTasks(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Такой username не найден"));
        Long userId = user.getId();
        List<TaskResponse> tasks = taskService.getTasks(userId);

        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(Authentication authentication,
                                        @Valid @RequestBody TaskRequest task) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Такой username не найден"));
        TaskResponse response = taskService.createTask(user, task);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, Authentication authentication,
                                        @Valid @RequestBody UpdateTaskRequest updateTaskRequest) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Такой username не найден"));
        Long userId = user.getId();
        TaskResponse response = taskService.updateTask(userId, updateTaskRequest, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Такой username не найден"));
        Long userId = user.getId();
        taskService.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }

}
