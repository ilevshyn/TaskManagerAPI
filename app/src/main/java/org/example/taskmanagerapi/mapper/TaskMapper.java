package org.example.taskmanagerapi.mapper;

import org.example.taskmanagerapi.dto.TaskDTO;
import org.example.taskmanagerapi.model.Task;

public class TaskMapper {
    public static TaskDTO toTaskDTO(Task task) {
        return new TaskDTO(task.getId(),task.getTitle(), task.getDescription(), task.isCompleted());
    }
}
