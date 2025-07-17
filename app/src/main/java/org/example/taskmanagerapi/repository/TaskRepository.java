package org.example.taskmanagerapi.repository;

import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByOwnerId(int ownerId);
}


