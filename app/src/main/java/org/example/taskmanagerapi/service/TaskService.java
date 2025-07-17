package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks(int ownerId);
    Task addTask(int ownerId, String title, String description);
    boolean deleteTask(int taskId, int requesterId);
    Task updateTask(int taskId, String title, String description, boolean completed, int requesterId);
    Task getTask(int taskId, int requesterId);
}
