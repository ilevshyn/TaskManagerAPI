package org.example.taskmanagerapi.controller;

import org.example.taskmanagerapi.dto.TaskDTO;
import org.example.taskmanagerapi.mapper.TaskMapper;
import org.example.taskmanagerapi.model.Task;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.example.taskmanagerapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    AppUserRepository appUserRepository;
    TaskService taskService;

    public TaskController(AppUserRepository appUserRepository, TaskService taskService) {
        this.appUserRepository = appUserRepository;
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getTasks(@AuthenticationPrincipal Jwt token) {
        int ownerId = Integer.parseInt(token.getSubject());
        return ResponseEntity.ok(taskService.getAllTasks(ownerId).stream().map(TaskMapper::toTaskDTO).toList());
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable int id, @AuthenticationPrincipal Jwt token) {
        int ownerId = Integer.parseInt(token.getSubject());
        Task result = taskService.getTask(id, ownerId);
        return result == null ? ResponseEntity.noContent().build()
                              : ResponseEntity.ok(TaskMapper.toTaskDTO(result));
    }


    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> addTask(@AuthenticationPrincipal Jwt token,
                                        @RequestParam String title,
                                        @RequestParam String description) {
        int ownerId = Integer.parseInt(token.getSubject());
        return ResponseEntity.ok(TaskMapper.toTaskDTO(taskService.addTask(ownerId, title, description)));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer taskId, @AuthenticationPrincipal Jwt token) {
        int ownerId = Integer.parseInt(token.getSubject());
        if (taskService.deleteTask(taskId, ownerId)) {
            return ResponseEntity.ok("Task has been deleted");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Integer taskId,
                                           @RequestParam String title,
                                           @RequestParam String description,
                                           @RequestParam boolean completed,
                                              @AuthenticationPrincipal Jwt token) {
        int requesterId = Integer.parseInt(token.getSubject());
        Task result = taskService.updateTask(taskId, title, description, completed, requesterId);
        if (result != null) {
            return ResponseEntity.ok(TaskMapper.toTaskDTO(result));
        } else  {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}