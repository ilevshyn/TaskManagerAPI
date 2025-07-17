package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Optional<List<Task>> getAllTasks(int ownerId);
    Optional<Task> addTask(int ownerId, String title, String description);
    boolean deleteTask(int taskId, int requesterId);
    Optional<Task> updateTask(int taskId, String title, String description, boolean completed, int requesterId);
    Optional<Task> getTask(int taskId, int requesterId);
}
