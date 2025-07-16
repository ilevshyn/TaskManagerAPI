package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.Task;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.example.taskmanagerapi.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    AppUserRepository appUserRepository;

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
    public void deleteTask(int taskId) {
        taskRepository.findById(taskId).ifPresent(task -> taskRepository.delete(task));
    }

    @Override
    public Task updateTask(int taskId, String title, String description, boolean completed) {
        try {
            var tempTask = taskRepository.findById(taskId);
            if (tempTask.isPresent()) {
                tempTask.get().setTitle(title);
                tempTask.get().setDescription(description);
                tempTask.get().setCompleted(completed);
                return taskRepository.save(tempTask.get());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
