package org.example.taskmanagerapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.taskmanagerapi.dto.TaskDTO;
import org.example.taskmanagerapi.mapper.TaskMapper;
import org.example.taskmanagerapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Get all tasks",
            description = "Gets all users tasks",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "204")
            }
    )
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getTasks(@AuthenticationPrincipal Jwt token) {
        int ownerId = Integer.parseInt(token.getSubject());
        var result = taskService.getAllTasks(ownerId);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get().stream().map(TaskMapper::toTaskDTO).collect(Collectors.toList()));
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(
            summary = "Get task by id",
            description = "Gets task with given id",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "204")
            }
    )
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable int id, @AuthenticationPrincipal Jwt token) {
        int ownerId = Integer.parseInt(token.getSubject());
        var result = taskService.getTask(id, ownerId);
        return result.isEmpty() ? ResponseEntity.noContent().build()
                              : ResponseEntity.ok(TaskMapper.toTaskDTO(result.get()));
    }


    @Operation(
            summary = "Create task",
            description = "Creates task with given title and description",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "400")
            }
    )
    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> addTask(@AuthenticationPrincipal Jwt token,
                                        @RequestParam String title,
                                        @RequestParam String description) {
        int ownerId = Integer.parseInt(token.getSubject());
        var result = taskService.addTask(ownerId, title, description);
        return result.isPresent() ? ResponseEntity.ok(TaskMapper.toTaskDTO(result.get()))
                : ResponseEntity.badRequest().build();
    }

    @Operation(
            summary = "Delete task by id",
            description = "Deletes task with given id, and returns result of operation",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "403", content = @Content(mediaType = "text/plain"))
            }
    )
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer taskId, @AuthenticationPrincipal Jwt token) {
        int ownerId = Integer.parseInt(token.getSubject());
        if (taskService.deleteTask(taskId, ownerId)) {
            return ResponseEntity.ok("Task has been deleted");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            summary = "Update task by id",
            description = "Updates task with given id, and returns updated task",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "403")
            }
    )
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Integer taskId,
                                           @RequestParam String title,
                                           @RequestParam String description,
                                           @RequestParam boolean completed,
                                              @AuthenticationPrincipal Jwt token) {
        int requesterId = Integer.parseInt(token.getSubject());
        var result = taskService.updateTask(taskId, title, description, completed, requesterId);
        if (result.isPresent()) {
            return ResponseEntity.ok(TaskMapper.toTaskDTO(result.get()));
        } else  {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}