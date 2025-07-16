package org.example.taskmanagerapi.controller;

import org.example.taskmanagerapi.model.Task;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.example.taskmanagerapi.repository.TaskRepository;
import org.example.taskmanagerapi.service.TaskService;
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
    public ResponseEntity<List<Task>> getTasks(@AuthenticationPrincipal Jwt token) {
        int ownerId = Integer.parseInt(token.getSubject());
        return ResponseEntity.ok(taskService.getAllTasks(ownerId));
    }


    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@AuthenticationPrincipal Jwt token,
                                        @RequestParam String title,
                                        @RequestParam String description) {
        int ownerId = Integer.parseInt(token.getSubject());
        return ResponseEntity.ok(taskService.addTask(ownerId, title, description));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Deleted task with id: " + taskId);
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer taskId,
                                           @RequestParam String title,
                                           @RequestParam String description,
                                           @RequestParam boolean completed) {
        return ResponseEntity.ok(taskService.updateTask(taskId, title, description, completed));
    }
}