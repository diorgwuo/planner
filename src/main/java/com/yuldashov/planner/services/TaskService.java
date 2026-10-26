package com.yuldashov.planner.services;

import com.yuldashov.planner.dto.task.TaskRequest;
import com.yuldashov.planner.dto.task.TaskResponse;
import com.yuldashov.planner.dto.task.UpdateTaskRequest;
import com.yuldashov.planner.exceptions.TaskNotFoundException;
import com.yuldashov.planner.models.Task;
import com.yuldashov.planner.models.TaskStatus;
import com.yuldashov.planner.models.User;
import com.yuldashov.planner.repository.TaskRepository;
import com.yuldashov.planner.utils.ValidateAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<TaskResponse> getTasks(Authentication auth) {
        User user = ValidateAuth.validate(auth);
        Long userId = user.getId();
        List<TaskResponse> responses = new ArrayList<>();
        List<Task> tasks = taskRepository.findAllByUserId(userId);

        for (Task task : tasks) {
            TaskResponse response = new TaskResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getStatus(),
                    task.getCompletedAt()
            );
            responses.add(response);
        }
        return responses;
    }

    public TaskResponse createTask(Authentication auth, TaskRequest task) {
        User user = ValidateAuth.validate(auth);
        Task saveTask = new Task();
        saveTask.setTitle(task.title());
        saveTask.setDescription(task.description());
        saveTask.setStatus(TaskStatus.UNDONE);
        saveTask.setUser(user);

        Task savedTask = taskRepository.save(saveTask);

        return new TaskResponse(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus(),
                savedTask.getCompletedAt()
        );
    }

    @Transactional
    public TaskResponse updateTask(Authentication auth, UpdateTaskRequest request, Long taskId) {
        User user = ValidateAuth.validate(auth);
        Long userId = user.getId();
        Task task = taskRepository.findByIdAndUserId(taskId, userId).orElseThrow(
                () -> new TaskNotFoundException("Задача не найдена"));

        if (request.title()!=null) {
            task.setTitle(request.title());
        }
        if (request.description()!=null) {
            task.setDescription(request.description());
        }

        if (request.status() == TaskStatus.DONE) {
            task.setStatus(TaskStatus.DONE);
            if (task.getCompletedAt() == null) {
                task.setCompletedAt(LocalDateTime.now());
            }
        } else if (request.status() == TaskStatus.UNDONE) {
            task.setStatus(TaskStatus.UNDONE);
            task.setCompletedAt(null);
        }

        return new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCompletedAt()
        );
    }

    @Transactional
    public void deleteTask(Long id, Authentication auth) {
        User user = ValidateAuth.validate(auth);
        Long userId = user.getId();
        Task task = taskRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));
        taskRepository.delete(task);
    }


}
