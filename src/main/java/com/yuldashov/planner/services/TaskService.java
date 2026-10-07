package com.yuldashov.planner.services;

import com.yuldashov.planner.dto.TaskRequest;
import com.yuldashov.planner.dto.TaskResponse;
import com.yuldashov.planner.dto.UpdateTaskRequest;
import com.yuldashov.planner.exceptions.TaskNotFoundException;
import com.yuldashov.planner.models.Task;
import com.yuldashov.planner.models.TaskStatus;
import com.yuldashov.planner.models.User;
import com.yuldashov.planner.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> getTasks(Long userId) {
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

    public TaskResponse createTask(User user, TaskRequest task) {
        Task saveTask = new Task();
        saveTask.setTitle(task.getTitle());
        saveTask.setDescription(task.getDescription());
        saveTask.setStatus(TaskStatus.UNDONE);
        saveTask.setUser(user);

        Task savedTask = taskRepository.save(saveTask);
        TaskResponse response = new TaskResponse(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus(),
                savedTask.getCompletedAt()
        );

        return response;
    }

    @Transactional
    public TaskResponse updateTask(Long userId, UpdateTaskRequest request, Long taskId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId).orElseThrow(
                () -> new TaskNotFoundException("Задача не найдена"));

        if (request.getTitle()!=null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription()!=null) {
            task.setDescription(request.getDescription());
        }

        if (request.getStatus() == TaskStatus.DONE) {
            task.setStatus(TaskStatus.DONE);
            if (task.getCompletedAt() == null) {
                task.setCompletedAt(LocalDateTime.now());
            }
        } else if (request.getStatus() == TaskStatus.UNDONE) {
            task.setStatus(TaskStatus.UNDONE);
            task.setCompletedAt(null);
        }
        TaskResponse newTask = new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCompletedAt()
        );

        return newTask;
    }

    @Transactional
    public void deleteTask(Long id, Long userId) {
        Task task = taskRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));
        taskRepository.delete(task);
    }


}
