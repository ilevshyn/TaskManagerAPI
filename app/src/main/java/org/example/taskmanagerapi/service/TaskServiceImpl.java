package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.Task;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.example.taskmanagerapi.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;

    public TaskServiceImpl(AppUserRepository appUserRepository, TaskRepository taskRepository) {
        this.appUserRepository = appUserRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<List<Task>> getAllTasks(int ownerId) {
        return taskRepository.findByOwnerId(ownerId);
    }

    @Override
    public Optional<Task> addTask(int ownerId, String title, String description) {
        Task newTask = new Task(title, description, appUserRepository.findById(ownerId).get(), false);
        return Optional.of(taskRepository.save(newTask));
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
    public Optional<Task> updateTask(int taskId, String title, String description, boolean completed, int requesterId) {
        Task result = taskRepository.findById(taskId).orElse(null);
        if (result != null && result.getOwner().getId() == requesterId) {
            result.setTitle(title);
            result.setDescription(description);
            result.setCompleted(completed);
            return Optional.of(taskRepository.save(result));
        } else  {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Task> getTask(int taskId, int requesterId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null && task.getOwner().getId() == requesterId) {
            return Optional.of(task);
        } else {
            return Optional.empty();
        }
    }
}
