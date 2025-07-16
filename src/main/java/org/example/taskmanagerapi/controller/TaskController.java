package org.example.taskmanagerapi.controller;

import org.example.taskmanagerapi.model.Task;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.example.taskmanagerapi.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    TaskRepository taskRepository;
    AppUserRepository appUserRepository;


    public TaskController(TaskRepository taskRepository, AppUserRepository appUserRepository) {
        this.taskRepository = taskRepository;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks(@AuthenticationPrincipal Jwt token) {
        int ownerId = Integer.parseInt(token.getSubject());
        System.out.println("owner id: " + token.getSubject());
        System.out.println("token: " + token);
        if (taskRepository.findByOwnerId(ownerId).isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(taskRepository.findByOwnerId(ownerId));
        }
    }


    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@RequestParam Integer ownerId,
                                        @RequestParam String title,
                                        @RequestParam String description) {
        if(appUserRepository.findById(ownerId).isPresent()) {
            Task task = new Task(title,
                    description,
                    appUserRepository.findById(ownerId).get(),
                    false);
            return ResponseEntity.ok(taskRepository.save(task));
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Integer id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id,
                                           @RequestParam String title,
                                           @RequestParam String description,
                                           @RequestParam boolean completed) {
        if (taskRepository.findById(id).isPresent()) {
            Task task = taskRepository.findById(id).get();
            task.setTitle(title);
            task.setDescription(description);
            task.setCompleted(completed);
            return ResponseEntity.ok(taskRepository.save(task));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}