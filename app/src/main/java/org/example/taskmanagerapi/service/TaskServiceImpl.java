package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.Task;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.example.taskmanagerapi.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;

    public TaskServiceImpl(AppUserRepository appUserRepository, TaskRepository taskRepository) {
        this.appUserRepository = appUserRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks(int ownerId) {
        return taskRepository.findByOwnerId(ownerId);
    }

    @Override
    public Task addTask(int ownerId, String title, String description) {
        Task newTask = new Task(title, description, appUserRepository.findById(ownerId), false);
        return taskRepository.save(newTask);
    }

    @Override
    public boolean deleteTask(int taskId, int requesterId) {
        Task result = taskRepository.findById(taskId).orElse(null);
        if (result != null && result.getOwner().getId() == requesterId) {
            taskRepository.delete(result);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Task updateTask(int taskId, String title, String description, boolean completed, int requesterId) {
        Task result = taskRepository.findById(taskId).orElse(null);
        if (result != null && result.getOwner().getId() == requesterId) {
            result.setTitle(title);
            result.setDescription(description);
            result.setCompleted(completed);
            return taskRepository.save(result);
        } else  {
            return null;
        }
    }

    @Override
    public Task getTask(int taskId, int requesterId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null && task.getOwner().getId() == requesterId) {
            return task;
        } else {
            return null;
        }
    }
}
