package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks(int ownerId);
    Task addTask(int ownerId, String title, String description);
    void deleteTask(int taskId);
    Task updateTask(int taskId, String title, String description, boolean completed);
}
